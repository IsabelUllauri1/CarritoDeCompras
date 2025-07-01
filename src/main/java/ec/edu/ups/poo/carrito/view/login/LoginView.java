package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JPanel panelSecundario;
    private JButton btnOlvide;
    private JLabel lblIniciarSesion;
    private JLabel lblUsuario;
    private JLabel lblContrasena;

    public LoginView( ) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);

        URL loginURL = LoginView.class.getClassLoader().getResource("login.html");
        if (loginURL != null) {
            ImageIcon iconIS = new ImageIcon(loginURL);
            btnIniciarSesion.setIcon(iconIS);
        }else {
            System.err.println("Error: Login URL not found");
        }
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }


    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JPanel getPanelSecundario() {
        return panelSecundario;
    }

    public void setPanelSecundario(JPanel panelSecundario) {
        this.panelSecundario = panelSecundario;
    }

    public JButton getBtnOlvide() {return btnOlvide;}

    public JLabel getLblUsuario() {return lblUsuario;}

    public JLabel getLblIniciarSesion() {return lblIniciarSesion;}

    public JLabel getLblContrasena() {
        return lblContrasena;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void cambiarIdioma(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler){
        lblUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usu"));
        lblContrasena.setText(mensajeInternacionalizacionHandler.get("menu.contrasena"));

    }
}
