package ec.edu.ups.poo.carrito.view.producto;

import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class ListarProductosPorCodigoView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblListarProdCodigo;
    private JLabel lblListarPorCodigo;

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

        URL buscURL = PreguntasView.class.getClassLoader().getResource("imagenes/lupa.png");
        if (buscURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(buscURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnBuscar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de busc");
        }
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JLabel getLblListarProdCodigo() {return lblListarProdCodigo;}

    public JLabel getLblListarPorCodigo() {return lblListarPorCodigo;}
}
