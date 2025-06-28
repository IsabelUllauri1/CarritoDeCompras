package ec.edu.ups.poo.carrito.view;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.YELLOW);
        g.fillOval(10,10,200,200);
    }


    //cerrar sesion en usuario controler usuario deberia ser null

}
