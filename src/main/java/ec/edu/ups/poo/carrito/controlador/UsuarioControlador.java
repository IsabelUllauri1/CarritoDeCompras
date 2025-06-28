package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.carrito.ListarMisCarritos;
import ec.edu.ups.poo.carrito.view.MiPaginaView;
import ec.edu.ups.poo.carrito.view.carrito.VerDetalleView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;

public class UsuarioControlador {
    private final Usuario usuario;
    private final CarritoDAO carritoDAO;
    private final MiPaginaView miPaginaView;
    private final ListarMisCarritos listarView;
    private final VerDetalleView verDetalleView;

    public UsuarioControlador(Usuario usuario, CarritoDAO carritoDAO, MiPaginaView miPaginaView, ListarMisCarritos listarView, VerDetalleView verDetalleView) {
        this.usuario  = usuario;
        this.carritoDAO = carritoDAO;
        this.miPaginaView = miPaginaView;
        this.listarView = listarView;
        this.verDetalleView  = verDetalleView;
        listeners();
    }

    private void listeners() {
        miPaginaView.getBtnListarCarritos().addActionListener(e -> listarCarritosMet(e));
        miPaginaView.getBtnActualizarDatos().addActionListener(e -> actualizarDatosMet(e));
        miPaginaView.getBtnCerrarSesion().addActionListener(e -> miPaginaView.dispose());

        listarView.getBtnVerDetalles().addActionListener(e -> verDetallesMet(e));
        listarView.getBtnEliminar().addActionListener(e -> eliminarCarritoMet(e));
    }

    private void listarCarritosMet(ActionEvent e) {
        List<Carrito> todos = carritoDAO.listarTodos();
        var modelo = (DefaultTableModel) listarView.getTblCarritos().getModel();
        modelo.setRowCount(0);
        for (Carrito c : todos) {
            if (c.getUsuario().equals(usuario)) {
                modelo.addRow(new Object[]{
                        c.getCodigo(),
                        c.getFechaCreacion().getTime(),
                        c.calcularSubtotal(),
                        c.calcularIVA(),
                        c.calcularTotal()
                });
            }
        }
        showInternalFrame(listarView);
    }

    private void actualizarDatosMet(ActionEvent e) {
        String nu = miPaginaView.getTxtUsuario().getText().trim();
        String np = new String(miPaginaView.getPwdContrasena().getPassword()).trim();
        if (nu.isEmpty() || np.isEmpty()) {
            miPaginaView.mostrarMensaje("Completa ambos campos");
            return;
        }
        usuario.setUsername(nu);
        usuario.setContrasenia(np);
        miPaginaView.mostrarMensaje("Datos actualizados");
    }

    private void verDetallesMet(ActionEvent e) {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero");
            return;
        }
        int codigo = (int) listarView.getTblCarritos().getValueAt(row, 0);
        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        var dm = (DefaultTableModel) verDetalleView.getTblProductos().getModel();
        dm.setRowCount(0);
        c.obtenerItems().forEach(it ->
                dm.addRow(new Object[]{
                        it.getProducto().getCodigo(),
                        it.getProducto().getNombre(),
                        it.getCantidad(),
                        it.getSubtotal()
                })
        );
        verDetalleView.getTxtSubtotal().setText(String.valueOf(c.calcularSubtotal()));
        verDetalleView.getTxtIVA().setText(String.valueOf(c.calcularIVA()));
        verDetalleView.getTxtTotal().setText(String.valueOf(c.calcularTotal()));
        showInternalFrame(verDetalleView);
    }

    private void eliminarCarritoMet(ActionEvent e) {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row<0) {
            JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero");
            return;
        }
        int codigo = (int) listarView.getTblCarritos().getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(
                listarView,
                "¿Eliminar carrito #" + codigo + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(codigo);
            ((DefaultTableModel)listarView.getTblCarritos().getModel()).removeRow(row);
        }
    }

    private void showInternalFrame(JInternalFrame f) {
        if (f.isClosed()) {
            miPaginaView.getDesktopPane().add(f);
        }
        f.setVisible(true);
        try { f.setSelected(true); } catch(PropertyVetoException ignore){}
    }
}
