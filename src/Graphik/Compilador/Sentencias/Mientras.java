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
public class Mientras extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo exp = raiz.hijos.get(0);
        Nodo sentencias = raiz.hijos.get(1);

        opL = new OperacionLogicaG(global, tabla);
        ResultadoG condicion = opL.operar(exp);
        if (condicion.tipo.equalsIgnoreCase("bool")) {
            while ((Boolean) condicion.valor) {
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
