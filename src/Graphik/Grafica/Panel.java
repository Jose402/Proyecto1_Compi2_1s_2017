/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Grafica;

import Interfaz.Inicio;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jose2
 */
public class Panel extends JPanel {

    private JFrame ventana;
    private int x;
    private int y;

    private int escalaX = 50; //estala 50/10
    private int escalaY = 10; //cada 50 pixeles son 10 unidades
    private int escala = escalaX / escalaY;

    public Panel(JFrame ventana) {
        setBackground(Color.white);
        setFocusable(true);
        this.ventana = ventana;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        x = ventana.getHeight() / 2;
        y = ventana.getWidth() / 2;
        GraficarFuncion((Graphics2D) g);
        g.setColor(Color.GRAY);
        graficarGuias(g);
    }

    private void graficarGuias(Graphics g) {
        g.drawLine(0, x, 10000, x);//x
        g.drawLine(y, 0, y, 10000);  //y

        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font(null, Font.PLAIN, 9);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(0), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);

        int cX = 0;
        for (int i = y; i < 10000; i += escalaX) {
            g2.drawString(cX + "", i - 6, x - 6);
            g2.drawLine(i + escalaX, x - 4, i + escalaX, x + 4);
            cX += escalaY;
        }
        cX = 0;
        for (int i = y; i >= -100; i -= escalaX) {
            if (cX == 0) {
                g2.drawString(cX + "", i - 6, x - 6);
            } else {
                g2.drawString("-" + cX + "", i - 6, x - 6);
            }
            g2.drawLine(i + escalaX, x - 4, i + escalaX, x + 4);
            cX += escalaY;
        }

        affineTransform.rotate(Math.toRadians(90), 0, 0);
        rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);

        int cY = 0;
        for (int i = x; i > -100; i -= escalaX) {
            if (cY != 0) {
                g2.drawString(cY + "", y - 12, i - 6);
            }
            g2.drawLine(y - 4, i, y + 4, i);
            cY += escalaY;
        }
        cY = 0;
        for (int i = x; i < 10000; i += escalaX) {
            if (cY != 0) {
                g2.drawString("-" + cY + "", y - 12, i - 6);
            }
            g2.drawLine(y - 4, i, y + 4, i);
            cY += escalaY;
        }
        //g2.dispose();
    }

    private void GraficarFuncion(Graphics2D g) {

        for (int j = 0; j < Inicio.coordenadas.size(); j++) {
            ArrayList<Object> vectorX = Inicio.coordenadas.getX(j);
            ArrayList<Object> vectorY = Inicio.coordenadas.getY(j);
            g.setColor(getColor());
            for (int i = 0; i < vectorX.size(); i++) {
                //int x=(int)Plano.x.get(i);
                //int y=(int)Plano.y.get(i);

                //x=this.y+x*5;
                //y=this.x-y*5;
                //g.fillOval(x,y,2,2);
                //g.setColor(Color.blue);
                Object o1 = getCX(i, vectorX);
                Object o2 = getCX(i + 1, vectorX);
                int x1 = 0;
                int x2 = 0;
                if (o2 != null) {
                    x1 = (int) o1;
                    x2 = (int) o2;
                }

                Object o3 = getCY(i, vectorY);
                Object o4 = getCY(i + 1, vectorY);
                int y1 = 0;
                int y2 = 0;
                if (o4 != null) {
                    y1 = (int) o3;
                    y2 = (int) o4;
                }
                if (o2 != null) {
                    g.drawLine(y + x2 * escala, x - y2 * escala, y + x1 * escala, x - y1 * escala);
                }

            }
        }

    }

    private Object getCX(int index, ArrayList<Object> vectorX) {
        Object o = null;
        try {
            o = vectorX.get(index);
        } catch (Exception e) {

        }
        return o;
    }

    private Object getCY(int index, ArrayList<Object> vectorY) {
        Object o = null;
        try {
            o = vectorY.get(index);
        } catch (Exception e) {

        }
        return o;
    }

    private Color getColor() {
        Random r = new Random();
        int i = r.nextInt(8);
        switch (i) {
            case 0:
                return Color.red;
            case 1:
                return Color.blue;
            case 2:
                return Color.GRAY;
            case 3:
                return Color.orange;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.black;
            case 6:
                return Color.PINK;
            case 7:
                return Color.MAGENTA;
            default:
                return Color.yellow;
        }

    }

}
