/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;

public class Panel extends JPanel {

    private JTabbedPane panelTab;

    public Panel(Inicio inicio) {
        if (inicio.panelTab != null) {
            this.panelTab = inicio.panelTab;
            setOpaque(false);

            JLabel titulo = new JLabel() {

                public String getText() {
                    int i = panelTab.indexOfTabComponent(Panel.this);
                    if (i != -1) {
                        return panelTab.getTitleAt(i);
                    }
                    return null;
                }
            };

            add(titulo);
            titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            JButton button = new Boton(inicio, this);
            add(button);
        }
    }
}
