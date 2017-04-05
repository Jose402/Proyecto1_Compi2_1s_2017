/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Arreglo;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class GraficarFuncion extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo exp1 = raiz.hijos.get(0);
        Nodo exp2 = raiz.hijos.get(1);

        Arreglo arreglo1 = null;
        Arreglo arreglo2 = null;

        //arrelgo1
        opL = new OperacionLogicaG(global, tabla);
        ResultadoG resultado1 = opL.operar(exp1);

        //arreglo2
        opL = new OperacionLogicaG(global, tabla);
        ResultadoG resultado2 = opL.operar(exp2);

        if (resultado1.tipo.equalsIgnoreCase("caracter") || resultado2.tipo.equalsIgnoreCase("caracter") || resultado1.tipo.equalsIgnoreCase("cadena") || resultado2.tipo.equalsIgnoreCase("cadena") || resultado1.tipo.equalsIgnoreCase("bool") || resultado2.tipo.equalsIgnoreCase("bool")) {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La funcion Graficar solo permite arreglos de enteros");
            return metodoActual;
        }

        if (resultado1.valor != null) {
            if (resultado1.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                arreglo1 = (Arreglo) resultado1.valor;
                if (arreglo1.dimensiones.size() > 1) {
                    arreglo1 = null;
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La funcion graficar solo permite arreglos de una dimension");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El primer parametro de la funcion graficar no es un arreglo");
            }
        }

        if (resultado2.valor != null) {
            if (resultado2.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                arreglo2 = (Arreglo) resultado2.valor;
                if (arreglo2.dimensiones.size() > 1) {
                    arreglo2 = null;
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La funcion graficar solo permite arreglos de una dimension");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El segundo parametro de la funcion graficar no es un arreglo");
            }
        }

        if (arreglo1 != null && arreglo2 != null) {
            if (arreglo1.getSize() == arreglo2.getSize()) {
                Inicio.coordenadas.setCoordenada((ArrayList<Object>) arreglo1.getDatos().clone(), (ArrayList<Object>) arreglo2.getDatos().clone());
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Los arreglos no poseen la misma cantidad de datos");
            }
        }

        return metodoActual;
    }

}
