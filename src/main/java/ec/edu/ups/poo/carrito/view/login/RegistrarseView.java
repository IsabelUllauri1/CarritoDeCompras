package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class RegistrarseView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel lblCont;
    private JLabel lblUsuario;
    private JButton btnSiguiente;
    private JButton btnRegresar;
    private JLabel lblNombreComp;
    private JTextField txtNombre;
    private JLabel lblFecha;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JSpinner spinnerFecha;
    private JLabel lblddmmyyyy;
    private JLabel lblRegistarse;
    private JTextField txtFechaN;

    public RegistrarseView() {
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        SpinnerDateModel model = new SpinnerDateModel();
        spinnerFecha.setModel(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(editor);

        URL atrasURL = PreguntasView.class.getClassLoader().getResource("imagenes/atras.png");
        if (atrasURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(atrasURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnRegresar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de atras");
        }

        URL sigURL = PreguntasView.class.getClassLoader().getResource("imagenes/siguiente.png");
        if (sigURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(sigURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnSiguiente.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de siguiente");
        }
    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("usuario.registarse.titulo"));

        lblRegistarse.setText(mh.get("usuario.registarse.titulo"));
        lblCont.setText(mh.get("etiqueta.registrarse"));
        lblUsuario.setText(mh.get("etiqueta.Usuario"));
        lblNombreComp.setText(mh.get("etiqueta.NombreComp"));
        lblFecha.setText(mh.get("etiqueta.fechadenacimiento"));
        lblTelefono.setText(mh.get("etiqueta.Telefono"));
        lblCorreo.setText(mh.get("etiqueta.Correo"));

        btnSiguiente.setText(mh.get("boton.siguiente"));
        btnRegresar.setText(mh.get("boton.regresar"));
    }


    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JSpinner getSpinnerFecha() {
        return spinnerFecha;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }


    public JLabel getLblCont() {return lblCont;}

    public JTextField getTextField1() {
        return textField1;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public JLabel getLblNombreComp() {
        return lblNombreComp;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtFechaN() {
        return txtFechaN;
    }

    public JLabel getLblddmmyyyy() {return lblddmmyyyy;}

    public JLabel getLblUsuario() {return lblUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
