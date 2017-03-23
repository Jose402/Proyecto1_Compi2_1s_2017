/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Ast.Nodo;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.TablaSimboloH;

/**
 *
 * @author Jose2
 */
public class OperacionLogica {

    private TablaSimboloH tabla;
    private OperacionRelacional opR;

    public OperacionLogica(TablaSimboloH tabla) {
        this.tabla = tabla;

    }

    public ResultadoH resolverLogica(Nodo raiz) {
        ResultadoH resultado1 = null;
        ResultadoH resultado2 = null;
        switch (raiz.etiqueta) {
            case ">":
            case ">=":
            case "<":
            case "<=":
            case "==":
            case "!=":
                opR = new OperacionRelacional(tabla);
                return opR.relacionar(raiz);
            case "&&":
            case "||":
                resultado1 = resolverLogica(raiz.hijos.get(0));
                resultado2 = resolverLogica(raiz.hijos.get(1));
        }

        switch (raiz.etiqueta) {
            case "&&":
                if (resultado1.valor.equals("true") && resultado2.valor.equals("true")) {
                    return new ResultadoH("bool", "true");
                } else {
                    return new ResultadoH("bool", "false");
                }
            case "||":
                if (resultado1.valor.equals("true") || resultado2.valor.equals("true")) {
                    return new ResultadoH("bool", "true");
                } else {
                    return new ResultadoH("bool", "false");
                }
        }

        return new ResultadoH("-1", "false");
    }

}
