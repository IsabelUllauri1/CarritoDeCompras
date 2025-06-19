package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.view.ProductoEliminarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductoEliminarControlador {

    private final ProductoDAO        dao;
    private final ProductoEliminarView view;
    private final DefaultTableModel  modelo;

    public ProductoEliminarControlador(ProductoDAO dao,
                                       ProductoEliminarView view) {
        this.dao   = dao;
        this.view  = view;
        this.modelo = (DefaultTableModel) view.getTblProductos().getModel();
        configurarEventos();
        view.cargarDatos(dao.listarTodos());
    }

    private void configurarEventos() {
        // 1) Buscar por código y mostrar en la tabla
        view.getBtnBuscar().addActionListener(e -> buscarYMostrar());

        // 2) Eliminar el producto seleccionado o buscado
        view.getBtnEliminar().addActionListener(e -> eliminarProducto());

        // 3) Cerrar ventana
        view.getBtnSalir().addActionListener(e -> view.dispose());
    }

    private void buscarYMostrar() {
        String txt = view.getTxtBuscar().getText().trim();
        if (txt.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view.getPanelPrincipal(),
                    "Ingrese un código",
                    "Atención",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        try {
            int codigo = Integer.parseInt(txt);
            Producto p = dao.buscarPorCodigo(codigo);
            view.getModelo().setRowCount(0);
            if (p != null) {
                view.getModelo().addRow(new Object[]{
                        p.getCodigo(), p.getNombre(), p.getPrecio()
                });
            } else {
                JOptionPane.showMessageDialog(
                        view.getPanelPrincipal(),
                        "Producto no encontrado",
                        "Resultado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    view.getPanelPrincipal(),
                    "Código inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarProducto() {
        DefaultTableModel m = view.getModelo();
        if (m.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    view.getPanelPrincipal(),
                    "Primero busque o liste un producto",
                    "Atención",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        int codigo = (int) m.getValueAt(0, 0);
        int resp = JOptionPane.showConfirmDialog(
                view.getPanelPrincipal(),
                "¿Eliminar producto código " + codigo + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        if (resp == JOptionPane.YES_OPTION) {
            dao.eliminar(codigo);
            // refresca tabla tras eliminación
            view.cargarDatos(dao.listarTodos());
            JOptionPane.showMessageDialog(
                    view.getPanelPrincipal(),
                    "Producto eliminado",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
