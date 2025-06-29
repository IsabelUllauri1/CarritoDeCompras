package ec.edu.ups.poo.carrito.view.usuario;

import javax.swing.*;

public class CrearUsuarioView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuarioNuevo;
    private JButton guardarButton;
    private JButton btnGuardar;
    private JPasswordField pwdContrasenaNueva;
    private JComboBox cbxRol;


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public void setGuardarButton(JButton guardarButton) {
        this.guardarButton = guardarButton;
    }

    public JTextField getTxtUsuarioNuevo() {
        return txtUsuarioNuevo;
    }

    public void setTxtUsuarioNuevo(JTextField txtUsuarioNuevo) {
        this.txtUsuarioNuevo = txtUsuarioNuevo;
    }

    public JPasswordField getPwdContrasenaNueva() {
        return pwdContrasenaNueva;
    }

    public void setPwdContrasenaNueva(JPasswordField pwdContrasenaNueva) {
        this.pwdContrasenaNueva = pwdContrasenaNueva;
    }

    public JComboBox getCbxRol() {
        return cbxRol;
    }

    public void setCbxRol(JComboBox cbxRol) {
        this.cbxRol = cbxRol;
    }
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
