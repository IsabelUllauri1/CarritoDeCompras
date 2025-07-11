package ec.edu.ups.poo.carrito.view.producto;

import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ProductoListarView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    //private JButton btnListar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnSalir;
    private JLabel lblBuscarPorNombre;
    private JLabel lblListarProductos;
    //private JButton btnEliminar;
    //private JButton btnActualizar;
    private DefaultTableModel modelo;




    public ProductoListarView() {

        setTitle("Ventana Listar Productos");
        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setMaximizable(true);

        modelo = new DefaultTableModel(new Object[] {"", "", ""}, 0){
            @Override public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        tblProductos.setModel(modelo);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        URL lupaURL = PreguntasView.class.getClassLoader().getResource("imagenes/lupa.png");
        if (lupaURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(lupaURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnBuscar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de lupa");
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
        setTitle(mh.get("producto.listar.titulo"));

        lblBuscarPorNombre.setText(mh.get("etiqueta.buscarpornombre"));
        lblListarProductos.setText(mh.get("producto.listar.titulo"));

        btnBuscar.setText(mh.get("boton.buscar"));
        btnSalir.setText(mh.get("boton.salir"));

        // Encabezados de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setColumnIdentifiers(new Object[] {
                mh.get("etiqueta.codigo"),
                mh.get("etiqueta.nombre"),
                mh.get("etiqueta.precio")
        });
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);

        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }




    }
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
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

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JLabel getLblListarProductos() {return lblListarProductos;}

    public JLabel getLblBuscarPorNombre() {return lblBuscarPorNombre;}
}
