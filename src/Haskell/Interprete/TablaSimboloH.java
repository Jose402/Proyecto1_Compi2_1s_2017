/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import java.util.*;

/**
 *
 * @author Jose2
 */
public class TablaSimboloH {

    private Hashtable<String, SimboloH> tabla;

    public TablaSimboloH() {
        tabla = new Hashtable<>();
    }

    public Boolean existe(String nombre) {
        nombre = nombre.toLowerCase();
        return tabla.containsKey(nombre);
    }

    public Boolean setSimbolo(SimboloH simbolo) {
        simbolo.nombre = simbolo.nombre.toLowerCase();
        if (!existe(simbolo.nombre)) {
            //si el simbolo se ingreso correctamente
            tabla.put(simbolo.nombre, simbolo);
            return true;
        } else {
            //si el simbolo ya existe no se agrega a la tabla
            return false;
        }
    }

    public SimboloH getSimbolo(String nombre) {
        nombre = nombre.toLowerCase();
        if (existe(nombre)) {
            return tabla.get(nombre);
        } else {
            return null;
        }
    }

    public void cambiarAmbito(TablaSimboloH actual) {
        for (SimboloH simbolo : actual.tabla.values()) {
            tabla.put(simbolo.nombre, simbolo);
        }
    }
}
