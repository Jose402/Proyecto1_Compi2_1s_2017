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
public class Columna {

    public String nombre;
    public ArrayList<Celda> celdas;

    public Columna() {
        celdas = new ArrayList<>();
    }

    public Columna(String nombre) {
        this.nombre = nombre.replace("\"", "");
        celdas = new ArrayList();
    }
}
