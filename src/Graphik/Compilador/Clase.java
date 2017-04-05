/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador;

import Ast.Nodo;
import Graphik.Compilador.Sentencias.Declaracion;
import Interfaz.Inicio;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jose2
 */
public class Clase implements Cloneable {

    //encabezado
    //variables usadas en la compilacion
    public Stack<TablaSimboloG> pilaTablas;

    //--------------------------------------
    public String archivo;
    public TablaSimboloG global;
    public TablaSimboloG tabla;
    public String nombre;
    public String visibilidad;
    public Clase herencia;
    public String nombreHereda;
    public ArrayList<Metodo> metodos;
    public ArrayList<Nodo> atributos;

    public Clase() {
        global = new TablaSimboloG();
        tabla = new TablaSimboloG();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
    }

    public Clase(Nodo raiz) {

        global = new TablaSimboloG();
        tabla = new TablaSimboloG();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
        if (raiz.hijos.size() == 3) {//si contiene herencia
            this.nombre = raiz.valor;
            this.visibilidad = raiz.hijos.get(1).valor;
            this.metodos = getMetodos(raiz.hijos.get(2));
            this.atributos = getAtributos(raiz.hijos.get(2));
            this.nombreHereda = raiz.hijos.get(0).valor;
        } else {//si no contiene herencia
            this.nombre = raiz.valor;
            this.visibilidad = raiz.hijos.get(0).valor;
            this.metodos = getMetodos(raiz.hijos.get(1));
            this.atributos = getAtributos(raiz.hijos.get(1));
        }
    }

    private ArrayList<Metodo> getMetodos(Nodo raiz) {
        ArrayList<Metodo> metodos = new ArrayList<>();
        for (Nodo hijo : raiz.hijos) {
            if (hijo.etiqueta.equalsIgnoreCase("datos") || hijo.etiqueta.equalsIgnoreCase("inicio") || hijo.etiqueta.equalsIgnoreCase("metodo")) {
                Metodo metodo = new Metodo(hijo);
                if (!existeMetodo(metodo.id, hijo)) {
                    metodos.add(metodo);
                    SimboloG simbolo = new SimboloG(metodo.tipo, metodo.nombre, "metodo");
                    simbolo.rol = "Metodo";
                    simbolo.ambito = nombre;
                    Graphik.reporteSimbolos.add(simbolo);
                }
            }
        }
        return metodos;
    }

    private Boolean existeMetodo(String id, Nodo raiz) {
        for (Metodo metodo : metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + metodo.nombre + " ya existe en la clase " + nombre);
                return true;
            }
        }
        return false;
    }

    private ArrayList<Nodo> getAtributos(Nodo raiz) {
        ArrayList<Nodo> atributos = new ArrayList<>();
        for (Nodo hijo : raiz.hijos) {
            if (!(hijo.etiqueta.equalsIgnoreCase("datos") || hijo.etiqueta.equalsIgnoreCase("inicio") || hijo.etiqueta.equalsIgnoreCase("metodo"))) {
                atributos.add(hijo);
            }
        }
        return atributos;
    }

    public void ejecutar() {
        Compilador.pilaClases.push(Compilador.claseActual);
        Compilador.claseActual = this;
        for (Nodo atributo : atributos) {
            new Declaracion(atributo, global, tabla);
        }
        Compilador.claseActual = Compilador.pilaClases.pop();
    }

    public Clase clonar() {
        Clase instancia = new Clase();
        instancia.archivo = archivo;
        instancia.nombre = nombre;
        instancia.visibilidad = visibilidad;
        instancia.nombreHereda = nombreHereda;
        instancia.tabla = new TablaSimboloG();
        try {
            if (herencia != null) {
                instancia.herencia = (Clase) herencia.clone();
            }

        } catch (Exception e) {
            System.out.println("Error al clonar: " + e);
            return null;
        }
        instancia.atributos = (ArrayList<Nodo>) atributos.clone();
        instancia.metodos = (ArrayList<Metodo>) metodos.clone();
        return instancia;
    }
}
