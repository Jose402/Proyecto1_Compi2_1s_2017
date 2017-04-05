/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Datos;

/**
 *
 * @author Jose2
 */
public class Celda {

    public String tipo;
    public Object valor;

    public Celda(String tipo, Object valor) {
        if (tipo.equalsIgnoreCase("cadena")) {
            String cad = (String) valor;
            cad = cad.replace("\"", "");
            this.tipo = tipo;
            this.valor = cad;
        } else {
            this.tipo = tipo;
            this.valor = valor;
        }
    }
}
