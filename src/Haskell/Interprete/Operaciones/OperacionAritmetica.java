/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.Operaciones;

import Graphik.Ast.Nodo;
import Haskell.Interprete.ResultadoH;
import Haskell.Interprete.SimboloH;
import Haskell.Interprete.TablaSimboloH;
import java.util.ArrayList;
import javax.swing.text.TabableView;

/**
 *
 * @author Jose2
 */
public class OperacionAritmetica {
   
    TablaSimboloH tabla;
    
    public OperacionAritmetica(TablaSimboloH tabla){
        this.tabla=tabla;
    }
    
    public ResultadoH resolver(Nodo raiz){
        ResultadoH resultado1=null;
        ResultadoH resultado2=null;
        if(raiz.valor!=null&&!(raiz.etiqueta.equals("acceso")||raiz.etiqueta.equals("id"))){
            return new ResultadoH(raiz.etiqueta,raiz.valor);
        }else if(raiz.hijos.size()==2){
            resultado1=resolver(raiz.hijos.get(0));
            resultado2=resolver(raiz.hijos.get(1));
        }else if(raiz.hijos.size()==1){
            if(raiz.etiqueta.equals("-")){
                resultado1=resolver(raiz.hijos.get(0));
                Double negativo=Double.parseDouble(resultado1.valor)*-1;
                return new ResultadoH(resultado1.tipo,negativo+"");
            }else if(raiz.etiqueta.equals("id")){
                
            }else if(raiz.etiqueta.equals("cadena")||raiz.etiqueta.equals("numero")){
                return  new ResultadoH(raiz.etiqueta,raiz.valor);
            }else if(raiz.etiqueta.equals("acceso")){
                String id=raiz.valor;
                SimboloH s=tabla.getSimbolo(id);
                if(s!=null){
                    Nodo valores=raiz.hijos.get(0);
                    ArrayList<Integer> index=new ArrayList<>();
                    for(Nodo nodo:valores.hijos){
                        index.add(Integer.parseInt(nodo.valor));
                    }
                    ResultadoH acceso=s.lista.getValor(index);
                    return acceso;
                }else{
                    System.out.println("Error semantico,la lista no existe");
                }
            }else{
                OperacionNativa opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(raiz);
                return new ResultadoH(r.tipo,r.valor);
            }
        }
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
            }
        }else{
            System.out.println("Error semantico,los tipos de datos son diferentes");
        }
        return new ResultadoH("-1","0");
    }
}
