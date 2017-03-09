/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Graphik.Ast.Nodo;
import Haskell.Interprete.ResultadoH;

/**
 *
 * @author Jose2
 */
public class OperacionRelacional {
    
    
    public OperacionRelacional(){
        
    }
    
    public ResultadoH relacionar(Nodo raiz){
        switch(raiz.etiqueta){
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "sqrt":
            case "mod":
                //llamar a expA
                break;
        }
        
        return new ResultadoH("-1","false");
    }
}
