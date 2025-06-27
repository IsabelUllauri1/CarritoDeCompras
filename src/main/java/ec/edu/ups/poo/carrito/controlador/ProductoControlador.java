package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoControlador {

    private final ProductoDAO productoDAO;
    private Principal principal;
    private  AnadirProductosView vistaAnadir;
    private  ProductoListarView  vistaListar;
    private CarritoAnadirView vistaCarrito;
    private CarritoDAO carritoDAO;

    public ProductoControlador(ProductoDAO dao, Principal principal, AnadirProductosView vAnadir, ProductoListarView vListar) {
        this.productoDAO = dao;
        this.principal =  principal;
        this.vistaAnadir = vAnadir;
        this.vistaListar = vListar;
        configurarEventosAñadirYListar();
        configurarMenu();
    }

    private void buscarProductoPorCodigo() {
        int codigo = Integer.parseInt(vistaCarrito.getTxtCodigo().getText());
        Producto p = productoDAO.buscarPorCodigo(codigo);
        if (p == null) {
            vistaCarrito.mostrarMensaje("No se encontró el producto");
        } else {
            vistaCarrito.getTxtNombre().setText(p.getNombre());
            vistaCarrito.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
        }
    }

    private void configurarMenu() {
        //anadir
        principal.getMenuItemAnadir().addActionListener(e -> {
            var v = new AnadirProductosView();
            principal.getDesktopPanel().add(v);
            v.getBtnAceptar().addActionListener(ev ->{
                try {
                    String nombre    = vistaAnadir.getTextField1().getText().trim();
                    String codigoTxt = vistaAnadir.getTextField2().getText().trim();
                    String precioTxt = vistaAnadir.getTxtPrecio().getText().trim();

                    if (nombre.isEmpty() || codigoTxt.isEmpty() || precioTxt.isEmpty()) {
                        JOptionPane.showMessageDialog(vistaAnadir,
                                "Completa todos los campos",
                                "Atención",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int    codigo = Integer.parseInt(codigoTxt);
                    double precio = Double.parseDouble(precioTxt);

                    if (productoDAO.buscarPorCodigo(codigo) != null) {
                        JOptionPane.showMessageDialog(vistaAnadir,
                                "Código repetido",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    productoDAO.crear(new Producto(nombre, codigo, precio));
                    JOptionPane.showMessageDialog(vistaAnadir,
                            "Producto guardado",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    limpiarCamposAnadir();
                    listarProductos();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vistaAnadir,
                            "Código/Precio inválidos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            setVistaAnadir(v);
            v.setVisible(true);
        });

        //creara
        principal.getMenuItemCrear().addActionListener(e -> {
            // si “Crear” es lo mismo que “Anadir”, rediriges a ese flujo:
            principal.getMenuItemAnadir().doClick();
        });



        //aactualizar
        principal.getMenuItemActualizar().addActionListener(e -> {
            var v = new ProductoActualizarView();
            principal.getDesktopPanel().add(v);

            v.getBtnBuscar().addActionListener(ev -> {
                String txt = v.getTxtCodigoBuscar().getText().trim();
                if (txt.isEmpty()) {
                    JOptionPane.showMessageDialog(v, "Ingrese un código");
                    return;
                }
                int code = Integer.parseInt(txt);
                Producto p = productoDAO.buscarPorCodigo(code);
                if (p == null) {
                    JOptionPane.showMessageDialog(v, "Producto no encontrado");
                    v.limpiarCampos();
                } else {
                    v.cargarProducto(p);
                }
            });


            v.getBtnActualizar().addActionListener(ev -> {
                try {
                    String n = v.getTxtNombre().getText().trim();
                    String c = v.getTxtCodigoBuscar().getText().trim();
                    String p = v.getTxtPrecio().getText().trim();
                    if (n.isEmpty() || c.isEmpty() || p.isEmpty()) {
                        JOptionPane.showMessageDialog(v, "Completa todos los campos");
                        return;
                    }
                    int code = Integer.parseInt(c);
                    double precio = Double.parseDouble(p);
                    if (productoDAO.buscarPorCodigo(code)==null) {
                        JOptionPane.showMessageDialog(v, "No existe ese código");
                        return;
                    }
                    productoDAO.actualizar(new Producto(n, code, precio));
                    JOptionPane.showMessageDialog(v, "Producto actualizado");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(v, "Datos inválidos");
                }
            });

            v.getBtnSalir().addActionListener(ev -> v.dispose());

            v.setVisible(true);
        });



        //eliminar
        principal.getMenuItemEliminar().addActionListener(e -> {
            var v = new ProductoEliminarView();
            principal.getDesktopPanel().add(v);
            var modelo=(DefaultTableModel)v.getTblProductos().getModel();

            v.getBtnBuscar().addActionListener(ev -> {
                String txt = v.getTxtBuscar().getText().trim();
                if (txt.isEmpty()) {
                    JOptionPane.showMessageDialog(v, "Ingrese un código");
                    return;
                }
                modelo.setRowCount(0);
                try {
                    int code = Integer.parseInt(txt);
                    Producto p = productoDAO.buscarPorCodigo(code);
                    if (p!=null) {
                        modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
                    } else {
                        JOptionPane.showMessageDialog(v, "No encontrado");
                    }
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(v, "Código inválido");
                }
            });

            v.getBtnEliminar().addActionListener(ev -> {
                if (modelo.getRowCount()==0) {
                    JOptionPane.showMessageDialog(v, "Primero busca un producto");
                    return;
                }
                int code = (int)modelo.getValueAt(0,0);
                int ok = JOptionPane.showConfirmDialog(v,
                        "¿Eliminar producto " + code + "?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (ok==JOptionPane.YES_OPTION) {
                    productoDAO.eliminar(code);
                    // recarga lista completa:
                    modelo.setRowCount(0);
                    for (Producto p : productoDAO.listarTodos()) {
                        modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
                    }
                    JOptionPane.showMessageDialog(v, "Eliminado");
                }
            });


            v.getBtnSalir().addActionListener(ev -> v.dispose());

            // carga inicial
            for (Producto p : productoDAO.listarTodos()) {
                modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
            }

            v.setVisible(true);

        });

        principal.getMenuItemCarrito().addActionListener(e -> {
            vistaCarrito.setVisible(true);
            try { vistaCarrito.setSelected(true); } catch(Exception ignore){}
        });
    }





    private void configurarEventos() {
        vistaAnadir.getBtnAnadir().addActionListener(e -> guardarProducto());
        vistaAnadir.getBtnLimpiar(). addActionListener(e -> limpiarCamposAnadir());
        vistaAnadir.getBtnSalir().  addActionListener(e -> vistaAnadir.dispose());

        vistaListar.getBtnBuscar(). addActionListener(e -> buscarProducto());

        vistaListar.getBtnSalir().  addActionListener(e -> vistaListar.dispose());




    }

    private void guardarProducto() {

    }

    private void limpiarCamposAnadir() {
        vistaAnadir.getTextField1().setText("");
        vistaAnadir.getTextField2().setText("");
        vistaAnadir.getTextField3().setText("");
        vistaAnadir.getTxtPrecio().setText("");
    }

    private void listarProductos() {
        List<Producto> todos = productoDAO.listarTodos();
        vistaListar.getModelo().setRowCount(0);
        for (Producto p : todos) {
            vistaListar.getModelo().addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getPrecio()
            });
        }

    }

    private void buscarProducto() {
        String nombre = vistaListar.getTxtBuscar().getText().trim();
        if (nombre.isEmpty()) {
            vistaListar.mostrarMensaje("Ingrese un nombre para buscar");
            return;
        }
        List<Producto> encontrados = productoDAO.buscarPorNombre(nombre);
        vistaListar.cargarDatos(encontrados);
        vistaListar.mostrarMensaje(
                encontrados.isEmpty()
                        ? "No se encontraron productos"
                        : "Se encontraron " + encontrados.size() + " productos"
        );
    }

    private void configurarEventosAñadirYListar() {
        vistaAnadir.getBtnAceptar().addActionListener(e -> guardarProducto());
        vistaAnadir.getBtnLimpiar(). addActionListener(e -> limpiarCamposAnadir());
        vistaAnadir.getBtnSalir().  addActionListener(e -> vistaAnadir.dispose());


        vistaListar.getBtnBuscar(). addActionListener(e -> buscarProducto());
        vistaListar.getBtnSalir().  addActionListener(e -> vistaListar.dispose());
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    public AnadirProductosView getVistaAnadir() {
        return vistaAnadir;
    }

    public ProductoListarView getVistaListar() {
        return vistaListar;
    }

    public void setProductoListarView(ProductoListarView vistaListar) {
        this.vistaListar = vistaListar;
        configurarEventosAñadirYListar();
    }

    public void setVistaAnadir(AnadirProductosView vistaAnadir) {
        this.vistaAnadir = vistaAnadir;
        configurarEventosAñadirYListar();
    }

    private void configurarAnadirEventos(){
        vistaAnadir.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        vistaListar.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }




}
