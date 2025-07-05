package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class ListarMisCarritos extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnVerDetalles;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    private JLabel lbMisCarritos;
    private DefaultTableModel modelo;
    public ListarMisCarritos() {
        super("Listar Mis Carritos", true, true, true, true);
        modelo = new DefaultTableModel(new Object[]{"Código","Fecha","Total"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        tblCarritos.setModel(modelo);
        setContentPane(panelPrincipal);
        pack();
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        URL buscarURL = PreguntasView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (buscarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(buscarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnVerDetalles.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de detalles");
        }

        URL refURL = PreguntasView.class.getClassLoader().getResource("imagenes/actualizar.png");
        if (refURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(refURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnRefrescar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de refrescar");
        }

        URL elimURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnEliminar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de eliminar");
        }


    }

    public JButton getBtnRefrescar() {return btnRefrescar;}

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnVerDetalles() {
        return btnVerDetalles;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public JLabel getLbMisCarritos() {return lbMisCarritos;}
}
