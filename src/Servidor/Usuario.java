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
public class Usuario {

    public String nombre_usuario;
    public String contrasenia;
    public String primer_nombre;
    public String primer_apellido;
    public String correo;

    public Usuario() {
    }

    public Usuario(String usuario, String contrasenia) {
        this.nombre_usuario = usuario;
        this.contrasenia = contrasenia;
    }
}
