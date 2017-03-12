/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Ast.Nodo;
import Haskell.Interprete.FuncionH;
import Haskell.Interprete.Lista;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.SimboloH;
import Haskell.Interprete.TablaSimboloH;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class OperacionAritmetica {
   
    private TablaSimboloH tabla;
    private OperacionNativa opN;
    public OperacionAritmetica(TablaSimboloH tabla){
        this.tabla=tabla;
    }
    
    public ResultadoH resolver(Nodo raiz){
        ResultadoH resultado1=null;
        ResultadoH resultado2=null;
        
        switch(raiz.etiqueta){
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "mod":
            case "sqrt":
                resultado1=resolver(raiz.hijos.get(0));
                resultado2=resolver(raiz.hijos.get(1));
                break;
            case "unitario":
                resultado1=resolver(raiz.hijos.get(0));
                Double negativo=Double.parseDouble(resultado1.valor)*-1;
                return new ResultadoH(resultado1.tipo,negativo+"");
            case "acceso":
                SimboloH s=null;
                if(raiz.hijos.get(0).etiqueta.equals("id")){
                    String id=raiz.hijos.get(0).valor;
                    s=tabla.getSimbolo(id);
                }else{
                    opN=new OperacionNativa(tabla);
                    Lista lista=opN.operacionLista(raiz.hijos.get(0));
                    s=new SimboloH("", lista);
                }
                if(s!=null){
                    Nodo valores=raiz.hijos.get(1);
                    ArrayList<Integer> index=new ArrayList<>();
                    for(Nodo nodo:valores.hijos){
                        opN=new OperacionNativa(tabla);
                        ResultadoH r=opN.operar(nodo);
                        Double d=Double.parseDouble(r.valor);
                        index.add(d.intValue());
                    }
                    ResultadoH acceso=s.lista.getValor(index);
                    return acceso;
                }else{
                    System.out.println("Error semantico,la lista no existe");
                }
            case "id":
                if(tabla.existe(raiz.valor)){
                SimboloH variable=tabla.getSimbolo(raiz.valor);
                if(variable.lista!=null){
                    return new ResultadoH(variable.tipo,variable.lista);
                }else{
                    return new ResultadoH(variable.tipo,variable.valor);
                }
                }else{
                    System.out.println("Error semantico, la varibale no existe!!!");
                }
            case "cadena":
            case "numero":
                return  new ResultadoH(raiz.etiqueta,raiz.valor);
            case "llamada":
                FuncionH funcion=Inicio.interprete.llamada(raiz);
                if(funcion.retorno!=null){
                    return new ResultadoH(funcion.tipo,funcion.retorno.valor);
                }else{
                    return new ResultadoH("-1","0");
                }
            default:
                OperacionNativa opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(raiz);
                return new ResultadoH(r.tipo,r.valor);
        }
        
        /*
        if(raiz.valor!=null&&!(raiz.etiqueta.equals("acceso")||raiz.etiqueta.equals("id")||raiz.etiqueta.equals("llamada"))){
            return new ResultadoH(raiz.etiqueta,raiz.valor);
        }else if(raiz.hijos.size()==2){
            if(raiz.etiqueta.equals("acceso")){
                SimboloH s=null;
                if(raiz.hijos.get(0).etiqueta.equals("id")){
                    String id=raiz.hijos.get(0).valor;
                    s=tabla.getSimbolo(id);
                }else{
                    opN=new OperacionNativa(tabla);
                    Lista lista=opN.operacionLista(raiz.hijos.get(0));
                    s=new SimboloH("", lista);
                }
                if(s!=null){
                    Nodo valores=raiz.hijos.get(1);
                    ArrayList<Integer> index=new ArrayList<>();
                    for(Nodo nodo:valores.hijos){
                        opN=new OperacionNativa(tabla);
                        ResultadoH r=opN.operar(nodo);
                        Double d=Double.parseDouble(r.valor);
                        index.add(d.intValue());
                    }
                    ResultadoH acceso=s.lista.getValor(index);
                    return acceso;
                }else{
                    System.out.println("Error semantico,la lista no existe");
                }
            }else{
                resultado1=resolver(raiz.hijos.get(0));
                resultado2=resolver(raiz.hijos.get(1));
            }
        }else if(raiz.hijos.isEmpty()){
            if(raiz.etiqueta.equals("id")){
                if(tabla.existe(raiz.valor)){
                SimboloH variable=tabla.getSimbolo(raiz.valor);
                if(variable.lista!=null){
                    return new ResultadoH(variable.tipo,variable.lista);
                }else{
                    return new ResultadoH(variable.tipo,variable.valor);
                }
                }else{
                    System.out.println("Error semantico, la varibale no existe!!!");
                }
            }
        }
        else if(raiz.hijos.size()==1){
            if(raiz.etiqueta.equals("-")){
                resultado1=resolver(raiz.hijos.get(0));
                Double negativo=Double.parseDouble(resultado1.valor)*-1;
                return new ResultadoH(resultado1.tipo,negativo+"");
            }else if(raiz.etiqueta.equals("cadena")||raiz.etiqueta.equals("numero")){
                return  new ResultadoH(raiz.etiqueta,raiz.valor);
            }else if(raiz.etiqueta.equals("llamada")){
                FuncionH funcion=Inicio.interprete.llamada(raiz);
                if(funcion.retorno!=null){
                    return new ResultadoH(funcion.tipo,funcion.retorno.valor);
                }else{
                    return new ResultadoH("-1","0");
                }
            }
            else{
                OperacionNativa opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(raiz);
                return new ResultadoH(r.tipo,r.valor);
            }
        }
        */
        Boolean estado=false;
        Double valor1=0.0;
        Double valor2=0.0;
        try{
            valor1=Double.parseDouble(resultado1.valor);
            valor2=Double.parseDouble(resultado2.valor);
            estado=true;
        }catch(Exception e){
            
        }
        if(estado){
            switch(raiz.etiqueta){
                case "+":
                    return new ResultadoH(resultado1.tipo,(valor1+valor2)+"");
                case "-":
                    return new ResultadoH(resultado1.tipo,(valor1-valor2)+""); 
                case "*":
                    return new ResultadoH(resultado1.tipo,(valor1*valor2)+"");
                case "/":
                    return new ResultadoH(resultado1.tipo,(valor1/valor2)+"");
                case "^":
                    return new ResultadoH(resultado1.tipo,(Math.pow(valor1,valor2))+"");
                case "mod":
                    return new ResultadoH(resultado1.tipo,(valor1%valor2)+"");
                case "sqrt"://aun falta implementarla
                    break;
            }
        }else{
            System.out.println("Error semantico,los tipos de datos son diferentes");
        }
        return new ResultadoH("-1","0");
    }
}
