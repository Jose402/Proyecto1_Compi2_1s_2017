/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Heredar {

    private ArrayList<Clase> clases = Graphik.clases;
    private Stack<Clase> pilaClases;

    public Heredar(Clase actual) {
        pilaClases = new Stack<>();
        heredar(actual);

    }

    private void heredar(Clase clase) {
        if (clase.nombreHereda != null) {
            clase.herencia = getClase(clase.nombreHereda);
            pilaClases.add(clase);
            heredar(clase.herencia);
        } else {
            clase.ejecutar();
            int j = pilaClases.size();
            for (int i = 0; i < j; i++) {
                Clase hereda = pilaClases.pop();
                hereda.ejecutar();
            }
        }

    }

    private Clase getClase(String nombre) {
        for (Clase clase : clases) {
            if (clase.nombre.equalsIgnoreCase(nombre) && (clase.visibilidad.equalsIgnoreCase("publico") || clase.visibilidad.equalsIgnoreCase("protegido"))) {
                return clase.clonar();
            }
        }
        return null;
    }
}
