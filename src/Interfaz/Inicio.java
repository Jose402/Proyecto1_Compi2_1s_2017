package Interfaz;

import Haskell.Analisis.*;
import Ast.Grafica;
import Ast.Nodo;
import Graphik.Analisis.*;
import Graphik.AnalisisDatos.LexicoD;
import Graphik.AnalisisDatos.SintacticoD;
import Graphik.Compilador.Archivo;
import Graphik.Compilador.Graphik;
import Graphik.Compilador.Operaciones.OperacionAritmeticaG;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.Operaciones.OperacionRelacionalG;
import Graphik.Compilador.ResultadoG;
import Graphik.Datos.Datos;
import Graphik.Grafica.Coordenadas;
import Graphik.Grafica.Plano;
import Haskell.Interprete.Consola;
import Haskell.Interprete.*;
import Haskell.Interprete.ArbolFunciones.Arbol;
import Haskell.Interprete.Operaciones.OperacionAritmetica;
import Reporte.ReporteError;
import Reporte.ReporteTablaSimbolo;
import Servidor.Conexion;
import Servidor.Interfaz.Login;
import Servidor.Interfaz.VentanaImportar;
import Servidor.Interfaz.VentanaProyecto;
import Servidor.Respuesta;
import Servidor.Usuario;
import com.google.gson.Gson;
import java.awt.Toolkit;
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
    public int indicePestania = 0;
    public ArrayList<Editor> listaPestania;
    private Editor pestaniaActiva;
    Consola consola;

    //--------------atributos staticos---------------------
    public static Interprete interprete;
    public static Graphik compilador;
    public static ReporteError reporteError;//errores del lenguaje graphik
    public static ReporteError reporteError2;//errores del lenguaje haskell++
    public static Coordenadas coordenadas;
    public static Plano plano;
    //-------------------------------------------

    //servidor web
    public static Usuario usuario;
    public static Datos datos;

    public Inicio() {
        initComponents();
        listaPestania = new ArrayList<>();
        consola = new Consola(txtConsola);
        setTitle("Graphik & Haskell++");
        txtConsola.setText(">Bienvenido a Graphik & Haskell++");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/haskell.png")));
        lblUsuario.setVisible(false);
        itemLogout.setVisible(false);

        String json = "{\"usuario\":\"Jose\",\"contrasenia\":\"201213402\","
                + "\"nombre\":\"Jose\",\"apellido\":\"Soc\",\"correo\":\"socmartinez13@gmail.com\"}";
        Gson gson = new Gson();
        Nodo padre = new Nodo("padre", "Juan");
        Nodo hijo1 = new Nodo("hijo", "Mario");
        Nodo hijo2 = new Nodo("hijo", "Raquel");
        padre.add(hijo1);
        padre.add(hijo2);
        String cadenaJson = gson.toJson(padre);
        //System.out.println(cadenaJson);
        //Usuario user=gson.fromJson(json,Usuario.class);
        Nodo nuevoPadre = gson.fromJson(cadenaJson, Nodo.class);
        int a = 2 + 3 * 4;

    }

    public void crearPestania(String texto, String nombre, File file) {
        //"text/html"
        Editor pestania = new Editor(texto, nombre, file);
        Panel panelTitulo = new Panel(this);
        panelTab.addTab(nombre, pestania);
        panelTab.setTabComponentAt(indicePestania, panelTitulo);
        panelTab.setSelectedIndex(indicePestania);
        indicePestania++;
        pestaniaActiva = pestania;
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
        lblPosicion = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        itemAbrir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemGuardar = new javax.swing.JMenuItem();
        itemGuardarComo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        itemErrores = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        itemCargarFunciones = new javax.swing.JMenuItem();
        itemMostarArbol = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itemLogin = new javax.swing.JMenuItem();
        itemLogout = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

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

        lblPosicion.setText(" Linea:0 Columna:0");

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/usuario.png"))); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTab)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addContainerGap())
            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(lblPosicion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCompilar)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTab, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPosicion, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuBar.setBackground(new java.awt.Color(255, 255, 255));
        menuBar.setToolTipText("");

        menuArchivo.setText("Archivo");

        itemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/nuevoGk.png"))); // NOI18N
        itemNuevo.setText("Nuevo Graphik");
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemNuevo);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/nuevoHk.png"))); // NOI18N
        jMenuItem1.setText("Nuevo Haskell++");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuArchivo.add(jMenuItem1);

        itemAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoAbrirD.png"))); // NOI18N
        itemAbrir.setText("Abrir");
        itemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemAbrir);
        menuArchivo.add(jSeparator1);

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
        menuArchivo.add(jSeparator2);

        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/iconoSalir.png"))); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);

        jMenu1.setText("Graphik");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/upload_database_21015.png"))); // NOI18N
        jMenuItem3.setText("Cargar datos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator5);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tabla2.png"))); // NOI18N
        jMenuItem2.setText("Tabla de simbolos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        itemErrores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tablaE2.png"))); // NOI18N
        itemErrores.setText("Errores");
        itemErrores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemErroresActionPerformed(evt);
            }
        });
        jMenu1.add(itemErrores);

        menuBar.add(jMenu1);

        menuEditar.setText("Haskell++");

        itemCargarFunciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/inicio1.png"))); // NOI18N
        itemCargarFunciones.setText("Cargar Funciones");
        itemCargarFunciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCargarFuncionesActionPerformed(evt);
            }
        });
        menuEditar.add(itemCargarFunciones);

        itemMostarArbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tree.png"))); // NOI18N
        itemMostarArbol.setText("Arbol de funciones");
        itemMostarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMostarArbolActionPerformed(evt);
            }
        });
        menuEditar.add(itemMostarArbol);
        menuEditar.add(jSeparator6);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/tablaE2.png"))); // NOI18N
        jMenuItem8.setText("Errores");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menuEditar.add(jMenuItem8);

        menuBar.add(menuEditar);

        jMenu2.setText("Servidor");

        itemLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/usuario.png"))); // NOI18N
        itemLogin.setText("Login");
        itemLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLoginActionPerformed(evt);
            }
        });
        jMenu2.add(itemLogin);

        itemLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/usuario2.png"))); // NOI18N
        itemLogout.setText("Logout");
        itemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLogoutActionPerformed(evt);
            }
        });
        jMenu2.add(itemLogout);
        jMenu2.add(jSeparator3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/openproject_1078.png"))); // NOI18N
        jMenuItem4.setText("Crear proyecto");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/expImp.png"))); // NOI18N
        jMenuItem5.setText("Importar/Exportar Proyecto");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        menuBar.add(jMenu2);

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
        nuevoArchivo(".gk");
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

    private void analizarArchivos() {
        pestaniaActiva = (Editor) panelTab.getSelectedComponent();
        Graphik.archivos = new ArrayList();
        if (pestaniaActiva != null) {
            if (pestaniaActiva.file != null) {
                File file = new File(pestaniaActiva.file.getParent());
                File files[] = file.listFiles();
                Graphik graphik = new Graphik(files, pestaniaActiva.nombre);
            }
        } else {

        }
    }

    public static Nodo raiz;
    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        pestaniaActiva = (Editor) panelTab.getSelectedComponent();
        if (pestaniaActiva == null) {
            JOptionPane.showMessageDialog(null, "No hay pestaña activas", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pestaniaActiva.tipo.equalsIgnoreCase("gk")) {
            JOptionPane.showMessageDialog(null, "La pestaña actual no contiene instrucciones Graphik", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Editor p : listaPestania) {
            if (p.file != null) {
                guardarArchivo(p.file, p.getText());
            }
        }

        txtConsola.setText("");
        reporteError = new ReporteError();
        coordenadas = new Coordenadas();
        reporteError2 = new ReporteError();
        analizarArchivos();
        if (!coordenadas.vacio()) {
            plano = new Plano();
        }

    }//GEN-LAST:event_btnCompilarActionPerformed

    private void txtConsolaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConsolaKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == evt.VK_ENTER) {
            String cadena = getTextLine();
            char c = cadena.charAt(0);
            if (c != '>') {
                try {

                    new SintacticoH(new LexicoH(new BufferedReader(new StringReader(cadena)))).parse();
                    consola.ejecutar(raiz);
                } catch (Exception ex) {
                    System.out.println(ex + "");
                }
            }
        }
    }//GEN-LAST:event_txtConsolaKeyPressed

    public static String archivoHaskellActual = "";
    private void itemCargarFuncionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCargarFuncionesActionPerformed
        pestaniaActiva = (Editor) panelTab.getSelectedComponent();
        reporteError2 = new ReporteError();
        if (pestaniaActiva != null) {
            if (!pestaniaActiva.tipo.equalsIgnoreCase("hk")) {
                JOptionPane.showMessageDialog(null, "La pestaña actual no contiene instrucciones Haskell++", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                // TODO add your handling code here:
                String cadena = pestaniaActiva.getText();
                archivoHaskellActual = pestaniaActiva.nombre;
                SintacticoH sintactico = new SintacticoH(new LexicoH(new BufferedReader(new StringReader(cadena))));
                sintactico.parse();
                Nodo raiz = sintactico.getRaiz();
                if (raiz != null) {
                    interprete = new Interprete(raiz, pestaniaActiva.nombre, cadena);
                    txtConsola.append("\n>Funciones Haskell++ cargadas");
                }

            } catch (Exception ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay pestañas activas", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_itemCargarFuncionesActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        nuevoArchivo(".hk");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void itemMostarArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMostarArbolActionPerformed
        // TODO add your handling code here:
        if (interprete == null) {
            JOptionPane.showMessageDialog(null, "No hay funciones haskell++ cargadas", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }
        Arbol arbol = new Arbol();
        arbol.setVisible(true);
    }//GEN-LAST:event_itemMostarArbolActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        datos = new Datos();
        String cadena = "";
        JFileChooser archivo = new JFileChooser();
        archivo.setVisible(true);
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("Archivos csv", "csv");
        archivo.setFileFilter(filtroImagen);
        archivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = archivo.showDialog(this, "Cargar datos");
        if (JFileChooser.APPROVE_OPTION == seleccion) {
            File file = new File(archivo.getSelectedFile().getAbsolutePath());
            cadena = obtenerTextoArchivo(file);
        } else {
            /*JOptionPane.showMessageDialog(null,"" +
           "\nNo se ha encontrado el archivo",
                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);*/
        }
        try {
            // TODO add your handling code here:

            SintacticoD sintactico = new SintacticoD(new LexicoD(new BufferedReader(new StringReader(cadena))));
            sintactico.parse();
            txtConsola.append("\n>Se cargaron los datos correctamente");
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void itemErroresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemErroresActionPerformed

        if (reporteError != null) {
            reporteError.generarHtml("ErroresGraphik", "Lenguaje Graphik");
        } else {
            JOptionPane.showMessageDialog(this, "No hay errores para mostrar", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_itemErroresActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        if (reporteError2 != null) {
            reporteError2.generarHtml("ErroresHaskell", "Lenguaje Haskell++");
        } else {
            JOptionPane.showMessageDialog(this, "No hay errores para mostrar", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if (Graphik.reporteSimbolos != null) {
            new ReporteTablaSimbolo();
        } else {
            JOptionPane.showMessageDialog(this, "No hay Simbolos para mostrar", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void itemLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLoginActionPerformed
        // TODO add your handling code here:
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_itemLoginActionPerformed

    private void itemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLogoutActionPerformed
        // TODO add your handling code here:
        usuario = null;
        itemLogin.setVisible(true);
        itemLogout.setVisible(false);
        lblUsuario.setVisible(false);
    }//GEN-LAST:event_itemLogoutActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        if (usuario == null) {
            Login login = new Login();
            login.setVisible(true);
        } else {
            VentanaProyecto proyecto = new VentanaProyecto();
            proyecto.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        if (usuario == null) {
            Login login = new Login();
            login.setVisible(true);
        } else {
            VentanaImportar impExp = new VentanaImportar(this);
            impExp.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private String getTextLine() {
        String cadena = txtConsola.getText();
        String cadenas[] = cadena.split("\n");
        return cadenas[cadenas.length - 1];
    }

    private void guardarArchivo(File file, String texto) {
        PrintWriter printwriter = null;
        try {
            printwriter = new PrintWriter(file);
            printwriter.print(texto);//escribe en el archivo todo lo que se encuentre en el JTextArea
            printwriter.close();//cierra el archivo
        } catch (Exception e) {

        } finally {
            printwriter.close();
        }
    }

    private void guardar() {
        pestaniaActiva = (Editor) panelTab.getSelectedComponent();
        if (pestaniaActiva != null) {
            if (pestaniaActiva.file != null) {
                if (pestaniaActiva.getText().matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
                    JOptionPane.showMessageDialog(null, "No hay texto para guardar!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    guardarArchivo(pestaniaActiva.file, pestaniaActiva.getText());
                }
            } else {
                guardarComo();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay pestañas activas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarComo() {

        pestaniaActiva = (Editor) panelTab.getSelectedComponent();

        if (pestaniaActiva != null) {
            String tipo = "";
            String contenido = "";
            if (pestaniaActiva.tipo.equalsIgnoreCase("gk")) {
                tipo = "gk";
                contenido = "Guardar archivo Graphik";
            } else {
                tipo = "hk";
                contenido = "Guardar archivo Haskell++";
            }

            String texto = pestaniaActiva.getText();//variable para comparacion

            if (texto.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
                JOptionPane.showMessageDialog(null, "No hay texto para guardar!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filtro = new FileNameExtensionFilter(contenido, tipo);
                fileChooser.setFileFilter(filtro);
                int seleccion = fileChooser.showSaveDialog(this);
                try {
                    if (seleccion == JFileChooser.APPROVE_OPTION) {//comprueba si ha presionado el boton de aceptar

                        File JFC = fileChooser.getSelectedFile();
                        String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar

                        panelTab.setTitleAt(panelTab.getSelectedIndex(), JFC.getName() + "." + pestaniaActiva.tipo);
                        if (pestaniaActiva.file == null) {
                            File nuevo = new File(PATH + "." + tipo);
                            pestaniaActiva.file = nuevo;
                        }
                        PrintWriter printwriter = new PrintWriter(JFC);
                        printwriter.print(texto);//escribe en el archivo todo lo que se encuentre en el JTextArea
                        printwriter.close();//cierra el archivo

                        //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                        if (!(PATH.endsWith("." + tipo))) {
                            File temp = new File(PATH + "." + tipo);
                            JFC.renameTo(temp);//renombramos el archivo
                        }

                        JOptionPane.showMessageDialog(null, "Guardado con exito!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {//por alguna excepcion salta un mensaje de error
                    JOptionPane.showMessageDialog(null, "Error al guardar el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay pestañas activas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirArchivo() {
        JFileChooser archivo = new JFileChooser();
        archivo.setVisible(true);
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("Archivos Graphik|Haskell", "gk", "hk");
        archivo.setFileFilter(filtroImagen);
        archivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = archivo.showDialog(this, "Cargar archivo");
        if (JFileChooser.APPROVE_OPTION == seleccion) {
            File file = new File(archivo.getSelectedFile().getAbsolutePath());
            crearPestania(obtenerTextoArchivo(file), file.getName(), file);
        } else {
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

    private void nuevoArchivo(String extension) {
        String nombre = "nuevo" + extension;
        crearPestania("", nombre, null);
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
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemCargarFunciones;
    private javax.swing.JMenuItem itemErrores;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemGuardarComo;
    public static javax.swing.JMenuItem itemLogin;
    public static javax.swing.JMenuItem itemLogout;
    private javax.swing.JMenuItem itemMostarArbol;
    private javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    public static javax.swing.JLabel lblPosicion;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JPanel panelPrincipal;
    public javax.swing.JTabbedPane panelTab;
    private javax.swing.JScrollPane scrollPanel;
    public static javax.swing.JTextArea txtConsola;
    // End of variables declaration//GEN-END:variables
}
