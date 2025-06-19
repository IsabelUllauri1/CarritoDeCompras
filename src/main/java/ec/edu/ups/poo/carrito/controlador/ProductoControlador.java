package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoControlador {

    private final ProductoDAO productoDAO;
    private Principal principal;
    private  AnadirProductosView vistaAnadir;
    private  ProductoListarView  vistaListar;

    public ProductoControlador(ProductoDAO dao, Principal principal) {
        this.productoDAO = dao;
        this.principal = new Principal();
        configurarMenu();

    }

    private void configurarMenu() {
        // — ANADIR PRODUCTO
        principal.getMenuItemAnadir().addActionListener(e -> {
            var v = new AnadirProductosView();
            principal.getDesktopPanel().add(v);
            setVistaAnadir(v);
            v.setVisible(true);
        });

        // — CREAR PRODUCTO (Puedes eliminar si es duplicado de Anadir)
        principal.getMenuItemCrear().addActionListener(e -> {
            // si “Crear” es lo mismo que “Anadir”, rediriges a ese flujo:
            principal.getMenuItemAnadir().doClick();
        });

        // — LISTAR PRODUCTOS
        principal.getMenuItemListar().addActionListener(e -> {
            var v = new ProductoListarView();
            principal.getDesktopPanel().add(v);
            setProductoListarView(v);
            // carga inicial de datos
            v.cargarDatos(productoDAO.listarTodos());
            v.setVisible(true);
        });

        // — ACTUALIZAR PRODUCTO
        principal.getMenuItemActualizar().addActionListener(e -> {
            var v = new ProductoActualizarView();
            principal.getDesktopPanel().add(v);
            new ProductoActualizarControlador(productoDAO, v);
            v.setVisible(true);
        });

        // — ELIMINAR PRODUCTO
        principal.getMenuItemEliminar().addActionListener(e -> {
            var v = new ProductoEliminarView();
            principal.getDesktopPanel().add(v);
            new ProductoEliminarControlador(productoDAO, v);
            v.setVisible(true);
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

            // **Refrescar** la vista de listar **inmediatamente**
            listarProductos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaAnadir,
                    "Código/Precio inválidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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
        // opcional: mensaje en etiqueta o JOptionPane

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
        // Crear/Añadir
        vistaAnadir.getBtnAceptar().addActionListener(e -> guardarProducto());
        vistaAnadir.getBtnLimpiar(). addActionListener(e -> limpiarCamposAnadir());
        vistaAnadir.getBtnSalir().  addActionListener(e -> vistaAnadir.dispose());

        // Listar/Buscar
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
