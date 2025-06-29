package ec.edu.ups.poo.carrito.view.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarTodosLosCarritosView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JButton btnVerDetalles;
    private DefaultTableModel modelo;

    public ListarTodosLosCarritosView(){
        super("Listar Todos Los Carritos", true, true,true,true);
        setContentPane(panelPrincipal);
        setSize(400, 400);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"Código", "Fecha", "Usuario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(modelo);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnVerDetalles() {
        return btnVerDetalles;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
