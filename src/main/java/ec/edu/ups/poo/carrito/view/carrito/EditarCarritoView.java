package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class EditarCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblItems;
    private JButton btnEditarCantidad;
    private JButton btnEliminarItem;
    private JButton btnAgregar;
    private JLabel lblEditarCarrito;

    public EditarCarritoView() {

        setContentPane(panelPrincipal);
        setSize(500, 500);

        URL addURL = PreguntasView.class.getClassLoader().getResource("imagenes/add.png");
        if (addURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(addURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnAgregar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de add");
        }

        URL elimitURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimitURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimitURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnEliminarItem.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de basurero");
        }

        URL editURL = PreguntasView.class.getClassLoader().getResource("imagenes/edit.png");
        if (editURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(editURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnEditarCantidad.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de edit");
        }
    }

    public JLabel getLblEditarCarrito() {return lblEditarCarrito;}
}
