/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import Ast.Nodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.Sentencias.Asignacion;
import Graphik.Compilador.Sentencias.Declaracion;
import Graphik.Compilador.Sentencias.GraficarFuncion;
import Graphik.Compilador.Sentencias.HacerMientras;
import Graphik.Compilador.Sentencias.LlamadaMetodo;
import Graphik.Compilador.Sentencias.Mientras;
import Graphik.Compilador.Sentencias.Para;
import Graphik.Compilador.Sentencias.Retornar;
import Graphik.Compilador.Sentencias.Seleccion;
import Graphik.Compilador.Sentencias.Si;
import Interfaz.Inicio;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public abstract class Compilador {

    public static ArrayList<Clase> clases;
    public static Clase claseActual;
    public static Stack<Clase> pilaClases;
    public static Stack<Metodo> pilaMetodos;
    public static Metodo metodoActual;
    public static Stack<TablaSimboloG> pilaTablas;

    //sirven para el controlar las sentencias continuar y terminar
    public static int nivelCiclo = 0;
    public static Stack<Integer> pilaNivelCiclo;
    protected OperacionLogicaG opL;
    public static TablaSimboloG tabla;
    public static TablaSimboloG global;
    protected Nodo raiz;

    public abstract Metodo ejecutar(Nodo raiz);

    protected Metodo ejecutarSentencias(Nodo sentencias) {
        for (Nodo sentencia : sentencias.hijos) {

            switch (sentencia.etiqueta) {
                case "varLocalD":
                case "varLocalDA":
                case "varLocalAlsD":
                case "varLocalAlsDI":
                case "varLocalArD":
                case "varLocalAlsDD":
                case "varLocalArDD":
                case "varLocalArDA":
                    new Declaracion(sentencia, global, tabla);
                    break;
                case "asignacion":
                case "asignacionAr":
                case "asignacionAlsI":
                    new Asignacion(sentencia, global, tabla);
                    //global=claseActual.global;
                    break;
                case "imprimir":
                    opL = new OperacionLogicaG(global, tabla);
                    ResultadoG imprimir = opL.operar(sentencia.hijos.get(0));
                    Inicio.txtConsola.append(">" + imprimir.valor + "\n");
                    break;
                case "si":
                    Si si = new Si();
                    metodoActual = si.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        return metodoActual;
                    }
                    if (metodoActual.estadoTerminar) {
                        //metodoActual.estadoTerminar=false;
                        return metodoActual;
                    }

                    if (metodoActual.estadoContinuar) {
                        return metodoActual;
                    }
                    break;
                case "seleccion":
                    nivelCiclo++;
                    Seleccion seleccion = new Seleccion();
                    metodoActual = seleccion.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        nivelCiclo--;
                        return metodoActual;
                    }
                    if (metodoActual.estadoContinuar) {
                        nivelCiclo--;
                        return metodoActual;
                    }
                    nivelCiclo--;
                    break;
                case "para":
                    nivelCiclo++;
                    Para para = new Para();
                    metodoActual = para.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        nivelCiclo--;
                        return metodoActual;
                    }
                    nivelCiclo--;
                    break;
                case "mientras":
                    Mientras mientras = new Mientras();
                    nivelCiclo++;
                    metodoActual = mientras.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        nivelCiclo--;
                        return metodoActual;
                    }
                    nivelCiclo--;
                    break;
                case "hacerMientras":
                    HacerMientras hacerMientras = new HacerMientras();
                    nivelCiclo++;
                    metodoActual = hacerMientras.ejecutar(sentencia);
                    if (metodoActual.estadoRetorno) {
                        nivelCiclo--;
                        return metodoActual;
                    }
                    nivelCiclo--;
                    break;
                case "++":
                case "--":
                    opL = new OperacionLogicaG(global, tabla);
                    opL.operar(sentencia);
                    break;
                case "retorno":
                    Retornar retorno = new Retornar();
                    metodoActual = retorno.ejecutar(sentencia);
                    return metodoActual;
                case "acceso":
                    opL = new OperacionLogicaG(global, tabla);
                    opL.operar(sentencia);
                    break;
                case "continuar":
                    if (nivelCiclo > 0) {
                        metodoActual.estadoContinuar = true;
                        return metodoActual;
                    } else {
                        Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia continuar solo puede estar dentro de ciclos");
                    }
                    break;
                case "terminar":
                    if (nivelCiclo > 0) {
                        metodoActual.estadoTerminar = true;
                        return metodoActual;
                    } else {
                        Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia terminar solo puede estar detro de ciclos");
                    }
                    break;
                case "graficarFuncion":
                    GraficarFuncion g = new GraficarFuncion();
                    g.ejecutar(sentencia);
                    break;
            }
        }
        return metodoActual;
    }

}
