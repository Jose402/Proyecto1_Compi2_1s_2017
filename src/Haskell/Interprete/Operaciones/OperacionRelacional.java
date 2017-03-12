/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Ast.Nodo;
import Haskell.Interprete.*;
import java.util.Objects;

/**
 *
 * @author Jose2
 */
public class OperacionRelacional {
    
    private TablaSimboloH tabla;
    private OperacionAritmetica opA;
    private OperacionNativa opN;
    public OperacionRelacional(TablaSimboloH tabla){
        this.tabla=tabla;
        
    }
    
    public ResultadoH relacionar(Nodo raiz){
        ResultadoH resultado1=null;
        ResultadoH resultado2=null;
        switch(raiz.etiqueta){
            case "==":
            case ">":
            case "<":
            case ">=":
            case "<=":
            case "!=":
                //llamar a expA
                opN=new OperacionNativa(tabla);
                resultado1=opN.operar(raiz.hijos.get(0));
                opN=new OperacionNativa(tabla);
                resultado2=opN.operar(raiz.hijos.get(1));
                break;
        }
        int tipoComp=0;
        String str1="";
        String str2="";
        double val1=0.0;
        double val2=0.0;
        if(resultado1.tipo.equals(resultado2.tipo)){
            //si es lista
            if(resultado1.lista!=null){
                tipoComp=0;
                str1=resultado1.lista.getString();
            }else{
                tipoComp=1;
                val1=Double.parseDouble(resultado1.valor);
            }
            
            //si es valor puntual
            if(resultado2.lista!=null){
                tipoComp=0;
                str2=resultado2.lista.getString();
            }else{
                tipoComp=1;
                val2=Double.parseDouble(resultado2.valor);
            }
            
        }else{
            return new ResultadoH("-1","false");
        }
        
        switch(tipoComp){
            case 0://si es lista
                switch(raiz.etiqueta){
                    case "==":
                        break;
                    case ">":
                        break;
                    case ">=":
                        break;
                    case "<":
                        break;
                    case "<=":
                        break;
                    case "!=":
                        break;
                }
                break;
            case 1://si es numero
                switch(raiz.etiqueta){
                    case "==":
                        if(val1==val2){
                            return new ResultadoH("Bool","true");
                        }else{
                           return new ResultadoH("Bool","false"); 
                        }
                    case ">":
                        if(val1>val2){
                            return new ResultadoH("Bool","true");
                        }else{
                           return new ResultadoH("Bool","false"); 
                        }
                    case ">=":
                        if(val1>=val2){
                            return new ResultadoH("Bool","true");
                        }else{
                           return new ResultadoH("Bool","false"); 
                        }
                    case "<":
                        if(val1<val2){
                            return new ResultadoH("Bool","true");
                        }else{
                           return new ResultadoH("Bool","false"); 
                        }
                    case "<=":
                        if(val1<=val2){
                            return new ResultadoH("Bool","true");
                        }else{
                           return new ResultadoH("Bool","false"); 
                        }
                    case "!=":
                        if(val1!=val2){
                            return new ResultadoH("Bool","true");
                        }else{
                           return new ResultadoH("Bool","false"); 
                        }
                }
                break;
        }
        
        return new ResultadoH("-1","false");
    }
}
