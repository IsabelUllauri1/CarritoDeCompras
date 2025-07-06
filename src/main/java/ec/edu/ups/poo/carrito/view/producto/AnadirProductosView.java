package ec.edu.ups.poo.carrito.view.producto;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class AnadirProductosView extends JInternalFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton btnAnadir;
    private JButton btnLimpiar;
    private JPanel PanelPrincipal;
    private JTextField txtPrecio;
    private JButton btnSalir;
    private JLabel lblPrecio;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblCrrearProducto;

    public AnadirProductosView() {
        setTitle("Carrito");
        setContentPane(PanelPrincipal);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(500, 500);
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        setVisible(true);

        URL limpURL = PreguntasView.class.getClassLoader().getResource("imagenes/vacio.png");
        if (limpURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(limpURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnLimpiar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de vacio");
        }

        URL closeURL = PreguntasView.class.getClassLoader().getResource("imagenes/close.png");
        if (closeURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(closeURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnSalir.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de close");
        }

        URL addpURL = PreguntasView.class.getClassLoader().getResource("imagenes/add.png");
        if (addpURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(addpURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            btnAnadir.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de add");
        }
    }
    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("producto.anadir.titulo"));

        lblCrrearProducto.setText(mh.get("producto.anadir.titulo"));
        lblCodigo.setText(mh.get("etiqueta.codigo"));
        lblNombre.setText(mh.get("etiqueta.nombre"));
        lblPrecio.setText(mh.get("etiqueta.precio"));

        btnAnadir.setText(mh.get("boton.anadir"));
        btnLimpiar.setText(mh.get("boton.limpiar"));
        btnSalir.setText(mh.get("boton.salir"));

    }


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

    public JButton getBtnSalir() {return btnSalir;}

    public JButton getBtnAnadir() {return btnAnadir;}

    public JLabel getLblPrecio() {return lblPrecio;}

    public JLabel getLblCrrearProducto() {return lblCrrearProducto;}

    public JLabel getLblNombre() {return lblNombre;}

    public JLabel getLblCodigo() {return lblCodigo;}


}



