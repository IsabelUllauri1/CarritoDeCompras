package ec.edu.ups.poo.carrito.view.usuario;

import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CrearUsuarioView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuarioNuevo;
    private JButton btnGuardar;
    private JButton btnSalir;
    private JPasswordField pwdContrasenaNueva;
    private JComboBox cbxRol;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JLabel lblRol;
    private JLabel lblCrearUsuario;


    public CrearUsuarioView() {
        super("Crear Usuario", true, true, true);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(600, 400);

        URL guardarURL = PreguntasView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(guardarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnGuardar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de guardar");
        }

        URL salirURL = PreguntasView.class.getClassLoader().getResource("imagenes/close.png");
        if (salirURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(salirURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnSalir.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de close");
        }

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

    public void setPwdContrasenaNueva(JPasswordField pwdContrasenaNueva) {this.pwdContrasenaNueva = pwdContrasenaNueva;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public JLabel getLblCrearUsuario() {
        return lblCrearUsuario;
    }

    public JLabel getLblRol() {
        return lblRol;
    }

    public JLabel getLblContrasena() {
        return lblContrasena;
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
