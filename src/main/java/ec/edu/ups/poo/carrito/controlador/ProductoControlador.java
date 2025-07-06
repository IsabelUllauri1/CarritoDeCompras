package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.*;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.producto.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private ProductoEliminarView vistaEliminar;
    private ProductoActualizarView vistaActualizar;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;


    public ProductoControlador(ProductoDAO productoDAO, Principal principal, AnadirProductosView vistaAnadir, ProductoListarView vistaListar, ListarProductosPorCodigoView vistaListarPorCodigo, CarritoAnadirView vistaCarrito, ProductoEliminarView vistaEliminar, ProductoActualizarView vistaActualizar) {
        this.productoDAO = productoDAO;
        this.principal =  principal;
        this.vistaAnadir = vistaAnadir;
        this.vistaListar = vistaListar;
        this.vistaCarrito = vistaCarrito;
        this.vistaListarPorCodigo = vistaListarPorCodigo;
        this.vistaEliminar = vistaEliminar;
        this.vistaActualizar = vistaActualizar;
        this.formatosUtils = new FormatosUtils();

        configurarEventosAnadir();
        configurarEventosListar();
        configurarEventosEliminar();
        configurarEventosListarPorCodigo();
        configurarActualizar();
    }

    public void configurarActualizar() {
        vistaActualizar.getBtnBuscar().addActionListener(ev -> {
            String txt = vistaActualizar.getTxtCodigoBuscar().getText().trim();
            if (txt.isEmpty()) {
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.ingreseCodigo"));                return;
            }
            try {
                int code = Integer.parseInt(txt);
                Producto p = productoDAO.buscarPorCodigo(code);
                if (p == null) {
                    JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.noEncontrado"));
                    vistaActualizar.limpiarCampos();
                } else {
                    vistaActualizar.cargarProducto(p);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.datosInvalidos"));
            }
        });

        vistaActualizar.getBtnActualizar().addActionListener(ev -> {
            try {
                String nombre = vistaActualizar.getTxtNombre().getText().trim();
                int codigo = Integer.parseInt(vistaActualizar.getTxtCodigoBuscar().getText().trim());
                double precio = Double.parseDouble(vistaActualizar.getTxtPrecio().getText().trim());

                productoDAO.actualizar(new Producto(nombre, codigo, precio));
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.actualizado"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.datosInvalidos"));
            }
        });

        vistaActualizar.getBtnSalir().addActionListener(ev -> vistaActualizar.dispose());
    }


    private void configurarEventosAnadir() {
        vistaAnadir.getBtnAnadir().addActionListener(e -> guardarProducto());
        vistaAnadir.getBtnLimpiar().addActionListener(e -> limpiarCamposAnadir());
        vistaAnadir.getBtnSalir().addActionListener(e -> vistaAnadir.dispose());
    }
    //1

    private void guardarProducto() {
        String nombre = vistaAnadir.getTextField1().getText().trim();
        String codigoTxt = vistaAnadir.getTextField2().getText().trim();
        String precioTxt = vistaAnadir.getTxtPrecio().getText().trim();

        if (nombre.isEmpty() || codigoTxt.isEmpty() || precioTxt.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.completarCampos"));            return;
        }

        try {
            int codigo = Integer.parseInt(codigoTxt);
            double precio = Double.parseDouble(precioTxt);

            if (productoDAO.buscarPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.yaExiste"));                return;
            }

            productoDAO.crear(new Producto(nombre, codigo, precio));
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.guardadoExito"));            listarProductos();
            limpiarCamposAnadir();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.codigoPrecioInvalido"));        }
    }

    private void limpiarCamposAnadir() {
        vistaAnadir.getTextField1().setText("");
        vistaAnadir.getTextField2().setText("");
        vistaAnadir.getTextField3().setText("");
        vistaAnadir.getTxtPrecio().setText("");
    }
    //2

    private void configurarEventosListar() {
        vistaListar.getBtnBuscar().addActionListener(e -> buscarProducto());
        vistaListar.getBtnSalir().addActionListener(e -> vistaListar.dispose());
    }
    private void buscarProducto() {
        String nombre = vistaListar.getTxtBuscar().getText().trim();
        if (nombre.isEmpty()) {
            vistaListar.mostrarMensaje(mensajeInternacionalizacionHandler.get("producto.noEncontrado"), mensajeInternacionalizacionHandler.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
        }

        List<Producto> encontrados = productoDAO.buscarPorNombre(nombre);
        vistaListar.cargarDatos(encontrados);
        vistaListar.mostrarMensaje(
                mensajeInternacionalizacionHandler.get("producto.noEncontrado"),
                mensajeInternacionalizacionHandler.get("titulo.error"),
                JOptionPane.ERROR_MESSAGE
        );    }

    public void listarProductos() {
        List<Producto> todos = productoDAO.listarTodos();
        DefaultTableModel m = vistaListar.getModelo();
        m.setRowCount(0);
        for (Producto p : todos) {
            m.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
        }
    }
    //3
    private void configurarEventosEliminar() {
        DefaultTableModel modelo = vistaEliminar.getModelo();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            System.out.println("Fila " + i + " → Precio: " + modelo.getValueAt(i, 2));
        }


        vistaEliminar.getBtnBuscar().addActionListener(e -> {
            String txt = vistaEliminar.getTxtBuscar().getText().trim();
            modelo.setRowCount(0);
            if (txt.isEmpty()) {
                JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.ingreseCodigo"));
                return;
            }

            try {
                int code = Integer.parseInt(txt);
                Producto p = productoDAO.buscarPorCodigo(code);
                if (p != null) {
                    modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
                } else {
                    JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.noEncontrado"));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.codigoInvalido"));
            }
        });

        vistaEliminar.getBtnEliminar().addActionListener(e -> {
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.buscarPrimero"));
                return;
            }

            int code = (int) modelo.getValueAt(0, 0);
            int ok = JOptionPane.showConfirmDialog(
                    vistaEliminar,
                    mensajeInternacionalizacionHandler.get("producto.confirmarEliminacion") + " " + code + "?",
                    mensajeInternacionalizacionHandler.get("titulo.confirmar"),
                    JOptionPane.YES_NO_OPTION
            );
            if (ok == JOptionPane.YES_OPTION) {
                productoDAO.eliminar(code);
                modelo.setRowCount(0);
                for (Producto p : productoDAO.listarTodos()) {
                    modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
                }
                JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.eliminado"));
            }
        });

        vistaEliminar.getBtnSalir().addActionListener(e -> vistaEliminar.dispose());

        // Cargar todos al inicio
        for (Producto p : productoDAO.listarTodos()) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
        }
    }

    private void configurarEventosListarPorCodigo() {
        vistaListarPorCodigo.getBtnBuscar().addActionListener(e -> {
            String txt = vistaListarPorCodigo.getTxtBuscar().getText().trim();
            if (txt.isEmpty()) {
                vistaListarPorCodigo.mostrarMensaje(mensajeInternacionalizacionHandler.get("producto.ingresaCodigo"));                return;
            }

            try {
                int code = Integer.parseInt(txt);
                Producto p = productoDAO.buscarPorCodigo(code);
                DefaultTableModel m = (DefaultTableModel) vistaListarPorCodigo.getTblProductos().getModel();
                m.setRowCount(0);
                if (p != null) {
                    m.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
                } else {
                    vistaListarPorCodigo.mostrarMensaje(mensajeInternacionalizacionHandler.get("producto.noEncontrado"));
                }
            } catch (NumberFormatException ex) {
                vistaListarPorCodigo.mostrarMensaje(mensajeInternacionalizacionHandler.get("producto.codigoInvalido"));
            }
        });
    }
//------




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
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mensajeInternacionalizacionHandler = mh;
    }






}
