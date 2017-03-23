/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Arreglo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Haskell.Interprete.FuncionH;
import Haskell.Interprete.Lista;
import Haskell.Interprete.Operaciones.OperacionLogica;
import Haskell.Interprete.ResultadoH;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class LlamadaHaskell {

    private TablaSimboloG global;
    private TablaSimboloG tabla;
    private OperacionLogicaG opL;

    public LlamadaHaskell(TablaSimboloG global, TablaSimboloG tabla) {
        this.tabla = tabla;
        this.global = global;
    }

    public ResultadoG ejecutar(Nodo raiz) {
        String nombre = raiz.valor;
        Nodo parametros = raiz.hijos.get(0);
        ArrayList<ResultadoH> listaParametros = new ArrayList();
        for (Nodo parametro : parametros.hijos) {
            opL = new OperacionLogicaG(global, tabla);
            ResultadoG resultadoG = opL.operar(parametro);
            if (resultadoG.valor != null) {
                if (resultadoG.tipo.equals("entero") || resultadoG.tipo.equals("decimal")) {
                    if (resultadoG.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                        Arreglo arreglo = (Arreglo) resultadoG.valor;
                        Lista lista = new Lista("numero");
                        for (int i = 0; i < arreglo.dimensiones.size(); i++) {
                            lista.indices.add(arreglo.dimensiones.get(i));
                        }
                        lista.valores = arreglo.getDatos();
                        ResultadoH resultadoH = new ResultadoH("numero", lista);
                        listaParametros.add(resultadoH);
                    } else {
                        ResultadoH resultadoH = new ResultadoH("numero", resultadoG.valor + "");
                        listaParametros.add(resultadoH);
                    }
                } else if (resultadoG.tipo.equals("cadena")) {
                    Lista lista = new Lista(parametro);
                    ResultadoH resultadoH = new ResultadoH("cadena", lista);
                    listaParametros.add(resultadoH);
                } else if (resultadoG.tipo.equals("caracter")) {
                    if (resultadoG.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                        Arreglo arreglo = (Arreglo) resultadoG.valor;
                        Lista lista = new Lista("cadena");
                        for (int i = 0; i < arreglo.dimensiones.size(); i++) {
                            lista.indices.add(arreglo.dimensiones.get(i));
                        }
                        lista.valores = arreglo.getDatos();
                        ResultadoH resultadoH = new ResultadoH("cadena", lista);
                        listaParametros.add(resultadoH);
                    }
                } else {
                    Inicio.reporteError.agregar("Semantico", parametro.linea, parametro.columna, "Solo se permiten datos tipo numerico y cadena en las llamadas a funciones haskell");
                }

            }
        }

        FuncionH funcion = Inicio.interprete.llamada(nombre, listaParametros);
        if (funcion.retorno != null) {
            return new ResultadoG("decimal", Double.parseDouble(funcion.retorno.valor));
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo acceder a la funcion haskell++ " + nombre);
        }
        return new ResultadoG("-1", null);
    }
}
