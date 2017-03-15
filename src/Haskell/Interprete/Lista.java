/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Ast.Nodo;
import Haskell.Interprete.Operaciones.OperacionNativa;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Lista extends Nodo{
    
    public ArrayList<Integer> indices;
    public ArrayList valores;
    public String tipo;
    private OperacionNativa opN;
    private TablaSimboloH tabla;
    
    public Lista(){
        indices=new ArrayList();
        valores=new ArrayList();
    }
    public Lista(Nodo raiz,TablaSimboloH tabla){
        indices=new ArrayList<>();
        valores=new ArrayList<>();
        this.tabla=tabla;
        linealizar(raiz);
    }
    
    private void linealizar(Nodo raiz){
        if(raiz.etiqueta.equals("listaValores")){
            //si es de 2 dimensiones
            Nodo hijo1=raiz.hijos.get(0);
            Nodo hijo2=raiz.hijos.get(1);
            
            indices.add(hijo1.hijos.size());
            indices.add(hijo2.hijos.size());
            String tipo1="-1";
            String tipo2="-2";
            for(Nodo hijo:hijo1.hijos){
                opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(hijo);
                tipo1=r.tipo;
                if(tipo1.equals("numero")){
                    tipo="numero";
                    Double d=Double.parseDouble(r.valor);
                    valores.add(d);
                }else{
                    tipo="cadena";
                    String val=r.valor;
                    val=val.replace("'","");
                    valores.add(val);
                }
            }
            for(Nodo hijo:hijo2.hijos){
                opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(hijo);
                tipo2=r.tipo;
                if(tipo1.equals("numero")){
                    tipo="numero";
                    Double d=Double.parseDouble(r.valor);
                    valores.add(d);
                }else{
                    tipo="cadena";
                    String val=r.valor;
                    val=val.replace("'","");
                    valores.add(val);
                }
            }
           // if(tipo1.equals(tipo2)){
          //      tipo=tipo1;
           // }
            
        }else if(raiz.etiqueta.equals("valores")){
            //si es de 1 dimension
            indices.add(raiz.hijos.size());
            for(Nodo hijo:raiz.hijos){
                opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(hijo);
                tipo=r.tipo;
                if(tipo.equals("numero")){
                    Double d=Double.parseDouble(r.valor);
                    valores.add(d);
                }else{
                    String val=r.valor;
                    tipo="cadena";
                    val=val.replace("'","");
                    valores.add(val);
                }
            }
        }else if(raiz.etiqueta.equals("cadena")){
            String cad=raiz.valor;
            tipo="cadena";
            valor=raiz.valor;
            cad=cad.replace("\"","");
            indices.add(cad.length());
            for(int i=0;i<cad.length();i++){
                char c=cad.charAt(i);
                valores.add(c+"");
            }
        }else{
            String cad=raiz.valor;
            tipo="cadena";
            valor=raiz.valor;
            cad=cad.replace("'","");
            indices.add(cad.length());
            for(int i=0;i<cad.length();i++){
                char c=cad.charAt(i);
                valores.add(c+"");
            }
        }
    }
    
    public String getString(){
        String valor="";
        if(valores.isEmpty()){
            if(indices.size()==1){
                return "[]";
            }else{
                return "[[],[]]";
            }
        }
        if(tipo.equals("numero")||tipo.equals("caracter")){
            if(indices.size()==1){
                valor+="[";
                for(int i=0;i<valores.size();i++){
                    if((valores.size()-1)==i){
                        valor+=valores.get(i);
                    }else{
                        valor+=valores.get(i)+",";
                    }
                }
                valor+="]";
            }else{
                valor=valor+"[[";
                int j=0;
                for(j=0;j<indices.get(0);j++){
                    if((valores.size()-1)==j){
                        valor+=valores.get(j);
                    }else{
                        valor+=valores.get(j)+",";
                    }
                }
                valor+="],[";
                for(int i=j;i<valores.size();i++){
                    if((valores.size()-1)==i){
                        valor+=valores.get(i);
                    }else{
                        valor+=valores.get(i)+",";
                    }
                }
                valor+="]]";
            }
        }else if(tipo.equals("cadena")){
            //valor+="\"";
            for(Object val:valores){
                valor+=val;
            }
            //valor+="\"";
        }
        return valor;
    }
    
    public ResultadoH getValor(ArrayList<Integer> index){
        
        int indice=index.get(0);
        indice=indice+1;
        for(int i=1;i<index.size();i++){
            int n=indices.get(i);
            int j=index.get(i)+1;
            //indice=indice+1;
            indice=(indice-1)*n+j;
        }
        //if(indices.size()>1){
            indice=indice-1;
        //}
        if(valores.size()>=indice){
            return new ResultadoH(tipo,valores.get(indice)+"");
        }else{
            return null;
        }
       
    }
}
