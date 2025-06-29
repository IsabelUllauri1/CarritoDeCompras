package ec.edu.ups.poo.carrito.view.usuario;

import javax.swing.*;

public class ListarUsuariosView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JButton btnElininar;
    private JButton buscarButton;
    private JButton btnListar;
    private JButton btnListarRol;

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


    public JButton getBtnElininar() {
        return btnElininar;
    }

    public JButton getBtnListarRol() {
        return btnListarRol;
    }
}


