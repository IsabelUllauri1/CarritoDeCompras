package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PreguntasView extends JFrame {
    private JPanel panelPrincipal;

    private JTextField txt1;
    private JTextField txt2;
    private JTextField txt3;
    private JTextField txt4;
    private JTextField txt9;
    private JTextField txt5;
    private JTextField txt6;
    private JTextField txt7;
    private JTextField txt8;
    private JTextField txt10;
    private JButton btnGuardar;
    private JTextField txtP1;
    private JTextField txtP2;
    private JTextField txtP3;
    private JTextField txtP4;
    private JTextField txtP5;
    private JTextField txtP6;
    private JTextField txtP7;
    private JTextField txtP8;
    private JTextField txtP9;
    private JTextField txtP10;
    private JButton btnRegresar;
    private JLabel lblPreguntasSeguridad;


    public PreguntasView() {
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);


        URL guardarURL = PreguntasView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardarURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(guardarURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnGuardar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de guardar");
        }
        URL atrasURL = PreguntasView.class.getClassLoader().getResource("imagenes/atras.png");
        if (atrasURL != null) {
            ImageIcon iconoOriginal = new ImageIcon(atrasURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnRegresar.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.err.println("Error: No se ha cargado el icono de atras");
        }
    }

    public void actualizarTexto(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("preguntas.titulo"));

        lblPreguntasSeguridad.setText(mh.get("preguntas.titulo"));

        btnGuardar.setText(mh.get("boton.guardar"));
        btnRegresar.setText(mh.get("boton.regresar"));
        txt1.setText(mh.get("pregunta.1"));
        txt2.setText(mh.get("pregunta.2"));
        txt3.setText(mh.get("pregunta.3"));
        txt4.setText(mh.get("pregunta.4"));
        txt5.setText(mh.get("pregunta.5"));
        txt6.setText(mh.get("pregunta.6"));
        txt7.setText(mh.get("pregunta.7"));
        txt8.setText(mh.get("pregunta.8"));
        txt9.setText(mh.get("pregunta.9"));
        txt10.setText(mh.get("pregunta.10"));
    }


    public JTextField[] getCamposPreguntas() {
        return new JTextField[] {
                txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,
        };
    }

    public JTextField[] getCamposRespuestas() {
        return new JTextField[] {
                txtP1,txtP2,txtP3,txtP4,txtP5,txtP6,txtP7,txtP8,txtP9,txtP10,
        };
    }


    public JButton getBtnGuardar() {return btnGuardar;
    }

    public JButton getBtnRegresar() {return btnRegresar;
    }

    public JTextField getTxtP10() {return txtP10;
    }

    public JTextField getTxtP9() {return txtP9;
    }

    public JTextField getTxtP8() {return txtP8;
    }

    public JPanel getPanelPrincipal() {return panelPrincipal;
    }

    public JTextField getTxt1() {return txt1;
    }

    public JTextField getTxt2() {return txt2;
    }

    public JTextField getTxt3() {return txt3;
    }

    public JTextField getTxt4() {return txt4;
    }

    public JTextField getTxt9() {return txt9;
    }

    public JTextField getTxt5() {return txt5;
    }

    public JTextField getTxt6() {return txt6;
    }

    public JTextField getTxt7() {return txt7;
    }

    public JTextField getTxt8() {return txt8;
    }

    public JTextField getTxt10() {return txt10;
    }

    public JTextField getTxtP1() {return txtP1;
    }

    public JTextField getTxtP2() {return txtP2;
    }

    public JTextField getTxtP3() {return txtP3;
    }

    public JTextField getTxtP4() {return txtP4;
    }

    public JTextField getTxtP5() {return txtP5;
    }

    public JTextField getTxtP6() {return txtP6;
    }

    public JTextField getTxtP7() {return txtP7;
    }

    public JLabel getLblPreguntasSeguridad() {return lblPreguntasSeguridad;
    }



    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
