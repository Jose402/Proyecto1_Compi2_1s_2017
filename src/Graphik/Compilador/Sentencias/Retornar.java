/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;

/**
 *
 * @author Jose2
 */
public class Retornar extends Compilador {

    @Override
    public Metodo ejecutar(Nodo raiz) {
        if (raiz.hijos.size() > 0) {
            opL = new OperacionLogicaG(global, tabla);
            ResultadoG retorno = opL.operar(raiz.hijos.get(0));
            metodoActual.retorno = retorno;
            metodoActual.estadoRetorno = true;
        } else {
            metodoActual.retorno = null;
            metodoActual.estadoRetorno = true;
        }
        return metodoActual;
    }

}
