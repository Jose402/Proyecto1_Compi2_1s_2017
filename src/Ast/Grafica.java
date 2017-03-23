/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ast;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Jose2
 */
public class Grafica {

    private File archivoDot;
    private String cadenaDot = "";

    public Grafica(Nodo opRaiz) {
        String nombre = "ast";
        cadenaDot = cadenaDot + "digraph lista{ rankdir=TB;node [shape = box, style=rounded]; ";
        recorrer(opRaiz);
        cadenaDot = cadenaDot + "}";
        try {

            archivoDot = new File("Graficas\\" + nombre + ".txt");
            FileWriter archivo = new FileWriter(archivoDot);
            archivo.write(cadenaDot);
            archivo.close();
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        }
        pintar(nombre);
        //operar(opRaiz);
        //relacionar(opRaiz);
        //System.out.println("resultado= "+opRaiz.valor);
    }

    private void recorrer(Nodo raiz) {
        if (raiz.valor != null) {
            String valor = raiz.valor;
            valor = valor.replace("\"", "\\\"");
            cadenaDot = cadenaDot + "nodo" + raiz.hashCode() + "[label=\"" + valor + "  [" + raiz.etiqueta + "]" + " \", color=\"cyan\", style =\"filled\", shape=\"doublecircle\"]; \n";
        } else {
            cadenaDot = cadenaDot + "nodo" + raiz.hashCode() + "[label=\"" + raiz.etiqueta + " \", color=\"cyan\", style =\"filled\", shape=\"doublecircle\"]; \n";
        }

        for (Nodo hijo : raiz.hijos) {
            cadenaDot = cadenaDot + "\"nodo" + raiz.hashCode() + "\"-> \"nodo" + hijo.hashCode() + "\"" + "\n";
        }

        for (Nodo hijo : raiz.hijos) {
            recorrer(hijo);
        }
    }

    private void pintar(String titulo) {

        try {

            String dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";

            String fileInputPath = "Graficas\\" + titulo + ".txt";
            String fileOutputPath = "Graficas\\" + titulo + ".jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            System.out.println("Error: " + ex);

        } finally {
            //      JOptionPane.showMessageDialog(, "Eliminado", "", JOptionPane.INFORMATION_MESSAGE);
//            JOptionPane.showMessageDialog(this,"Imagen generada con éxito en I:\\"+nomArchivo+".jpg"); 
        }

    }
}
