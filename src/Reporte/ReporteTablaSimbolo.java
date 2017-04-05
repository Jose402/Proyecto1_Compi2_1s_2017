/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporte;

import Graphik.Compilador.Arreglo;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.SimboloG;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jose2
 */
public class ReporteTablaSimbolo {

    private ArrayList<SimboloG> lista;

    public ReporteTablaSimbolo() {
        lista = Compilador.reporteSimbolos;
        generarHtml();
    }

    public void generarHtml() {
        String titulo = "TablaSimbolos.html";
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
        String fecha = hourdateFormat.format(date);
        String html = "";
        html += "<!DOCTYPE html";
        html += "<html>";
        html += "<title> Tabla de Simbolos</title>";
        html += "<body>";
        html += "<span style=\"color: #ff0000;\">";
        html += "<center> <h1>" + "Tabla de Simbolos" + "</h1> </center>";
        html += "</span>";
        html += "<span style=\"color: #0000ff;\">";
        html += "<center> <h4>" + "Fecha: " + fecha + "</h4> </center>";
        html += "</span>";
        html += "<hr/>";
        html += "<center>";
        html += "<table class=\"egt\" border=1>";
        html += "<thead>";
        //aqui van los titulos
        html += "<tr>";
        html += "<td>" + "" + "</td>";
        html += "<td>" + "Nombre" + "</td>";
        html += "<td>" + "Tipo" + "</td>";
        html += "<td>" + "Rol" + "</td>";
        html += "<td>" + "Ambito" + "</td>";
        html += "<td>" + "Dimension" + "</td>";
        html += "</tr>";
        html += "</thead>";
        int i = 0;
        for (SimboloG simbolo : lista) {
            html += "<tr>";
            html += "<td>" + i + "</td>";
            html += "<td>" + simbolo.nombre + "</td>";
            html += "<td>" + simbolo.tipo + "</td>";
            html += "<td>" + simbolo.rol + "</td>";
            html += "<td>" + simbolo.ambito + "</td>";
            if (simbolo.valor != null) {
                if (simbolo.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    html += "<td>" + arreglo.dimensiones.size() + "</td>";
                } else {
                    html += "<td>" + "No aplica" + "</td>";
                }
            } else {
                html += "<td>" + "No aplica" + "</td>";
            }
            html = html + "</tr>";
            i++;
        }

        html += "</table>";
        html += "</center>";
        html += "</body>";
        html += "</html>";

        try {
            FileWriter archivo = new FileWriter(titulo);
            PrintWriter escritura = new PrintWriter(archivo);
            escritura.println(html);
            archivo.close();

            File file = new File(titulo);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
        }
    }
}
