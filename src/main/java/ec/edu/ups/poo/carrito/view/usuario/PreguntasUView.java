package ec.edu.ups.poo.carrito.view.usuario;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PreguntasUView extends JInternalFrame {
    private JButton btnGuardar;
    private JTextField txtP1;
    private JTextField txtR1;
    private JTextField txtP2;
    private JTextField txtR2;
    private JTextField txtP3;
    private JTextField txtR3;
    private JTextField txtP4;
    private JTextField txtR4;
    private JTextField txtP5;
    private JTextField txtR5;
    private JTextField txtP6;
    private JTextField txtR6;
    private JTextField txtP7;
    private JTextField txtR7;
    private JTextField txtP8;
    private JTextField txtR8;
    private JPanel panelPrncipal;
    private JTextField txtP9;
    private JTextField txtR9;
    private JTextField txtR10;
    private JTextField txtP10;
    private JLabel lblResponderPreguntas;

    public PreguntasUView() {
        setSize( 500, 500 );
        setClosable( true );
        setContentPane(panelPrncipal);
        setDefaultCloseOperation(HIDE_ON_CLOSE);


        URL guardarURL = PreguntasView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(guardarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            btnGuardar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de preguntas");
        }
    }
    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("preguntas.responder.titulo"));

        lblResponderPreguntas.setText(mh.get("preguntas.responder.titulo"));
        btnGuardar.setText(mh.get("boton.guardar"));

        txtP1.setText(mh.get("pregunta.1"));
        txtP2.setText(mh.get("pregunta.2"));
        txtP3.setText(mh.get("pregunta.3"));
        txtP4.setText(mh.get("pregunta.4"));
        txtP5.setText(mh.get("pregunta.5"));
        txtP6.setText(mh.get("pregunta.6"));
        txtP7.setText(mh.get("pregunta.7"));
        txtP8.setText(mh.get("pregunta.8"));
        txtP9.setText(mh.get("pregunta.9"));
        txtP10.setText(mh.get("pregunta.10"));
    }


    public JTextField[] getCamposPreguntas() {
        return new JTextField[] {
                txtP1,txtP2,txtP3,txtP4,txtP5,txtP6,txtP7,txtP8, txtP9,txtP10
        };
    }

    public JTextField[] getCamposRespuestas() {
        return new JTextField[] {
                txtR1,txtR2,txtR3,txtR4,txtR5,txtR6,txtR7,txtR8,txtR9,txtR10
        };
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JPanel getPanelPrncipal() {
        return panelPrncipal;
    }

    public JLabel getLblResponderPreguntas() {
        return lblResponderPreguntas;
    }

    public JTextField getTxtP10() {
        return txtP10;
    }

    public JTextField getTxtR10() {
        return txtR10;
    }

    public JTextField getTxtR9() {
        return txtR9;
    }

    public JTextField getTxtP9() {
        return txtP9;
    }

    public JTextField getTxtR8() {
        return txtR8;
    }

    public JTextField getTxtP8() {
        return txtP8;
    }

    public JTextField getTxtP7() {
        return txtP7;
    }

    public JTextField getTxtR7() {
        return txtR7;
    }

    public JTextField getTxtR6() {
        return txtR6;
    }

    public JTextField getTxtR5() {
        return txtR5;
    }

    public JTextField getTxtP6() {
        return txtP6;
    }

    public JTextField getTxtP1() {
        return txtP1;
    }

    public JTextField getTxtP5() {
        return txtP5;
    }

    public JTextField getTxtR4() {
        return txtR4;
    }

    public JTextField getTxtP4() {
        return txtP4;
    }

    public JTextField getTxtR3() {
        return txtR3;
    }

    public JTextField getTxtP3() {
        return txtP3;
    }

    public JTextField getTxtR2() {
        return txtR2;
    }

    public JTextField getTxtP2() {
        return txtP2;
    }

    public JTextField getTxtR1() {
        return txtR1;
    }

    public void mostrarMensaje(String msg) { JOptionPane.showMessageDialog(this, msg); }

}
