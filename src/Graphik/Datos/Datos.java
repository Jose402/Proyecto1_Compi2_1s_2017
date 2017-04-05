/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Datos;

import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Datos {

    public ArrayList<Columna> datos;
    public int indice = 0;

    public Datos() {
        datos = new ArrayList();
    }

    public int getNumeroFilas() {
        try {
            return datos.get(0).celdas.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void add(Columna columna) {
        datos.add(columna);

    }

    public void addCelda(Celda celda) {
        if (celda != null) {
            datos.get(indice++).celdas.add(celda);
        }
    }

    public Celda getDato(int columna, int celda) {
        try {
            return datos.get(columna).celdas.get(celda);
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public Columna getColumna(int indice) {
        indice = indice - 1;
        if (indice >= 0 && indice < datos.size()) {
            return datos.get(indice);
        } else {
            return null;
        }
    }

}
