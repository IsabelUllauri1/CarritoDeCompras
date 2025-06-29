package ec.edu.ups.poo.carrito.view.usuario;

import javax.swing.*;

public class MiPaginaView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField pwdContrasena;
    private JButton btnActualizarDatos;
    private JButton btnCerrarSesion;
    private JButton btnListarCarritos;

    public MiPaginaView() {
        super("Mi Pagina", true, true,true,true);
        setContentPane(panelPrincipal);
        pack();
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(500,500);

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
