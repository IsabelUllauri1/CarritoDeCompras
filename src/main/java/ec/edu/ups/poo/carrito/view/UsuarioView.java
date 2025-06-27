package ec.edu.ups.poo.carrito.view;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.security.PrivateKey;

public class UsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JButton btnAcceder;
    private JTable tblUsuarios;
    private JButton button1;
    private DefaultTableModel modelo;
    private JLabel lblUsuario;
    public UsuarioView() {
        super("Listar Usuarios", true, true, true, true);
        inicializarComponentes();
        setContentPane(panelPrincipal);
        pack();
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        modelo = new DefaultTableModel(new Object[]{"Username", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tblUsuarios);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JLabel getLblUsuario() {return lblUsuario;    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }
    public void cambiarIdioma(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        lblUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usu"));

    }
}
