package ec.edu.ups.poo.carrito.view.usuario;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class EditarUsuarioView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField pwdNContrasena;
    private JButton btnGuardar;
    private JComboBox cbxRol;
    private JLabel lblRol;
    private JLabel lblNuevaContrasena;
    private JLabel lblNuevoNombre;
    private JLabel lblEditarUsuario;
    private JSpinner spinnerFecha;

    public EditarUsuarioView() {
        setSize(500, 500);
        setResizable(false);
        setContentPane(panelPrincipal);

        URL guardarURL = PreguntasView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(guardarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnGuardar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de guardar");
        }
    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("usuario.editar.titulo"));

        lblEditarUsuario.setText(mh.get("usuario.editar.titulo"));
        lblNuevoNombre.setText(mh.get("etiqueta.usuario"));
        lblNuevaContrasena.setText(mh.get("etiqueta.nuevaContrasena"));
        lblRol.setText(mh.get("etiqueta.rol"));

        btnGuardar.setText(mh.get("boton.guardar"));

        // Traducir combo de roles
        cbxRol.removeAllItems();
        cbxRol.addItem(mh.get("rol.administrador"));
        cbxRol.addItem(mh.get("rol.usuario"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JSpinner getSpinnerFecha() { return spinnerFecha;}

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

    public JLabel getLblRol() {return lblRol;
    }

    public JLabel getLblEditarUsuario() {return lblEditarUsuario;
    }

    public JLabel getLblNuevaContrasena() {return lblNuevaContrasena;
    }

    public JLabel getLblNuevoNombre() {return lblNuevoNombre;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
