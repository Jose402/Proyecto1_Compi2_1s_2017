/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

/**
 *
 * @author Jose2
 */
public class ResultadoG {
    
    public String tipo;
    public Object valor;
    
    public ResultadoG(){
        
    }
    
    public ResultadoG(String tipo){
        this.tipo=tipo;
    }
    
    public ResultadoG(Object valor){
        this.valor=valor;
    }
    
    public ResultadoG(String tipo,Object valor){
        this.tipo=tipo;
        this.valor=valor;
    }
}
