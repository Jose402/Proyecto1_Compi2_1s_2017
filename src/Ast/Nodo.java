/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ast;

import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Nodo {

    public String etiqueta;
    public String valor;
    public int linea;
    public int columna;
    public ArrayList<Nodo> hijos;

    public Nodo() {
        hijos = new ArrayList<>();
    }

    public Nodo(String etiqueta) {
        this.etiqueta = etiqueta;
        hijos = new ArrayList<>();
    }

    public Nodo(String etiqueta, int linea, int columna) {
        this.etiqueta = etiqueta;
        this.linea = linea + 1;
        this.columna = columna + 1;
        hijos = new ArrayList<>();
    }

    public Nodo(String etiqueta, String valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
        hijos = new ArrayList<>();
    }

    public Nodo(String etiqueta, String valor, int linea, int columna) {
        this.etiqueta = etiqueta;
        this.valor = valor;
        this.linea = linea + 1;
        this.columna = columna + 1;
        hijos = new ArrayList<>();
    }

    public void add(Nodo nodo) {
        if (nodo != null) {
            hijos.add(nodo);
        }
    }
}
