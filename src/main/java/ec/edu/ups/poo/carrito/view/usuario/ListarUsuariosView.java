package ec.edu.ups.poo.carrito.view.usuario;

import ec.edu.ups.poo.carrito.modelo.Rol;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarUsuariosView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JButton btnElininar;
    private JButton btnListar;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblBuscar;
    private JComboBox cbxRol;
    private JLabel lblListarUsuARIOS;

    public ListarUsuariosView() {
        super("Listar Usuarios", true, true, true, true);



        cbxRol.setModel(new DefaultComboBoxModel<>(Rol.values()));
        tblUsuarios.setModel(new DefaultTableModel(
                new Object[]{"Usuario","Rol"}, 0
        ){
            @Override public boolean isCellEditable(int row, int col){
                return false;
            }
        });

        pack();
        setSize(600, 400);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setContentPane(panelPrincipal);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void setTblUsuarios(JTable tblUsuarios) {
        this.tblUsuarios = tblUsuarios;
    }

    public JButton getBtnListar() {
        return btnListar;
    }


    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnElininar() {
        return btnElininar;
    }

    public JComboBox getCbxRol() {
        return cbxRol;
    }

    public JLabel getLblListarUsuARIOS() {
        return lblListarUsuARIOS;
    }
}


