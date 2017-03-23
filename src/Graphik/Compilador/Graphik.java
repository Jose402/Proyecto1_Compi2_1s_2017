/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import Ast.Nodo;
import Interfaz.Inicio;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Graphik extends Compilador {

    //----------------------
    public Graphik(Nodo raiz) {
        pilaNivelCiclo = new Stack<>();
        clases = new ArrayList<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        guardarClases(raiz.hijos.get(2));
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

    private void guardarClases(Nodo raiz) {
        for (Nodo hijo : raiz.hijos) {
            Clase clase = new Clase(hijo);
            clases.add(clase);
        }
    }

    private Clase getClasePrincipal() {
        for (Clase clase : clases) {
            for (Metodo metodo : clase.metodos) {
                if (metodo.nombre.equalsIgnoreCase("inicio")) {
                    return clase;
                }
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

}
