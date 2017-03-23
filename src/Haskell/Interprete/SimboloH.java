/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

/**
 *
 * @author Jose2
 */
public class SimboloH {

    public String tipo;
    public String nombre;
    public String valor;
    public Lista lista;

    public SimboloH(String nombre, Lista lista) {
        this.tipo = lista.tipo;
        this.nombre = nombre;
        this.lista = lista;
        this.valor = lista.getString();
    }

    public SimboloH(String tipo, String nombre, String valor) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }

}
