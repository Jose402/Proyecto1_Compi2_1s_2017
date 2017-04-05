/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Operaciones;

import Ast.Nodo;
import Graphik.Compilador.*;
import Graphik.Compilador.Sentencias.LlamadaHaskell;
import Graphik.Compilador.Sentencias.LlamadaMetodo;
import Graphik.Datos.Celda;
import Haskell.Interprete.Operaciones.OperacionLogica;
import Interfaz.Inicio;
import java.util.ArrayList;
import javax.script.Compilable;

/**
 *
 * @author Jose2
 */
public class OperacionAritmeticaG extends OperacionAbstracta {

    public OperacionAritmeticaG(TablaSimboloG global, TablaSimboloG local) {
        this.tabla = local;
        this.global = global;
    }

    @Override
    public ResultadoG operar(Nodo raiz) {
        ResultadoG resultado1 = null;
        ResultadoG resultado2 = null;
        SimboloG simbolo;
        switch (raiz.etiqueta) {
            case "==":
            case "!=":
            case ">":
            case "<":
            case ">=":
            case "<=":
            case "!":
            case "&&":
            case "||":
            case "&|":
                opL = new OperacionLogicaG(global, tabla);
                resultado1 = opL.operar(raiz);
                if ((Boolean) resultado1.valor) {
                    return new ResultadoG("entero", 1);
                } else {
                    return new ResultadoG("entero", 0);
                }
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "^":
                resultado1 = operar(raiz.hijos.get(0));
                resultado2 = operar(raiz.hijos.get(1));
                break;
            case "unitario":
                resultado1 = operar(raiz.hijos.get(0));
                switch (resultado1.tipo) {
                    case "entero":
                        return new ResultadoG(resultado1.tipo, (int) resultado1.valor * -1);
                    case "decimal":
                        return new ResultadoG(resultado1.tipo, (Double) resultado1.valor * -1);
                    case "caracter":
                        Object val = (char) resultado1.valor * -1;
                        return new ResultadoG(getTipo(val), val);
                    case "bool":
                        if ((boolean) resultado1.valor) {
                            return new ResultadoG("entero", -1);
                        } else {
                            return new ResultadoG("entero", 0);
                        }
                    default:
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El operador unitario - no puede aplicarse a cadenas");
                        return new ResultadoG("-1", null);
                }

            case "entero":
                return new ResultadoG(raiz.etiqueta, Integer.parseInt(raiz.valor));
            case "decimal":

                return new ResultadoG(raiz.etiqueta, Double.parseDouble(raiz.valor));
            case "caracter":
                return new ResultadoG(raiz.etiqueta, (raiz.valor.charAt(0)));
            case "bool":
                if (raiz.valor.equals("verdadero") || raiz.valor.equals("1")) {
                    return new ResultadoG(raiz.etiqueta, true);
                } else {
                    return new ResultadoG(raiz.etiqueta, false);
                }
            case "cadena":
                return new ResultadoG(raiz.etiqueta, raiz.valor + "");
            case "++":
            case "--":
                return operacionSimplificada(raiz.etiqueta, raiz.hijos.get(0));
            case "acceso":

                return acceso(raiz);
            case "accesoVar":
                simbolo = tabla.getSimbolo((String) raiz.hijos.get(0).valor, Graphik.claseActual);
                if (simbolo != null) {
                    return new ResultadoG(simbolo.tipo, simbolo.valor);
                } else {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.linea, "La variable " + raiz.hijos.get(0).valor + " no existe");
                    return new ResultadoG("-1", null);
                }
            case "accesoAr":

                break;
            case "llamadaMetodoHK":
                LlamadaHaskell haskell = new LlamadaHaskell(global, tabla);
                return haskell.ejecutar(raiz);

            case "columna":
                return columna(raiz);
        }

