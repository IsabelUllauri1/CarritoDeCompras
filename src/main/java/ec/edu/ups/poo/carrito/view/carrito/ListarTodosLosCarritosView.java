package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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

        modelo = new DefaultTableModel(0, 3) {
            @Override public boolean isCellEditable(int r, int c){ return false; }
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

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("carrito.listartodos.titulo"));

        lblVerTodosCarritos.setText(mh.get("carrito.listartodos.titulo"));

        btnVerDetalles.setText(mh.get("boton.verDetalles"));
        actualizarColumnas(mh);
    }

    public void actualizarColumnas(MensajeInternacionalizacionHandler mh) {
        TableColumnModel columnModel = tblUsuarios.getColumnModel();
        columnModel.getColumn(0).setHeaderValue(mh.get("tabla.codigo"));
        columnModel.getColumn(1).setHeaderValue(mh.get("tabla.fecha"));
        columnModel.getColumn(2).setHeaderValue(mh.get("tabla.Usuario"));
        tblUsuarios.getTableHeader().repaint();
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
