package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.ItemCarrito;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.carrito.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.carrito.CarritoListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyVetoException;
import java.util.List;

public class CarritoControlador {

    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final CarritoAnadirView anadirView;
    private final CarritoListarView listarView;
    private Carrito carrito;
    private final DefaultTableModel modeloItems;
    private final DefaultTableModel modeloList;
    private  final Usuario usuarioAutenticado;

    public CarritoControlador(ProductoDAO productoDAO, CarritoDAO carritoDAO, CarritoAnadirView anadirView, CarritoListarView listarView, Usuario usuarioAutenticado) {
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.anadirView = anadirView;
        this.listarView = listarView;
        this.carrito = new Carrito();
        this.usuarioAutenticado = usuarioAutenticado;
        this.modeloItems = (DefaultTableModel) anadirView.getTblProductos().getModel();
        this.modeloList = (DefaultTableModel) listarView.getTblCarritos().getModel();

        configurarEventos();
        refrescarTablaItems();
        refrescarLista();
    }

    private void configurarEventos() {
        //anadirview
        anadirView.getBtnBuscar().addActionListener(e -> {
            try {
                int code = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
                Producto p = productoDAO.buscarPorCodigo(code);
                if (p != null) {
                    anadirView.getTxtNombre().setText(p.getNombre());
                    anadirView.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
                } else {
                    anadirView.mostrarMensaje("Producto no encontrado");
                }
            } catch (NumberFormatException ex) {
                anadirView.mostrarMensaje("Código inválido");
            }
        });

        anadirView.getBtnAnadir().addActionListener(e -> {
            try {
                int code = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
                int qty  = Integer.parseInt(anadirView.getCbxCantidad().getSelectedItem().toString());
                Producto p = productoDAO.buscarPorCodigo(code);
                if (p == null) {
                    anadirView.mostrarMensaje("Producto no encontrado");
                    return;
                }

                //sustituye por cantidad actual
                boolean encontrado = false;
                for (ItemCarrito item : carrito.obtenerItems()) {
                    if (item.getProducto().getCodigo() == code) {
                        item.setCantidad(qty);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    carrito.agregarProducto(p, qty);
                }


                DefaultTableModel modelo = (DefaultTableModel) anadirView.getTblProductos().getModel();
                modelo.setRowCount(0);
                for (ItemCarrito iCarrito : carrito.obtenerItems()) {
                    modelo.addRow(new Object[]{iCarrito.getProducto().getCodigo(), iCarrito.getProducto().getNombre(), iCarrito.getCantidad(), iCarrito.getSubtotal()});
                }


                double sub   = carrito.calcularSubtotal();
                double iva   = carrito.calcularIVA();
                double total = carrito.calcularTotal();

                anadirView.getTxtSubtotal().setText(String.format("%.2f", sub));
                anadirView.getTxtIVA().setText   (String.format("%.2f", iva));
                anadirView.getTxtTotal().setText (String.format("%.2f", total));

            } catch (NumberFormatException ex) {
                anadirView.mostrarMensaje("Datos inválidos");
            }
        });


        anadirView.getBtnVaciar().addActionListener(e -> {
            carrito.vaciarCarrito();
            refrescarTablaItems();
        });

        anadirView.getBtnEliminar().addActionListener(e -> {
            int row = anadirView.getTblProductos().getSelectedRow();
            if (row >= 0) {
                int code = (int) modeloItems.getValueAt(row, 0);
                int opt = JOptionPane.showConfirmDialog(anadirView,
                        "¿Eliminar item código " + code + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (opt == JOptionPane.YES_OPTION) {
                    carrito.eliminarProducto(code);
                    refrescarTablaItems();
                }
            } else {
                anadirView.mostrarMensaje("Selecciona un ítem primero");
            }
        });

        anadirView.getBtnGuardar().addActionListener(e -> {
            guardarCarrito();
        });

        anadirView.getBtnCancelar().addActionListener(e -> anadirView.dispose());

        //listarview
        listarView.getBtnRefrescar().addActionListener(e -> refrescarLista());

        listarView.getBtnEliminar().addActionListener(e -> {
            int row = listarView.getTblCarritos().getSelectedRow();
            if (row >= 0) {
                int code = (int) modeloList.getValueAt(row, 0);
                int opt = JOptionPane.showConfirmDialog(listarView,
                        "¿Eliminar carrito #" + code + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (opt == JOptionPane.YES_OPTION) {
                    carritoDAO.eliminar(code);
                    refrescarLista();
                }
            } else {
                JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero");
            }
        });

        listarView.getBtnModificar().addActionListener(e -> {
            int row = listarView.getTblCarritos().getSelectedRow();
            if (row >= 0) {
                int code = (int) modeloList.getValueAt(row, 0);
                Carrito c = carritoDAO.buscarPorCodigo(code);
                if (c != null) {
                    // abrir AddView para edición
                    try {
                        anadirView.setTitle("Modificar Carrito #" + code);
                        listarView.getDesktopPane().add(anadirView);
                        anadirView.setSelected(true);
                    } catch (PropertyVetoException ignore) {}
                    carrito = c; // carga carrito existente
                    refrescarTablaItems();
                    anadirView.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero");
            }
        });
    }

    private void refrescarTablaItems() {
        modeloItems.setRowCount(0);
        for (ItemCarrito it : carrito.obtenerItems()) {
            modeloItems.addRow(new Object[]{
                    it.getProducto().getCodigo(),
                    it.getProducto().getNombre(),
                    it.getCantidad(),
                    it.getSubtotal()
            });
        }
        //titales
        double sub = carrito.calcularSubtotal();
        anadirView.getTxtSubtotal().setText(String.format("%.2f", sub));
        anadirView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
        anadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));
    }

    private void refrescarLista() {
        modeloList.setRowCount(0);
        List<Carrito> list = carritoDAO.listarTodos();
        for (Carrito c : list) {
            modeloList.addRow(new Object[]{
                    c.getCodigo(),
                    c.getFechaCreacion().getTime(),
                    c.calcularSubtotal(),
                    c.calcularIVA(),
                    c.calcularTotal()
            });
        }
    }

    private void guardarCarrito() {

        if (carrito.estaVacio()) {
            listarView.mostrarMensaje("El carrito está vacío.");
            return;
        }

        carrito.setUsuario(usuarioAutenticado);
        //guarda
        carritoDAO.crear(carrito);
        listarView.mostrarMensaje("Carrito registrado con éxito. Código: " + carrito.getCodigo());

        carrito = new Carrito();
        carrito.setUsuario(usuarioAutenticado);

        refrescarTablaItems();
        refrescarLista();
    }

}
