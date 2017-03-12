/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Sentencias;

import Ast.Nodo;
import Haskell.Interprete.Lista;
import Haskell.Interprete.Operaciones.OperacionNativa;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.SimboloH;
import Haskell.Interprete.TablaSimboloH;

/**
 *
 * @author Jose2
 */
public class Declaracion {
    private TablaSimboloH tabla;
    private OperacionNativa opN;
    public Declaracion(Nodo raiz,TablaSimboloH tabla){
        this.tabla=tabla;
        declarar(raiz);
    }
    
    public Declaracion(TablaSimboloH tabla){
        this.tabla=tabla;
    }
    
    public SimboloH declarar(Nodo raiz){
        return declaracion(raiz);
    }
    
    public Declaracion(String nombre,ResultadoH valor,TablaSimboloH tabla){
        this.tabla=tabla;
        SimboloH simbolo=new SimboloH(valor.tipo,nombre,valor.valor);
        if(!tabla.setSimbolo(simbolo)){
            System.out.println("Error semantico,La variable ya existe!!!");
        }
        
    }
    
    private SimboloH declaracion(Nodo raiz){
        opN=new OperacionNativa(tabla);
        Lista lista=null;
        if(!raiz.hijos.isEmpty()){
            lista=opN.operacionLista(raiz.hijos.get(0));
        }else{
            lista=new Lista();
            lista.indices.add(0);
        }
        String nombre=raiz.valor;
        SimboloH simbolo=new SimboloH(nombre,lista);
        if(!tabla.setSimbolo(simbolo)){
           System.out.println("Error semantico,El simbolo ya existe!!!");
           return null;
        }else{
            return simbolo;
        }
    }
    
}
