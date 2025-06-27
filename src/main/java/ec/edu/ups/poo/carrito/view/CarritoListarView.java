package ec.edu.ups.poo.carrito.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    private JButton btnModificar;
    public CarritoListarView() {
        super("Listar Carritos", true, true, true, true);
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Código", "Nombre", "Cantidad", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) {

                return false;
            }
        };
        tblCarritos.setModel(modelo);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        pack();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnRefrescar() {
        return btnRefrescar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public void mostrarMensaje(String msg) { JOptionPane.showMessageDialog(this, msg); }

}
