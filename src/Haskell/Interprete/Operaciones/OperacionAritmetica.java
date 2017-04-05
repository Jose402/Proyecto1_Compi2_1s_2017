/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Ast.Nodo;
import Haskell.Interprete.FuncionH;
import Haskell.Interprete.Interprete;
import Haskell.Interprete.Lista;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.SimboloH;
import Haskell.Interprete.TablaSimboloH;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class OperacionAritmetica {

    private TablaSimboloH tabla;
    private OperacionNativa opN;

    public OperacionAritmetica(TablaSimboloH tabla) {
        this.tabla = tabla;
    }

    public ResultadoH resolver(Nodo raiz) {
        ResultadoH resultado1 = null;
        ResultadoH resultado2 = null;

        switch (raiz.etiqueta) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "mod":
            case "sqrt":
                resultado1 = resolver(raiz.hijos.get(0));
                resultado2 = resolver(raiz.hijos.get(1));
                break;
            case "unitario":
                resultado1 = resolver(raiz.hijos.get(0));
                if (resultado1 != null) {
                    Double negativo = Double.parseDouble(resultado1.valor) * -1;
                    return new ResultadoH(resultado1.tipo, negativo + "");
                } else {
                    Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo realizar la operacion unitaria", Inicio.interprete.archivo);
                    return null;
                }
            case "acceso":
                SimboloH s = null;
                if (raiz.hijos.get(0).etiqueta.equals("id")) {
                    String id = raiz.hijos.get(0).valor;
                    s = tabla.getSimbolo(id);
                } else {
                    opN = new OperacionNativa(tabla);
                    Lista lista = opN.operacionLista(raiz.hijos.get(0));
                    s = new SimboloH("", lista);
                }
                if (s != null) {
                    Nodo valores = raiz.hijos.get(1);
                    ArrayList<Integer> index = new ArrayList<>();
                    for (Nodo nodo : valores.hijos) {
                        opN = new OperacionNativa(tabla);
                        ResultadoH r = opN.operar(nodo);
                        if (r != null) {
                            Double d = Double.parseDouble(r.valor);
                            index.add(d.intValue());
                        } else {
                            Inicio.reporteError2.agregar("Semantico", nodo.linea, nodo.columna, "Indice invalido", Inicio.interprete.archivo);
                        }
                    }
                    ResultadoH acceso = s.lista.getValor(index);
                    return acceso;
                } else {
                    Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "La lista no existe", Inicio.interprete.archivo);
                }
            case "id":
                if (tabla.existe(raiz.valor)) {
                    SimboloH variable = tabla.getSimbolo(raiz.valor);
                    if (variable.lista != null) {
                        return new ResultadoH(variable.tipo, variable.lista);
                    } else {
                        return new ResultadoH(variable.tipo, variable.valor);
                    }
                } else {
                    Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no existe", Inicio.interprete.archivo);
                }
            case "numero":
                return new ResultadoH(raiz.etiqueta, raiz.valor);
            case "cadena":
            case "caracter":
                Lista l1 = new Lista(raiz, tabla);
                return new ResultadoH(raiz.etiqueta, l1);
            case "llamada":
                FuncionH funcion = Inicio.interprete.llamada(raiz);
                if (funcion.retorno != null) {
                    return new ResultadoH(funcion.tipo, funcion.retorno.valor);
                } else {
                    Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "Se produjo un error al momento de ejecutar el metodo", Inicio.interprete.archivo);
                    return new ResultadoH("-1", "0");
                }
            default:
                OperacionNativa opN = new OperacionNativa(tabla);
                ResultadoH r = opN.operar(raiz);
                return new ResultadoH(r.tipo, r.valor);
        }

        Boolean estado = false;
        Double valor1 = 0.0;
        Double valor2 = 0.0;
        try {
            valor1 = Double.parseDouble(resultado1.valor);
            valor2 = Double.parseDouble(resultado2.valor);
            estado = true;
        } catch (Exception e) {

        }
        if (estado) {
            switch (raiz.etiqueta) {
                case "+":
                    return new ResultadoH("numero", (valor1 + valor2) + "");
                case "-":
                    return new ResultadoH("numero", (valor1 - valor2) + "");
                case "*":
                    return new ResultadoH(resultado1.tipo, (valor1 * valor2) + "");
                case "/":
                    if (valor2 == 0) {
                        Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0", Inicio.interprete.archivo);
                        return new ResultadoH("-1", "0");
                    }
                    return new ResultadoH("numero", (valor1 / valor2) + "");
                case "^":
                    if (valor2 < 0 && valor1 == 0) {
                        Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero dentro de 0", Inicio.interprete.archivo);
                        return new ResultadoH("-1", "0");
                    }
                    return new ResultadoH("numero", (Math.pow(valor1, valor2)) + "");
                case "mod":
                    if (valor2 == 0) {
                        Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0", Inicio.interprete.archivo);
                        return new ResultadoH("-1", "0");
                    }
                    return new ResultadoH("numero", (valor1 % valor2) + "");
                case "sqrt"://aun falta implementarla
                    if (valor1 <= 0 && valor2 == 0) {
                        Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero dentro de 0", Inicio.interprete.archivo);
                        return new ResultadoH("-1", "0");
                    }
                    return new ResultadoH("numero", (Math.pow(valor2, 1 / valor1)) + "");
            }
        } else {

            Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "Los tipos de datos a operar son diferentes", Inicio.interprete.archivo);
        }
        return new ResultadoH("-1", "0");
    }
}
