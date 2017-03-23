/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Operaciones;

import Ast.Nodo;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public abstract class OperacionAbstracta {

    protected TablaSimboloG tabla;
    protected TablaSimboloG global;
    protected OperacionAritmeticaG opA;
    protected OperacionRelacionalG opR;
    protected OperacionLogicaG opL;

    protected abstract ResultadoG operar(Nodo raiz);

    protected int getBoolValor(Object objeto) {
        Boolean valor = (Boolean) objeto;
        if (valor) {
            return 1;
        } else {
            return 0;
        }
    }

    protected String getTipo(Object objeto) {
        String tipo = objeto.getClass().getSimpleName();
        switch (tipo) {
            case "Integer":
                return "entero";
            case "String":
                return "cadena";
            case "Character":
                return "caracter";
            case "Double":
                return "decimal";
            case "Boolean":
                return "bool";
            default:
                return tipo;
        }
    }

    protected int getCharValor(Object objeto, Nodo raiz) {
        String valor = (char) objeto + "";
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo castear el caracter a tipo Bool");
            return -1;
        }
    }

    protected int getStringValor(Object objeto, Nodo raiz) {
        String valor = (String) objeto;
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo castear la cadena a tipo Bool");
            return -1;
        }
    }

}
