package ec.edu.ups.poo.carrito.view;

import ec.edu.ups.poo.carrito.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ProductoEliminarView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnEliminar;
    private JButton btnSalir;
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;

    public ProductoEliminarView() {
        super("Eliminar Producto", true, true,
                true,
                true);
        modelo = new DefaultTableModel(
                new Object[]{"Código","Nombre","Precio"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        tblProductos.setModel(modelo);
        setContentPane(panelPrincipal);  // panelPrincipal es tu formulario
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void cargarDatos(List<Producto> productos) {
        modelo.setRowCount(0);
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getPrecio()
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
}
