/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporte;

import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class ReporteError {
    private ArrayList<ErrorP> listaErrores;
    
    public ReporteError(){
        listaErrores=new ArrayList<>();
    }
    
    public void agregar(String tipo,int linea,int columna,String descripcion){
        ErrorP error=new ErrorP(tipo, linea, columna, descripcion);
        listaErrores.add(error);
    }
}
