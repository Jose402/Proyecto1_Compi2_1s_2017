/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import java.util.*;

/**
 *
 * @author Jose2
 */
public class TablaSimboloG {

    public Hashtable<String, SimboloG> tabla;

    public TablaSimboloG() {
        tabla = new Hashtable<>();
    }

    public Boolean existe(String nombre) {
        nombre = nombre.toLowerCase();
        return tabla.containsKey(nombre);
    }

    public SimboloG getSimbolo(String nombre, Clase claseActual) {
        SimboloG buscado = null;
        TablaSimboloG principal = claseActual.global;
        nombre = nombre.toLowerCase();
        if (existe(nombre)) {
            return tabla.get(nombre);
        } else//si no existe en el ambito local se busca en el ambito global
        {
            if (principal.existe(nombre)) {
                return principal.tabla.get(nombre);
            } else {
                Clase hereda = claseActual.herencia;

                if (hereda != null) {
                    buscado = hereda.global.getSimbolo(nombre, hereda);
                } else {
                    return null;
                }
            }
        }
        if (buscado != null) {
            if (buscado.visibilidad.equalsIgnoreCase("publico") || buscado.visibilidad.equalsIgnoreCase("protegido")) {
                //buscado.seHereda = true;
                return buscado;
            } else {
                return null;
            }
        }
        return buscado;
    }

    public boolean setSimbolo(SimboloG simbolo) {
        simbolo.nombre = simbolo.nombre.toLowerCase();
        if (!existe(simbolo.nombre)) {
            tabla.put(simbolo.nombre, simbolo);
            return true;//se agrego correctamente
        } else {
            return false;//no se agrego a la global
        }
    }

    public void cambiarAmbito(TablaSimboloG actual) {
        for (SimboloG simbolo : actual.tabla.values()) {
            tabla.put(simbolo.nombre, simbolo);
        }
    }

}
