/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import Ast.Nodo;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Archivo {

    public String nombre;
    Nodo raiz;
    public ArrayList<Clase> clases;
    public ArrayList<String> archivosImportados;
    public ArrayList<String> archivosIncluidos;

    public Archivo(String nombre, Nodo raiz) {
        clases = new ArrayList();
        archivosImportados = new ArrayList<>();
        archivosIncluidos = new ArrayList<>();
        this.nombre = nombre;
        this.raiz = raiz;
        guardarImports(raiz.hijos.get(0));
        guardarClases(raiz.hijos.get(1));
    }

    private void guardarImports(Nodo raiz) {
        for (Nodo imp : raiz.hijos) {
            if (imp.etiqueta.equalsIgnoreCase("importar")) {
                archivosImportados.add(imp.valor);
            } else if (imp.etiqueta.equalsIgnoreCase("incluir")) {
                archivosIncluidos.add(imp.valor);
            }
        }
    }

    private void guardarIncluyes(Nodo raiz) {
        for (Nodo in : raiz.hijos) {
            archivosIncluidos.add(in.valor);
        }
    }

    private void guardarClases(Nodo raiz) {

        for (Nodo hijo : raiz.hijos) {
            Clase clase = new Clase(hijo);
            clase.archivo = nombre;
            clases.add(clase);
        }
    }

}
