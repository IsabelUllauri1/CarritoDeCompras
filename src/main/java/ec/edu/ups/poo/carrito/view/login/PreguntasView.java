package ec.edu.ups.poo.carrito.view.login;

import ec.edu.ups.poo.carrito.modelo.Pregunta;

import javax.swing.*;

public class PreguntasView extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox cbx1;
    private JTextField txt1;
    private JComboBox cbx2;
    private JTextField txt2;
    private JComboBox cbx3;
    private JTextField txt3;
    private JComboBox cbx4;
    private JTextField txt4;
    private JComboBox cbx5;
    private JComboBox cbx6;
    private JComboBox cbx7;
    private JComboBox cbx8;
    private JComboBox cbx9;
    private JComboBox cbx10;
    private JTextField txt10;
    private JTextField txt9;
    private JTextField txt5;
    private JTextField txt6;
    private JTextField txt7;
    private JTextField txt8;
    private JButton btnGuardar;



    public PreguntasView() {
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JComboBox<Pregunta>[] getCbxPreguntas() {
        return new JComboBox[] { cbx1, cbx2, cbx3, cbx4, cbx5, cbx6, cbx7, cbx8, cbx9, cbx10 };
    }

    public JTextField[] getTxtRespuestas() {
        return new JTextField[] { txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10 };
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTextField getTxt8() {
        return txt8;
    }

    public JComboBox getCbx4() {
        return cbx4;
    }

    public JTextField getTxt7() {
        return txt7;
    }

    public JTextField getTxt6() {
        return txt6;
    }

    public JTextField getTxt5() {
        return txt5;
    }

    public JTextField getTxt9() {
        return txt9;
    }

    public JTextField getTxt10() {
        return txt10;
    }

    public JComboBox getCbx10() {
        return cbx10;
    }

    public JComboBox getCbx9() {
        return cbx9;
    }

    public JComboBox getCbx8() {
        return cbx8;
    }

    public JComboBox getCbx7() {
        return cbx7;
    }

    public JComboBox getCbx6() {
        return cbx6;
    }

    public JComboBox getCbx5() {
        return cbx5;
    }

    public JTextField getTxt4() {
        return txt4;
    }

    public JTextField getTxt3() {
        return txt3;
    }

    public JComboBox getCbx3() {
        return cbx3;
    }

    public JTextField getTxt2() {
        return txt2;
    }

    public JComboBox getCbx2() {
        return cbx2;
    }

    public JTextField getTxt1() {
        return txt1;
    }

    public JComboBox getCbx1() {
        return cbx1;
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
