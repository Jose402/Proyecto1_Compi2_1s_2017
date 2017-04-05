/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import Interfaz.Inicio;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Heredar {

    private ArrayList<Archivo> archivos = Graphik.archivos;
    private Stack<Clase> pilaClases;

    public Heredar(Clase actual) {
        pilaClases = new Stack<>();
        heredar(actual);

    }

    private void heredar(Clase clase) {
        if (clase.nombreHereda != null) {
            clase.herencia = getClase(clase);
            if (clase.herencia != null) {
                pilaClases.add(clase);
                heredar(clase.herencia);
            } else {
                Inicio.reporteError.agregar("Semantico", 0, 0, "La clase " + clase.nombreHereda + " no existe en el ambito donde fue llamada");
            }
        } else {
            clase.ejecutar();
            int j = pilaClases.size();
            for (int i = 0; i < j; i++) {
                Clase hereda = pilaClases.pop();
                hereda.ejecutar();
            }
        }

    }

    private Clase getClase(Clase clase) {

        Archivo archivo = getArchivo(clase.archivo);
        Archivo aux = archivo;
        ArrayList<Clase> clases = archivo.clases;
        for (Clase hereda : clases) {
            if (hereda.nombre.equalsIgnoreCase(clase.nombreHereda)) {
                return hereda.clonar();
            }
        }

        for (int i = 0; i < archivos.size(); i++) {
            archivo = getArchivo(archivos.get(i).nombre);
            if (archivo != null) {

                clases = archivo.clases;
                for (Clase hereda : clases) {
                    if (hereda.nombre.equalsIgnoreCase(clase.nombreHereda) && hereda.visibilidad.equalsIgnoreCase("publico") && aux.archivosImportados.contains(archivo.nombre)) {
                        return hereda.clonar();
                    }
                }
            }
        }

        return null;
    }

    private Archivo getArchivo(String nombre) {
        for (Archivo archivo : archivos) {
            if (archivo.nombre.equalsIgnoreCase(nombre)) {
                return archivo;
            }
        }
        return null;
    }
}
