/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Grafica;

import Interfaz.Escala;
import Interfaz.Inicio;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Jose2
 */
public class Plano extends javax.swing.JFrame {

    /**
     * Creates new form Plano
     */
    private Panel panel;

    public Plano() {
        //initComponents();
        ///crearPlano();
        fChart();
    }

    JFreeChart chart;

    public void fChart() {

        // Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < Inicio.coordenadas.size(); i++) {
            // Create a simple XY chart
            XYSeries series = new XYSeries("Graphik " + i);
            ArrayList<Object> vectorX = Inicio.coordenadas.getX(i);
            ArrayList<Object> vectorY = Inicio.coordenadas.getY(i);
            for (int j = 0; j < vectorX.size(); j++) {
                Object x = vectorX.get(j);
                Object y = vectorY.get(j);
                if (x != null && y != null) {
                    if (x.getClass().getSimpleName().equalsIgnoreCase("double")) {
                        if (y.getClass().getSimpleName().equalsIgnoreCase("double")) {
                            series.add((double) x, (double) y);
                        } else {
                            series.add((double) x, (int) y);
                        }
                    } else if (y.getClass().getSimpleName().equalsIgnoreCase("double")) {
                        series.add((int) x, (double) y);
                    } else {
                        series.add((int) x, (int) y);
                    }
                }
            }
            dataset.addSeries(series);
        }

        // Generate the graph
        chart = ChartFactory.createXYLineChart(
                "Funcion", // Title
                "X", // x-axis Label
                "Y", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        //Mostramos la grafica en pantalla
        ChartFrame fr = new ChartFrame("Graphik", chart);
        fr.pack();
        fr.setVisible(true);
    }

    /*
    private void fChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int tiempo = -10; tiempo <=10; tiempo++) {

            //Crecimiento
            //poblacion = poblacion * (1 + tc);
            dataset.addValue(tiempo*tiempo,"f(x)=y", "" + tiempo);

           

        }
        
        
        for(int i=0;i<Inicio.coordenadas.size();i++){
            ArrayList<Object> vectorX = Inicio.coordenadas.getX(i);
            ArrayList<Object> vectorY = Inicio.coordenadas.getY(i);
            for(int j=0;j<vectorX.size();j++){
                Object x=vectorX.get(j);
                Object y=vectorY.get(j);
                
                if(x.getClass().getSimpleName().equalsIgnoreCase("double")){
                    dataset.addValue((double)y,"f(x)=y", "" +(double)x);
                }else{
                    dataset.addValue((int)y,"f(x)=y", "" +(int)x);   
                }
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Calculo demografico",
                "Tiempo",
                "Población",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        //Mostramos la grafica en pantalla
        ChartFrame fr = new ChartFrame("Funcion X vs Y", chart);
        fr.pack();
        fr.setVisible(true);

    
    }*/
    public void setEscada(int x, int y) {
        panel.setEscala(x, y);
        panel.repaint();
        panel.revalidate();

    }

    private void crearPlano() {
        panel = new Panel(this);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/grafica.png")));
        setTitle("Graphik");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(10000, 10000));
        scrollPlano.setViewportView(panel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPlano = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        scrollPlano.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                scrollPlanoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPlano, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPlano, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void scrollPlanoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_scrollPlanoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_F1) {
            Escala escala = new Escala(null, true);
            escala.setVisible(true);
        }
    }//GEN-LAST:event_scrollPlanoKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Plano().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPlano;
    // End of variables declaration//GEN-END:variables
}
