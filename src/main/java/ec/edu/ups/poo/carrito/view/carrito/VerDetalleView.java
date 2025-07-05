package ec.edu.ups.poo.carrito.view.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VerDetalleView extends  JInternalFrame{

    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JLabel lblVerDetalles;


    public VerDetalleView() {
        super("Detalle View", true, true, true, true);
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Código", "Nombre", "Cantidad", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) {

                return false;
            }
        };
        tblProductos.setModel(modelo);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        pack();

    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JLabel getLblSubtotal() {return lblSubtotal;}

    public JLabel getLblTotal() {return lblTotal;}

    public JLabel getLblIVA() {return lblIVA;}

    public JLabel getLblVerDetalles() {return lblVerDetalles;}
}
