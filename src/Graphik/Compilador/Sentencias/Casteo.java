/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.ResultadoG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class Casteo {

    public ResultadoG castear(Nodo raiz, String tipo, ResultadoG resultado) {
        Object valor;
        Double doble;
        switch (tipo) {
            case "entero":
                switch (resultado.tipo) {
                    case "entero":
                        return resultado;
                    case "decimal":
                        doble = (double) resultado.valor;
                        return new ResultadoG(tipo, doble.intValue());
                    case "caracter":
                        valor = (int) (char) resultado.valor;
                        return new ResultadoG(tipo, valor);
                    case "bool":
                        return new ResultadoG(tipo, getBoolValor(resultado.valor));
                    case "cadena":
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Un entero no puede ser cadena");
                        break;
                    case "-1":
                        break;
                    default:
                        break;
                }
                break;
            case "decimal":
                switch (resultado.tipo) {
                    case "entero":
                        return new ResultadoG(tipo, (double) (int) resultado.valor);
                    case "decimal":
                        return resultado;
                    case "caracter":
                        valor = (double) (char) resultado.valor;
                        return new ResultadoG(tipo, valor);
                    case "bool":
                        return new ResultadoG(tipo, (double) getBoolValor(resultado.valor));
                    case "cadena":
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Un decimal no puede ser cadena");
                        break;
                    case "-1":
                        break;
                    default:
                        break;
                }
                break;
            case "caracter":
                switch (resultado.tipo) {
                    case "entero":
                        return new ResultadoG(tipo, (char) (int) resultado.valor);
                    case "caracter":
                        return resultado;
                    case "decimal":
                    case "bool":
                    case "cadena":
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Error al castear el dato dicimal|bool|cadena a caracter");
                        break;
                    case "-1":
                        break;
                    default:
                        break;
                }
                break;
            case "bool":
                switch (resultado.tipo) {
                    case "bool":
                        return resultado;
                    case "entero":
                        if ((int) resultado.valor == 0) {
                            return new ResultadoG("bool", false);
                        } else if ((int) resultado.valor == 1) {
                            return new ResultadoG("bool", true);
                        }
                    case "decimal":
                    case "caracter":
                    case "cadena":
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Error al castear el dato decimal|caracter|entero|cadena a bool");
                        break;
                    case "-1":
                        break;
                    default:
                        break;
                }
                break;
            case "cadena":
                switch (resultado.tipo) {
                    case "entero":
                    case "decimal":
                    case "caracter":
                    case "cadena":
                        return new ResultadoG(tipo, resultado.valor + "");
                    case "bool":
                        return new ResultadoG(tipo, getBoolValor(resultado.valor) + "");
                    case "-1":
                        break;
                    default:
                        break;
                }
                break;
            default:
                if (tipo.equalsIgnoreCase(resultado.tipo)) {
                    return new ResultadoG(tipo, resultado.valor);
                } else {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo realizar el casteo de " + resultado.tipo + " a" + tipo);
                }
                break;
        }
        return new ResultadoG("-1", null);
    }

    private int getBoolValor(Object objeto) {
        if ((Boolean) objeto) {
            return 1;
        } else {
            return 0;
        }

    }
}
