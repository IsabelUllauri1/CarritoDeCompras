package ec.edu.ups.poo.carrito.view.carrito;

import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class CarritoAnadirView extends JInternalFrame{
    private JTextField TxtCodigo;
    private JButton btnBuscar;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JComboBox cbxCantidad;
    private JButton btnDescuento;
    private JTextField txtDescuento;
    private JButton btnVaciar;
    private JButton btnEliminar;
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JLabel lblAnadirAlCarrito;
    private JButton btnActualizar;

    public CarritoAnadirView() {
        super("Gestión de Carrito", true, true, true, true);
        setContentPane(panelPrincipal);
        pack();
        String[] columnas = { "Código", "Nombre", "Cantidad", "Subtotal" };
        DefaultTableModel tm = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tblProductos.setModel(tm);
        for (int i=1; i<=10; i++) {
            cbxCantidad.addItem(i);
        }
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        URL elimURL = PreguntasView.class.getClassLoader().getResource("imagenes/basurero.png");
        if (elimURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(elimURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnEliminar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de eliminar");
        }

        URL buscarURL = PreguntasView.class.getClassLoader().getResource("imagenes/lupa.png");
        if (buscarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(buscarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnBuscar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de buscar");
        }

        URL addURL = PreguntasView.class.getClassLoader().getResource("imagenes/add.png");
        if (addURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(addURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnAnadir.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de add");
        }

        URL vaURL = PreguntasView.class.getClassLoader().getResource("imagenes/vacio.png");
        if (vaURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(vaURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnVaciar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de vaciar");
        }

        URL guardariURL = PreguntasView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardariURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(guardariURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnGuardar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de vaciar");
        }

        URL cancelURL = PreguntasView.class.getClassLoader().getResource("imagenes/close.png");
        if (cancelURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(cancelURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnCancelar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de close");
        }

    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return TxtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    public JTable getTableProductos() {
        return tblProductos;
    }

    public JTextField getTxtDescuento() {
        return txtDescuento;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnDescuento() {
        return btnDescuento;
    }

    public void setBtnDescuento(JButton btnDescuento) {
        this.btnDescuento = btnDescuento;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JButton getBtnVaciar() {
        return btnVaciar;
    }

    public void setBtnVaciar(JButton btnVaciar) {
        this.btnVaciar = btnVaciar;
    }

    public JTextField getTextField1() {
        return txtDescuento;
    }

    public void setTextField1(JTextField textField1) {
        this.txtDescuento = textField1;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JLabel getLblCodigo() {return lblCodigo;
    }

    public JLabel getLblAnadirAlCarrito() {return lblAnadirAlCarrito;
    }

    public JLabel getLblTotal() {return lblTotal;
    }

    public JLabel getLblIVA() {return lblIVA;
    }

    public JLabel getLblSubtotal() {return lblSubtotal;
    }

    public JLabel getLblCantidad() {return lblCantidad;
    }

    public JLabel getLblPrecio() {return lblPrecio;
    }

    public JLabel getLblNombre() {return lblNombre;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        TxtCodigo = txtCodigo;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public void setComboBoxCantidad(JComboBox comboBoxCantidad) {
        this.cbxCantidad = comboBoxCantidad;
    }
    public void mostrarMensaje(String msg) { JOptionPane.showMessageDialog(this, msg); }

}
