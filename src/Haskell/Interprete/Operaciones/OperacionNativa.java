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
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Jose2
 */
public class OperacionNativa {

    OperacionAritmetica opA;
    Lista lista;
    TablaSimboloH tabla;

    public OperacionNativa(TablaSimboloH tabla) {

        this.tabla = tabla;
    }

    public ResultadoH imprimirConsola(Nodo raiz) {
        switch (raiz.etiqueta) {
            case "concatenar":
            case "revers":
            case "impr":
            case "par":
            case "desc":
            case "asc":
                Lista l = operacionLista(raiz);
                return new ResultadoH(l.tipo, l.getString());
            default:
                return operar(raiz);
        }
    }

    //devuelve un valor puntual
    public ResultadoH operar(Nodo raiz) {

        switch (raiz.etiqueta) {
            case "calcular":
                return calcular(raiz);
            case "succ":
                return succ(raiz);
            case "decc":
                return decc(raiz);
            case "min":
                lista = operacionLista(raiz.hijos.get(0));
                return min(lista);
            case "max":
                lista = operacionLista(raiz.hijos.get(0));
                return max(lista);
            case "product":
                lista = operacionLista(raiz.hijos.get(0));
                return product(lista);
            case "sum":
                lista = operacionLista(raiz.hijos.get(0));
                return sum(lista);
            case "length":
                lista = operacionLista(raiz.hijos.get(0));
                return length(lista);
            case "caracter":
                opA = new OperacionAritmetica(tabla);
                return opA.resolver(raiz);
            case "id":
            case "numero":
            case "cadena":
                opA = new OperacionAritmetica(tabla);
                return opA.resolver(raiz);
        }
        return null;
    }

    public Lista operar(String tipo, Lista lista) {
        switch (tipo) {
            case "revers":
                return revers(lista);
            case "impr":
                return impar(lista);
            case "par":
                return par(lista);
            case "asc":
                return asc(lista);
            case "desc":
                return desc(lista);
        }
        return lista;
    }

    private ResultadoH calcular(Nodo raiz) {
        opA = new OperacionAritmetica(tabla);
        return opA.resolver(raiz.hijos.get(0));
    }

    private ResultadoH succ(Nodo raiz) {
        opA = new OperacionAritmetica(tabla);
        ResultadoH calcular = opA.resolver(raiz.hijos.get(0));
        Double valor = Double.parseDouble(calcular.valor);
        valor = valor + 1;
        return new ResultadoH("numero", valor + "");
    }

    private ResultadoH decc(Nodo raiz) {
        opA = new OperacionAritmetica(tabla);
        ResultadoH calcular = opA.resolver(raiz.hijos.get(0));
        Double valor = Double.parseDouble(calcular.valor);
        valor = valor - 1;
        return new ResultadoH("numero", valor + "");
    }

    private ResultadoH min(Lista lista) {
        ArrayList<Double> lista2 = (ArrayList<Double>) lista.valores.clone();
        Collections.sort(lista2);
        return new ResultadoH(lista.tipo, lista2.get(0) + "");
    }

    private ResultadoH max(Lista lista) {
        ArrayList<Double> lista2 = (ArrayList<Double>) lista.valores.clone();
        Collections.sort(lista2);
        return new ResultadoH(lista.tipo, lista2.get(lista2.size() - 1) + "");
    }

    private ResultadoH product(Lista lista) {
        Double d = 1.0;
        if (lista.tipo.equals("numero")) {
            for (int i = 0; i < lista.valores.size(); i++) {
                d = d * Double.parseDouble(lista.valores.get(i) + "");
            }
        } else {
            for (int i = 0; i < lista.valores.size(); i++) {
                String c1 = (String) lista.valores.get(i);
                char c = c1.charAt(0);
                d = d * c;
            }
        }
        return new ResultadoH("numero", d + "");
    }

    private ResultadoH sum(Lista lista) {
        Double d = 0.0;
        if (lista.tipo.equals("numero")) {
            for (int i = 0; i < lista.valores.size(); i++) {
                d = d + Double.parseDouble(lista.valores.get(i) + "");
            }
        } else {
            for (int i = 0; i < lista.valores.size(); i++) {
                String c1 = (String) lista.valores.get(i);
                char c = c1.charAt(0);
                d = d + c;
            }
        }
        return new ResultadoH("numero", d + "");
    }

    private ResultadoH length(Lista lista) {
        return new ResultadoH("numero", lista.valores.size() + "");
    }

