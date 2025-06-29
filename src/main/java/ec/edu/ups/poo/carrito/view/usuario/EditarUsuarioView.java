package ec.edu.ups.poo.carrito.view.usuario;

import javax.swing.*;

public class EditarUsuarioView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField pwdNContrasena;
    private JButton btnGuardar;
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

    public JPasswordField getPwdNContrasena() {
        return pwdNContrasena;
    }

    public void setPwdNContrasena(JPasswordField pwdNContrasena) {
        this.pwdNContrasena = pwdNContrasena;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
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
