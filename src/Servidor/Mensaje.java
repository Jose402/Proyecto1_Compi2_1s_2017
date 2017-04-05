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
public class Mensaje {

    public String nombre;
    public String dato;

    public Mensaje(String nombre, String json) {
        this.nombre = nombre;
        this.dato = json;
    }

    public Mensaje(String nombre) {
        this.nombre = nombre;
    }
}
