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
public class Seleccion extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {
        Nodo acceso = raiz.hijos.get(0);
        Nodo casos = raiz.hijos.get(1);
        boolean estado = true;

        if (casos.hijos.size() > 0) {
            for (int i = 0; i < (casos.hijos.size() - 1); i++) {
                Nodo caso = casos.hijos.get(i);
                Nodo comp = caso.hijos.get(0);
                Nodo sentencias = caso.hijos.get(1);
                Nodo exp = new Nodo("==", comp.linea - 1, comp.columna - 1);
                exp.add(acceso);
                exp.add(comp);

                opL = new OperacionLogicaG(global, tabla);
                ResultadoG condicion = opL.operar(exp);
                if (condicion.tipo.equals("bool")) {
                    if ((Boolean) condicion.valor) {
                        TablaSimboloG tablaTemp = new TablaSimboloG();
                        tablaTemp.cambiarAmbito(tabla);
                        pilaTablas.push(tabla);
                        tabla = tablaTemp;
                        metodoActual = ejecutarSentencias(sentencias);

                        if (metodoActual.estadoRetorno) {
                            tabla = pilaTablas.pop();
                            return metodoActual;
                        }

                        if (metodoActual.estadoTerminar) {
                            estado = false;
                            tabla = pilaTablas.pop();
                            metodoActual.estadoTerminar = false;
                            break;
                        }
                        if (metodoActual.estadoContinuar) {
                            estado = false;
                            tabla = pilaTablas.pop();
                            //metodoActual.estadoContinuar=false;
                            break;
                        }
                        tabla = pilaTablas.pop();

                    }
                }

            }
            //defecto
            if (estado) {
                Nodo caso = casos.hijos.get(casos.hijos.size() - 1);
                Nodo sentencias = caso.hijos.get(0);

                estado = false;
                TablaSimboloG tablaTemp = new TablaSimboloG();
                tablaTemp.cambiarAmbito(tabla);
                pilaTablas.push(tabla);
                tabla = tablaTemp;
                metodoActual = ejecutarSentencias(sentencias);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }

                if (metodoActual.estadoTerminar) {
                    tabla = pilaTablas.pop();
                    metodoActual.estadoTerminar = false;
                }
                if (metodoActual.estadoContinuar) {
                    estado = false;
                    tabla = pilaTablas.pop();
                    //metodoActual.estadoContinuar=false;
                } else {
                    tabla = pilaTablas.pop();
                }
            }

        }

        return metodoActual;
    }

}
