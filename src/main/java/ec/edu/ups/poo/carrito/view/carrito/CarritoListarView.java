package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;


public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnEliminar;
    private JButton btnDetalless;
    private JButton btnModificar;
    private JLabel lblListarCarritos;

    public CarritoListarView() {
        super("Listar Carritos", true, true, true, true);
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Código", "Nombre", "Cantidad", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) {

                return false;
            }
        };
        tblCarritos.setModel(modelo);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        pack();

        URL elimURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnEliminar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de eliminar");
        }

        URL detURL = PreguntasView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (detURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(detURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnEliminar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de deta");
        }

        URL editURL = PreguntasView.class.getClassLoader().getResource("imagenes/edit.png");
        if (editURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(editURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnModificar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de edit");
        }
    }
    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.listar.titulo")); // título de la ventana (si aplica)

        lblListarCarritos.setText(mh.get("etiqueta.listar.carritos"));

        btnEliminar.setText(mh.get("boton.eliminar"));
        btnDetalless.setText(mh.get("boton.detalles"));
        btnModificar.setText(mh.get("boton.modificar"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnDetalless() {return btnDetalless;   }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public JLabel getLblListarCarritos() {return lblListarCarritos;}

    public void mostrarMensaje(String msg) { JOptionPane.showMessageDialog(this, msg); }

}
