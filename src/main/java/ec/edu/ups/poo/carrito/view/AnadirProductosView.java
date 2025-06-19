package ec.edu.ups.poo.carrito.view;

import javax.swing.*;


public class AnadirProductosView extends JInternalFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton btnAnadir;
    private JButton btnLimpiar;
    private JPanel PanelPrincipal;
    private JTextField txtPrecio;
    private JButton btnSalir;

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        PanelPrincipal = panelPrincipal;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public JButton getBtnAceptar() {
        return btnAnadir;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAnadir = btnAceptar;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public void setTextField3(JTextField textField3) {
        this.textField3 = textField3;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void setTextField2(JTextField textField2) {
        this.textField2 = textField2;
    }

    public JButton getBtnSalir() {return btnSalir;
    }

    public JButton getBtnAnadir() {return btnAnadir;
    }

    public AnadirProductosView() {
        setTitle("Carrito");
        setContentPane(PanelPrincipal);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        setVisible(true);


    }

}



