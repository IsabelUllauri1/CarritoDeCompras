package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.ItemCarrito;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.Principal;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.carrito.CarritoListarView;
import ec.edu.ups.poo.carrito.view.carrito.VerDetalleView;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
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

    private void agregarItem() {
        try {
            int code = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
            int qty  = Integer.parseInt(anadirView.getCbxCantidad().getSelectedItem().toString());
            Producto p = productoDAO.buscarPorCodigo(code);
            if (p == null) {
                anadirView.mostrarMensaje(mh.get("mensaje.productoNoEncontrado"));
                return;
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
                carrito.agregarProducto(p, qty);
            }
            refrescarTablaItems();
        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje(mh.get("mensaje.datosInvalidos"));
        }
    }

    private void vaciarCarrito() {
        carrito.vaciarCarrito();
        refrescarTablaItems();
    }

    private void eliminarItem() {
        int row = anadirView.getTblProductos().getSelectedRow();
        if (row < 0) {
            anadirView.mostrarMensaje(mh.get("mensaje.seleccionaItem"));
            return;
        }
        int code = (int) modeloItems.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(anadirView, MessageFormat.format(mh.get("mensaje.confirmarEliminarItem"), code), mh.get("titulo.confirmar.eliminacion"), JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            carrito.eliminarProducto(code);
            refrescarTablaItems();
        }
    }

    private void guardarCarrito() {
        if (carrito.estaVacio()) {
            anadirView.mostrarMensaje(mh.get("mensaje.carritoVacio"));
            return;
        }
        carritoDAO.crear(carrito);
        anadirView.mostrarMensaje(MessageFormat.format(mh.get("mensaje.carritoRegistrado"), carrito.getCodigo()));

        this.carrito = new Carrito();
        this.carrito.setUsuario(usuario);
        refrescarTablaItems();
        refrescarLista();
    }

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

    private void refrescarLista() {
        modeloList.setRowCount(0);
        List<Carrito> todos = carritoDAO.listarTodos();
        for (Carrito c : todos) {
            modeloList.addRow(new Object[]{c.getCodigo(),
                    formatosUtils.formatearFecha(c.getFechaCreacion().getTime(),Locale.getDefault()),
                    formatosUtils.formatearMoneda(c.calcularSubtotal(), Locale.getDefault()),
                    formatosUtils.formatearMoneda(c.calcularIVA(), Locale.getDefault()),
                    formatosUtils.formatearMoneda(c.calcularTotal(), Locale.getDefault())
            });
        }
    }

    private void modificarCarrito() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            listarView.mostrarMensaje(mh.get("mensaje.seleccionaCarrito"));
            return;
        }
        int code = (int) modeloList.getValueAt(row, 0);
        Carrito c = carritoDAO.buscarPorCodigo(code);
        if (c == null) return;
        anadirView.setTitle(MessageFormat.format(mh.get("titulo.modificarCarrito"), code));
        if(!anadirView.isShowing()){
            listarView.getDesktopPane().add(anadirView);
        }
        anadirView.setVisible(true);
        refrescarTablaItems();
    }

    private void eliminarCarrito() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            listarView.mostrarMensaje(mh.get("mensaje.seleccionaCarrito"));
            return;
        }
        int code = (int) modeloList.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(listarView, MessageFormat.format(mh.get("mensaje.confirmarEliminarCarrito"), code), mh.get("titulo.confirmar.eliminacion"), JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(code);
            refrescarLista();
        }
    }

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
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mh = mh;
    }



}
