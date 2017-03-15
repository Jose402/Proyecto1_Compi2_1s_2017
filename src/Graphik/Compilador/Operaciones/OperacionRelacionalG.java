/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Operaciones;

import Ast.Nodo;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class OperacionRelacionalG {
    private TablaSimboloG tabla;
    private OperacionAritmeticaG opA;
    public OperacionRelacionalG(){
        tabla=Inicio.compilador.tabla;
    }
    
    public OperacionRelacionalG(TablaSimboloG tabla){
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
                opA=new OperacionAritmeticaG();
                return opA.operar(raiz);
            case "==":
            case "!=":
            case ">":
            case "<":
            case ">=":
            case "<=":
                opA=new OperacionAritmeticaG();
                resultado1=opA.operar(raiz.hijos.get(0));
                opA=new OperacionAritmeticaG();
                resultado2=opA.operar(raiz.hijos.get(1));
                break;
            default:
                opA=new OperacionAritmeticaG();
                return opA.operar(raiz);
        }
        
        //-------------------operaciones relacionales-----------------------
        int valComp;
        switch(raiz.etiqueta){
            case "==":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor==(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((int)resultado1.valor==(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((int)resultado1.valor==getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((int)resultado1.valor==(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar datos numericos con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                if((Double)resultado1.valor==(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((Double)resultado1.valor==(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Double)resultado1.valor==getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((Double)resultado1.valor==(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar datos numericos con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                if(getBoolValor(resultado1.valor)==(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if(getBoolValor(resultado1.valor)==(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Boolean)resultado1.valor==(Boolean)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(getBoolValor(resultado1.valor)==getCharValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(getBoolValor(resultado1.valor)==getStringValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                if((char)resultado1.valor==(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((char)resultado1.valor==(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getCharValor(resultado1.valor, raiz)==getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((char)resultado1.valor==(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(((char)resultado1.valor+"").equals((String)resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar datos de tipo numerico con cadenas");
                                break;
                            case "bool":
                                if(getStringValor(resultado1.valor,raiz)==getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(((String)resultado1.valor).equals((char)resultado2.valor+"")){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(((String)resultado1.valor).equals((String)resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
                
                
                
                
                
                
                
                
            case "!=":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor!=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((int)resultado1.valor!=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((int)resultado1.valor!=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((int)resultado1.valor!=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar datos numericos con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                if((Double)resultado1.valor!=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((Double)resultado1.valor!=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Double)resultado1.valor!=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((Double)resultado1.valor!=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar datos numericos con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                if(getBoolValor(resultado1.valor)!=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if(getBoolValor(resultado1.valor)!=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Boolean)resultado1.valor!=(Boolean)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(getBoolValor(resultado1.valor)!=getCharValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(getBoolValor(resultado1.valor)!=getStringValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                if((char)resultado1.valor!=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((char)resultado1.valor!=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getCharValor(resultado1.valor, raiz)!=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((char)resultado1.valor!=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(!((char)resultado1.valor+"").equals((String)resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar datos de tipo numerico con cadenas");
                                break;
                            case "bool":
                                if(getStringValor(resultado1.valor,raiz)!=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(!((String)resultado1.valor).equals((char)resultado2.valor+"")){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(!((String)resultado1.valor).equals((String)resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
                
                
                
                
                
                
                
                
            case ">":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((int)resultado1.valor>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((int)resultado1.valor>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((int)resultado1.valor>(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((Double)resultado1.valor>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Double)resultado1.valor>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((Double)resultado1.valor>(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                if(getBoolValor(resultado1.valor)>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if(getBoolValor(resultado1.valor)>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getBoolValor(resultado1.valor)>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(getBoolValor(resultado1.valor)>getCharValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(getBoolValor(resultado1.valor)>getStringValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                if((char)resultado1.valor>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((char)resultado1.valor>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getCharValor(resultado1.valor, raiz)>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((char)resultado1.valor>(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((char)resultado1.valor+"").compareTo((String)resultado2.valor);
                                if(valComp>0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            case "bool":
                                if(getStringValor(resultado2.valor, raiz)>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                valComp=((String)resultado1.valor).compareTo((char)resultado2.valor+"");
                                if(valComp>0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((String)resultado1.valor).compareTo((String)resultado2.valor);
                                if(valComp>0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
                
                
                
                
                
                
                
            case ">=":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((int)resultado1.valor>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((int)resultado1.valor>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((int)resultado1.valor>=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((Double)resultado1.valor>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Double)resultado1.valor>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((Double)resultado1.valor>=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                if(getBoolValor(resultado1.valor)>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if(getBoolValor(resultado1.valor)>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getBoolValor(resultado1.valor)>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(getBoolValor(resultado1.valor)>=getCharValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(getBoolValor(resultado1.valor)>=getStringValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                if((char)resultado1.valor>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((char)resultado1.valor>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getCharValor(resultado1.valor, raiz)>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((char)resultado1.valor>=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((char)resultado1.valor+"").compareTo((String)resultado2.valor);
                                if(valComp>=0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            case "bool":
                                if(getStringValor(resultado2.valor, raiz)>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                valComp=((String)resultado1.valor).compareTo((char)resultado2.valor+"");
                                if(valComp>=0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((String)resultado1.valor).compareTo((String)resultado2.valor);
                                if(valComp>=0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
                
                
                
                
                
                
                
                
            case "<":
                ResultadoG aux=resultado1;
                resultado1=resultado2;
                resultado2=aux;
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((int)resultado1.valor>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((int)resultado1.valor>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((int)resultado1.valor>(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((Double)resultado1.valor>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Double)resultado1.valor>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((Double)resultado1.valor>(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                if(getBoolValor(resultado1.valor)>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if(getBoolValor(resultado1.valor)>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getBoolValor(resultado1.valor)>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(getBoolValor(resultado1.valor)>getCharValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(getBoolValor(resultado1.valor)>getStringValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                if((char)resultado1.valor>(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((char)resultado1.valor>(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getCharValor(resultado1.valor, raiz)>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((char)resultado1.valor>(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((char)resultado1.valor+"").compareTo((String)resultado2.valor);
                                if(valComp>0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            case "bool":
                                if(getStringValor(resultado2.valor, raiz)>getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                valComp=((String)resultado1.valor).compareTo((char)resultado2.valor+"");
                                if(valComp>0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((String)resultado1.valor).compareTo((String)resultado2.valor);
                                if(valComp>0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
                
                
                
                
                
                
                
                
            case "<=":
                ResultadoG aux2=resultado1;
                resultado1=resultado2;
                resultado2=aux2;
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((int)resultado1.valor>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((int)resultado1.valor>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((int)resultado1.valor>=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                if((int)resultado1.valor>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((Double)resultado1.valor>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if((Double)resultado1.valor>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((Double)resultado1.valor>=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                if(getBoolValor(resultado1.valor)>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if(getBoolValor(resultado1.valor)>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getBoolValor(resultado1.valor)>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if(getBoolValor(resultado1.valor)>=getCharValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                if(getBoolValor(resultado1.valor)>=getStringValor(resultado2.valor, raiz)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                if((char)resultado1.valor>=(int)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "decimal":
                                if((char)resultado1.valor>=(Double)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "bool":
                                if(getCharValor(resultado1.valor, raiz)>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                if((char)resultado1.valor>=(char)resultado2.valor){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((char)resultado1.valor+"").compareTo((String)resultado2.valor);
                                if(valComp>=0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No es posible comparar valores tipo numerico con cadenas");
                                break;
                            case "bool":
                                if(getStringValor(resultado2.valor, raiz)>=getBoolValor(resultado2.valor)){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                                valComp=((String)resultado1.valor).compareTo((char)resultado2.valor+"");
                                if(valComp>=0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "cadena":
                                valComp=((String)resultado1.valor).compareTo((String)resultado2.valor);
                                if(valComp>=0){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "entero":
                ResultadoG unitario=new ResultadoG("Bool",Integer.parseInt(raiz.valor));
                if((int)unitario.valor==1){
                    return new ResultadoG("bool",true);
                }else if((int)unitario.valor==0){
                    return new ResultadoG("bool",false);
                }else{
                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"Solo se perminte 0,1,verdadero o falso como valor para un dato tipo Bool");
                }
                break;
            default:
                break;
        }
        //------------------------fin-----------------------------------------
        
        return new ResultadoG("-1",false);
    }
 
    private int getBoolValor(Object objeto){
        Boolean valor=(Boolean)objeto;
        if(valor){
            return 1;
        }else{
            return 0;
        }
    }
    
    public int getCharValor(Object objeto,Nodo raiz){
        String valor=(char)objeto+"";
        try{
            return Integer.parseInt(valor);
        }catch(Exception e){
            Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se pudo castear el caracter a tipo Bool");
            return -1;
        }
    }
    
    public int getStringValor(Object objeto,Nodo raiz){
        String valor=(String)objeto;
        try{
            return Integer.parseInt(valor);
        }catch(Exception e){
            Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se pudo castear la cadena a tipo Bool");
            return -1;
        }
    }
}
