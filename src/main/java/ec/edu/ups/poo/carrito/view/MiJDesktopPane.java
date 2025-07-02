package ec.edu.ups.poo.carrito.view;
import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Obtener el centro del panel
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Dimensiones del supermercado
        int width = 200;
        int height = 160;
        int startX = centerX - width / 2;
        int startY = centerY - height / 2;

        // Habilitar antialiasing para mejor calidad
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // EColor fondo
        g2d.setColor(new Color(222, 237, 255));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar el edificio principal (base del supermercado)
        g2d.setColor(new Color(177,193,227));
        g2d.fillRect(startX, startY + 40, width, height - 40);

        // Contorno del edificio
        g2d.setColor(new Color(50, 100, 200));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(startX, startY + 40, width, height - 40);

        // Dibujar el toldo superior
        g2d.setColor(new Color(132, 161, 223));
        g2d.fillRect(startX - 10, startY + 30, width + 20, 30);

        // Borde del toldo
        g2d.setColor(new Color(50, 100, 200));
        g2d.drawRect(startX - 10, startY + 30, width + 20, 30);

        // Dibujar las ondas decorativas del toldo (convexas)
        g2d.setColor(new Color(205, 52, 185));
        g2d.setStroke(new BasicStroke(2));
        int waveY = startY + 55;
        int waveWidth = 20;
        for (int i = startX - 10; i < startX + width + 10; i += waveWidth) {
            // Crear pequeñas ondas decorativas convexas (invertidas)
            g2d.drawArc(i, waveY, waveWidth, 15, 180, 180);
        }

        // Dibujar la puerta
        g2d.setColor(Color.WHITE);
        int doorWidth = 30;
        int doorHeight = 30;
        int doorX = startX + 20;
        int doorY = startY + height - 30;
        g2d.fillRect(doorX, doorY, doorWidth, doorHeight);

        // Marco de la puerta
        g2d.setColor(new Color(50, 100, 200));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(doorX, doorY, doorWidth, doorHeight);

        // Manija de la puerta
        g2d.setColor(new Color(50, 100, 200));
        g2d.fillOval(doorX + doorWidth - 8, doorY + doorHeight/2 - 2, 4, 4);

        // Dibujar la ventana
        g2d.setColor(Color.WHITE);
        int windowWidth = 75;
        int windowHeight = 60;
        int windowX = startX + width - windowWidth - 20;
        int windowY = startY + 84;
        g2d.fillRect(windowX, windowY, windowWidth, windowHeight);

        // Marco de la ventana
        g2d.setColor(new Color(50, 100, 200));
        g2d.drawRect(windowX, windowY, windowWidth, windowHeight);

        // División de la ventana (cruz)
        g2d.drawLine(windowX + windowWidth/2, windowY, windowX + windowWidth/2, windowY + windowHeight);
        g2d.drawLine(windowX, windowY + windowHeight/2, windowX + windowWidth, windowY + windowHeight/2);

        // Reflejo en la ventana
        g2d.setColor(new Color(200, 220, 255, 100));
        g2d.fillRect(windowX + 5, windowY + 5, windowWidth/2 - 5, windowHeight/2 - 5);

        // Dibujar los postes del cartel
        int postWidth = 4;
        int postHeight = 10;
        int leftPostX = startX + 30;
        int rightPostX = startX + width - 30;
        int postY = startY + 30 - postHeight;

        // Postes verticales
        g2d.setColor(new Color(80, 80, 80));
        g2d.fillRect(leftPostX, postY, postWidth, postHeight);
        g2d.fillRect(rightPostX, postY, postWidth, postHeight);

        // Dibujar el cartel sostenido por los postes
        int signWidth = rightPostX - leftPostX + postWidth;
        int signHeight = 25;
        int signX = leftPostX;
        int signY = postY - signHeight - 5;

        // Fondo del cartel
        g2d.setColor(new Color(70, 120, 220));
        g2d.fillRect(signX, signY, signWidth, signHeight);

        // Borde del cartel
        g2d.setColor(new Color(40, 80, 180));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(signX, signY, signWidth, signHeight);

        // Conexiones del cartel a los postes
        g2d.setColor(new Color(80, 80, 80));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(leftPostX + postWidth/2, postY, leftPostX + postWidth/2, signY + signHeight);
        g2d.drawLine(rightPostX + postWidth/2, postY, rightPostX + postWidth/2, signY + signHeight);

        // Texto del cartel
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 12);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        String texto = "SUPERMARKET";
        int textWidth = fm.stringWidth(texto);
        int textX = signX + (signWidth - textWidth) / 2;
        int textY = signY + signHeight/2 + fm.getAscent()/2 - 2;
        g2d.drawString(texto, textX, textY);

        // Dibujar una pequeña base/acera
        g2d.setColor(new Color(180, 180, 180));
        g2d.fillRect(startX - 15, startY + height, width + 30, 8);

        // Borde de la base
        g2d.setColor(new Color(120, 120, 120));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(startX - 15, startY + height, width + 30, 8);

        // Agregar algunos detalles decorativos (líneas en el edificio)
        g2d.setColor(new Color(50, 100, 200));
        g2d.setStroke(new BasicStroke(1));
        // Línea horizontal decorativa
        g2d.drawLine(startX + 10, startY + 120, startX + width /2 -15, startY + 120);

        dibujarEstrella(g2d,  50, 40, 7);
        dibujarEstrella(g2d, 100, 120, 15);
        dibujarEstrella(g2d, 150, 60, 6);
        dibujarEstrella(g2d, 85, 270, 19);//--
        dibujarEstrella(g2d, 200, 200, 18);
        dibujarEstrella(g2d, 250, 90, 9);
        dibujarEstrella(g2d, 300, 170, 14);
        dibujarEstrella(g2d, 350, 50, 11);
        dibujarEstrella(g2d, 400, 240, 20);
        dibujarEstrella(g2d, 450, 110, 8);
        dibujarEstrella(g2d, 500, 300, 16);
        dibujarEstrella(g2d, 550, 70, 10);
        dibujarEstrella(g2d, 600, 180, 12);
        dibujarEstrella(g2d, 650, 130, 9);
        dibujarEstrella(g2d, 700, 280, 17);
        dibujarEstrella(g2d, 750, 100, 11);
        dibujarEstrella(g2d, 685, 50, 16);//--
        dibujarEstrella(g2d, 800, 243, 19);//--
        dibujarEstrella(g2d, 850, 40, 5);
        dibujarEstrella(g2d, 850, 110, 10);
        dibujarEstrella(g2d, 900, 200, 13);//--
        dibujarEstrella(g2d, 950, 90, 7);
        dibujarEstrella(g2d, 1000, 260, 15);//
        dibujarEstrella(g2d, 1050, 150, 12);
        dibujarEstrella(g2d, 1100, 300, 16);//
        dibujarEstrella(g2d, 1150, 60, 6);
        dibujarEstrella(g2d, 1200, 250, 17);
        dibujarEstrella(g2d, 1250, 170, 14);
        dibujarEstrella(g2d, 1300, 40, 5);
        dibujarEstrella(g2d, 1230, 59, 19);//--
        dibujarEstrella(g2d, 1010, 59, 19);//--
        dibujarEstrella(g2d, 1350, 100, 10);
        dibujarEstrella(g2d, 1400, 280, 18);//
        dibujarEstrella(g2d, 1450, 80, 7);

    }

    private void dibujarEstrella(Graphics2D g2d, int x, int y, int size) {
        int half = size / 2;

        Polygon estrella = new Polygon();
        estrella.addPoint(x, y - half);
        estrella.addPoint(x + half, y);
        estrella.addPoint(x, y + half);
        estrella.addPoint(x - half, y);

        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(estrella);


    }

}