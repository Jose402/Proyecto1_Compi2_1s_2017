/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Ast.Nodo;

/**
 *
 * @author Jose2
 */
public class FuncionH {

    public String nombre;
    public String tipo;
    public String id;
    public ResultadoH retorno;
    public Nodo raiz;

    public FuncionH(String nombre, Nodo raiz) {
        this.nombre = nombre;
        this.raiz = raiz;
        this.id = nombre;
    }
}
