/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import Ast.Grafica;
import Ast.Nodo;
import Graphik.Analisis.Lexico;
import Graphik.Analisis.Sintactico;
import Interfaz.Inicio;
import static Interfaz.Inicio.raiz;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Graphik extends Compilador {

    public Graphik(File files[], String archivoActual) {
        archivos = new ArrayList();
        reporteSimbolos = new ArrayList();
        this.archivoActual = archivoActual;
        for (File file : files) {
            String nombre = file.getName();
            String tipo = nombre.substring(nombre.length() - 2, nombre.length());
            if (tipo.equalsIgnoreCase("gk")) {
                String cadena = obtenerTextoArchivo(file);
                try {
                    Sintactico sin = new Sintactico(new Lexico(new BufferedReader(new StringReader(cadena))));
                    sin.parse();
                    Nodo raiz = sin.getRaiz();
                    if (raiz != null) {
                        //new Grafica(raiz);
                        Archivo archivo = new Archivo(nombre, raiz);
                        archivos.add(archivo);
                    }
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            }
        }

        //------------------------------
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        claseActual = getClasePrincipal();
        if (claseActual == null) {
            Inicio.reporteError.agregar("Semantico", 0, 0, "Metodo inicio no encontrado");
            return;
        }
        tabla = claseActual.tabla;
        global = claseActual.global;
        new Heredar(claseActual);

        if (claseActual != null) {
            //claseActual.ejecutar();
            metodoActual = getInicio();
            pilaTablas = claseActual.pilaTablas;
            global = claseActual.global;
            tabla = claseActual.tabla;
            ejecutarSentencias(metodoActual.sentencias);
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Metodo inicio no encontrado");
        }

    }

    //----------------------
    public Graphik(Nodo raiz) {
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        claseActual = getClasePrincipal();
        tabla = claseActual.tabla;
        global = claseActual.global;
        new Heredar(claseActual);

        if (claseActual != null) {
            //claseActual.ejecutar();
            metodoActual = getInicio();
            pilaTablas = claseActual.pilaTablas;
            global = claseActual.global;
            tabla = claseActual.tabla;
            ejecutarSentencias(metodoActual.sentencias);
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Metodo inicio no encontrado");
        }
    }

    private Clase getClasePrincipal() {
        ArrayList<Clase> clases;
        Archivo archivo = getArchivoPrincipal();
        if (archivo == null) {
            return null;
        }
        clases = archivo.clases;
        for (Clase clase : clases) {
            for (Metodo metodo : clase.metodos) {
                if (metodo.nombre.equalsIgnoreCase("inicio") && clase.archivo.equalsIgnoreCase(archivoActual)) {
                    return clase;
                }
            }
        }
        return null;
    }

    private Archivo getArchivoPrincipal() {
        for (Archivo archivo : archivos) {
            if (archivo.nombre.equalsIgnoreCase(archivoActual)) {
                return archivo;
            }
        }
        return null;
    }

    private Metodo getInicio() {
        for (Metodo metodo : claseActual.metodos) {
            if (metodo.nombre.equalsIgnoreCase("inicio")) {
                return metodo;
            }
        }
        return null;
    }

    @Override
    public Metodo ejecutar(Nodo raiz) {
        return null;
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

}
