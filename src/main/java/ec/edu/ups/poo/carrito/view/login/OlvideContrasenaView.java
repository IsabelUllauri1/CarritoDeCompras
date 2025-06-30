package ec.edu.ups.poo.carrito.view.login;

import javax.swing.*;

public class OlvideContrasenaView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtUser;
    private JTextField txtPregunta;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JButton btnBuscarUsuario;
    private JPasswordField pwdNueva;

    public OlvideContrasenaView() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    public JPasswordField getPwdNueva() {return pwdNueva;}

    public JPanel getPanelPrincipal() {return panelPrincipal;}

    public JButton getBtnBuscarUsuario() {return btnBuscarUsuario;}

    public JButton getBtnGuardar() {return btnGuardar;}

    public JTextField getTxtRespuesta() {return txtRespuesta;}

    public JTextField getTxtPregunta() {return txtPregunta;}

    public JTextField getTxtUser() {return txtUser;}
}
