/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Ast.Nodo;
import Haskell.Interprete.Operaciones.*;
import Haskell.Interprete.Sentencias.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Interprete {
    
    private TablaSimboloH tabla;
    private ArrayList<FuncionH> funciones;
    private Stack<FuncionH> pilaFunciones;
    private Stack<TablaSimboloH> pilaTablas;
    private FuncionH funcionActual;
    private OperacionNativa opN;
    private OperacionLogica opL;
    public Interprete(Nodo raiz){
        funciones=new ArrayList<>();
        tabla=new TablaSimboloH();
        pilaFunciones=new Stack<>();
        pilaTablas=new Stack<>();
        guardarMetodos(raiz);
    }
    
    private void guardarMetodos(Nodo raiz){
        for(Nodo hijo:raiz.hijos){
            FuncionH funcion=new FuncionH(hijo.valor,hijo);
            funciones.add(funcion);
        }
    }
    
    public Boolean existeFuncion(String id){       
        for(FuncionH funcion:funciones){
            if(id.equalsIgnoreCase(funcion.id)){
                return true;
            }
        }   
        return false;
    }
    
    private FuncionH buscarFuncion(String id){
        for(FuncionH funcion:funciones){
            if(id.equalsIgnoreCase(funcion.id)){
                return funcion;
            }
        }
        return null;
    }
    
    public ResultadoH ejecutar(String id,ArrayList<ResultadoH> valorParametros){
        funcionActual=buscarFuncion(id);
        Nodo parametros=funcionActual.raiz.hijos.get(0);
        Nodo sentencias=funcionActual.raiz.hijos.get(1);
        guardarParametros(parametros, valorParametros);
        FuncionH funcion=ejecutar(sentencias);
        return funcion.retorno;
    }
    
    private void guardarParametros(Nodo parametros,ArrayList<ResultadoH> valorParametros){
        for(int i=0;i<parametros.hijos.size();i++){
            Nodo parametro=parametros.hijos.get(i);
            ResultadoH valor=valorParametros.get(i);
            new Declaracion(parametro.valor,valor,tabla);
        }
    }
    
    private FuncionH ejecutar(Nodo sentencias){
        ResultadoH resultado=null;
        for(Nodo sentencia:sentencias.hijos){
            switch(sentencia.etiqueta){
                case "declaracionLista":
                    new Declaracion(sentencia, tabla);
                    break;
                case "acceso":
                    SimboloH s=null;
                    if(sentencia.hijos.get(0).etiqueta.equals("id")){
                        String id=sentencia.hijos.get(0).valor;
                        s=tabla.getSimbolo(id);
                    }else{
                        opN=new OperacionNativa(tabla);
                        Lista lista=opN.operacionLista(sentencia.hijos.get(0));
                        s=new SimboloH("", lista);
                    }
                    if(s!=null){
                        Nodo valores=sentencia.hijos.get(1);
                        ArrayList<Integer> index=new ArrayList<>();
                        for(Nodo nodo:valores.hijos){
                            opN=new OperacionNativa(tabla);
                            ResultadoH r=opN.operar(nodo);
                            Double d=Double.parseDouble(r.valor);
                            index.add(d.intValue());
                        }
                        ResultadoH acceso=s.lista.getValor(index);
                        funcionActual.retorno=acceso;
                    }else{
                        System.out.println("Error semantico,la lista no existe");
                    }
                    break;
                case "if":
                    funcionActual=sentenciaIf(sentencia);
                    break;
                case "switch":
                    funcionActual=sentenciaSwitch(sentencia);
                    break;
                case "llamada":
                    funcionActual=llamada(sentencia);
                    break;
                case "calcular"://returnan valores puntuales
                case "succ":
                case "decc":
                case "min":
                case "max":
                case "product":
                case "sum":
                case "length":
                    opN=new OperacionNativa(tabla);
                    resultado=opN.operar(sentencia);
                    funcionActual.retorno=resultado;
                    break;
                case "concatenar"://retornan valores tipo lista
                case "revers":
                case "impr":
                case "par":
                case "asc":
                case "desc":
                    opN=new OperacionNativa(tabla);
                    Lista lista=opN.operacionLista(sentencia);
                    resultado=new ResultadoH(lista.tipo, lista);
                    funcionActual.retorno=resultado;
                    break;
            }
        }
        return funcionActual;
    }
    
    public FuncionH llamada(Nodo raiz){
        String id=raiz.valor;
        TablaSimboloH tablaTemp=new TablaSimboloH();
            
        FuncionH funcionTemp=buscarFuncion(id);
        ArrayList<ResultadoH> valores=new ArrayList<>();
        for(Nodo nodo:raiz.hijos.get(0).hijos){
            if(nodo.etiqueta.equals("valores")||nodo.etiqueta.equals("listaValores")||nodo.etiqueta.equals("cadena")){
                Lista lista=new Lista(nodo, tabla);
                ResultadoH r=new ResultadoH(lista.tipo, lista);
                valores.add(r);
            }else{
                opN=new OperacionNativa(tabla);
                ResultadoH r=opN.operar(nodo);
                valores.add(r);
            }
        }
        pilaTablas.push(tabla);
        tabla=tablaTemp;
        guardarParametros(funcionTemp.raiz.hijos.get(0),valores);
        
        pilaFunciones.push(funcionActual);
        funcionActual=funcionTemp;
        
        funcionTemp=ejecutar(funcionTemp.raiz.hijos.get(1));
        
        tabla=pilaTablas.pop();
        funcionActual=pilaFunciones.pop();
        return funcionTemp;
    }
    
    private FuncionH sentenciaIf(Nodo raiz){
        //cambio de ambito
        TablaSimboloH tablaTemp=new TablaSimboloH();
        tablaTemp.cambiarAmbito(tabla);
        pilaTablas.push(tabla);
        tabla=tablaTemp;
        Nodo cond=raiz.hijos.get(0).hijos.get(0);
        opL=new OperacionLogica(tabla);
        ResultadoH condicion=opL.resolverLogica(cond);
        
        if(condicion.valor.equals("true")){
            funcionActual=ejecutar(raiz.hijos.get(1));
        }else{
            funcionActual=ejecutar(raiz.hijos.get(2));
        }
        
        //regreso al ambito
        tabla=pilaTablas.pop();
        return funcionActual;
    }
    
    private FuncionH sentenciaSwitch(Nodo raiz){
                //cambio de ambito
        
        
        Nodo exp=raiz.hijos.get(0);;
        Nodo casos=raiz.hijos.get(1);
            
        for(Nodo caso:casos.hijos){
            Nodo val=caso.hijos.get(0);
            Nodo sentencias=caso.hijos.get(1);
            Nodo expCond=new Nodo("==",exp.linea-1,exp.columna-1);
            expCond.add(exp);
            expCond.add(val);
            opL=new OperacionLogica(tabla);
            ResultadoH resultado=opL.resolverLogica(expCond);
            TablaSimboloH tablaTemp=new TablaSimboloH();
            tablaTemp.cambiarAmbito(tabla);
            pilaTablas.push(tabla);
            tabla=tablaTemp;
            if(resultado.valor.equals("true")){
                funcionActual=ejecutar(sentencias);
                break;
            }
        }
        
        //regreso al ambito
        tabla=pilaTablas.pop();
        return funcionActual;
    }
}
