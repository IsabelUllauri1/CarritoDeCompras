package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.ItemCarrito;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.Principal;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.carrito.CarritoListarView;
import ec.edu.ups.poo.carrito.view.carrito.VerDetalleView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.beans.PropertyVetoException;
import java.util.List;

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

        // — ListarView —
        listarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
        listarView.getBtnModificar().addActionListener(e -> modificarCarrito());
        listarView.getBtnDetalless().addActionListener(e -> verDetalles());
    }

    private void buscarProducto() {
        try {
            int code = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
            Producto p = productoDAO.buscarPorCodigo(code);
            if (p == null) {
                anadirView.mostrarMensaje("Producto no encontrado");
            } else {
                anadirView.getTxtNombre().setText(p.getNombre());
                anadirView.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
            }
        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje("Código inválido");
        }
    }

    private void agregarItem() {
        try {
            int code = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
            int qty  = Integer.parseInt(anadirView.getCbxCantidad().getSelectedItem().toString());
            Producto p = productoDAO.buscarPorCodigo(code);
            if (p == null) {
                anadirView.mostrarMensaje("Producto no encontrado");
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
            anadirView.mostrarMensaje("Datos inválidos");
        }
    }

    private void vaciarCarrito() {
        carrito.vaciarCarrito();
        refrescarTablaItems();
    }

    private void eliminarItem() {
        int row = anadirView.getTblProductos().getSelectedRow();
        if (row < 0) {
            anadirView.mostrarMensaje("Selecciona un ítem primero");
            return;
        }
        int code = (int) modeloItems.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(anadirView, "¿Eliminar item código " + code + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            carrito.eliminarProducto(code);
            refrescarTablaItems();
        }
    }

    private void guardarCarrito() {
        if (carrito.estaVacio()) {
            anadirView.mostrarMensaje("El carrito está vacío.");
            return;
        }
        carritoDAO.crear(carrito);
        anadirView.mostrarMensaje("Carrito registrado. Código: " + carrito.getCodigo());

        this.carrito = new Carrito();
        this.carrito.setUsuario(usuario);
        refrescarTablaItems();
        refrescarLista();
    }

    private void refrescarTablaItems() {
        modeloItems.setRowCount(0);
        for (ItemCarrito it : carrito.obtenerItems()) {
            modeloItems.addRow(new Object[]{
                    it.getProducto().getCodigo(), it.getProducto().getNombre(), it.getCantidad(), it.getSubtotal()
            });
        }
        anadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubtotal()));
        anadirView.getTxtIVA().setText   (String.format("%.2f", carrito.calcularIVA()));
        anadirView.getTxtTotal().setText (String.format("%.2f", carrito.calcularTotal()));
    }

    private void refrescarLista() {
        modeloList.setRowCount(0);
        List<Carrito> todos = carritoDAO.listarTodos();
        for (Carrito c : todos) {
            modeloList.addRow(new Object[]{c.getCodigo(), c.getFechaCreacion().getTime(), c.calcularSubtotal(), c.calcularIVA(), c.calcularTotal()
            });
        }
    }

    private void modificarCarrito() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            listarView.mostrarMensaje("Selecciona un carrito primero");
            return;
        }
        int code = (int) modeloList.getValueAt(row, 0);
        Carrito c = carritoDAO.buscarPorCodigo(code);
        if (c == null) return;
        anadirView.setTitle("Modificar Carrito #" + code);
        if(!anadirView.isShowing()){
            listarView.getDesktopPane().add(anadirView);
        }
        anadirView.setVisible(true);
        refrescarTablaItems();
    }

    private void eliminarCarrito() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            listarView.mostrarMensaje("Selecciona un carrito primero");
            return;
        }
        int code = (int) modeloList.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(listarView, "¿Eliminar carrito #" + code + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(code);
            refrescarLista();
        }
    }

    private void verDetalles() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) listarView.getTblCarritos()
                .getValueAt(row, 0);

        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        if (c == null) {
            JOptionPane.showMessageDialog(listarView, "No se encontró el carrito", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel dm = (DefaultTableModel) verDetalleView.getTblProductos().getModel();
        dm.setRowCount(0);
        for (ItemCarrito it : c.obtenerItems()) {
            dm.addRow(new Object[]{it.getProducto().getCodigo(), it.getProducto().getNombre(), it.getCantidad(), String.format("%.2f", it.getSubtotal())
            });
        }

        verDetalleView.getTxtSubtotal().setText(String.format("%.2f", c.calcularSubtotal()));
        verDetalleView.getTxtIVA().setText(   String.format("%.2f", c.calcularIVA()));
        verDetalleView.getTxtTotal().setText(  String.format("%.2f", c.calcularTotal()));


        try {
            if (!verDetalleView.isShowing()) {principal.getDesktopPanel().add(verDetalleView);
            }
            verDetalleView.setVisible(true);
            verDetalleView.setSelected(true);
        } catch (PropertyVetoException ex) {
        }
    }

    //
}
