/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Graphik.Ast.Nodo;
import Haskell.Interprete.Lista;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.SimboloH;
import Haskell.Interprete.TablaSimboloH;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.text.TabableView;

/**
 *
 * @author Jose2
 */
public class OperacionNativa {
 
    OperacionAritmetica opA;
    Lista lista;
    TablaSimboloH tabla;    
    public OperacionNativa(TablaSimboloH tabla){
        opA=new OperacionAritmetica(tabla);
        this.tabla=tabla;
    }
    
    public ResultadoH imprimirConsola(Nodo raiz){
        switch(raiz.etiqueta){
            case "concatenar":
            case "revers":
            case "impr":
            case "par":
            case "desc":
            case "asc":
                Lista l=concatenar(raiz);
                return new ResultadoH(l.tipo,l.getString());
            default:
                return  operar(raiz);
        }
    }
    
    //devuelve un valor puntual
    public ResultadoH operar(Nodo raiz){

        switch(raiz.etiqueta){
            case "calcular":
                return calcular(raiz);
            case "succ":
                return succ(raiz);
            case "decc":
                return decc(raiz);
            case "min":
                lista=concatenar(raiz.hijos.get(0));
                return min(lista);
            case "max":
                lista=concatenar(raiz.hijos.get(0));
                return max(lista);
            case "product":
                lista=concatenar(raiz.hijos.get(0));
                return product(lista);
            case "sum":
                lista=concatenar(raiz.hijos.get(0));
                return sum(lista);
            case "length":
                lista=concatenar(raiz.hijos.get(0));
                return length(lista);
            case "caracter":
                return opA.resolver(raiz);
        }
        return null;
    }
      
    public Lista operar(String tipo,Lista lista){
        switch(tipo){
            case "revers":
                break;
            case "impr":
                break;
            case "par":
                break;
            case "asc":
                return asc(lista);
            case "desc":
                return desc(lista);
        }
        return lista;
    }
    
    private ResultadoH calcular(Nodo raiz){
        return opA.resolver(raiz.hijos.get(0));
    }
    
    private ResultadoH succ(Nodo raiz){
        ResultadoH calcular=calcular(raiz.hijos.get(0));
        Double valor=Double.parseDouble(calcular.valor);
        valor=valor+1;
        return new ResultadoH(calcular.tipo,valor+"");
    }
    private ResultadoH decc(Nodo raiz){
        ResultadoH calcular=calcular(raiz.hijos.get(0));
        Double valor=Double.parseDouble(calcular.valor);
        valor=valor-1;
        return new ResultadoH(calcular.tipo,valor+"");
    }
    
    private ResultadoH min(Lista lista){
        ArrayList<Double> lista2=(ArrayList<Double>) lista.valores.clone();
        Collections.sort(lista2);
        return new ResultadoH(lista.tipo,lista2.get(0)+"");
    }
    
    private ResultadoH max(Lista lista){
        ArrayList<Double> lista2=(ArrayList<Double>) lista.valores.clone();
        Collections.sort(lista2);
        return new ResultadoH(lista.tipo,lista2.get(lista2.size()-1)+"");
    }
    
    private ResultadoH product(Lista lista){
        Double d=1.0;
        for(int i=0;i<lista.valores.size();i++){
            d=d*Double.parseDouble(lista.valores.get(i)+"");
        }
        return new ResultadoH(lista.tipo,d+"");
    }
    
    private ResultadoH sum(Lista lista){
        Double d=0.0;
        for(int i=0;i<lista.valores.size();i++){
            d=d+Double.parseDouble(lista.valores.get(i)+"");
        }
        return new ResultadoH(lista.tipo,d+"");
    }
    
    private ResultadoH length(Lista lista){
        return new ResultadoH(lista.tipo,lista.valores.size()+"");
    }
    
    
    private Lista desc(Lista lista){
        Comparator<Integer> comparador = Collections.reverseOrder();
        Collections.sort(lista.valores, comparador);
        return lista;
    }
    
    private Lista asc(Lista lista){
        Collections.sort(lista.valores);
        return lista;
    }
    
    //devuelve una lista
    public Lista concatenar(Nodo raiz){
        Lista lista1=null;
        Lista lista2=null;
        if(raiz.etiqueta.equals("concatenar")){
            lista1=concatenar(raiz.hijos.get(0));
            lista2=concatenar(raiz.hijos.get(1));
        }else if(raiz.etiqueta.equals("valores")||raiz.etiqueta.equals("listaValores")||raiz.etiqueta.equals("cadena")){
            Lista l1=new Lista(raiz,tabla);
            l1=operar(raiz.etiqueta,l1);
            return l1;
        }else if(raiz.etiqueta.equals("id")){
            //buscar id en la tabla de simbolos
            String nombre=raiz.valor;
            SimboloH s=tabla.getSimbolo(nombre);
            if(s!=null){
                return s.lista;
            }else{
                System.out.println("Error semantico,la lista no existe!!!");
            }
        }else{
            Lista l=concatenar(raiz.hijos.get(0));
            return operar(raiz.etiqueta,l);
        }
        
        //falta considerar los arreglos de 2 dimensiones
        //por el momento los arreglos de 2 dimensiones se concatenan linealmente
        if(raiz.etiqueta.equals("concatenar")){
            for(int i=0;i<lista2.valores.size();i++){
                lista1.indices.clear();
                lista1.indices.add(lista1.valores.size()+lista2.valores.size());
                lista1.valores.add(lista2.valores.get(i));
            }
            return lista1;
        }
        
        return null;
    }
}
