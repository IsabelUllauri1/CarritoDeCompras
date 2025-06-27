package ec.edu.ups.poo.carrito.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
