package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
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

        URL loginURL = LoginView.class.getClassLoader().getResource("imagenes/login.png");
        if (loginURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(loginURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnIniciarSesion.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL registrarseURL = LoginView.class.getClassLoader().getResource("imagenes/registarse.png");
        if (registrarseURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(registrarseURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnRegistrarse.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
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
