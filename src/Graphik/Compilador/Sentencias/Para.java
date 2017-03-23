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
import Graphik.Compilador.SimboloG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class Para extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {

        Nodo variable = raiz.hijos.get(0);
        Nodo expCondicion = raiz.hijos.get(1);
        Nodo expSimplificada = raiz.hijos.get(2);
        Nodo sentencias = raiz.hijos.get(3);

        TablaSimboloG tablaTempPrincipal = new TablaSimboloG();
        tablaTempPrincipal.cambiarAmbito(tabla);
        pilaTablas.add(tabla);
        tabla = tablaTempPrincipal;

        SimboloG simbolo = null;
        if (variable.etiqueta.equals("asignacion")) {
            Asignacion asig = new Asignacion(variable, global, tabla, "asignacion");
            simbolo = asig.asignar();
        } else {
            Declaracion declara = new Declaracion(variable, global, tabla, "Declaracon");
            simbolo = (SimboloG) declara.declarar();
        }

        if (simbolo != null) {

            opL = new OperacionLogicaG(global, tabla);
            ResultadoG condicion = opL.operar(expCondicion);

            if (condicion.tipo.equals("bool")) {
                while ((Boolean) condicion.valor) {
                    TablaSimboloG tablaTemp = new TablaSimboloG();
                    tablaTemp.cambiarAmbito(tabla);
                    pilaTablas.push(tabla);
                    tabla = tablaTemp;
                    metodoActual = ejecutarSentencias(sentencias);
                    tabla = pilaTablas.pop();
                    opL = new OperacionLogicaG(global, tabla);
                    opL.operar(expSimplificada);
                    //if(expSimplificada.etiqueta.equals("++")){
                    //  simbolo.valor=(int)simbolo.valor+1;
                    //}else{
                    //  simbolo.valor=(int)simbolo.valor-1;
                    //}
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

                    condicion = opL.operar(expCondicion);
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La condicion del ciclo para solo permite operaciones que retornan un valor booleano");
            }

        }
        tabla = pilaTablas.pop();
        return metodoActual;
    }

}
