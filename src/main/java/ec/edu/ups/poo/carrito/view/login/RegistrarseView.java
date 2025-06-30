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

    public RegistrarseView() {
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
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
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
