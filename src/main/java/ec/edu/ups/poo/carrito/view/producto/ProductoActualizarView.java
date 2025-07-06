package ec.edu.ups.poo.carrito.view.producto;

import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProductoActualizarView extends JInternalFrame {
    private  JPanel panelPrincipal;
    private  JTextField txtCodigoBuscar;
    private  JButton  btnBuscar;
    private  JTextField   txtNombre;
    private  JTextField   txtPrecio;
    private JTextField txtCodigo;
    private  JButton  btnActualizar;
    private  JButton btnSalir;
    private  JLabel  lblMensaje;
    private JPanel panelDatos;
    private JLabel lblActualizarProd;
    private JLabel lblBuscar;
    private JLabel lblNombre;
    private JLabel lblCodigo;
    private JLabel lblPreci;

    public ProductoActualizarView()  {
        super("Actualizar Producto", true, true, true, true);
        setSize(500,500);

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(HIDE_ON_CLOSE);


        setIcono(btnActualizar, "imagenes/actualizar.png", "act");
        setIcono(btnBuscar, "imagenes/lupa.png", "lupa");
        setIcono(btnSalir, "imagenes/close.png", "close");
    }

    private void setIcono(JButton boton, String ruta, String nombre) {
        URL url = ProductoActualizarView.class.getClassLoader().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de " + nombre);
        }
    }
    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {

        setTitle(mh.get("producto.actualizar.titulo"));
        lblBuscar.setText(mh.get("lblBuscar"));
        lblActualizarProd.setText(mh.get("lblActualizar"));
        lblCodigo.setText(mh.get("lblCodigo"));
        lblPreci.setText(mh.get("lblPrecio"));
        lblNombre.setText(mh.get("lblNombre"));
        btnBuscar.setText(mh.get("btnBuscar"));
        btnActualizar.setText(mh.get("btnActualizar"));
        btnSalir.setText(mh.get("btnSalir"));
    }


    public JTextField getTxtCodigo() {return txtCodigo;}

    public JLabel getLblPreci() {return lblPreci;}

    public JLabel getLblCodigo() {return lblCodigo;}

    public JLabel getLblNombre() {return lblNombre;}

    public JLabel getLblBuscar() {return lblBuscar;}

    public JLabel getLblActualizarProd() {return lblActualizarProd;}

    public JLabel getLblMensaje() {return lblMensaje;}

    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }

    public JTextField getTxtCodigoBuscar(){
        return txtCodigoBuscar;
    }

    public JButton    getBtnBuscar(){
        return btnBuscar;
    }

    public JTextField getTxtNombre(){
        return txtNombre;
    }

    public JTextField getTxtPrecio(){
        return txtPrecio;
    }

    public JButton    getBtnActualizar(){
        return btnActualizar;
    }
    

    public JButton    getBtnSalir(){
        return btnSalir;
    }

    public void limpiarCampos() {
        txtCodigoBuscar.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        lblMensaje.setText(" ");
    }

    public void mostrarMensaje(String msg, boolean exito) {
        lblMensaje.setText(msg);
        lblMensaje.setForeground(exito
                ? java.awt.Color.GREEN
                : java.awt.Color.RED);
    }

    public void clearFields() {
        txtCodigoBuscar.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        lblMensaje.setText(" ");
    }

    public void cargarProducto(Producto p) {
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
    }
}
