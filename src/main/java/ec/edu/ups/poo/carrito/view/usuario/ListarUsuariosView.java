package ec.edu.ups.poo.carrito.view.usuario;

import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class ListarUsuariosView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JButton btnElininar;
    private JButton btnListar;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblBuscar;
    private JComboBox cbxRol;
    private JLabel lblListarUsuARIOS;

    public ListarUsuariosView() {
        super("Listar Usuarios", true, true, true, true);



        cbxRol.setModel(new DefaultComboBoxModel<>(Rol.values()));
        tblUsuarios.setModel(new DefaultTableModel(
                new Object[]{"Usuario","Rol"}, 0
        ){
            @Override public boolean isCellEditable(int row, int col){
                return false;
            }
        });

        pack();
        setSize(600, 400);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setContentPane(panelPrincipal);

        URL elimURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnElininar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de basurero");
        }

        URL listarURL = PreguntasView.class.getClassLoader().getResource("imagenes/listaUsuarios.png");
        if (listarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(listarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnListar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de listaUsuarios");
        }

        URL buscarURL = PreguntasView.class.getClassLoader().getResource("imagenes/lupa.png");
        if (buscarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(buscarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnBuscar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de lupa");
        }

        URL listuURL = PreguntasView.class.getClassLoader().getResource("imagenes/listaUsuarios.png");
        if (listuURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(listuURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnListar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de lsit");
        }

        URL elimiURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimiURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimiURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnElininar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de basu");
        }


    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void setTblUsuarios(JTable tblUsuarios) {
        this.tblUsuarios = tblUsuarios;
    }

    public JButton getBtnListar() {
        return btnListar;
    }


    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnElininar() {
        return btnElininar;
    }

    public JComboBox getCbxRol() {
        return cbxRol;
    }

    public JLabel getLblListarUsuARIOS() {
        return lblListarUsuARIOS;
    }


}


