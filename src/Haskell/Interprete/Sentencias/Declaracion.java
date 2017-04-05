/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Sentencias;

import Ast.Nodo;
import Haskell.Interprete.Interprete;
import Haskell.Interprete.Lista;
import Haskell.Interprete.Operaciones.OperacionNativa;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.SimboloH;
import Haskell.Interprete.TablaSimboloH;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class Declaracion {

    private TablaSimboloH tabla;
    private OperacionNativa opN;

    public Declaracion(Nodo raiz, TablaSimboloH tabla) {
        this.tabla = tabla;
        declarar(raiz);
    }

    public Declaracion(TablaSimboloH tabla) {
        this.tabla = tabla;
    }

    public SimboloH declarar(Nodo raiz) {
        return declaracion(raiz);
    }

    public Declaracion(String nombre, ResultadoH valor, TablaSimboloH tabla, Nodo raiz) {
        this.tabla = tabla;

        SimboloH simbolo = new SimboloH(valor.tipo, nombre, valor.valor);
        simbolo.lista = valor.lista;
        if (!tabla.setSimbolo(simbolo)) {

            Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe", Inicio.interprete.archivo);
        }

    }

    private SimboloH declaracion(Nodo raiz) {
        opN = new OperacionNativa(tabla);
        Lista lista = null;
        if (!raiz.hijos.isEmpty()) {
            lista = opN.operacionLista(raiz.hijos.get(0));
        } else {
            lista = new Lista();
            lista.indices.add(0);
        }
        String nombre = raiz.valor;

        SimboloH simbolo = new SimboloH(nombre, lista);
        if (!tabla.setSimbolo(simbolo)) {
            Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe", Inicio.interprete.archivo);
            return null;
        } else {
            return simbolo;
        }
    }

}
