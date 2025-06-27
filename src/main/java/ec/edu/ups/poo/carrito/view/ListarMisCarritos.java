package ec.edu.ups.poo.carrito.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarMisCarritos extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnVerDetalles;
    private JButton btnEliminar;
    private DefaultTableModel modelo;
    public ListarMisCarritos() {
        super("Listar Mis Carritos", true, true, true, true);
        modelo = new DefaultTableModel(new Object[]{"Código","Fecha","Total"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        tblCarritos.setModel(modelo);
        setContentPane(panelPrincipal);
        pack();
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);


    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnVerDetalles() {
        return btnVerDetalles;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }
}
