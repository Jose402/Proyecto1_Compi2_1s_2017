/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Ast.Nodo;
import Haskell.Interprete.Operaciones.*;
import Haskell.Interprete.Sentencias.*;
import Interfaz.Inicio;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Interprete {

    private TablaSimboloH tabla;
    public ArrayList<FuncionH> funciones;
    private Stack<FuncionH> pilaFunciones;
    private Stack<TablaSimboloH> pilaTablas;
    private FuncionH funcionActual;
    private OperacionNativa opN;
    private OperacionLogica opL;

    public String contenido;
    public String archivo;

    public Interprete(Nodo raiz, String archivo, String contenido) {
        this.contenido = contenido;
        this.archivo = archivo;
        funciones = new ArrayList<>();
        tabla = new TablaSimboloH();
        pilaFunciones = new Stack<>();
        pilaTablas = new Stack<>();
        guardarMetodos(raiz);
    }

    private void guardarMetodos(Nodo raiz) {
        for (Nodo hijo : raiz.hijos) {
            FuncionH existe = existeFuncion(hijo.valor);
            if (existe == null) {
                FuncionH funcion = new FuncionH(hijo.valor, hijo);
                funciones.add(funcion);
            } else {
                funciones.remove(existe);
                FuncionH funcion = new FuncionH(hijo.valor, hijo);
                funciones.add(funcion);
                //Inicio.reporteError2.agregar("Semantico", hijo.linea, hijo.columna, "Ya existe una funcion con el nombre " + hijo.valor,archivo);
            }

        }
    }

    public FuncionH existeFuncion(String id) {
        for (FuncionH funcion : funciones) {
            if (id.equalsIgnoreCase(funcion.id)) {
                return funcion;
            }
        }
        return null;
    }

    private FuncionH buscarFuncion(String id) {
        for (FuncionH funcion : funciones) {
            if (id.equalsIgnoreCase(funcion.id)) {
                return funcion;
            }
        }

        return null;
    }

    public ResultadoH ejecutar(String id, ArrayList<ResultadoH> valorParametros) {
        funcionActual = buscarFuncion(id);
        if (funcionActual != null) {
            Nodo parametros = funcionActual.raiz.hijos.get(0);
            Nodo sentencias = funcionActual.raiz.hijos.get(1);
            guardarParametros(parametros, valorParametros);
            FuncionH funcion = ejecutar(sentencias);
            return funcion.retorno;
        } else {
            Inicio.reporteError2.agregar("Semantido", 2, 7, "La funcion " + id + " no existe", archivo);
            return null;
        }
    }

    private void guardarParametros(Nodo parametros, ArrayList<ResultadoH> valorParametros) {
        for (int i = 0; i < valorParametros.size(); i++) {
            Nodo parametro = parametros.hijos.get(i);
            ResultadoH valor = valorParametros.get(i);
            new Declaracion(parametro.valor, valor, tabla, parametro);
        }
    }

    private FuncionH ejecutar(Nodo sentencias) {
        ResultadoH resultado = null;
        for (Nodo sentencia : sentencias.hijos) {
            switch (sentencia.etiqueta) {
                case "declaracionLista":
                    Declaracion dec = new Declaracion(tabla);
                    SimboloH sDec = dec.declarar(sentencia);
                    funcionActual.retorno = new ResultadoH();
                    funcionActual.retorno.lista = sDec.lista;
                    funcionActual.retorno.valor = sDec.valor;
                    funcionActual.retorno.tipo = sDec.tipo;
                    break;
                case "acceso":
                    SimboloH s = null;

                    if (sentencia.hijos.get(0).etiqueta.equals("id")) {
                        String id = sentencia.hijos.get(0).valor;
                        s = tabla.getSimbolo(id);
                    } else {
                        opN = new OperacionNativa(tabla);
                        Lista lista = opN.operacionLista(sentencia.hijos.get(0));
                        s = new SimboloH("", lista);
                    }
                    if (s != null) {
                        Nodo valores = sentencia.hijos.get(1);
                        ArrayList<Integer> index = new ArrayList<>();
                        for (Nodo nodo : valores.hijos) {
                            opN = new OperacionNativa(tabla);
                            ResultadoH r = opN.operar(nodo);
                            if (r != null) {
                                Double d = Double.parseDouble(r.valor);
                                index.add(d.intValue());
                            } else {
                                Inicio.reporteError2.agregar("Semantico", nodo.linea, nodo.columna, "Indice incorrecto para acceder a la lista", archivo);
                            }
                        }
                        ResultadoH acceso = s.lista.getValor(index);
                        funcionActual.retorno = acceso;
                    } else {

                        Inicio.reporteError2.agregar("Semantico", sentencia.linea, sentencia.columna, "La variable no existe", archivo);
                    }
                    break;
                case "if":
                    funcionActual = sentenciaIf(sentencia);
                    break;
                case "switch":
                    funcionActual = sentenciaSwitch(sentencia);
                    break;
                case "llamada":
                    funcionActual = llamada(sentencia);

                    break;
                case "calcular"://returnan valores puntuales
                case "succ":
                case "decc":
                case "min":
                case "max":
                case "product":
                case "sum":
                case "length":
                    opN = new OperacionNativa(tabla);
                    resultado = opN.operar(sentencia);
                    funcionActual.retorno = resultado;
                    break;
                case "concatenar"://retornan valores tipo lista
                case "revers":
                case "impr":
                case "par":
                case "asc":
                case "desc":
                    opN = new OperacionNativa(tabla);
                    Lista lista = opN.operacionLista(sentencia);
                    if (lista != null) {
                        resultado = new ResultadoH(lista.tipo, lista);
                    }
                    funcionActual.retorno = resultado;
                    break;
            }
        }
        return funcionActual;
    }

    public FuncionH llamada(Nodo raiz, TablaSimboloH tabla) {
        this.tabla = tabla;
        return llamada(raiz);
    }

    public FuncionH llamada(Nodo raiz) {
        String id = raiz.valor;
        TablaSimboloH tablaTemp = new TablaSimboloH();

        FuncionH funcionTemp = buscarFuncion(id);
        if (funcionTemp != null) {
            ArrayList<ResultadoH> valores = new ArrayList<>();
            for (Nodo nodo : raiz.hijos.get(0).hijos) {
                if (nodo.etiqueta.equals("valores") || nodo.etiqueta.equals("listaValores") || nodo.etiqueta.equals("cadena")) {
                    Lista lista = new Lista(nodo, tabla);
                    ResultadoH r = new ResultadoH(lista.tipo, lista);
                    valores.add(r);
                } else {
                    opN = new OperacionNativa(tabla);
                    ResultadoH r = opN.operar(nodo);
                    valores.add(r);
                }
            }
            pilaTablas.push(tabla);
            tabla = tablaTemp;
            guardarParametros(funcionTemp.raiz.hijos.get(0), valores);

            pilaFunciones.push(funcionActual);
            funcionActual = funcionTemp;

            funcionTemp = ejecutar(funcionTemp.raiz.hijos.get(1));

            tabla = pilaTablas.pop();
            funcionActual = pilaFunciones.pop();
        } else {
            Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "La funcion " + id + " no existe", archivo);
        }
        return funcionTemp;
    }

    public FuncionH llamada(String id, ArrayList<ResultadoH> valores) {
        TablaSimboloH tablaTemp = new TablaSimboloH();

        FuncionH funcionTemp = buscarFuncion(id);
        if (funcionTemp != null) {
            pilaTablas.push(tabla);
            tabla = tablaTemp;
            guardarParametros(funcionTemp.raiz.hijos.get(0), valores);

            pilaFunciones.push(funcionActual);
            funcionActual = funcionTemp;

            funcionTemp = ejecutar(funcionTemp.raiz.hijos.get(1));

            tabla = pilaTablas.pop();
            funcionActual = pilaFunciones.pop();
        } else {
            Inicio.reporteError2.agregar("Semantido", 2, 7, "La funcion " + id + " no existe", archivo);
        }
        return funcionTemp;
    }

    private FuncionH sentenciaIf(Nodo raiz) {
        //cambio de ambito
        TablaSimboloH tablaTemp = new TablaSimboloH();
        tablaTemp.cambiarAmbito(tabla);
        pilaTablas.push(tabla);
        tabla = tablaTemp;
        Nodo cond = raiz.hijos.get(0).hijos.get(0);
        opL = new OperacionLogica(tabla);
        ResultadoH condicion = opL.resolverLogica(cond);
        if (condicion != null) {
            if (condicion.valor.equals("true")) {
                funcionActual = ejecutar(raiz.hijos.get(1));
            } else {
                funcionActual = ejecutar(raiz.hijos.get(2));
            }
        } else {
            Inicio.reporteError2.agregar("Semantico", cond.linea, cond.columna, "Se produjo un error al evaluar la condicion de la sentencia if", archivo);
        }
        //regreso al ambito
        tabla = pilaTablas.pop();
        return funcionActual;
    }

    private FuncionH sentenciaSwitch(Nodo raiz) {
        //cambio de ambito

        Nodo exp = raiz.hijos.get(0);;
        Nodo casos = raiz.hijos.get(1);

        for (Nodo caso : casos.hijos) {
            Nodo val = caso.hijos.get(0);
            Nodo sentencias = caso.hijos.get(1);
            Nodo expCond = new Nodo("==", exp.linea - 1, exp.columna - 1);
            expCond.add(exp);
            expCond.add(val);
            opL = new OperacionLogica(tabla);
            ResultadoH resultado = opL.resolverLogica(expCond);
            if (resultado != null) {
                TablaSimboloH tablaTemp = new TablaSimboloH();
                tablaTemp.cambiarAmbito(tabla);
                pilaTablas.push(tabla);
                tabla = tablaTemp;
                if (resultado.valor.equals("true")) {
                    funcionActual = ejecutar(sentencias);
                    break;
                }
            } else {
                Inicio.reporteError2.agregar("Semantico", caso.linea, caso.columna, "Se produjo un error al evaluar la condicion del caso ", archivo);
            }
        }

        //regreso al ambito
        tabla = pilaTablas.pop();
        return funcionActual;
    }
}
