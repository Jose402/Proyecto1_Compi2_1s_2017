/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose2
 */
public class Conexion {

    private String ip;
    private int puerto;
    private Socket socket;
    Gson gson;
    Thread hiloDatos;
    String respuesta;

    public Conexion() {
        ip = "127.0.0.1";
        puerto = 5678;
    }

    public Respuesta enviar(String tipo, Object usuario) {

        try {
            socket = new Socket(ip, puerto);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            gson = new Gson();
            Mensaje mensaje = new Mensaje(tipo, gson.toJson(usuario));
            String men = gson.toJson(mensaje);
            writer.print(gson.toJson(mensaje));
            writer.flush();
            socket.shutdownOutput();
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            respuesta = total.toString();
            socket.close();
            writer.close();

            if (!respuesta.equalsIgnoreCase("")) {

                return new Respuesta(respuesta);
            } else {
                return null;
            }

        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("Error: "+e.toString());
            return new Respuesta();
        }

    }

    public void verificarDatos() {
        respuesta = "";
        hiloDatos = new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, puerto);
                    BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                        System.out.print(line);
                    }
                    respuesta = total.toString();
                    hiloDatos.stop();
                    //txtResultado.setText(total.toString()+"\n");

                } catch (IOException ex) {

                }

            }
        };
        hiloDatos.start();

    }

}
