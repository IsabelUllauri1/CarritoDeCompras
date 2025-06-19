package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.view.ProductoActualizarView;

import javax.swing.*;

public class ProductoActualizarControlador {

    private final ProductoDAO             dao;
    private final ProductoActualizarView  view;

    public ProductoActualizarControlador(ProductoDAO dao, ProductoActualizarView view) {
        this.dao  = dao;
        this.view = view;
        configurarEventos();
    }

    private void configurarEventos() {
        // 1) Buscar por código
        view.getBtnBuscar().addActionListener(e -> buscarProducto());

        // 2) Actualizar datos
        view.getBtnActualizar().addActionListener(e -> actualizarProducto());

        // 3) Salir
        view.getBtnSalir().addActionListener(e -> view.dispose());
    }

    private void buscarProducto() {
        String codigoText = view.getTxtCodigoBuscar().getText().trim();
        if (codigoText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view.getPanelPrincipal(),
                    "Ingrese un código",
                    "Atención",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        int codigo = Integer.parseInt(codigoText);
        Producto p = dao.buscarPorCodigo(codigo);
        if (p == null) {
            JOptionPane.showMessageDialog(
                    view.getPanelPrincipal(),
                    "Producto no encontrado",
                    "Resultado",
                    JOptionPane.INFORMATION_MESSAGE
            );
            view.clearFields();
        } else {
            view.cargarProducto(p);
        }
    }

    private void actualizarProducto() {
        try {
            String n = view.getTxtNombre().getText().trim();
            String c = view.getTxtCodigoBuscar().getText().trim();
            String p = view.getTxtPrecio().getText().trim();

            if (n.isEmpty() || c.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Completa todos los campos",
                        "Atención",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(c);
            double precio = Double.parseDouble(p);

            Producto existe = dao.buscarPorCodigo(codigo);
            if (existe == null) {
                JOptionPane.showMessageDialog(view,
                        "No existe un producto con ese código",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.actualizar(new Producto(n, codigo, precio));
            JOptionPane.showMessageDialog(view,
                    "Producto actualizado",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,
                    "Código o precio inválidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
