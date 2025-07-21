package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.*;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.util.exception.ValidacionException;
import ec.edu.ups.poo.carrito.view.Principal;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.carrito.CarritoListarView;
import ec.edu.ups.poo.carrito.view.carrito.VerDetalleView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.beans.PropertyVetoException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

public class CarritoControlador {

    private final ProductoDAO productoDAO;
    private final CarritoDAO   carritoDAO;
    private final CarritoAnadirView anadirView;
    private final CarritoListarView listarView;
    private final Usuario usuario;
    private Carrito carrito;
    private final DefaultTableModel modeloItems;
    private final DefaultTableModel modeloList;
    private VerDetalleView verDetalleView;
    private Principal principal;
    private FormatosUtils formatosUtils;
    private MensajeInternacionalizacionHandler mh;
    /**
     * Crea una nueva instancia del controlador del carrito de compras.
     *
     * @param productoDAO DAO para acceder a los productos.
     * @param carritoDAO DAO para acceder a los carritos.
     * @param anadirView Vista para añadir productos al carrito.
     * @param listarView Vista para listar los carritos del usuario.
     * @param usuario Usuario actualmente autenticado.
     */

    public CarritoControlador(ProductoDAO productoDAO, CarritoDAO carritoDAO, CarritoAnadirView anadirView, CarritoListarView listarView, Usuario usuario) {
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.anadirView = anadirView;
        this.listarView = listarView;
        this.usuario = usuario;
        this.carrito = new Carrito();
        this.carrito.setUsuario(usuario);

        this.modeloItems = (DefaultTableModel) anadirView.getTblProductos().getModel();
        this.modeloList  = (DefaultTableModel) listarView.getTblCarritos().getModel();

        configurarEventos();
        refrescarTablaItems();
        refrescarLista();
    }

    /**
     * Configura los eventos (listeners) de las vistas asociadas al carrito.
     */


    private void configurarEventos() {
        // — AnadirView —
        anadirView.getBtnBuscar().addActionListener(e -> buscarProducto());
        anadirView.getBtnAnadir().addActionListener(e -> agregarItem());
        anadirView.getBtnVaciar().addActionListener(e -> vaciarCarrito());
        anadirView.getBtnEliminar().addActionListener(e -> eliminarItem());
        anadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        anadirView.getBtnCancelar().addActionListener(e -> anadirView.dispose());

        listarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
        listarView.getBtnModificar().addActionListener(e -> modificarCarrito());
        listarView.getBtnDetalless().addActionListener(e -> verDetalles());

    }

    /**
     * Busca un producto por su código ingresado en la vista y muestra su información.
     *
     * @throws NumberFormatException Si el código no es un número válido.
     */


    private void buscarProducto() {
        try {
            int code = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
            Producto p = productoDAO.buscarPorCodigo(code);
            if (p == null) {
                anadirView.mostrarMensaje(mh.get("mensaje.productoNoEncontrado"));
            } else {
                anadirView.getTxtNombre().setText(p.getNombre());
                anadirView.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
            }
        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje(mh.get("mensaje.productoNoEncontrado"));
        }
    }

    /**
     * Agrega un producto al carrito con la cantidad seleccionada.
     * Si el producto ya existe, actualiza su cantidad.
     *
     * @throws ValidacionException Si hay errores de validación en el código o cantidad.
     * @throws NumberFormatException Si el código o cantidad no son números válidos.
     */


