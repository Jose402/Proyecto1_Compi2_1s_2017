/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Arreglo;
import Graphik.Compilador.Clase;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class LlamadaMetodo extends Compilador {

    private Clase actual;
    private int nivel = 0;

    public LlamadaMetodo(Clase actual) {
        this.actual = actual;
    }

    public LlamadaMetodo(Clase actual, int nivel) {
        this.actual = actual;
        this.nivel = nivel;
    }

    @Override
    public Metodo ejecutar(Nodo raiz) {
        String nombre = raiz.valor;
        ArrayList<ResultadoG> parametros = getParametros(raiz);
        String id = getId(nombre, parametros);
        Metodo metodoTemp = getMetodo(id);
        if (metodoTemp != null) {
            pilaNivelCiclo.push(nivelCiclo);
            nivelCiclo = 0;
            //actual.tabla=new TablaSimboloG();
            pilaTablas.push(tabla);
            TablaSimboloG tablaTemp = new TablaSimboloG();
            tabla = tablaTemp;
            for (int i = 0; i < metodoTemp.parametros.size(); i++) {
                Nodo parametro = metodoTemp.parametros.get(i);
                ResultadoG valor = parametros.get(i);
                new Declaracion(parametro, valor, actual.global, tabla);
            }
            pilaMetodos.push(metodoActual);
            metodoActual = metodoTemp;

            global = actual.global;
            pilaClases.push(claseActual);
            claseActual = actual;

            metodoTemp = ejecutarSentencias(metodoActual.sentencias);

            metodoActual = pilaMetodos.pop();
            claseActual = pilaClases.pop();
            global = claseActual.global;
            tabla = pilaTablas.pop();
            nivelCiclo = pilaNivelCiclo.pop();
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + nombre + " no existe en el ambito donde fue invocado");
        }
        return metodoTemp;
    }

    /*
    @Override
    public Metodo ejecutar(Nodo raiz) {
        String nombre=raiz.valor;
        ArrayList<ResultadoG> parametros=getParametros(raiz);
        String id=getId(nombre,parametros);
        Metodo metodoTemp=getMetodo(id);
        if(metodoTemp!=null){
            //actual.tabla=new TablaSimboloG();
            pilaTablas.push(tabla);
            TablaSimboloG tablaTemp=new TablaSimboloG();
            tabla=tablaTemp;
            for(int i=0;i<metodoTemp.parametros.size();i++){
                Nodo parametro=metodoTemp.parametros.get(i);
                ResultadoG valor=parametros.get(i);
                new Declaracion(parametro, valor,actual.global,actual.tabla);
            }
            pilaMetodos.push(metodoActual);
            metodoActual=metodoTemp;
            
            tabla=actual.tabla;
            global=actual.global;
            pilaClases.push(claseActual);
            claseActual=actual;

            metodoTemp=ejecutarSentencias(metodoActual.sentencias);
            
            claseActual.tabla.tabla.clear();
            metodoActual=pilaMetodos.pop();
            claseActual=pilaClases.pop();
            global=claseActual.global;
            tabla=pilaTablas.pop();
        }else{
            Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"El metodo "+nombre+" no existe en el ambito donde fue invocado");
        }
        return metodoTemp;
    }*/
    private String getId(String nombre, ArrayList<ResultadoG> parametros) {
        for (ResultadoG resultado : parametros) {
            if (resultado.valor != null) {
                if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                    Arreglo arr = (Arreglo) resultado.valor;
                    nombre += resultado.tipo + arr.dimensiones.size();
                } else {
                    nombre += resultado.tipo;
                }
            }
        }
        return nombre;
    }

    private ArrayList<ResultadoG> getParametros(Nodo raiz) {
        ArrayList<ResultadoG> parametros = new ArrayList<>();
        Nodo nodoParametros = raiz.hijos.get(0);
        for (Nodo hijo : nodoParametros.hijos) {
            opL = new OperacionLogicaG(global, tabla);
            ResultadoG resultado = opL.operar(hijo);
            parametros.add(resultado);
        }
        return parametros;
    }

    private Metodo getMetodo(String id) {
        Metodo metodo = buscarMetodo(id, actual);
        if (metodo == null) {
            if (actual.herencia != null) {
                actual = actual.herencia;
                metodo = getMetodo(id);
            }
        }
        if (metodo != null) {
            if (nivel > 0 && (metodo.visibilidad.equalsIgnoreCase("privado") || metodo.visibilidad.equalsIgnoreCase("protegido"))) {
                metodo = null;
            } else {
                return metodo;
            }
        }
        return metodo;
    }

    private Metodo buscarMetodo(String id, Clase actual) {
        for (Metodo metodo : actual.metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                return metodo;
            }
        }
        return null;
    }

}
