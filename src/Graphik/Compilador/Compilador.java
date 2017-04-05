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
import Graphik.Datos.*;
import Interfaz.Inicio;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public abstract class Compilador {

    //archivos
    public static ArrayList<Archivo> archivos;
    public String archivoActual;
    public static ArrayList<SimboloG> reporteSimbolos;

    //public static ArrayList<Clase> clases;
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

    //datos---------------------
    private Columna columna;
    public static int indice = 0;

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
                case "procesar":
                    if (metodoActual.nombre.equalsIgnoreCase("datos")) {
                        if (Inicio.datos != null) {
                            procesar(sentencia);
                        } else {
                            Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "No hay datos cargados");
                        }
                    } else {
                        Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia procesar solo se puede usar en el metodo datos");
                    }
                    break;
                case "donde":
                    if (metodoActual.nombre.equalsIgnoreCase("datos")) {
                        if (Inicio.datos != null) {
                            donde(sentencia);
                        } else {
                            Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "No hay datos cargados");
                        }
                    } else {
                        Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia procesar solo se puede usar en el metodo datos");
                    }
                    break;
                case "dondeCada":
                    if (metodoActual.nombre.equalsIgnoreCase("datos")) {
                        if (Inicio.datos != null) {
                            dondeCada(sentencia);
                        } else {
                            Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "No hay datos cargados");
                        }
                    } else {
                        Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia procesar solo se puede usar en el metodo datos");
                    }
                    break;
                case "dondeTodo":
                    if (metodoActual.nombre.equalsIgnoreCase("datos")) {
                        if (Inicio.datos != null) {
                            dondeTodo(sentencia);
                        } else {
                            Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "No hay datos cargados");
                        }
                    } else {
                        Inicio.reporteError.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia procesar solo se puede usar en el metodo datos");
                    }
                    break;
            }
        }
        return metodoActual;
    }

    private void procesar(Nodo sentencia) {
        columna = new Columna("procesar");
        int filas = Inicio.datos.getNumeroFilas();
        Nodo exp = sentencia.hijos.get(0);
        for (indice = 0; indice < filas; indice++) {
            opL = new OperacionLogicaG(global, tabla);
            ResultadoG res = opL.operar(exp);
            if (res.valor != null) {
                Celda celda = new Celda(res.tipo, res.valor);
                columna.celdas.add(celda);
            }
        }
    }

    private void dondeCada(Nodo sentencia) {
        Datos datosDonde = new Datos();
        Columna col = new Columna("dondeCada");
        Columna pro = new Columna("Procesar");
        datosDonde.add(col);
        datosDonde.add(pro);
        Nodo exp1 = sentencia.hijos.get(0);
        opL = new OperacionLogicaG(global, tabla);
        ResultadoG indiceColumna = opL.operar(exp1);

        if (indiceColumna.tipo.equalsIgnoreCase("entero")) {
            Columna buscada = Inicio.datos.getColumna((int) indiceColumna.valor);
            if (buscada == null) {
                Inicio.reporteError.agregar("Semantico", exp1.linea, exp1.columna, "Indice invalido para la columna");
                return;
            }
            col.celdas = buscada.celdas;
            pro.celdas = columna.celdas;
            Tabla tabla = new Tabla(datosDonde);
            tabla.setVisible(true);
        } else {
            Inicio.reporteError.agregar("Semantico", exp1.linea, exp1.columna, "El indice de la columna solo puede ser tipo entero");
        }

    }

    private void dondeTodo(Nodo sentencia) {
        Datos datosTodo = new Datos();
        Columna col = new Columna("Donde");
        Columna pro = new Columna("Procesar");
        datosTodo.add(col);
        datosTodo.add(pro);
        Nodo exp1 = sentencia.hijos.get(0);
        opL = new OperacionLogicaG(global, tabla);
        ResultadoG indiceColumna = opL.operar(exp1);

        if (indiceColumna.tipo.equalsIgnoreCase("entero")) {

            Object val = 0;
            for (int i = 0; i < columna.celdas.size(); i++) {
                Celda aux = columna.celdas.get(i);
                switch (val.getClass().getSimpleName()) {
                    case "String":
                        switch (aux.tipo) {
                            case "cadena":
                                val = val.toString() + aux.valor.toString();
                                break;
                            case "entero":
                                val = val.toString() + (int) aux.valor;
                                break;
                            case "decimal":
                                val = val.toString() + (double) aux.valor;
                                break;
                            default:
                                break;
                        }
                        break;
                    case "Integer":
                        switch (aux.tipo) {
                            case "cadena":
                                val = (int) val + aux.valor.toString();
                                break;
                            case "entero":
                                val = (int) val + (int) aux.valor;
                                break;
                            case "decimal":
                                val = (int) val + (double) aux.valor;
                                break;
                            default:
                                break;
                        }
                        break;
                    case "Double":
                        switch (aux.tipo) {
                            case "cadena":
                                val = (double) val + aux.valor.toString();
                                break;
                            case "entero":
                                val = (double) val + (int) aux.valor;
                                break;
                            case "decimal":
                                val = (double) val + (double) aux.valor;
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }

            Celda todo = new Celda("cadena", "Todo");
            col.celdas.add(todo);
            Celda suma = new Celda(val.getClass().getSimpleName(), val);
            pro.celdas.add(suma);
            Tabla tabla = new Tabla(datosTodo);
            tabla.setVisible(true);
        } else {
            Inicio.reporteError.agregar("Semantico", exp1.linea, exp1.columna, "El indice de la columna solo puede ser tipo entero");
        }

    }

    private void donde(Nodo sentencia) {
        Datos datosDonde = new Datos();
        Columna col = new Columna("Donde");
        Columna pro = new Columna("Procesar");
        datosDonde.add(col);
        datosDonde.add(pro);
        Nodo exp1 = sentencia.hijos.get(0);
        opL = new OperacionLogicaG(global, tabla);
        ResultadoG indiceColumna = opL.operar(exp1);
        Nodo exp2 = sentencia.hijos.get(1);
        if (indiceColumna.tipo.equalsIgnoreCase("entero")) {
            Columna buscada = Inicio.datos.getColumna((int) indiceColumna.valor);
            if (buscada == null) {
                Inicio.reporteError.agregar("Semantico", exp1.linea, exp1.columna, "Indice invalido para la columna");
                return;
            }
            int i = 0;
            Celda celda = null;
            for (i = 0; i < buscada.celdas.size(); i++) {
                celda = buscada.celdas.get(i);

                Nodo e = new Nodo(celda.tipo, celda.valor.toString(), exp1.linea - 1, exp1.columna - 1);
                Nodo condicion = new Nodo("==", exp1.linea - 1, exp2.columna - 1);
                condicion.add(e);
                condicion.add(exp2);
                opL = new OperacionLogicaG(global, tabla);
                ResultadoG resultadoCondicion = opL.operar(condicion);
                if (resultadoCondicion.tipo.equalsIgnoreCase("bool")) {
                    if ((boolean) resultadoCondicion.valor) {
                        col.celdas.add(celda);
                        Celda celda2 = columna.celdas.get(i);
                        pro.celdas.add(celda2);
                    }
                } else {
                    Inicio.reporteError.agregar("Semantico", condicion.linea, condicion.columna, "La condicion solo puede ser tipo bool");
                }

            }
            Tabla tabla = new Tabla(datosDonde);
            tabla.setVisible(true);
        } else {
            Inicio.reporteError.agregar("Semantico", exp1.linea, exp1.columna, "El indice de la columna solo puede ser tipo entero");
        }

    }

}
