/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class Boton extends JButton implements MouseListener {

    private Panel lblTitulo;
    private Inicio inicio;

    public Boton(Inicio inici, Panel lblTitulo) {
        this.lblTitulo = lblTitulo;
        this.inicio = inici;
        int size = 16;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("Cerrar Pestaña");
        setUI(new BasicButtonUI());
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        addMouseListener(this);
        setRolloverEnabled(true);
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i = inicio.panelTab.indexOfTabComponent(Boton.this.lblTitulo);
                if (i != -1) {
                    inicio.indicePestania--;
                    inicio.listaPestania.remove((Editor) inicio.panelTab.getSelectedComponent());
                    inicio.panelTab.remove(i);
                    int a = 0;
                }
            }
        });
    }

    public void updateUI() {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        ImageIcon img = new ImageIcon(this.getClass().getResource("/Media/cerrar.png"));
        g2.drawImage(img.getImage(), 1, 1, this);
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(false);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(false);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
