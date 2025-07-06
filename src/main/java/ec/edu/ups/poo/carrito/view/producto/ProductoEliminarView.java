package ec.edu.ups.poo.carrito.view.producto;

import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ProductoEliminarView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnEliminar;
    private JButton btnSalir;
    private JPanel panelPrincipal;
    private JLabel lblBuscar;
    private JLabel lblEliminarProducto;
    private DefaultTableModel modelo;
    private FormatosUtils formatosUtils;

    public ProductoEliminarView() {
        super("Eliminar Producto", true, true, true, true);
        setSize(500, 500);
        modelo = new DefaultTableModel(
                new Object[]{"","",""}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        tblProductos.setModel(modelo);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        URL lupaURL = PreguntasView.class.getClassLoader().getResource("imagenes/lupa.png");
        if (lupaURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(lupaURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnBuscar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de lupa");
        }
        URL elimURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnEliminar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de basurero");
        }

        URL closeURL = PreguntasView.class.getClassLoader().getResource("imagenes/close.png");
        if (closeURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(closeURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnSalir.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de close");
        }
    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("producto.eliminar.titulo"));

        lblEliminarProducto.setText(mh.get("producto.eliminar.titulo"));
        lblBuscar.setText(mh.get("etiqueta.buscarporcodigo"));

        btnBuscar.setText(mh.get("boton.buscar"));
        btnEliminar.setText(mh.get("boton.eliminar"));
        btnSalir.setText(mh.get("boton.salir"));

        // Cambiar encabezados de la tabla
        modelo.setColumnIdentifiers(new Object[]{
                mh.get("etiqueta.codigo"),
                mh.get("etiqueta.nombre"),
                mh.get("etiqueta.precio")
        });
    }


    public void cargarDatos(List<Producto> productos) {
        modelo.setRowCount(0);
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), formatosUtils.formatearMoneda(p.getPrecio(), Locale.getDefault())
            });
        }
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JLabel getLblBuscar() {return lblBuscar;}

    public JLabel getLblEliminarProducto() {return lblEliminarProducto;}
}
