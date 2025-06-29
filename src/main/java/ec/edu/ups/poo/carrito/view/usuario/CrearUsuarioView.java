package ec.edu.ups.poo.carrito.view.usuario;

import javax.swing.*;

public class CrearUsuarioView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuarioNuevo;
    private JButton btnGuardar;
    private JButton btnSalir;
    private JPasswordField pwdContrasenaNueva;
    private JComboBox cbxRol;


    public CrearUsuarioView() {
        super("Crear Usuario", true, true, true);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(600, 400);

    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnSalir() {return btnSalir;}

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
