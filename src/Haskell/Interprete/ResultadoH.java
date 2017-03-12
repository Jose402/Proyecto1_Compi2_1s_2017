/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Ast.Nodo;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class ResultadoH {
    
    public String tipo;
    public String valor;
    public Lista lista;
    
    public ResultadoH(String tipo,String valor){
        this.valor=valor;
        this.tipo=tipo;        
    }
    
    public ResultadoH(String tipo,Lista lista){
        this.tipo=tipo;
        this.lista=lista;
        this.valor=lista.getString();
    }
    
}