        //------------------------operaciones-------------------------
        Object valor = new Object();
        switch (raiz.etiqueta) {
            case "+":
                switch (resultado1.tipo) {
                    case "entero":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (int) resultado1.valor + (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (int) resultado1.valor + (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (int) resultado1.valor + getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (int) resultado1.valor + (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                valor = (int) resultado1.valor + (String) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                        break;
                    case "decimal":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (Double) resultado1.valor + (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (Double) resultado1.valor + (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (Double) resultado1.valor + getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (Double) resultado1.valor + (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                valor = (double) resultado1.valor + (String) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                        break;
                    case "bool":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = getBoolValor(resultado1.valor) + (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = getBoolValor(resultado1.valor) + (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = getBoolValor(resultado1.valor) + getBoolValor(resultado2.valor);
                                if ((int) valor == 2 || (int) valor == 1) {
                                    return new ResultadoG("bool", true);
                                } else {
                                    return new ResultadoG("bool", false);
                                }

                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede sumar datos tipo Bool con Cadenas y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (char) resultado1.valor + (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (char) resultado1.valor + (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "caracter":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede sumar datos tipo Caracter con Caracteres y Booleanos");
                                break;
                            case "cadena":
                                valor = (char) resultado1.valor + (String) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                        break;
                    case "cadena":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (String) resultado1.valor + (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (String) resultado1.valor + (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (String) resultado1.valor + getBoolValor(resultado2.valor);
                                //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede sumar datos tipo Cadena con Booleanos");
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (String) resultado1.valor + (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                valor = (String) resultado1.valor + (String) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                    default:
                }

                break;
            case "-":
                switch (resultado1.tipo) {
                    case "entero":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (int) resultado1.valor - (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (int) resultado1.valor - (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (int) resultado1.valor - getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (int) resultado1.valor - (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede restar datos tipo entero con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "decimal":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (Double) resultado1.valor - (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (Double) resultado1.valor - (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (Double) resultado1.valor - getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (Double) resultado1.valor - (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede restar datos tipo Decimal con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "bool":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = getBoolValor(resultado1.valor) - (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = getBoolValor(resultado1.valor) - (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede restar datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (char) resultado1.valor - (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (char) resultado1.valor - (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (char) resultado1.valor - (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede restar datos tipo Caracter con Cadenas y Booleanos");
                                break;
                            default:
                        }
                        break;
                    case "cadena":
                        switch (resultado2.tipo) {
                            case "entero":
                            case "decimal":
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede restar datos tipo Cadena con cualquier otro tipo de dato");
                                break;
                            default:
                        }
                    default:
                }

                break;
            case "*":
                switch (resultado1.tipo) {
                    case "entero":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (int) resultado1.valor * (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (int) resultado1.valor * (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (int) resultado1.valor * getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (int) resultado1.valor * (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede multiplicar datos tipo entero con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "decimal":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (Double) resultado1.valor * (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                String tip = resultado1.valor.getClass().getSimpleName();
                                valor = (Double) resultado1.valor * (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = (Double) resultado1.valor * getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (Double) resultado1.valor * (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede multiplicar datos tipo Decimal con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "bool":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = getBoolValor(resultado1.valor) * (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = getBoolValor(resultado1.valor) * (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = getBoolValor(resultado1.valor) * getBoolValor(resultado2.valor);
                                if ((int) valor == 1) {
                                    return new ResultadoG("bool", true);
                                } else {
                                    return new ResultadoG("bool", false);
                                }
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede multiplicar datos tipo Bool con Cadenas y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch (resultado2.tipo) {
                            case "entero":
                                valor = (char) resultado1.valor * (int) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = (char) resultado1.valor * (Double) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = (char) resultado1.valor * (char) resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede multiplicar datos tipo Caracter con Cadenas y Booleanos");
                                break;
                            default:
                        }
                        break;
                    case "cadena":
                        switch (resultado2.tipo) {
                            case "entero":
                            case "decimal":
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede multiplicar datos tipo Cadena con cualquier otro tipo de dato");
                                break;
                            default:
                        }
                    default:
                }

                break;
            case "/":
                try {
                    switch (resultado1.tipo) {
                        case "entero":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = (int) resultado1.valor / (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = (int) resultado1.valor / (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    try {
                                        valor = (int) resultado1.valor / getBoolValor(resultado2.valor);
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    try {
                                        valor = (int) resultado1.valor / (char) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir datos tipo entero con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "decimal":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = (Double) resultado1.valor / (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = (Double) resultado1.valor / (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    try {
                                        valor = (Double) resultado1.valor / getBoolValor(resultado2.valor);
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    try {
                                        valor = (Double) resultado1.valor / (char) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir datos tipo Decimal con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "bool":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = getBoolValor(resultado1.valor) / (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = getBoolValor(resultado1.valor) / (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                    break;
                                default:
                            }
                            break;
                        case "caracter":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = (char) resultado1.valor / (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = (char) resultado1.valor / (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    try {
                                        valor = (char) resultado1.valor / (char) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir datos tipo Caracter con Cadenas y Booleanos");
                                    break;
                                default:
                            }
                            break;
                        case "cadena":
                            switch (resultado2.tipo) {
                                case "entero":
                                case "decimal":
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir datos tipo Cadena con cualquier otro tipo de dato");
                                    break;
                                default:
                            }
                        default:
                    }
                } catch (Exception e) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                }

                break;
            case "%":
                try {
                    switch (resultado1.tipo) {
                        case "entero":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = (int) resultado1.valor % (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = (int) resultado1.valor % (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    try {
                                        valor = (int) resultado1.valor % getBoolValor(resultado2.valor);
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    try {
                                        valor = (int) resultado1.valor % (char) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Modulo en datos tipo entero con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "decimal":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = (Double) resultado1.valor % (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = (Double) resultado1.valor % (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    try {
                                        valor = (Double) resultado1.valor % getBoolValor(resultado2.valor);
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    try {
                                        valor = (Double) resultado1.valor % (char) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Modulo en datos tipo Decimal con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "bool":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = getBoolValor(resultado1.valor) % (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = getBoolValor(resultado1.valor) % (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Modulo en datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                    break;
                                default:
                            }
                            break;
                        case "caracter":
                            switch (resultado2.tipo) {
                                case "entero":
                                    try {
                                        valor = (char) resultado1.valor % (int) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    try {
                                        valor = (char) resultado1.valor % (Double) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    try {
                                        valor = (char) resultado1.valor % (char) resultado2.valor;
                                    } catch (Exception e) {
                                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero por 0 (" + e.toString() + ")");
                                        return new ResultadoG("-1", valor);
                                    }
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Modulo en datos tipo Caracter con Cadenas y Booleanos");
                                    break;
                                default:
                            }
                            break;
                        case "cadena":
                            switch (resultado2.tipo) {
                                case "entero":
                                case "decimal":
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Modulo  en datos tipo Cadena con cualquier otro tipo de dato");
                                    break;
                                default:
                            }
                        default:
                    }
                } catch (Exception e) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                }

                break;
            case "^":
                Double doubleVal;
                switch (resultado1.tipo) {
                    case "entero":
                        switch (resultado2.tipo) {
                            case "entero":
                                if ((int) resultado1.valor == 0 && (int) resultado2.valor < 0) {
                                    doubleVal = 0.0;
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                                    return new ResultadoG("-1", doubleVal);
                                } else {
                                    doubleVal = Math.pow((int) resultado1.valor, (int) resultado2.valor);
                                }
                                valor = doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                if ((int) resultado1.valor == 0 && (Double) resultado2.valor < 0) {
                                    doubleVal = 0.0;
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                                    return new ResultadoG("-1", doubleVal);
                                } else {
                                    valor = Math.pow((int) resultado1.valor, (Double) resultado2.valor);
                                }
                                //valor = Math.pow((int) resultado1.valor, (Double) resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                if ((int) resultado1.valor == 0 && getBoolValor(resultado2.valor) < 0) {
                                    doubleVal = 0.0;
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                                    return new ResultadoG("-1", doubleVal);
                                } else {
                                    doubleVal = Math.pow((int) resultado1.valor, getBoolValor(resultado2.valor));
                                }
                                //doubleVal = Math.pow((int) resultado1.valor, getBoolValor(resultado2.valor));
                                valor = doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                doubleVal = Math.pow((int) resultado1.valor, (char) resultado2.valor);
                                valor = doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Potencia en datos tipo entero con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "decimal":
                        switch (resultado2.tipo) {
                            case "entero":
                                if ((Double) resultado1.valor == 0 && (int) resultado2.valor < 0) {
                                    doubleVal = 0.0;
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                                    return new ResultadoG("-1", doubleVal);
                                } else {
                                    valor = Math.pow((Double) resultado1.valor, (int) resultado2.valor);
                                }
                                //valor = Math.pow((Double) resultado1.valor, (int) resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                if ((Double) resultado1.valor == 0 && (Double) resultado2.valor < 0) {
                                    doubleVal = 0.0;
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede dividir un numero con 0");
                                    return new ResultadoG("-1", doubleVal);
                                } else {
                                    valor = Math.pow((Double) resultado1.valor, (Double) resultado2.valor);
                                }
                                //valor = Math.pow((Double) resultado1.valor, (Double) resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor = Math.pow((Double) resultado1.valor, getBoolValor(resultado2.valor));
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor = Math.pow((Double) resultado1.valor, (char) resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Potencia en datos tipo Decimal con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "bool":
                        switch (resultado2.tipo) {
                            case "entero":
                                doubleVal = Math.pow(getBoolValor(resultado1.valor), (int) resultado2.valor);
                                valor = doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = Math.pow(getBoolValor(resultado1.valor), (Double) resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Potencia en datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch (resultado2.tipo) {
                            case "entero":
                                doubleVal = Math.pow((char) resultado1.valor, (int) resultado2.valor);
                                valor = doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor = Math.pow((char) resultado1.valor, (Double) resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                doubleVal = Math.pow((char) resultado1.valor, (char) resultado2.valor);
                                valor = doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Potencia en datos tipo Caracter con Cadenas y Booleanos");
                                break;
                            default:
                        }
                        break;
                    case "cadena":
                        switch (resultado2.tipo) {
                            case "entero":
                            case "decimal":
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puede usar el operador Potencia en datos tipo Cadena con cualquier otro tipo de dato");
                                break;
                            default:
                        }
                    default:
                }

            default:
        }

        //------------------------fin operaciones----------------------
        return new ResultadoG("-1", null);
    }

    private ResultadoG operacionSimplificada(String tipo, Nodo raiz) {

        SimboloG simbolo = null;
        if (raiz.etiqueta.equals("acceso")) {
            int ent = 0;
            double doble;
            char c;
            simbolo = getSimbolo(raiz, tipo);
            if (simbolo != null) {
                if (tipo.equals("++")) {
                    switch (simbolo.tipo) {
                        case "entero":
                            ent = (int) simbolo.valor;
                            simbolo.valor = (int) simbolo.valor + 1;
                            return new ResultadoG(simbolo.tipo, ent);
                        case "decimal":
                            doble = (double) simbolo.valor;
                            simbolo.valor = (double) simbolo.valor + 1;
                            return new ResultadoG(simbolo.tipo, doble);
                        case "caracter":
                            c = (char) simbolo.valor;
                            simbolo.valor = (char) ((char) simbolo.valor + 1);
                            return new ResultadoG(simbolo.tipo, c);
                        default:
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Las operaciones simplificadas solo son validas con datos tipo entero,decimal y caracter");
                            break;
                    }
                } else {
                    switch (simbolo.tipo) {
                        case "entero":
                            ent = (int) simbolo.valor;
                            simbolo.valor = (int) simbolo.valor - 1;
                            return new ResultadoG(simbolo.tipo, ent);
                        case "decimal":
                            doble = (double) simbolo.valor;
                            simbolo.valor = (double) simbolo.valor - 1;
                            return new ResultadoG(simbolo.tipo, doble);
                        case "caracter":
                            c = (char) simbolo.valor;
                            simbolo.valor = (char) ((char) simbolo.valor - 1);
                            return new ResultadoG(simbolo.tipo, c);
                        default:
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Las operaciones simplificadas solo son validas con datos tipo entero,decimal y caracter");
                            return new ResultadoG("-1", null);
                    }
                }
            }
        } else {
            ResultadoG resultado = operar(raiz);
            Object valor = resultado.valor;
            if (resultado.tipo.equals("entero") || resultado.tipo.equals("decimal") || resultado.tipo.equals("caracter")) {
                if (tipo.equals("++")) {
                    switch (resultado.tipo) {
                        case "entero":
                            valor = (int) resultado.valor + 1;
                            break;
                        case "decimal":
                            valor = (Double) resultado.valor + 1;
                            break;
                        case "caracter":
                            valor = (char) resultado.valor + 1;
                    }
                    return new ResultadoG(getTipo(valor), valor);
                } else {//si es --
                    switch (resultado.tipo) {
                        case "entero":
                            valor = (int) resultado.valor - 1;
                            break;
                        case "decimal":
                            valor = (Double) resultado.valor - 1;
                            break;
                        case "caracter":
                            valor = (char) resultado.valor - 1;
                    }
                    return new ResultadoG(getTipo(valor), valor);
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pueden realizar operaciones simplificadas sobre datos tipo cadena y bool");
                return new ResultadoG("-1", null);
            }
        }
        return new ResultadoG("-1", null);
    }

    private SimboloG getSimbolo(Nodo raiz, String tipo) {

        Clase aux = Graphik.claseActual;
        TablaSimboloG tablaAux = tabla;
        SimboloG sim = null;

        int nivel = 0;
        ResultadoG retorno;
        for (Nodo acceso : raiz.hijos) {
            String nombre;
            SimboloG simbolo;
            switch (acceso.etiqueta) {
                case "accesoAr":
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    sim = accesoArSet(acceso, nivel, aux, tipo);
                    break;
                case "id":
                    nombre = acceso.valor;
                    simbolo = tabla.getSimbolo(nombre, aux);

                    if (simbolo != null) {
                        if (simbolo.inicializado) {
                            if (nivel > 0) {
                                if (simbolo.visibilidad.equalsIgnoreCase("privado") || simbolo.visibilidad.equalsIgnoreCase("protegido")) {
                                    simbolo = null;
                                }
                            }
                        } else {
                            //Inicio.reporteError.agregar("Semantico",acceso.linea,acceso.columna,"La variable "+nombre+" no ha sido inicializada");
                        }
                    } else {
                        simbolo = null;
                    }
                    if (simbolo != null) {
                        if (simbolo.inicializado) {
                            //simbolo.seHereda = false;
                            switch (simbolo.tipo) {
                                case "entero":
                                case "cadena":
                                case "bool":
                                case "caracter":
                                case "decimal":
                                    sim = simbolo;
                                    break;
                                default:
                                    nivel++;
                                    aux = (Clase) simbolo.valor;
                                    tabla = aux.tabla;
                                    sim = simbolo;
                                    break;
                            }
                        } else {
                            Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no ha sido inicializada");
                            return null;
                        }
                    } else {
                        Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                        return null;
                    }
                    break;
                case "llamadaMetodo":
                    LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel);
                    Metodo metodo = llamada.ejecutar(acceso);
                    metodo.estadoRetorno = false;
                    if (metodo != null) {
                        if (metodo.retorno != null) {
                            if (metodo.tipo.equalsIgnoreCase(metodo.retorno.tipo)) {
                                retorno = metodo.retorno;
                                metodo.estadoRetorno = false;
                                if (!retorno.tipo.equalsIgnoreCase("cadena") && !retorno.tipo.equalsIgnoreCase("entero") && !retorno.tipo.equalsIgnoreCase("decimal") && !retorno.tipo.equalsIgnoreCase("caracter") && !retorno.tipo.equalsIgnoreCase("bool")) {
                                    aux = (Clase) retorno.valor;
                                    tabla = aux.tabla;
                                }
                            } else {
                                Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "El metodo " + metodo.nombre + " no es tipo " + metodo.retorno.tipo);
                                sim = null;
                            }
                        }
                    } else {
                        sim = null;
                    }
                    break;
            }

        }
        tabla = tablaAux;
        return sim;
    }

    private ResultadoG acceso(Nodo raiz) {
        Clase aux = Graphik.claseActual;
        TablaSimboloG tablaAux = tabla;
        //Compilador.pilaTablas.push(tabla);
        int nivel = 0;
        ResultadoG retorno = new ResultadoG("-1", null);
        for (Nodo acceso : raiz.hijos) {
            String nombre;
            SimboloG simbolo;
            switch (acceso.etiqueta) {
                case "accesoAr":
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    retorno = accesoAr(acceso, nivel, aux);
                    break;
                case "id":

                    nombre = acceso.valor;
                    if (nombre.equals("cir")) {
                        int cc = 2 + 4;
                    }
                    simbolo = tabla.getSimbolo(nombre, aux);

                    if (simbolo != null) {
                        if (simbolo.inicializado) {
                            if (nivel > 0) {
                                if (simbolo.visibilidad.equalsIgnoreCase("privado") || simbolo.visibilidad.equalsIgnoreCase("protegido")) {
                                    simbolo = null;
                                }
                            }
                        } else {
                            //Inicio.reporteError.agregar("Semantico",acceso.linea,acceso.columna,"La variable "+nombre+" no ha sido inicializada");
                        }
                    } else {
                        simbolo = null;
                    }
                    if (simbolo != null) {
                        if (simbolo.inicializado) {
                            //simbolo.seHereda = false;
                            switch (simbolo.tipo) {
                                case "entero":
                                case "cadena":
                                case "bool":
                                case "caracter":
                                case "decimal":
                                    retorno.valor = simbolo.valor;
                                    retorno.tipo = simbolo.tipo;
                                    retorno.simbolo = simbolo;
                                    break;
                                default:
                                    nivel++;
                                    aux = (Clase) simbolo.valor;
                                    tabla = aux.tabla;
                                    retorno.tipo = simbolo.tipo;
                                    retorno.valor = simbolo.valor;
                                    retorno.simbolo = simbolo;
                                    break;
                            }
                        } else {
                            retorno.tipo = "-1";
                            retorno.valor = null;
                            Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no ha sido inicializada");
                            return retorno;
                        }
                    } else {
                        retorno.tipo = "-1";
                        retorno.valor = null;
                        Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                        return retorno;
                    }
                    break;
                case "llamadaMetodo":
                    LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel);
                    Metodo metodo = llamada.ejecutar(acceso);
                    if (metodo != null) {
                        if (metodo.retorno != null) {
                            if (metodo.tipo.equalsIgnoreCase(metodo.retorno.tipo)) {
                                retorno = metodo.retorno;
                                metodo.estadoRetorno = false;
                                if (!retorno.tipo.equalsIgnoreCase("cadena") && !retorno.tipo.equalsIgnoreCase("entero") && !retorno.tipo.equalsIgnoreCase("decimal") && !retorno.tipo.equalsIgnoreCase("caracter") && !retorno.tipo.equalsIgnoreCase("bool")) {
                                    aux = (Clase) retorno.valor;
                                    tabla = aux.tabla;
                                }
                            } else {
                                Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "El metodo " + metodo.nombre + " no es tipo " + metodo.retorno.tipo);
                                retorno.tipo = "-1";
                                retorno.valor = null;
                            }
                        }
                    } else {
                        retorno.tipo = "-1";
                        retorno.valor = null;
                    }
                    break;
            }

        }
        tabla = tablaAux;
        //tabla=Compilador.pilaTablas.pop();
        return retorno;
    }

    private SimboloG accesoArSet(Nodo raiz, int nivel, Clase aux, String tipo) {
        SimboloG simbolo;
        simbolo = aux.tabla.getSimbolo((String) raiz.valor, aux);
        if (nivel > 0 && (simbolo.visibilidad.equalsIgnoreCase("privado") || simbolo.visibilidad.equalsIgnoreCase("protegido"))) {
            simbolo = null;
        }
        int ent = 0;
        double doble;
        char c;
        if (simbolo != null) {
            if (simbolo.inicializado) {
                if (simbolo.esArreglo) {
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    ArrayList<Integer> indices = new ArrayList<>();
                    for (Nodo nodo : raiz.hijos.get(0).hijos) {
                        opL = new OperacionLogicaG(global, tabla);
                        ResultadoG indice = opL.operar(nodo);
                        if (indice.tipo.equalsIgnoreCase("entero")) {
                            indices.add((int) indice.valor);
                        } else {
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores enteros al acceder a un indce de un arreglo");
                            return null;
                        }
                    }
                    opL = new OperacionLogicaG(global, tabla);

                    Object val = arreglo.getValor(indices);
                    boolean estado = false;
                    SimboloG simboloAux = null;
                    if (val != null) {
                        simboloAux = new SimboloG(simbolo.tipo, simbolo.nombre, val);
                        ResultadoG r;
                        if (tipo.equals("++")) {
                            switch (simbolo.tipo) {
                                case "entero":
                                    r = new ResultadoG(simbolo.tipo, (int) val + 1);
                                    estado = arreglo.setValor(indices, r);
                                    break;
                                case "decimal":
                                    r = new ResultadoG(simbolo.tipo, (double) val + 1);
                                    estado = arreglo.setValor(indices, r);
                                    break;
                                case "caracter":
                                    r = new ResultadoG(simbolo.tipo, (char) ((char) val + 1));
                                    estado = arreglo.setValor(indices, r);
                                    break;
                                default:
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Las operaciones simplificadas solo son validas con datos tipo entero,decimal y caracter");
                                    break;
                            }
                        } else {
                            switch (simbolo.tipo) {
                                case "entero":
                                    r = new ResultadoG(simbolo.tipo, (int) val - 1);
                                    estado = arreglo.setValor(indices, r);
                                    break;
                                case "decimal":
                                    r = new ResultadoG(simbolo.tipo, (double) val - 1);
                                    estado = arreglo.setValor(indices, r);
                                    break;
                                case "caracter":
                                    r = new ResultadoG(simbolo.tipo, (char) ((char) val - 1));
                                    estado = arreglo.setValor(indices, r);
                                    break;
                                default:
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Las operaciones simplificadas solo son validas con datos tipo entero,decimal y caracter");
                                    break;
                            }
                        }
                    } else {
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo acceder al arreglo  " + simbolo.nombre);
                    }

                    if (estado) {
                        return simboloAux;
                    } else {
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se puedo ingresar el dato al arreglo " + simbolo.nombre + " porque el indice no es correcto");
                    }
                } else {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no es arreglo");
                    return null;
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no ha sido inicializada");
                return null;
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no existe");
            return null;
        }
        return null;
    }

    private ResultadoG accesoAr(Nodo raiz, int nivel, Clase aux) {
        SimboloG simbolo;
        simbolo = aux.tabla.getSimbolo((String) raiz.valor, aux);
        if (nivel > 0 && (simbolo.visibilidad.equalsIgnoreCase("privado") || simbolo.visibilidad.equalsIgnoreCase("protegido"))) {
            simbolo = null;
        }
        if (simbolo != null) {
            if (simbolo.inicializado) {
                if (simbolo.esArreglo) {
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    ArrayList<Integer> indices = new ArrayList<>();
                    for (Nodo nodo : raiz.hijos.get(0).hijos) {
                        opL = new OperacionLogicaG(global, tabla);
                        ResultadoG indice = opL.operar(nodo);
                        if (indice.tipo.equalsIgnoreCase("entero")) {
                            indices.add((int) indice.valor);
                        } else {
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores enteros al acceder a un indce de un arreglo");
                            return new ResultadoG("-1", null);
                        }
                    }
                    if (indices.size() != arreglo.dimensiones.size()) {
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La dimension del arreglo " + simbolo.nombre + " no coincide con la del acceso");
                        return new ResultadoG("-1", null);
                    }
                    Object valor = arreglo.getValor(indices);
                    if (valor != null) {
                        ResultadoG ree = new ResultadoG(simbolo.tipo, valor);
                        ree.simbolo = simbolo;
                        return ree;
                    } else {
                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo acceder al indice del arreglo");
                    }
                } else {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no es arreglo");
                    return new ResultadoG("-1", null);
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no ha sido inicializada");
                return new ResultadoG("-1", null);
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no existe");
            return new ResultadoG("-1", null);
        }
        return new ResultadoG("-1", null);
    }

    private ResultadoG columna(Nodo raiz) {
        if (Graphik.metodoActual.nombre.equalsIgnoreCase("datos")) {
            if (Inicio.datos != null) {
                Nodo dato = raiz.hijos.get(0);
                opL = new OperacionLogicaG(global, tabla);
                ResultadoG columna = opL.operar(dato);
                if (columna.tipo.equalsIgnoreCase("entero")) {
                    Celda celda = Inicio.datos.getDato((int) columna.valor - 1, Compilador.indice);
                    if (celda != null) {
                        return new ResultadoG(celda.tipo, celda.valor);
                    } else {
                        Inicio.reporteError.agregar("Semantico", dato.linea, dato.columna, "No se pudo acceder a la posicion columna:" + columna + " fila:" + celda);
                        return new ResultadoG("-1", null);
                    }
                } else {
                    Inicio.reporteError.agregar("Semantico", dato.linea, dato.columna, "El valor de la columna solo puede ser un valor entero");
                    return new ResultadoG("-1", null);
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No hay datos cargados");
                return new ResultadoG("-1", null);
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La sentencia columna solamente se puede usar en el metodo datos");
            return new ResultadoG("-1", null);
        }
    }

}
