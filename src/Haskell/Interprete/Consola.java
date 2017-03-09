/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Graphik.Ast.Nodo;
import Haskell.Interprete.Operaciones.OperacionNativa;
import static Interfaz.Inicio.raiz;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Jose2
 */
public class Consola {
   
    private TablaSimboloH tabla;
    private final JTextArea txtConsola;
    private OperacionNativa opN;
    public Consola(JTextArea txtConsola){
        tabla=new TablaSimboloH();
        opN=new OperacionNativa(tabla);
        this.txtConsola=txtConsola;
    }
    
    
    public Lista ejecutar(Nodo raiz){
        switch(raiz.etiqueta){
            case "declaracionLista":
                declaracion(raiz);
                break; 
            case "acceso":
                String id=raiz.valor;
                SimboloH s=tabla.getSimbolo(id);
                if(s!=null){
                    Nodo valores=raiz.hijos.get(0);
                    ArrayList<Integer> index=new ArrayList<>();
                    for(Nodo nodo:valores.hijos){
                        index.add(Integer.parseInt(nodo.valor));
                    }
                    ResultadoH acceso=s.lista.getValor(index);
                    txtConsola.append("\n");
                    txtConsola.append(">"+acceso.valor);
                }else{
                    System.out.println("Error semantico,la lista no existe");
                }
                break;
            default:             
                ResultadoH r=opN.imprimirConsola(raiz);
                txtConsola.append("\n");
                txtConsola.append(">"+r.valor);
        }
        return null;
    }
    
    private void declaracion(Nodo raiz){
        Lista lista=opN.concatenar(raiz.hijos.get(0));
        String nombre=raiz.valor;
        SimboloH simbolo=new SimboloH(nombre,lista);
        if(!tabla.setSimbolo(simbolo)){
           System.out.println("Error semantico,El simbolo ya existe!!!");
        }
    }
    
}
