/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author Jose2
 */
public class Respuesta {

    public boolean estado;
    public Object dato;

    public Respuesta() {
        estado = false;
    }

    public Respuesta(Object dato) {
        estado = true;
        this.dato = dato;
    }
}