    private void agregarItem() {
        try {
            String codigoStr = anadirView.getTxtCodigo().getText().trim();
            if (codigoStr.isEmpty()) {
                throw new ValidacionException(mh.get("mensaje.codigoVacio"));
            }

            int code = Integer.parseInt(codigoStr);
            int qty = Integer.parseInt(anadirView.getCbxCantidad().getSelectedItem().toString());

            if (qty <= 0) {
                throw new ValidacionException(mh.get("mensaje.cantidadInvalida"));
            }

            Producto p = productoDAO.buscarPorCodigo(code);
            if (p == null) {
                throw new ValidacionException(mh.get("mensaje.productoNoEncontrado"));
            }

            boolean encontrado = false;
            for (ItemCarrito it : carrito.obtenerItems()) {
                if (it.getProducto().getCodigo() == code) {
                    it.setCantidad(qty);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                carrito.agregarProducto(p, qty); // si este setter valida, puede lanzar también
            }

            refrescarTablaItems();

        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje(mh.get("mensaje.datosInvalidos"));
        } catch (ValidacionException ve) {
            anadirView.mostrarMensaje(ve.getMessage());
        } catch (Exception ex) {
            anadirView.mostrarMensaje(mh.get("mensaje.errorAgregarItem"));
        }
    }

    /**
     * Elimina todos los productos del carrito actual.
     */
    private void vaciarCarrito() {
        carrito.vaciarCarrito();
        refrescarTablaItems();
    }


    /**
     * Elimina un producto del carrito, basado en la fila seleccionada en la tabla.
     *
     * @throws ValidacionException Si no se ha seleccionado ningún ítem.
     */
    private void eliminarItem() {
        try {
            int row = anadirView.getTblProductos().getSelectedRow();
            if (row < 0) {
                throw new ValidacionException(mh.get("mensaje.seleccionaItem"));
            }

            int code = (int) modeloItems.getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(anadirView, MessageFormat.format(mh.get("mensaje.confirmarEliminarItem"), code), mh.get("titulo.confirmar.eliminacion"), JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                carrito.eliminarProducto(code);
                refrescarTablaItems();
            }
        } catch (ValidacionException ve) {
            anadirView.mostrarMensaje(ve.getMessage());
        } catch (Exception ex) {
            anadirView.mostrarMensaje(mh.get("mensaje.errorEliminarItem"));
        }
    }

    /**
     * Guarda el carrito actual si contiene productos. Luego reinicia el carrito.
     *
     * @throws ValidacionException Si el carrito está vacío.
     */
    private void guardarCarrito() {
        try {

            if (carrito.estaVacio()) {
                throw new ValidacionException(mh.get("mensaje.carritoVacio"));
            }

            carritoDAO.crear(carrito);
            anadirView.mostrarMensaje(MessageFormat.format(mh.get("mensaje.carritoRegistrado"), carrito.getCodigo()));

            // Reinicio del carrito
            this.carrito = new Carrito();
            this.carrito.setUsuario(usuario);
            refrescarTablaItems();
            refrescarLista();

        } catch (ValidacionException ve) {
            anadirView.mostrarMensaje(ve.getMessage());
        } catch (Exception ex) {
            anadirView.mostrarMensaje(mh.get("mensaje.errorGuardarCarrito"));
        }
    }

    /**
     * Refresca la tabla de productos del carrito en la vista, mostrando
     * cada ítem con su subtotal, y recalculando el total, IVA y subtotal general.
     */
    private void refrescarTablaItems() {
        modeloItems.setRowCount(0);
        for (ItemCarrito it : carrito.obtenerItems()) {
            modeloItems.addRow(new Object[]{
                    it.getProducto().getCodigo(), it.getProducto().getNombre(), it.getCantidad(), formatosUtils.formatearMoneda(it.getSubtotal(), Locale.getDefault())
            });
        }
        anadirView.getTxtSubtotal().setText(formatosUtils.formatearMoneda(carrito.calcularSubtotal(), Locale.getDefault()));
        anadirView.getTxtIVA().setText(formatosUtils.formatearMoneda(carrito.calcularIVA(), Locale.getDefault()));
        anadirView.getTxtTotal().setText(formatosUtils.formatearMoneda(carrito.calcularTotal(), Locale.getDefault()));
    }


    /**
     * Refresca la tabla de carritos mostrados, según el rol del usuario.
     * Muestra todos los carritos si es administrador, o solo los del usuario si no.
     */
    private void refrescarLista() {
        try {
            modeloList.setRowCount(0);
            List<Carrito> todos;
            if (usuario.getRol()== ROL.ADMINISTRADOR) {
                todos = carritoDAO.listarTodos();
            } else {
                todos = carritoDAO.listarPorUsuario(usuario);
            }

            for (Carrito c : todos) {
                modeloList.addRow(new Object[]{
                        c.getCodigo(),
                        formatosUtils.formatearFecha(c.getFechaCreacion().getTime(), Locale.getDefault()),
                        formatosUtils.formatearMoneda(c.calcularSubtotal(), Locale.getDefault()),
                        formatosUtils.formatearMoneda(c.calcularIVA(), Locale.getDefault()),
                        formatosUtils.formatearMoneda(c.calcularTotal(), Locale.getDefault())
                });
            }
        } catch (Exception ex) {
            listarView.mostrarMensaje(mh.get("mensaje.errorCargarCarritos"));
        }
    }

    /**
     * Permite modificar un carrito existente, abriendo la vista de edición.
     *
     * @throws ValidacionException Si no se ha seleccionado ningún carrito.
     */
    private void modificarCarrito() {
        try {
            int row = listarView.getTblCarritos().getSelectedRow();
            if (row < 0) {
                throw new ValidacionException(mh.get("mensaje.seleccionaCarrito"));
            }
            int code = (int) modeloList.getValueAt(row, 0);
            Carrito c = carritoDAO.buscarPorCodigo(code);
            if (c == null) return;
            anadirView.setTitle(MessageFormat.format(mh.get("titulo.modificarCarrito"), code));
            if (!anadirView.isShowing()) {
                listarView.getDesktopPane().add(anadirView);
            }
            anadirView.setVisible(true);
            refrescarTablaItems();
        }catch (ValidacionException ve) {
            listarView.mostrarMensaje(ve.getMessage());
        }
    }

    /**
     * Elimina un carrito de la lista, previa confirmación del usuario.
     *
     * @throws ValidacionException Si no se ha seleccionado ningún carrito.
     */
    private void eliminarCarrito() {
        try {
            int row = listarView.getTblCarritos().getSelectedRow();
            if (row < 0) {
                throw new ValidacionException(mh.get("mensaje.seleccionaCarrito"));
            }
            int code = (int) modeloList.getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(listarView, MessageFormat.format(mh.get("mensaje.confirmarEliminarCarrito"), code), mh.get("titulo.confirmar.eliminacion"), JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                carritoDAO.eliminar(code);
                refrescarLista();
            }
        }catch (ValidacionException ve) {
            listarView.mostrarMensaje(ve.getMessage());

        }
    }

    /**
     * Muestra los detalles del carrito seleccionado en la tabla,
     * incluyendo sus ítems y totales calculados.
     */
    private void verDetalles() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(listarView, mh.get("mensaje.seleccionaCarrito"), mh.get("titulo.atencion"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) listarView.getTblCarritos().getValueAt(row, 0);

        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        if (c == null) {
            JOptionPane.showMessageDialog(listarView, mh.get("mensaje.carritoNoEncontrado"), mh.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel dm = (DefaultTableModel) verDetalleView.getTblProductos().getModel();
        dm.setRowCount(0);
        for (ItemCarrito it : c.obtenerItems()) {
            dm.addRow(new Object[]{it.getProducto().getCodigo(), it.getProducto().getNombre(), it.getCantidad(),formatosUtils.formatearMoneda(it.getSubtotal(), Locale.getDefault())
            });
        }

        verDetalleView.getTxtSubtotal().setText(formatosUtils.formatearMoneda(c.calcularSubtotal(), Locale.getDefault()));
        verDetalleView.getTxtIVA().setText (formatosUtils.formatearMoneda(c.calcularIVA(), Locale.getDefault()));
        verDetalleView.getTxtTotal().setText (formatosUtils.formatearMoneda(c.calcularTotal(), Locale.getDefault()));


        try {
            if (!verDetalleView.isShowing()) {principal.getDesktopPanel().add(verDetalleView);
            }
            verDetalleView.setVisible(true);
            verDetalleView.setSelected(true);
        } catch (PropertyVetoException ex) {
        }
    }
    /**
     * Asigna el manejador de mensajes internacionalizados para el controlador.
     *
     * @param mh Manejador de internacionalización.
     */
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mh = mh;
    }



}
