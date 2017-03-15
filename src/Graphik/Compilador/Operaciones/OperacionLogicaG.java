/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Operaciones;

import Ast.Nodo;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class OperacionLogicaG {
    private TablaSimboloG tabla;
    OperacionRelacionalG opR;
    
    public OperacionLogicaG(){
        tabla=Inicio.compilador.tabla;
    }
    
    public OperacionLogicaG(TablaSimboloG tabla){
        this.tabla=tabla;
    }
    
    public ResultadoG operar(Nodo raiz){
        ResultadoG resultado1=null;
        ResultadoG resultado2=null;
        
        switch(raiz.etiqueta){
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "^":
                opR=new OperacionRelacionalG();
                return opR.operar(raiz);
            case "==":
            case "!=":
            case ">":
            case ">=":
            case "<":
            case "<=":
                opR=new OperacionRelacionalG();
                return opR.operar(raiz);
            case "&&":
            case "||":
            case "&|":
                resultado1=operar(raiz.hijos.get(0));
                resultado2=operar(raiz.hijos.get(1));
                if(resultado1.tipo.equals("entero")){
                    if((int)resultado1.valor==1){
                        resultado1=new ResultadoG("bool",true);
                    }else{
                        resultado1=new ResultadoG("bool",false);
                    }
                }else if(!resultado1.tipo.equals("bool")){
                    Inicio.reporteError.agregar("Sintactico",raiz.linea,raiz.columna,"Se esperaba 0,1,veradder o falso");
                    resultado1=new ResultadoG("-1",false);
                }
                
                if(resultado2.tipo.equals("entero")){
                    if((int)resultado2.valor==1){
                        resultado2=new ResultadoG("bool",true);
                    }else{
                        resultado2=new ResultadoG("bool",false);
                    }
                }else if(!resultado2.tipo.equals("bool")){
                    Inicio.reporteError.agregar("Sintactico",raiz.linea,raiz.columna,"Se esperaba 0,1,veradder o falso");
                    resultado2=new ResultadoG("-1",false);
                }
                
                break;
            case "!":
                resultado1=operar(raiz.hijos.get(0));
                if((Boolean)resultado1.valor){
                    return new ResultadoG("bool",false);
                }else{
                    return new ResultadoG("bool",true);
                }
            default:
                opR=new OperacionRelacionalG();
                return opR.operar(raiz);
        }
        
        
        //-------------------------operaciones logicas-----------------------
        
        switch(raiz.etiqueta){
            case "&&":
                if((Boolean)resultado1.valor&&(Boolean)resultado2.valor){
                    return new ResultadoG("bool",true);
                }else{
                    return new ResultadoG("bool",false);
                }
            case "||":
                if((Boolean)resultado1.valor||(Boolean)resultado2.valor){
                    return new ResultadoG("bool",true);
                }else{
                    return new ResultadoG("bool",false);
                }
            case "&|":
                if((Boolean)resultado1.valor^(Boolean)resultado2.valor){
                    return new ResultadoG("bool",true);
                }else{
                    return new ResultadoG("bool",false);
                }
        }
        
        //-------------------------fin operaciones logicas--------------------
        
        return new ResultadoG("-1",false);
    }
}
