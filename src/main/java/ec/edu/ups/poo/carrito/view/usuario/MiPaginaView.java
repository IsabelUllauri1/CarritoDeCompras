package ec.edu.ups.poo.carrito.view.usuario;

import javax.swing.*;

public class MiPaginaView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField pwdContrasena;
    private JButton btnActualizarDatos;
    private JButton btnCerrarSesion;
    private JButton btnListarCarritos;
    private JLabel lblNombreCompleto;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JTextField txtNombreCompleto;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JSpinner spinnerFecha;

    public MiPaginaView() {
        super("Mi Pagina", false, true,true,true);
        setContentPane(panelPrincipal);
        pack();
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(600,600);
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinnerFecha = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(editor);
    }

    public JSpinner getSpinnerFecha() {
        return spinnerFecha;
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

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

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

    public JButton getBtnListarCarritos() {
        return btnListarCarritos;
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
