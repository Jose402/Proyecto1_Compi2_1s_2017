/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class Si extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {

        Nodo exp = raiz.hijos.get(0);
        Nodo sentenciasSi = raiz.hijos.get(1);
        Nodo sentenciasSino = raiz.hijos.get(2);

        opL = new OperacionLogicaG(global, tabla);
        ResultadoG condicion = opL.operar(exp);

        //cambio de ambito
        TablaSimboloG tablaTemp = new TablaSimboloG();
        tablaTemp.cambiarAmbito(tabla);
        pilaTablas.push(tabla);
        tabla = tablaTemp;

        if (condicion.tipo.equals("entero")) {
            if ((int) condicion.valor == 1) {
                condicion = new ResultadoG("bool", true);
            } else if ((int) condicion.valor == 0) {
                condicion = new ResultadoG("bool", false);
            }
        }

        if (condicion.tipo.equalsIgnoreCase("bool")) {
            if ((Boolean) condicion.valor) {
                metodoActual = ejecutarSentencias(sentenciasSi);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }

            } else {
                metodoActual = ejecutarSentencias(sentenciasSino);
                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores booleanos en la condicion de la sentencia si o enteros 0 o 1");
        }

        //regreso al ambito 
        tabla = pilaTablas.pop();
        return metodoActual;
    }

}
