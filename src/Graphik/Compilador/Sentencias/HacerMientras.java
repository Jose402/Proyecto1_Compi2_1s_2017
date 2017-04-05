/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Compilador;
import static Graphik.Compilador.Compilador.global;
import static Graphik.Compilador.Compilador.metodoActual;
import static Graphik.Compilador.Compilador.pilaTablas;
import static Graphik.Compilador.Compilador.tabla;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class HacerMientras extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo exp = raiz.hijos.get(0);
        Nodo sentencias = raiz.hijos.get(1);

        ResultadoG condicion = new ResultadoG("bool", false);
        boolean doWhile = true;
        if (condicion.tipo.equalsIgnoreCase("bool")) {
            while ((Boolean) condicion.valor || doWhile) {
                doWhile = false;
                //se cambia el ambito
                TablaSimboloG tablaTemp = new TablaSimboloG();
                tablaTemp.cambiarAmbito(tabla);
                pilaTablas.push(tabla);
                tabla = tablaTemp;
                metodoActual = ejecutarSentencias(sentencias);
                //regresamos al ambito
                tabla = pilaTablas.pop();
                opL = new OperacionLogicaG(global, tabla);
                condicion = opL.operar(exp);
                if (condicion.tipo.equals("entero")) {
                    if ((int) condicion.valor == 1) {
                        condicion = new ResultadoG("bool", true);
                    } else if ((int) condicion.valor == 0) {
                        condicion = new ResultadoG("bool", false);
                    }
                }

                if (metodoActual.estadoRetorno) {
                    break;
                }
                if (metodoActual.estadoTerminar) {
                    metodoActual.estadoTerminar = false;
                    break;
                }

                if (metodoActual.estadoContinuar) {
                    metodoActual.estadoContinuar = false;
                }

            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo de dato de la condicion no es Bool");
        }

        return metodoActual;
    }

}
