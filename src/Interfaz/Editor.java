/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author jose
 */
public class Editor extends JPanel {

    //private final JTextPane txtArea;
    private RSyntaxTextArea txtArea;
    public File file;
    public String nombre;
    public String tipo;

    public Editor(String texto, String nombre, File file) {
        this.tipo = nombre.substring(nombre.length() - 2, nombre.length());
        this.file = file;
        this.nombre = nombre;
        txtArea = new RSyntaxTextArea(20, 60);
        txtArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        txtArea.setText(texto);
        //txtArea.setSyntaxEditingStyle("text/SBScript");
        txtArea.setCodeFoldingEnabled(true);
        txtArea.setCurrentLineHighlightColor(new Color(227, 242, 253, 200));
        txtArea.setFadeCurrentLineHighlight(true);
        txtArea.setBorder(BorderFactory.createEmptyBorder());
        actualizarLinea();
        setLayout(new BorderLayout());
        RTextScrollPane rtsp = new RTextScrollPane(txtArea);
        rtsp.setViewportBorder(BorderFactory.createEmptyBorder());
        add(rtsp);
        rtsp.setViewportView(txtArea);
    }

    public String getText() {
        return txtArea.getText();
    }

    public void setText(String texto) {
        txtArea.setText(texto);
    }

    public JTextArea getTextArea() {
        return txtArea;
    }

    private void actualizarLinea() {
        txtArea.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                actualizarLabel();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        txtArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                actualizarLabel();
            }
        });
    }

    private void actualizarLabel() {
        try {
            int caretPos = txtArea.getCaretPosition();
            int offset = Utilities.getRowStart(txtArea, caretPos);
            int columna = caretPos - offset + 1;
            int linea = txtArea.getLineOfOffset(caretPos);
            linea += 1;
            Inicio.lblPosicion.setText("Linea:" + linea + " Columna:" + columna);
        } catch (BadLocationException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String getTextLine() {
        try {
            String cadena = "";
            int caretPos = txtArea.getCaretPosition();
            int linea = txtArea.getLineOfOffset(caretPos);
            int offset = Utilities.getRowStart(txtArea, caretPos);
            int columna = caretPos - offset;
            cadena = txtArea.getText(linea, columna);
            return cadena;
        } catch (BadLocationException ex) {

        }
        return "";
    }
}
