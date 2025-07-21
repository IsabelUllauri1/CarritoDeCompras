package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.util.exception.ValidacionException;
import ec.edu.ups.poo.carrito.view.*;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.producto.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    /**
     * Inicializa el controlador con los DAOs y vistas correspondientes,
     * y configura los listeners de todos los formularios relacionados con productos.
     *
     * @param productoDAO DAO de productos para acceso a datos.
     * @param principal Vista principal del sistema.
     * @param vistaAnadir Vista para añadir productos.
     * @param vistaListar Vista para listar productos por nombre.
     * @param vistaListarPorCodigo Vista para listar productos por código.
     * @param vistaCarrito Vista relacionada con el carrito de compras.
     * @param vistaEliminar Vista para eliminar productos.
     * @param vistaActualizar Vista para actualizar productos.
     */

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


    /**
     * Configura los eventos del formulario de actualización de productos:
     * búsqueda por código, actualización de datos y cierre de la vista.
     */

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
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.codigoPrecioInvalido"));
            } catch (ValidacionException ex) {
                JOptionPane.showMessageDialog(vistaActualizar, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.errorActualizar"));
            }
        });

        vistaActualizar.getBtnActualizar().addActionListener(ev -> {
            try {
                String nombre = vistaActualizar.getTxtNombre().getText().trim();
                int codigoAnterior = Integer.parseInt(vistaActualizar.getTxtCodigoBuscar().getText().trim());
                int codigoNuevo = Integer.parseInt(vistaActualizar.getTxtCodigo().getText().trim()); // nuevo campo editable
                double precio = Double.parseDouble(vistaActualizar.getTxtPrecio().getText().trim());

                Producto nuevoProducto = new Producto(nombre, codigoNuevo, precio);
                productoDAO.actualizar(codigoAnterior, nuevoProducto); // llamada extendida
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.actualizado"));
            } catch (UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(vistaActualizar, "Este DAO no permite cambiar el código del producto.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaActualizar, mensajeInternacionalizacionHandler.get("producto.datosInvalidos"));
            }
        });

        vistaActualizar.getBtnSalir().addActionListener(ev -> vistaActualizar.dispose());
    }

    /**
     * Limpia los campos del formulario de registro de productos.
     */

    private void configurarEventosAnadir() {
        vistaAnadir.getBtnAnadir().addActionListener(e -> guardarProducto());
        vistaAnadir.getBtnLimpiar().addActionListener(e -> limpiarCamposAnadir());
        vistaAnadir.getBtnSalir().addActionListener(e -> vistaAnadir.dispose());
    }
    //1
    /**
     * Guarda un nuevo producto si los datos ingresados son válidos.
     * Realiza validaciones de campos, conversión de tipos y persistencia.
     *
     * @throws NumberFormatException Si el código o precio no son numéricos.
     * @throws ValidacionException Si los datos no cumplen con los criterios del modelo.
     */

    private void guardarProducto() {
        String nombre = vistaAnadir.getTextField1().getText().trim();
        String codigoTxt = vistaAnadir.getTextField2().getText().trim();
        String precioTxt = vistaAnadir.getTextField3().getText().trim();

        if (nombre.isEmpty() || codigoTxt.isEmpty() || precioTxt.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.completarCampos"));
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoTxt);
            double precio = Double.parseDouble(precioTxt);

            if (productoDAO.buscarPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.yaExiste"));
                return;
            }

            productoDAO.crear(new Producto(nombre, codigo, precio));
            System.out.println("Producto creado");
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.guardadoExito"));
            System.out.println("Mensaje mostrado");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.codigoPrecioInvalido"));
            return;
        } catch (ValidacionException ex) {
            JOptionPane.showMessageDialog(vistaAnadir, ex.getMessage());
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaAnadir, mensajeInternacionalizacionHandler.get("producto.errorGuardar"));
            return;
        }

        // Separar la parte que puede fallar después
        try {
            listarProductos();
            System.out.println("Productos listados");
            limpiarCamposAnadir();
            System.out.println("Campos limpiados");
        } catch (Exception ex) {
            System.err.println("⚠ Error al actualizar la vista: " + ex.getMessage());
            ex.printStackTrace();
        }

    }
    /**
     * Limpia los campos del formulario de registro de productos.
     */

    private void limpiarCamposAnadir() {
        vistaAnadir.getTextField1().setText("");
        vistaAnadir.getTextField2().setText("");
        vistaAnadir.getTextField3().setText("");
    }
    //2

    /**
     * Configura los eventos de la vista de listado de productos por nombre.
     */

    private void configurarEventosListar() {
        vistaListar.getBtnBuscar().addActionListener(e -> buscarProducto());
        vistaListar.getBtnSalir().addActionListener(e -> vistaListar.dispose());
    }

    /**
     * Busca productos por nombre ingresado y actualiza la tabla de resultados.
     *
     * Muestra mensaje de error si no se encuentra coincidencia o si el campo está vacío.
     */

    private void buscarProducto() {
        String nombre = vistaListar.getTxtBuscar().getText().trim();
        if (nombre.isEmpty()) {
            vistaListar.mostrarMensaje(
                    mensajeInternacionalizacionHandler.get("producto.noEncontrado"),
                    mensajeInternacionalizacionHandler.get("titulo.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<Producto> encontrados = productoDAO.buscarPorNombre(nombre);
        if (encontrados.isEmpty()) {
            vistaListar.mostrarMensaje(
                    mensajeInternacionalizacionHandler.get("producto.noEncontrado"),
                    mensajeInternacionalizacionHandler.get("titulo.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        DefaultTableModel m = vistaListar.getModelo();
        m.setRowCount(0);
        for (Producto p : encontrados) {
            m.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())
            });
        }
    }

    /**
     * Lista todos los productos disponibles en el sistema y los muestra en la vista de listado.
     *
     *  Exception Si ocurre un error al acceder a los datos.
     */

    public void listarProductos() {
        try {
            List<Producto> todos = productoDAO.listarTodos();
            DefaultTableModel m = vistaListar.getModelo();
            m.setRowCount(0);
            for (Producto p : todos) {
                m.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
            }
        }catch (Exception ex) {
            vistaListar.mostrarMensaje(mensajeInternacionalizacionHandler.get("producto.errorListar"),
            mensajeInternacionalizacionHandler.get("titulo.error"), JOptionPane.ERROR_MESSAGE
            );
        }
    }
    /**
     * Configura los eventos para buscar y eliminar productos en la vista de eliminación.
     *
     * Incluye confirmación previa y actualización de listas tras la eliminación.
     */

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
                try {
                    productoDAO.eliminar(code);
                    modelo.setRowCount(0);
                    for (Producto p : productoDAO.listarTodos()) {
                        modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
                    }


                    listarProductos();
                    listarProductosEnVistaPorCodigo();

                    JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.eliminado"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vistaEliminar, mensajeInternacionalizacionHandler.get("producto.errorEliminar"));
                }
            }



        });

        vistaEliminar.getBtnSalir().addActionListener(e -> vistaEliminar.dispose());

    }

    /**
     * Recarga la tabla de productos en la vista de eliminación con todos los productos existentes.
     */

    public void recargarEliminarProductos() {
        DefaultTableModel modelo = vistaEliminar.getModelo();
        modelo.setRowCount(0);
        for (Producto p : productoDAO.listarTodos()) {
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())
            });
        }
    }

    /**
     * Configura el evento de búsqueda de productos por código en la vista correspondiente.
     */

    private void configurarEventosListarPorCodigo() {
        vistaListarPorCodigo.getBtnBuscar().addActionListener(e -> {
            String txt = vistaListarPorCodigo.getTxtBuscar().getText().trim();
            if (txt.isEmpty()) {
                vistaListarPorCodigo.mostrarMensaje(mensajeInternacionalizacionHandler.get("producto.ingresaCodigo"));
                return;
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

    /**
     * Lista todos los productos disponibles en la tabla de la vista por código.
     *
     *  Exception Si ocurre un error al acceder a los datos.
     */

    public void listarProductosEnVistaPorCodigo() {
        try {

            DefaultTableModel m = (DefaultTableModel) vistaListarPorCodigo.getTblProductos().getModel();
            m.setRowCount(0);
            for (Producto p : productoDAO.listarTodos()) {
                m.addRow(new Object[]{p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())});
            }
        }catch (Exception ex) {
            vistaListar.mostrarMensaje(
                    mensajeInternacionalizacionHandler.get("producto.errorListar"),
                    mensajeInternacionalizacionHandler.get("titulo.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mensajeInternacionalizacionHandler = mh;
    }






}
