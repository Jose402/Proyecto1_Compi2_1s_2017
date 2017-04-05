/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Proyecto {

    public String nombre;
    public String permiso;
    public ArrayList<String> listaUsuarios;
    public String propietario;
    public String cadena;
    public String contenido;
    public String estructura;

    public Proyecto(String propietario, String nombre, String permiso, String cadena) {
        this.nombre = nombre;
        this.permiso = permiso;
        this.propietario = propietario;
        this.cadena = cadena;
    }
}