    private Lista desc(Lista lista) {
        Lista clon = new Lista();
        clon.tipo = lista.tipo;
        clon.indices = (ArrayList<Integer>) lista.indices.clone();
        clon.valores = (ArrayList) lista.valores.clone();
        Comparator<Integer> comparador = Collections.reverseOrder();
        Collections.sort(clon.valores, comparador);
        return clon;
    }

    private Lista asc(Lista lista) {
        Lista clon = new Lista();
        clon.tipo = lista.tipo;
        clon.indices = (ArrayList<Integer>) lista.indices.clone();
        clon.valores = (ArrayList) lista.valores.clone();
        Collections.sort(clon.valores);
        return clon;
    }

    private Lista revers(Lista lista) {
        ArrayList revers = new ArrayList();
        for (int i = lista.valores.size() - 1; i >= 0; i--) {
            revers.add(lista.valores.get(i));
        }
        Lista clon = new Lista();
        clon.tipo = lista.tipo;
        clon.indices = (ArrayList<Integer>) lista.indices.clone();
        clon.valores = revers;
        return clon;
    }

    private Lista par(Lista lista) {
        Lista clon = new Lista();
        clon.tipo = lista.tipo;
        if (lista.tipo.equals("numero")) {
            for (int i = 0; i < lista.valores.size(); i++) {
                Double num = (Double) lista.valores.get(i);
                if ((num % 2) == 0) {
                    clon.valores.add(num);
                }
            }
            clon.indices.add(clon.valores.size());
            return clon;
        } else {
            for (int i = 0; i < lista.valores.size(); i++) {
                String c1 = (String) lista.valores.get(i);
                char c = c1.charAt(0);
                if ((c % 2) == 0) {
                    clon.valores.add(c);
                }

            }
            clon.indices.add(clon.valores.size());
            return clon;
        }
    }

    private Lista impar(Lista lista) {
        Lista clon = new Lista();
        clon.tipo = lista.tipo;
        if (lista.tipo.equals("numero")) {
            for (int i = 0; i < lista.valores.size(); i++) {
                Double num = (Double) lista.valores.get(i);
                if (!((num % 2) == 0)) {
                    clon.valores.add(num);
                }
            }
            clon.indices.add(clon.valores.size());
            return clon;
        } else {
            for (int i = 0; i < lista.valores.size(); i++) {
                String c1 = (String) lista.valores.get(i);
                char c = c1.charAt(0);
                if (!((c % 2) == 0)) {
                    clon.valores.add(c);
                }

            }
            clon.indices.add(clon.valores.size());
            return clon;
        }
    }

    //devuelve una lista
    public Lista operacionLista(Nodo raiz) {
        Lista lista1 = null;
        Lista lista2 = null;
        if (raiz.etiqueta.equals("concatenar")) {
            lista1 = operacionLista(raiz.hijos.get(0));
            lista2 = operacionLista(raiz.hijos.get(1));
        } else if (raiz.etiqueta.equals("valores") || raiz.etiqueta.equals("listaValores") || raiz.etiqueta.equals("cadena")) {
            Lista l1 = new Lista(raiz, tabla);
            l1 = operar(raiz.etiqueta, l1);
            return l1;
        } else if (raiz.etiqueta.equals("llamada")) {
            FuncionH f = Inicio.interprete.llamada(raiz);
            return f.retorno.lista;
        } else if (raiz.etiqueta.equals("id")) {
            //buscar id en la tabla de simbolos
            String nombre = raiz.valor;
            SimboloH s = tabla.getSimbolo(nombre);
            if (s != null) {
                return s.lista;
            } else {
                System.out.println("Error semantico,la lista no existe!!!");
                Inicio.reporteError2.agregar("Semantico", raiz.linea, raiz.columna, "La lista " + nombre + " no existe", Inicio.interprete.archivo);
            }
        } else {
            Lista l = operacionLista(raiz.hijos.get(0));
            return operar(raiz.etiqueta, l);
        }

        //falta considerar los arreglos de 2 dimensiones
        //por el momento los arreglos de 2 dimensiones se concatenan linealmente
        if (raiz.etiqueta.equals("concatenar")) {
            for (int i = 0; i < lista2.valores.size(); i++) {
                lista1.indices.clear();
                lista1.indices.add(lista1.valores.size() + lista2.valores.size());
                lista1.valores.add(lista2.valores.get(i));
            }
            return lista1;
        }

        return null;
    }
}
