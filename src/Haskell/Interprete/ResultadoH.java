/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Graphik.Ast.Nodo;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class ResultadoH {
    
    public String tipo;
    public String valor;
    public ArrayList<Nodo> lista;
    
    public ResultadoH(String tipo,String valor){
        this.valor=valor;
        this.tipo=tipo;        
    }
    
    public ResultadoH(String tipo,ArrayList<Nodo> lista){
        this.tipo=tipo;
        this.lista=lista;
    }
    
}
