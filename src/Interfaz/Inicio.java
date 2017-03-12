
package Interfaz;

import Haskell.Analisis.*;
import Ast.Graficar;
import Ast.Nodo;
import Haskell.Interprete.Consola;
import Haskell.Interprete.Interprete;
import Haskell.Interprete.ResultadoH;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

/**
 *
 * @author Jose2
 */
public class Inicio extends javax.swing.JFrame {

    /**
     * Creates new form Inicio
     */
    public  int indicePestania=0;
    public  ArrayList<Editor> listaPestania;
    private Editor pestaniaActiva;
    Consola consola;
    public static Interprete interprete;
    public Inicio() {
        initComponents();
        listaPestania=new ArrayList<>();
        consola=new Consola(txtConsola);
        
    }
    
    
    private void crearPestania(String texto, String nombre, File file) {
        //"text/html"
        Editor pestania = new Editor(texto,nombre,file);
        Panel panelTitulo = new Panel(this);
        panelTab.addTab(nombre, pestania);
        panelTab.setTabComponentAt(indicePestania, panelTitulo);
        panelTab.setSelectedIndex(indicePestania);
        indicePestania++;
        pestaniaActiva=pestania;
        listaPestania.add(pestaniaActiva);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelTab = new javax.swing.JTabbedPane();
        scrollPanel = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextArea();
        btnCompilar = new javax.swing.JButton();
        btnTablaSimoblos = new javax.swing.JButton();
        btnTablaError = new javax.swing.JButton();
        lblPosicion = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        itemAbrir = new javax.swing.JMenuItem();
        itemGuardar = new javax.swing.JMenuItem();
        itemGuardarComo = new javax.swing.JMenuItem();
        itemSalir = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        itemCargarFunciones = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        panelTab.setBackground(new java.awt.Color(255, 255, 255));

        txtConsola.setBackground(new java.awt.Color(153, 153, 153));
        txtConsola.setColumns(20);
        txtConsola.setRows(5);
        txtConsola.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConsolaKeyPressed(evt);
            }
        });
        scrollPanel.setViewportView(txtConsola);

        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/ejecutar2.png"))); // NOI18N
        btnCompilar.setToolTipText("Compilar");
        btnCompilar.setContentAreaFilled(false);
        btnCompilar.setFocusable(false);
        btnCompilar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompilar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/ejecutar3.png"))); // NOI18N
        btnCompilar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/ejecutar1.png"))); // NOI18N
        btnCompilar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        btnTablaSimoblos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tabla2.png"))); // NOI18N
        btnTablaSimoblos.setToolTipText("Tabla de simbolos");
        btnTablaSimoblos.setContentAreaFilled(false);
        btnTablaSimoblos.setFocusable(false);
        btnTablaSimoblos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTablaSimoblos.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tabla3.png"))); // NOI18N
        btnTablaSimoblos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tabla1.png"))); // NOI18N
        btnTablaSimoblos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnTablaError.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tablaE2.png"))); // NOI18N
        btnTablaError.setToolTipText("Errores");
        btnTablaError.setContentAreaFilled(false);
        btnTablaError.setFocusable(false);
        btnTablaError.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTablaError.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tablaE3.png"))); // NOI18N
        btnTablaError.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tablaE1.png"))); // NOI18N
        btnTablaError.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lblPosicion.setText(" Linea:0 Columna:0");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTab)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTablaError, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTablaSimoblos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(lblPosicion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTablaSimoblos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCompilar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTablaError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTab, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPosicion))
        );

        menuBar.setBackground(new java.awt.Color(255, 255, 255));
        menuBar.setToolTipText("");

        menuArchivo.setText("Archivo");

        itemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoNuevo.png"))); // NOI18N
        itemNuevo.setText("Nuevo");
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemNuevo);

        itemAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoAbrirD.png"))); // NOI18N
        itemAbrir.setText("Abrir");
        itemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemAbrir);

        itemGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoGuardar.png"))); // NOI18N
        itemGuardar.setText("Guardar");
        itemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(itemGuardar);

        itemGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoGuardar.png"))); // NOI18N
        itemGuardarComo.setText("Guardar como ...");
        itemGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarComoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemGuardarComo);

        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoSalir.png"))); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);

        menuEditar.setText("Haskell++");

        itemCargarFunciones.setText("Cargar Funciones");
        itemCargarFunciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCargarFuncionesActionPerformed(evt);
            }
        });
        menuEditar.add(itemCargarFunciones);

        menuBar.add(menuEditar);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNuevoActionPerformed
        // TODO add your handling code here:
        nuevoArchivo();
    }//GEN-LAST:event_itemNuevoActionPerformed

    private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAbrirActionPerformed
        // TODO add your handling code here:
        abrirArchivo();
    }//GEN-LAST:event_itemAbrirActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_itemSalirActionPerformed

    private void itemGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarComoActionPerformed
        // TODO add your handling code here:
        guardarComo();
    }//GEN-LAST:event_itemGuardarComoActionPerformed

    private void itemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_itemGuardarActionPerformed

    public static Nodo raiz;
    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        String cadena=pestaniaActiva.getText();
        
        try { 
            //new Sintactico(new Lexico(new BufferedReader( new StringReader(cadena)))).parse();
            //new Graficar(raiz);
            new SintacticoH(new LexicoH(new BufferedReader( new StringReader(cadena)))).parse();
            //new Graficar(raiz);

            interprete=new Interprete(raiz);
            ArrayList<ResultadoH> l=new ArrayList<>();
            ResultadoH r1=new ResultadoH("numero","1");
            l.add(r1);
            //r1=new ResultadoH("numero","3");
            //l.add(r1);
            ResultadoH r3=interprete.ejecutar("switch", l);
            txtConsola.append(">"+r3.valor+"\n");
                    
// TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void txtConsolaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConsolaKeyPressed
        // TODO add your handling code here:
        
        if(evt.getKeyCode()==evt.VK_ENTER){
            String cadena=getTextLine();
            char c=cadena.charAt(0);
            if(c!='>'){
                try {
                
                new SintacticoH(new LexicoH(new BufferedReader( new StringReader(cadena)))).parse();
                consola.ejecutar(raiz);
                } catch (Exception ex) {
                    System.out.println(ex+"");
                }
            }
        }
    }//GEN-LAST:event_txtConsolaKeyPressed

    private void itemCargarFuncionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCargarFuncionesActionPerformed
        try {
            // TODO add your handling code here:
            String cadena=pestaniaActiva.getText();
            new SintacticoH(new LexicoH(new BufferedReader( new StringReader(cadena)))).parse();
            interprete=new Interprete(raiz);
            
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_itemCargarFuncionesActionPerformed

    private String getTextLine(){
        String cadena=txtConsola.getText();
        String cadenas[]=cadena.split("\n");
        return cadenas[cadenas.length-1];
    }
    private void guardar(){
        if(pestaniaActiva!=null){
            if(pestaniaActiva.file!=null){
                if (pestaniaActiva.getText().matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
                JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                PrintWriter printwriter = null;
                try {
                    printwriter = new PrintWriter(pestaniaActiva.file);
                    printwriter.print(pestaniaActiva.getText());//escribe en el archivo todo lo que se encuentre en el JTextArea
                    printwriter.close();//cierra el archivo
                } catch (Exception e) {
                    
                } finally {
                    printwriter.close();
                }
            }
            }else{
                guardarComo();
            }
        }else{
            JOptionPane.showMessageDialog(null,"No hay pestañas activas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void guardarComo(){
        if(pestaniaActiva!=null){
            String texto = pestaniaActiva.getText();//variable para comparacion

            if (texto.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
                JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Graphik", "gk");
                fileChooser.setFileFilter(filtro);
                int seleccion = fileChooser.showSaveDialog(null);
                try{
                    if (seleccion == JFileChooser.APPROVE_OPTION){//comprueba si ha presionado el boton de aceptar
                        File JFC = fileChooser.getSelectedFile();
                        String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                        PrintWriter printwriter = new PrintWriter(JFC);
                        printwriter.print(texto);//escribe en el archivo todo lo que se encuentre en el JTextArea
                        printwriter.close();//cierra el archivo

                        //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                        if(!(PATH.endsWith(".gk"))){
                            File temp = new File(PATH+".gk");
                            JFC.renameTo(temp);//renombramos el archivo
                        }

                        JOptionPane.showMessageDialog(null,"Guardado con exito!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch (Exception e){//por alguna excepcion salta un mensaje de error
                    JOptionPane.showMessageDialog(null,"Error al guardar el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
        }else{
            JOptionPane.showMessageDialog(null,"No hay pestañas activas", "Error", JOptionPane.ERROR_MESSAGE);       
        }
    }
    
    private void abrirArchivo(){
        JFileChooser archivo = new JFileChooser();
        archivo.setVisible(true);
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("Archivos Graphik", "gk");
        archivo.setFileFilter(filtroImagen);
        archivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = archivo.showDialog(this, "Cargar archivo");
        if (JFileChooser.APPROVE_OPTION == seleccion) {
            File file = new File(archivo.getSelectedFile().getAbsolutePath());
            crearPestania(obtenerTextoArchivo(file), file.getName(), file);
        }else{
             /*JOptionPane.showMessageDialog(null,"" +
           "\nNo se ha encontrado el archivo",
                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);*/
        }
    }
    
    String obtenerTextoArchivo(File file) {
        String texto = "";
        try {
            BufferedReader bufer = new BufferedReader(
                    new InputStreamReader(new FileInputStream((String) file.getAbsolutePath())));
            String temp = "";
            while (temp != null) {
                temp = bufer.readLine();
                if (temp != null) {
                    texto = texto + temp + "\n";
                    //txtEditor.append(temp + "\n");
                    temp = "";
                } else {
                }

            }
            bufer.close();
        } catch (Exception e) {
        }
        return texto;
    }
    
    private void nuevoArchivo(){
        String nombre = "nuevo" + ".gk";
        crearPestania("",nombre,null);
    }
    
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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnTablaError;
    private javax.swing.JButton btnTablaSimoblos;
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemCargarFunciones;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemGuardarComo;
    private javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemSalir;
    public static javax.swing.JLabel lblPosicion;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JPanel panelPrincipal;
    public javax.swing.JTabbedPane panelTab;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JTextArea txtConsola;
    // End of variables declaration//GEN-END:variables
}
