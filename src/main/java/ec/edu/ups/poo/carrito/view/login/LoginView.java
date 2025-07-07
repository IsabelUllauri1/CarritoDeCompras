package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.Principal;

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
    private JMenuBar menuBar;
    private JMenu menuIdioma;
    private JMenuItem itemEspanol;
    private JMenuItem itemIngles;
    private JMenuItem itemAleman;


    public LoginView( ) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        menuBar = new JMenuBar();
        menuIdioma = new JMenu("Idioma");

        itemEspanol = new JMenuItem();
        itemIngles = new JMenuItem();
        itemAleman  = new JMenuItem();

        menuIdioma.add(itemEspanol);
        menuIdioma.add(itemIngles);
        menuIdioma.add(itemAleman);

        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);
        cargarIconosIdiomas();
        ponerIcono(btnIniciarSesion, "imagenes/login.png", 20, 20, "Login");
        ponerIcono(btnRegistrarse, "imagenes/registarse.png", 25, 25, "Registrarse");
        ponerIcono(btnOlvide, "imagenes/pregunta.png", 20, 20, "Olvidé contraseña");

    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("login.titulo"));

        lblIniciarSesion.setText(mh.get("login.lblTitulo"));
        lblUsuario.setText(mh.get("login.lblUsuario"));
        lblContrasena.setText(mh.get("login.lblContrasena"));

        btnIniciarSesion.setText(mh.get("boton.iniciarSesion"));
        btnRegistrarse.setText(mh.get("boton.registrarse"));
        btnOlvide.setText(mh.get("boton.olvideContrasena"));
        menuIdioma.setText(mh.get("menu.idioma"));

    }
    private void ponerIcono(JButton boton, String ruta, int ancho, int alto, String nombreAlternativo) {
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de " + nombreAlternativo);
        }
    }

    private void cargarIconosIdiomas() {
        ponerIcono(itemEspanol, "imagenes/bandera.png");
        ponerIcono(itemIngles, "imagenes/USA_Flag.png");
        ponerIcono(itemAleman, "imagenes/alemania.png");

    }

    private void ponerIcono(JMenuItem item, String ruta) {
        URL url = Principal.class.getClassLoader().getResource(ruta);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image imgEscalada = original.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            item.setIcon(new ImageIcon(imgEscalada));
        } else {
            System.err.println("No se pudo cargar el ícono de " + ruta);
        }
    }

    public JMenu getMenuIdioma() {return menuIdioma;}

    public JMenuItem getItemEspanol() {return itemEspanol;}

    public JMenuItem getItemIngles() {return itemIngles;}

    public JMenuItem getItemAleman() {return itemAleman;}

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
