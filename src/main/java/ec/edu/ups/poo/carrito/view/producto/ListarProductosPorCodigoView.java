package ec.edu.ups.poo.carrito.view.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarProductosPorCodigoView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtBuscar;
    private JButton btnBuscar;

    public ListarProductosPorCodigoView() {
        super("Listar Productos", true, true, true, true);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Código", "Nombre", "Precio"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblProductos.setModel(model);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(400, 300);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }
}
