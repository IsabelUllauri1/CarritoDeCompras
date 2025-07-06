package ec.edu.ups.poo.carrito.view.usuario;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.DateFormat;

public class MiPaginaView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField pwdContrasena;
    private JButton btnActualizarDatos;
    private JButton btnCerrarSesion;
    private JLabel lblNombreCompleto;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JTextField txtNombreCompleto;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JSpinner spinnerFecha;
    private JLabel lblMiPagina;
    private JButton btnResponderPreguntas;
    private JLabel lblUsuaraio;
    private JLabel lblContrasena;

    public MiPaginaView() {
        super("Mi Pagina", false, true,true,true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(600,600);
        SpinnerDateModel model = new SpinnerDateModel();
        spinnerFecha.setModel(model);
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));


        URL actualizarURL = PreguntasView.class.getClassLoader().getResource("imagenes/actualizarD.png");
        if (actualizarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(actualizarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            btnActualizarDatos.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de actuD");
        }

        URL salirURL = PreguntasView.class.getClassLoader().getResource("imagenes/logout.png");
        if (salirURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(salirURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            btnCerrarSesion.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de logout");
        }

        URL preguntasURL = PreguntasView.class.getClassLoader().getResource("imagenes/listaUsuarios.png");
        if (preguntasURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(preguntasURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            btnResponderPreguntas.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de preguntas");
        }
    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("usuario.miPagina.titulo"));

        lblMiPagina.setText(mh.get("usuario.miPagina.titulo"));
        lblUsuaraio.setText(mh.get("etiqueta.Usuario"));
        lblContrasena.setText(mh.get("etiqueta.contrasena"));
        lblNombreCompleto.setText(mh.get("etiqueta.NombreComp"));
        lblTelefono.setText(mh.get("etiqueta.Telefono"));
        lblCorreo.setText(mh.get("etiqueta.Correo"));
        lblFechaNacimiento.setText(mh.get("etiqueta.fechadenacimiento"));

        btnActualizarDatos.setText(mh.get("boton.actualizar"));
        btnCerrarSesion.setText(mh.get("boton.cerrarSesion"));
        btnResponderPreguntas.setText(mh.get("boton.responderPreguntas"));

    }


    public JSpinner getSpinnerFecha() {
        return spinnerFecha;
    }

    public void setSpinnerFecha(JSpinner spinnerFecha) {
        this.spinnerFecha = spinnerFecha;
    }

    public JLabel getLblNombreCompleto() {
        return lblNombreCompleto;
    }


    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JLabel getLblMiPagina() {
        return lblMiPagina;
    }

    public JButton getBtnResponderPreguntas() {
        return btnResponderPreguntas;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {this.panelPrincipal = panelPrincipal;}

    public JLabel getLblUsuaraio() {return lblUsuaraio;}

    public JLabel getLblContrasena() {return lblContrasena;}

    public void setPwdContrasena(JPasswordField pwdContrasena) {
        this.pwdContrasena = pwdContrasena;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }


    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public void setTxtNombreCompleto(JTextField txtNombreCompleto) {
        this.txtNombreCompleto = txtNombreCompleto;
    }

    public JTextField getTxtNombreCompleto() {
        return txtNombreCompleto;
    }

    public JLabel getLblFechaNacimiento() {
        return lblFechaNacimiento;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }

    public JButton getBtnActualizarDatos() {
        return btnActualizarDatos;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getPwdContrasena() {
        return pwdContrasena;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);

    }
}
