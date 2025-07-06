package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class OlvideContrasenaView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtUser;
    private JTextField txtPregunta;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JButton btnBuscarUsuario;
    private JLabel lblSiDesea;
    private JLabel lblUser;
    private JLabel lblPreguntaRecuperacion;
    private JLabel lblRespuesta;
    private JLabel lblOlvideContrasena;
    private JPasswordField pwdNueva;

    public OlvideContrasenaView() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(600,300);
        setResizable(false);

        URL loginURL = OlvideContrasenaView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (loginURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(loginURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
            btnGuardar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("olvide.titulo"));

        lblOlvideContrasena.setText(mh.get("etiqueta.OlvideContrasena"));
        lblUser.setText(mh.get("etiqueta.Usuario"));
        lblPreguntaRecuperacion.setText(mh.get("etiqueta.PreguntaRecuperacion"));
        lblRespuesta.setText(mh.get("etiqueta.Respuesta"));
        lblSiDesea.setText(mh.get("etiqueta.SiDesea"));

        btnBuscarUsuario.setText(mh.get("boton.buscarUsuario"));
        btnGuardar.setText(mh.get("boton.guardarNuevaContrasena"));
    }


    public JPasswordField getPwdNueva() {return pwdNueva;}

    public JPanel getPanelPrincipal() {return panelPrincipal;}

    public JButton getBtnBuscarUsuario() {return btnBuscarUsuario;}

    public JButton getBtnGuardar() {return btnGuardar;}

    public JTextField getTxtRespuesta() {return txtRespuesta;}

    public JTextField getTxtPregunta() {return txtPregunta;}

    public JTextField getTxtUser() {return txtUser;}

    public JLabel getLblPreguntaRecuperacion() {return lblPreguntaRecuperacion;
    }

    public JLabel getLblRespuesta() {return lblRespuesta;
    }

    public JLabel getLblUser() {return lblUser;
    }
    public JLabel getLblSiDesea() {return lblSiDesea;}

    public JLabel getLblOlvideContrasena() {
        return lblOlvideContrasena;
    }
}
