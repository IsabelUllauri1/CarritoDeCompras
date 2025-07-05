package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.view.*;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.producto.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Locale;

public class ProductoControlador {

    private final ProductoDAO productoDAO;
    private Principal principal;
    private AnadirProductosView vistaAnadir;
    private ProductoListarView vistaListar;
    private CarritoAnadirView vistaCarrito;
    private ListarProductosPorCodigoView vistaListarPorCodigo;
    private FormatosUtils formatosUtils;


    public ProductoControlador(ProductoDAO dao, Principal principal, AnadirProductosView vAnadir, ProductoListarView vListar, ListarProductosPorCodigoView vistaListarPorCodigo, CarritoAnadirView vistaCarrito) {
        this.productoDAO = dao;
        this.principal =  principal;
        this.vistaAnadir = vAnadir;
        this.vistaListar = vListar;
        this.vistaCarrito = vistaCarrito;
        this.vistaListarPorCodigo = vistaListarPorCodigo;
        configurarEventosAñadirYListar();
        configurarMenu();
        configurarBuscarPorCodigo();
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
        //crearprod
        principal.getMenuItemCrear().addActionListener(e -> {
            if (!vistaAnadir.isShowing()) {
                principal.getDesktopPanel().add(vistaAnadir);
            }
            vistaAnadir.setVisible(true);
            try {
                vistaAnadir.setSelected(true);
                vistaAnadir.moveToFront();
            } catch (PropertyVetoException ignore) {}
        });
        //listarporcodigo
        principal.getMenuItemListarCodigo().addActionListener(e -> {
            if (!vistaListarPorCodigo.isShowing()) {
                principal.getDesktopPanel().add(vistaListarPorCodigo);
            }
            cargarTodosEnBuscarCodigo();      // carga todos al abrir
            vistaListarPorCodigo.setVisible(true);
            try {
                vistaListarPorCodigo.setSelected(true);
                vistaListarPorCodigo.moveToFront();
            } catch (PropertyVetoException ignore) {}
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
            // actualizar

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
        //listar
        principal.getMenuItemListarProductos().addActionListener(e -> {
            if (!vistaListar.isShowing()) {
                principal.getDesktopPanel().add(vistaListar);
            }
            listarProductos();
            vistaListar.setVisible(true);
            try {
                vistaListar.setSelected(true);
                vistaListar.moveToFront();
            } catch (PropertyVetoException ignore) {}
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
                        modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), FormatosUtils.formatearNumero(p.getPrecio())});
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
                int ok = JOptionPane.showConfirmDialog(v, "¿Eliminar producto " + code + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (ok==JOptionPane.YES_OPTION) {
                    productoDAO.eliminar(code);
                    //actualizar lista
                    modelo.setRowCount(0);
                    for (Producto p : productoDAO.listarTodos()) {
                        modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearNumero(p.getPrecio())});
                    }
                    JOptionPane.showMessageDialog(v, "Eliminado");
                }
            });


            v.getBtnSalir().addActionListener(ev -> v.dispose());

            // carga inicial
            for (Producto p : productoDAO.listarTodos()) {
                modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearNumero(p.getPrecio())});
            }

            v.setVisible(true);

        });

        principal.getMenuItemCarrito().addActionListener(e -> {
            vistaCarrito.setVisible(true);
            try { vistaCarrito.setSelected(true); } catch(Exception ignore){}
        });
    }
    private void configurarBuscarPorCodigo() {
        vistaListarPorCodigo.getBtnBuscar().addActionListener(ev -> {
            String txt = vistaListarPorCodigo.getTxtBuscar().getText().trim();
            if (txt.isEmpty()) {
                vistaListarPorCodigo.mostrarMensaje("Ingresa un código de producto");
                return;
            }

            try {
                int codigo = Integer.parseInt(txt);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                DefaultTableModel modelo = (DefaultTableModel) vistaListarPorCodigo.getTblProductos().getModel();
                modelo.setRowCount(0); // Limpiar tabla
                if (producto != null) {
                    modelo.addRow(new Object[]{
                            producto.getCodigo(),
                            producto.getNombre(),
                            formatosUtils.formatearMoneda(producto.getPrecio(), Locale.getDefault())
                    });
                } else {
                    vistaListarPorCodigo.mostrarMensaje("Producto no encontrado");
                }
            } catch (NumberFormatException ex) {
                vistaListarPorCodigo.mostrarMensaje("Código inválido (debe ser un número)");
            }
        });
    }

    private void cargarTodosEnBuscarCodigo() {
        DefaultTableModel m = (DefaultTableModel) vistaListarPorCodigo.getTblProductos().getModel();
        m.setRowCount(0);
        for (Producto p : productoDAO.listarTodos()) {
            m.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearNumero(p.getPrecio())});
        }
    }


    private void guardarProducto() {
        String nombre    = vistaAnadir.getTextField1().getText().trim();
        String codigoTxt = vistaAnadir.getTextField2().getText().trim();
        String precioTxt = vistaAnadir.getTxtPrecio().getText().trim();

        if (nombre.isEmpty() || codigoTxt.isEmpty() || precioTxt.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAnadir, "Completa todos los campos", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo;
        double precio;
        try {
            codigo = Integer.parseInt(codigoTxt);
            precio = Double.parseDouble(precioTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaAnadir, "Código o precio inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (productoDAO.buscarPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(vistaAnadir, "Ya existe un producto con ese código", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        productoDAO.crear(new Producto(nombre, codigo, precio));
        JOptionPane.showMessageDialog(vistaAnadir, "Producto guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        listarProductos();

        vistaAnadir.getTextField1().setText("");
        vistaAnadir.getTextField2().setText("");
        vistaAnadir.getTxtPrecio() .setText("");

    }

    private void limpiarCamposAnadir() {
        vistaAnadir.getTextField1().setText("");
        vistaAnadir.getTextField2().setText("");
        vistaAnadir.getTextField3().setText("");
        vistaAnadir.getTxtPrecio().setText("");
    }

    private void listarProductos() {
        List<Producto> todos = productoDAO.listarTodos();
        DefaultTableModel m = vistaListar.getModelo();
        m.setRowCount(0);
        for (Producto p : todos) {
            m.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
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
        vistaListar.mostrarMensaje(encontrados.isEmpty() ? "No se encontraron productos" : "Se encontraron " + encontrados.size() + " productos");
    }

    private void configurarEventosAñadirYListar() {

        vistaAnadir.getBtnLimpiar(). addActionListener(e -> limpiarCamposAnadir());
        vistaAnadir.getBtnSalir().  addActionListener(e -> vistaAnadir.dispose());
        vistaAnadir.getBtnAnadir().addActionListener(e -> guardarProducto());


        vistaListar.getBtnBuscar(). addActionListener(e -> buscarProducto());
        vistaListar.getBtnSalir().  addActionListener(e -> vistaListar.dispose());

        vistaListarPorCodigo.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigo());

    }
    public void listarProductosEnVistaPorCodigo() {

        DefaultTableModel m = (DefaultTableModel) vistaListarPorCodigo.getTblProductos().getModel();
        m.setRowCount(0);
        for (Producto p : productoDAO.listarTodos()) {
            m.addRow(new Object[]{ p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault()) });
        }
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






}
