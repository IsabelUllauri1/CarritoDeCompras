package ec.edu.ups.poo.carrito.view.login;

import javax.swing.*;

public class RegistrarseView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel lblCont;
    private JLabel lblUusuario;
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

    public JLabel getLblUusuario() {
        return lblUusuario;
    }

    public JLabel getLblCont() {
        return lblCont;
    }

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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
