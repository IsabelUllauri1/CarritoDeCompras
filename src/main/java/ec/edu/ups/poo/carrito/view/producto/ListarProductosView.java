package ec.edu.ups.poo.carrito.view.producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarProductosView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblProductos;

    public ListarProductosView() {
        super("Listar Productos", true, true, true,true);
        panelPrincipal = new JPanel();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Código", "Nombre", "Cantidad", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) {

                return false;
            }
        };

        tblProductos.setModel(model);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
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

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }
}
