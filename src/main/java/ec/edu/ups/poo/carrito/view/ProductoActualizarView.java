package ec.edu.ups.poo.carrito.view;

import ec.edu.ups.poo.carrito.modelo.Producto;

import javax.swing.*;

public class ProductoActualizarView extends JInternalFrame {
    private  JPanel       panelPrincipal;
    private  JTextField   txtCodigoBuscar;
    private  JButton      btnBuscar;
    private  JTextField   txtNombre;
    private  JTextField   txtPrecio;
    private JTextField txtCodigo;
    private JTextField txtCantidad;
    private  JButton      btnActualizar;
    private  JButton      btnLimpiar;
    private  JButton      btnSalir;
    private  JLabel       lblMensaje;
    private JPanel panelDatos;

    public ProductoActualizarView() {
        super("Actualizar Producto", true, true, true, true);

        // → Panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // → Buscar por código
        JPanel pBuscar = new JPanel();
        pBuscar.add(new JLabel("Código:"));
        txtCodigoBuscar = new JTextField(8);
        pBuscar.add(txtCodigoBuscar);
        btnBuscar = new JButton("Buscar");
        pBuscar.add(btnBuscar);
        panelPrincipal.add(pBuscar);

        JPanel pNombre = new JPanel();
        pNombre.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);
        pNombre.add(txtNombre);
        panelPrincipal.add(pNombre);

        JPanel pPrecio = new JPanel();
        pPrecio.add(new JLabel("Precio:"));
        txtPrecio = new JTextField(10);
        pPrecio.add(txtPrecio);
        panelPrincipal.add(pPrecio);

        JPanel pBotones = new JPanel();
        btnActualizar = new JButton("Actualizar");
        btnLimpiar    = new JButton("Limpiar");
        btnSalir      = new JButton("Salir");
        pBotones.add(btnActualizar);
        pBotones.add(btnLimpiar);
        pBotones.add(btnSalir);
        panelPrincipal.add(pBotones);

        lblMensaje = new JLabel(" ");
        lblMensaje.setAlignmentX(CENTER_ALIGNMENT);
        panelPrincipal.add(lblMensaje);

        setContentPane(panelPrincipal);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    public JPanel getPanelPrincipal()  { return panelPrincipal;   }

    public JTextField getTxtCodigoBuscar() { return txtCodigoBuscar; }

    public JButton    getBtnBuscar()        { return btnBuscar;       }

    public JTextField getTxtNombre()       { return txtNombre;       }

    public JTextField getTxtPrecio()       { return txtPrecio;       }

    public JButton    getBtnActualizar()   { return btnActualizar;   }

    public JButton    getBtnLimpiar()      { return btnLimpiar;      }

    public JButton    getBtnSalir()        { return btnSalir;        }


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
