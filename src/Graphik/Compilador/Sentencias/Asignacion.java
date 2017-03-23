/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Arreglo;
import Graphik.Compilador.Clase;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.Graphik;
import Graphik.Compilador.Heredar;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.SimboloG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Asignacion extends Compilador {

    TablaSimboloG tabla;
    TablaSimboloG global;

    public Asignacion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla) {
        this.raiz = raiz;
        this.global = global;//tabla de sibolos de una clase
        this.tabla = tabla;//tabla de simbolos de un metodo
        opL = new OperacionLogicaG(global, tabla);
        asignar();
    }

    public Asignacion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla, String tipo) {
        this.raiz = raiz;
        this.global = global;//tabla de sibolos de una clase
        this.tabla = tabla;//tabla de simbolos de un metodo
        opL = new OperacionLogicaG(global, tabla);

    }

    public SimboloG asignar() {
        switch (raiz.etiqueta) {
            case "asignacion"://asignacion de objetos,variables y arreglos
                return asignacion();
            case "asignacionAr"://asignacion de arreglos con datos puntuales
                return asignacionAr();
            case "asignacionAlsI"://instancia de un objeto previamente declarado
                return asignacionAlsI();
        }

        return null;
    }

    Nodo valorIndice;

    private SimboloG asignacion() {
        valorIndice = raiz.hijos.get(1);
        SimboloG simbolo = acceso(raiz.hijos.get(0));
        if (simbolo != null) {
            if (simbolo.esArreglo) {
                return simbolo;
            }
        }
        ResultadoG resultado = opL.operar(raiz.hijos.get(1));
        if (resultado.valor != null) {
            if (simbolo != null) {
                if (simbolo.tipo.equalsIgnoreCase(resultado.tipo)) {
                    if (simbolo.esArreglo) {
                        if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                            Arreglo arreglo1 = (Arreglo) simbolo.valor;
                            Arreglo arreglo2 = (Arreglo) resultado.valor;
                            if (arreglo1.dimensiones.size() == arreglo2.dimensiones.size()) {
                                Boolean estado = true;
                                for (int indice : arreglo1.dimensiones) {
                                    if (indice != arreglo2.dimensiones.get(0)) {
                                        estado = false;
                                    }
                                }
                                if (estado) {
                                    simbolo.valor = arreglo2;
                                    simbolo.inicializado = true;
                                } else {
                                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Las dimensiones del arreglo a asignar no son iguales al arreglo " + simbolo.nombre);
                                }
                            } else {
                                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Las dimensiones del arreglo a asignar no son iguales al arreglo " + simbolo.nombre);
                            }
                        } else {
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Tipo de dato invalido para el arreglo " + simbolo.nombre);
                        }
                    } else//si es cadena,entero,decimal,caracter,bool u objeto
                     if (simbolo.tipo.equals("entero") || simbolo.tipo.equals("decimal") || simbolo.tipo.equals("cadena") || simbolo.tipo.equals("caracter") || simbolo.tipo.equals("bool")) {
                            resultado = castear(simbolo.tipo, resultado);
                            if (resultado != null) {
                                simbolo.valor = resultado.valor;
                                simbolo.inicializado = true;
                                return simbolo;
                            }
                        } else if (simbolo.tipo.equalsIgnoreCase(resultado.tipo)) {
                            simbolo.valor = resultado.valor;
                        } else {
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Tipo de dato incorrecto para el objeto " + simbolo.nombre);
                        }
                } else {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Tipo de dato invalido");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no existe");
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Tipo de dato invalido");
        }
        return null;
    }

    private SimboloG acceso(Nodo raiz) {
        Clase aux = Graphik.claseActual;
        TablaSimboloG tablaAux = tabla;
        int nivel = 0;
        SimboloG sim = null;
        for (Nodo acceso : raiz.hijos) {
            String nombre;
            SimboloG simbolo;
            switch (acceso.etiqueta) {
                case "accesoAr":
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    sim = accesoAr(acceso, nivel, aux);
                    break;
                case "id":
                    nombre = acceso.valor;
                    simbolo = tabla.getSimbolo(nombre, aux);
                    if (nivel > 0) {
                        int a = 1 + 2 * 3;
                    }
                    if (simbolo != null) {

                        if (nivel > 0) {
                            if (simbolo.visibilidad.equalsIgnoreCase("privado") || simbolo.visibilidad.equalsIgnoreCase("protegido")) {
                                simbolo = null;
                            }
                        }

                    } else {
                        simbolo = null;
                    }
                    if (simbolo != null) {

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
                        Inicio.reporteError.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                        return null;
                    }
                    break;
                case "llamadaMetodo":
                    LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel);
                    Metodo metodo = llamada.ejecutar(acceso);
                    if (metodo != null) {
                        if (metodo.retorno != null) {
                            if (metodo.tipo.equalsIgnoreCase(metodo.retorno.tipo)) {
                                sim = (SimboloG) metodo.retorno.valor;
                                metodo.estadoRetorno = false;
                                if (!sim.tipo.equalsIgnoreCase("cadena") && !sim.tipo.equalsIgnoreCase("entero") && !sim.tipo.equalsIgnoreCase("decimal") && !sim.tipo.equalsIgnoreCase("caracter") && !sim.tipo.equalsIgnoreCase("bool")) {
                                    aux = (Clase) sim.valor;
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

    private SimboloG accesoAr(Nodo raiz, int nivel, Clase aux) {
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
                            return null;
                        }
                    }
                    opL = new OperacionLogicaG(global, tabla);
                    ResultadoG res = opL.operar(valorIndice);
                    res = castear(simbolo.tipo, res);
                    if (res == null) {
                        return null;
                    }
                    boolean estado = false;
                    if (res.valor != null) {
                        if (res.tipo.equalsIgnoreCase(simbolo.tipo)) {
                            estado = arreglo.setValor(indices, res);
                        } else {
                            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Tipo de dato incorrecto para el arreglo " + simbolo.nombre);
                        }
                    }
                    if (estado) {
                        return simbolo;
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

    private SimboloG asignacionAr() {
        SimboloG simbolo = acceso(raiz.hijos.get(0));
        if (simbolo != null) {
            if (simbolo.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                Nodo nuevaRaiz = new Nodo();
                nuevaRaiz.valor = simbolo.tipo;
                nuevaRaiz.hijos = raiz.hijos;
                Arreglo arregloId = (Arreglo) simbolo.valor;
                Arreglo arreglo = new Arreglo(nuevaRaiz, global, tabla, arregloId.dimensiones);
                if (arreglo.estado) {
                    simbolo.valor = arreglo;
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable no es de tipo arreglo");
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable no existe");
        }
        return null;
    }

    private SimboloG asignacionAlsI() {
        SimboloG simbolo = acceso(raiz.hijos.get(0));
        if (simbolo != null) {
            if (!simbolo.tipo.equals("numero") && !simbolo.tipo.equals("cadena") && !simbolo.tipo.equals("bool") && !simbolo.tipo.equals("caracter") && !simbolo.tipo.equals("decimal")) {
                String tipo2 = raiz.hijos.get(1).valor;
                if (tipo2.equalsIgnoreCase(simbolo.tipo)) {
                    Clase instancia = getClase(tipo2);
                    if (instancia != null) {
                        simbolo.inicializado = true;
                        simbolo.valor = instancia;
                        Heredar hereda = new Heredar(instancia);
                    } else {

                        Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No existe la clase " + tipo2);
                    }

                } else {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El objeto " + simbolo.nombre + " no es tipo " + tipo2);
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La instancia solo es valida para los objetos");
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable no existe");
        }

        return null;
    }

    private Clase getClase(String tipo) {
        for (Clase clase : clases) {
            if (clase.nombre.equalsIgnoreCase(tipo)) {
                Clase instancia = clase.clonar();
                return instancia.clonar();
            }
        }
        return null;
    }

    private ResultadoG castear(String tipo, ResultadoG resultado) {
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
                        return new ResultadoG(tipo, (double) resultado.valor);
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
                    case "decimal":
                    case "caracter":
                    case "entero":
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
                    case "bool":
                    case "cadena":
                        return new ResultadoG(tipo, resultado.valor + "");
                    case "-1":
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return null;
    }

    private int getBoolValor(Object objeto) {
        if ((Boolean) objeto) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public Metodo ejecutar(Nodo raiz) {
        return null;
    }

}
