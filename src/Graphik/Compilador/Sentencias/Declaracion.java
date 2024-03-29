/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Sentencias;

import Ast.Nodo;
import Graphik.Compilador.Archivo;
import Graphik.Compilador.Arreglo;
import Graphik.Compilador.Clase;
import Graphik.Compilador.Compilador;
import Graphik.Compilador.Graphik;
import Graphik.Compilador.Heredar;
import Graphik.Compilador.Metodo;
import Graphik.Compilador.Operaciones.OperacionLogicaG;
import Graphik.Compilador.ResultadoG;
import Graphik.Compilador.SimboloG;
import Graphik.Compilador.TablaSimboloG;
import Interfaz.Inicio;
import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Declaracion extends Compilador {

    TablaSimboloG tabla;
    TablaSimboloG global;
    private ArrayList<Archivo> archivos = Graphik.archivos;

    public Declaracion(Nodo raiz, TablaSimboloG global) {
        this.raiz = raiz;
        this.tabla = global;//tabla de atributos
        this.global = new TablaSimboloG();//tabla de variables locales
        opL = new OperacionLogicaG(global, global);
        declarar();
    }

    public Declaracion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla) {
        this.raiz = raiz;
        this.global = global;//tabla de variables locales
        this.tabla = tabla;//tabla de variables globales
        opL = new OperacionLogicaG(global, tabla);
        declarar();
    }

    public Declaracion(Nodo raiz, TablaSimboloG global, TablaSimboloG tabla, String tipo) {
        this.raiz = raiz;
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionLogicaG(global, tabla);
    }

    public Declaracion(Nodo raiz, ResultadoG resultado, TablaSimboloG global, TablaSimboloG tabla) {
        this.raiz = raiz;
        this.global = global;
        this.tabla = tabla;
        declararParametro(resultado);
    }

    private SimboloG declararParametro(ResultadoG resultado) {

        switch (raiz.etiqueta) {
            case "parametro":
                return parametro(resultado);
            case "parametroAr":
                return parametroAr(resultado);
        }

        return null;
    }

    public Object declarar() {
        switch (raiz.etiqueta) {
            case "atributoVarDA"://atributo tipo entero,cadena... declarado y asignado
                return atributoVarDA();
            case "atributoVarD"://atributo tipo entero,cadena... declarado
                return atributoVarD();
            case "atributoAlsDI"://atributo tipo Als declarado e instanciado
                return atributoAlsDI();
            case "atributoAlsD"://atributo tipo Als declarado
                return atributoAlsD();
            case "atributoVarArDA"://atributo arreglo entero,cadena.. declarado y asignado
                return atributoVarArDA();
            case "atributoVarArD"://atributo arreglo entero,cadena.. declarado
                return atributoVarArD();
            case "varLocalD"://variable local tipo,entero,cadena... declarada
                return varLocalD();
            case "varLocalDA"://variable local tipo,entero,cadena... declarada y asignada
                return varLocalDA();
            case "varLocalAlsD"://variable local Als... declarada
                return varLocalAlsD();
            case "varLocalAlsDI"://variable local Als... declarada e instanciada
                return varLocalAlsDI();
            case "varLocalArD"://variable arreglo local tipo cadena,numero... declarada
                return varLocalArD();
            case "varLocalArDA"://variable arreglo local tipo cadena,numero... declarada y asignada
                return varLocalArDA();

            //direccionamientos
            case "atributoVarArDD"://atributo arreglo direccionado
                return atributoVarArDD();
            case "atributoAlsDD"://atributo als direccionado
                return atributoAlsDD();
            case "varLocalAlsDD"://variable local Als... direccionada
                return varLocalAlsDD();
            case "varLocalArDD"://variable arreglo local tipo cadena,numero... direccionada
                return varLocalArDD();
        }

        return null;
    }

    private SimboloG atributoVarDA() {
        String tipo = raiz.hijos.get(0).valor;
        String nombre = raiz.hijos.get(1).valor;
        String visibilidad = raiz.hijos.get(1).hijos.get(0).valor;
        Nodo exp = raiz.hijos.get(2);

        ResultadoG resultado = opL.operar(exp);
        Casteo casteo = new Casteo();
        resultado = casteo.castear(raiz, tipo, resultado);
        if (resultado != null) {
            SimboloG simbolo = new SimboloG(tipo, nombre, visibilidad, resultado.valor);
            simbolo.inicializado = true;
            if (!global.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
        return null;
    }

    private SimboloG atributoVarD() {
        String tipo = raiz.hijos.get(0).valor;
        for (Nodo variable : raiz.hijos.get(1).hijos) {
            String nombre = variable.valor;
            String visibilidad = variable.hijos.get(0).valor;
            SimboloG simbolo = new SimboloG(tipo, nombre, visibilidad, null);
            if (!global.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
        return null;
    }

    private SimboloG atributoAlsDI() {
        String tipo1 = raiz.hijos.get(0).valor;
        String nombre = raiz.hijos.get(1).valor;
        String visibilidad = raiz.hijos.get(1).hijos.get(0).valor;
        String tipo2 = raiz.hijos.get(2).valor;

        if (tipo1.equalsIgnoreCase(tipo2)) {
            Clase instancia = getClase(Graphik.claseActual, tipo1);
            if (instancia != null) {
                SimboloG simbolo = new SimboloG(tipo1, nombre, visibilidad, instancia);
                simbolo.inicializado = true;
                if (!global.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
                Heredar hereda = new Heredar(instancia);
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No existe la clase " + nombre + " o no es de tipo " + tipo1);
            }

        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto no es igual a el tipo de su instancia");
        }
        return null;
    }

    private SimboloG atributoAlsD() {
        String tipo = raiz.hijos.get(0).valor;
        ArrayList<Nodo> listaIds = raiz.hijos.get(1).hijos;
        Clase instancia = getClase(Graphik.claseActual, tipo);
        if (instancia != null) {
            for (Nodo variable : listaIds) {
                String nombre = variable.valor;
                String visibilidad = variable.hijos.get(0).valor;
                SimboloG simbolo = new SimboloG(tipo, nombre, visibilidad, instancia);
                if (!global.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            }

        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto con es igual a el tipo de su instancia");
        }

        return null;
    }

    private boolean existeClase(ArrayList<Clase> clases, String tipo) {
        for (Clase clase : clases) {
            if (clase.nombre.equalsIgnoreCase(tipo)) {
                return true;
            }
        }
        return false;
    }

    private SimboloG atributoVarArDA() {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        String visibilidad = raiz.hijos.get(2).valor;
        Arreglo arreglo = new Arreglo(raiz, tabla, global);
        if (arreglo.estado) {
            SimboloG simbolo = new SimboloG(tipo, nombre, visibilidad, arreglo);
            simbolo.inicializado = true;
            if (!global.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
        return null;
    }

    private SimboloG atributoVarArD() {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        String visibilidad = raiz.hijos.get(2).valor;
        Arreglo arreglo = new Arreglo(raiz, tabla, global, 0);
        SimboloG simbolo = new SimboloG(tipo, nombre, visibilidad, arreglo);
        simbolo.inicializado = true;
        if (!global.setSimbolo(simbolo)) {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
        }
        return null;
    }

    private SimboloG atributoVarArDD() {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        String visibilidad = raiz.hijos.get(2).valor;
        Nodo exp = raiz.hijos.get(3);
        ResultadoG resultado = opL.operar(exp);
        if (resultado.valor != null) {
            if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                SimboloG simbolo = new SimboloG(tipo, nombre, visibilidad, resultado.valor);
                simbolo.inicializado = true;
                if (!global.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo de dato de la asignacion no es igual al de la variable declarada");
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo de dato de la asignacion no es igual al de la variable declarada");
        }
        return null;
    }

    private SimboloG atributoAlsDD() {
        String tipo1 = raiz.hijos.get(0).valor;
        String nombre = raiz.hijos.get(1).valor;
        String visibilidad = raiz.hijos.get(1).hijos.get(0).valor;
        Nodo exp = raiz.hijos.get(2);
        ResultadoG resultado = opL.operar(exp);
        if (resultado.valor != null) {

            if (!resultado.tipo.equals("numero") && !resultado.tipo.equals("cadeba") && !resultado.tipo.equals("decimal") && !resultado.tipo.equals("caracter") && !resultado.tipo.equals("bool")) {
                SimboloG simbolo = new SimboloG(tipo1, nombre, visibilidad, resultado.valor);
                simbolo.inicializado = true;
                if (!global.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto no es igual al de su asignacion");
            }

        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto no es igual al de su asignacion");
        }
        return null;
    }

    private SimboloG varLocalD() {
        String tipo = raiz.hijos.get(0).valor;
        for (Nodo variable : raiz.hijos.get(1).hijos) {
            String nombre = variable.valor;
            SimboloG simbolo = new SimboloG(tipo, nombre, null);
            if (!tabla.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
        return null;
    }

    private SimboloG varLocalDA() {
        String tipo = raiz.hijos.get(0).valor;
        String nombre = raiz.hijos.get(1).valor;
        Nodo exp = raiz.hijos.get(2);
        ResultadoG resultado = opL.operar(exp);
        Casteo casteo = new Casteo();
        resultado = casteo.castear(raiz, tipo, resultado);

        if (resultado != null) {
            SimboloG simbolo = new SimboloG(tipo, nombre, resultado.valor);
            simbolo.inicializado = true;
            if (!tabla.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            } else {
                return simbolo;
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "Tipo de dato incorrecto para la variable " + nombre);
        }
        return null;
    }

    private SimboloG varLocalAlsD() {
        String tipo = raiz.hijos.get(0).valor;
        ArrayList<Nodo> listaIds = raiz.hijos.get(1).hijos;
        Clase instancia = getClase(Graphik.claseActual, tipo);
        if (instancia != null) {
            for (Nodo variable : listaIds) {
                String nombre = variable.valor;
                SimboloG simbolo = new SimboloG(tipo, nombre, instancia);
                if (!tabla.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            }

        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto con es igual a el tipo de su instancia");
        }
        return null;
    }

    private SimboloG varLocalAlsDI() {
        String tipo1 = raiz.hijos.get(0).valor;
        String nombre = raiz.hijos.get(1).valor;
        String tipo2 = raiz.hijos.get(2).valor;

        if (tipo1.equalsIgnoreCase(tipo2)) {
            Clase instancia = getClase(Graphik.claseActual, tipo1);
            if (instancia != null) {
                SimboloG simbolo = new SimboloG(tipo1, nombre, instancia);
                simbolo.inicializado = true;
                if (!tabla.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
                Heredar hereda = new Heredar(instancia);
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No existe la clase " + nombre + " o no es de tipo " + tipo1);
            }

        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto no es igual a el tipo de su instancia");
        }
        return null;
    }

    private SimboloG varLocalArD() {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        Arreglo arreglo = new Arreglo(raiz, tabla, global, 0);
        SimboloG simbolo = new SimboloG(tipo, nombre, arreglo);
        simbolo.inicializado = true;
        if (!tabla.setSimbolo(simbolo)) {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
        }
        return null;
    }

    private SimboloG varLocalArDA() {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        Arreglo arreglo = new Arreglo(raiz, tabla, global);
        if (arreglo.estado) {
            SimboloG simbolo = new SimboloG(tipo, nombre, arreglo);
            simbolo.inicializado = true;
            if (!tabla.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
        return null;
    }

    private SimboloG varLocalAlsDD() {
        String tipo1 = raiz.hijos.get(0).valor;
        String nombre = raiz.hijos.get(1).valor;
        Nodo exp = raiz.hijos.get(2);
        ResultadoG resultado = opL.operar(exp);
        if (resultado.valor != null) {

            if (!resultado.tipo.equals("numero") && !resultado.tipo.equals("cadeba") && !resultado.tipo.equals("decimal") && !resultado.tipo.equals("caracter") && !resultado.tipo.equals("bool")) {
                SimboloG simbolo = new SimboloG(tipo1, nombre, resultado.valor);
                simbolo.inicializado = true;
                if (!tabla.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto no es igual al de su asignacion");
            }

        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo del objeto no es igual al de su asignacion");
        }
        return null;
    }

    private SimboloG varLocalArDD() {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        Nodo exp = raiz.hijos.get(2);
        ResultadoG resultado = opL.operar(exp);
        if (resultado.valor != null) {
            if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                SimboloG simbolo = new SimboloG(tipo, nombre, resultado.valor);
                simbolo.inicializado = true;
                if (!tabla.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo de dato de la asignacion no es igual al de la variable declarada");
            }
        } else {
            Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "El tipo de dato de la asignacion no es igual al de la variable declarada");
        }
        return null;
    }

    private SimboloG parametro(ResultadoG resultado) {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        if (resultado != null) {
            SimboloG simbolo = new SimboloG(tipo, nombre, resultado.valor);
            simbolo.inicializado = true;
            if (!tabla.setSimbolo(simbolo)) {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        } else {

        }
        return null;
    }

    private SimboloG parametroAr(ResultadoG resultado) {
        String nombre = raiz.valor;
        String tipo = raiz.hijos.get(0).valor;
        int dimension = raiz.hijos.get(1).hijos.size();

        if (resultado != null) {
            Arreglo arreglo = (Arreglo) resultado.valor;
            if (arreglo.dimensiones.size() == dimension) {
                SimboloG simbolo = new SimboloG(tipo, nombre, arreglo);
                simbolo.inicializado = true;
                simbolo.esArreglo = true;
                if (!tabla.setSimbolo(simbolo)) {
                    Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
                }
            } else {
                Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "La dimension de los arreglos no son iguales");
            }

        } else {

        }
        return null;
    }

    @Override
    public Metodo ejecutar(Nodo raiz) {
        return null;
    }

    private Clase getClase(Clase clase, String tipo) {

        Archivo archivo = getArchivo(clase.archivo);
        Archivo aux = archivo;
        ArrayList<Clase> clases = archivo.clases;
        for (Clase hereda : clases) {
            if (hereda.nombre.equalsIgnoreCase(tipo)) {
                return hereda.clonar();
            }
        }

        for (int i = 0; i < archivos.size(); i++) {
            archivo = archivos.get(i);
            if (archivo != null) {
                clases = archivo.clases;
                for (Clase hereda : clases) {
                    if (hereda.nombre.equalsIgnoreCase(tipo) && hereda.visibilidad.equalsIgnoreCase("publico") && aux.archivosImportados.contains(archivo.nombre)) {
                        return hereda.clonar();
                    }
                }
            }
        }
        return null;
    }

    private Archivo getArchivo(String nombre) {
        for (Archivo archivo : archivos) {
            if (archivo.nombre.equalsIgnoreCase(nombre)) {
                return archivo;
            }
        }
        return null;
    }

}
