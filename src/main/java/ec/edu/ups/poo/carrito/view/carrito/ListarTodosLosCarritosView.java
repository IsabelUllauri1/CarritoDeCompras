package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class ListarTodosLosCarritosView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblUsuarios;
    private JButton btnVerDetalles;
    private JLabel lblVerTodosCarritos;
    private DefaultTableModel modelo;

    public ListarTodosLosCarritosView(){
        super("Listar Todos Los Carritos", true, true,true,true);

        setSize(400, 400);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"Código", "Fecha", "Usuario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(modelo);
        setContentPane(panelPrincipal);

        URL guardariURL = PreguntasView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (guardariURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(guardariURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnVerDetalles.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de detalles");
        }
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnVerDetalles() {
        return btnVerDetalles;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JLabel getLblVerTodosCarritos() {return lblVerTodosCarritos;}
}
