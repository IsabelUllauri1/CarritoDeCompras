package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.net.URL;
import java.util.Locale;

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
        setSize(600, 400);
        modelo = new DefaultTableModel(0, 3) {
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        tblCarritos.setModel(modelo);
        setContentPane(panelPrincipal);
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


    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.todos.titulo"));

        lbMisCarritos.setText(mh.get("carrito.todos.titulo"));

        btnVerDetalles.setText(mh.get("boton.verDetalles"));
        btnEliminar.setText(mh.get("boton.eliminar"));
        btnRefrescar.setText(mh.get("boton.refrescar"));
        actualizarColumnas(mh);

    }
    public void actualizarColumnas(MensajeInternacionalizacionHandler mh) {
        TableColumnModel columnModel = tblCarritos.getColumnModel();
        columnModel.getColumn(0).setHeaderValue(mh.get("tabla.codigo"));
        columnModel.getColumn(1).setHeaderValue(mh.get("tabla.fecha"));
        columnModel.getColumn(2).setHeaderValue(mh.get("tabla.total"));
        tblCarritos.getTableHeader().repaint();
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
