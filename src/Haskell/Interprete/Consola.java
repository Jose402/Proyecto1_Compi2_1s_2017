/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete;

import Ast.Nodo;
import Haskell.Interprete.Operaciones.OperacionNativa;
import Haskell.Interprete.Sentencias.Declaracion;
import Interfaz.Inicio;
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

    public Consola(JTextArea txtConsola) {
        tabla = new TablaSimboloH();
        this.txtConsola = txtConsola;
    }

    public Lista ejecutar(Nodo raiz) {
        switch (raiz.etiqueta) {
            case "declaracionLista":
                Declaracion declara = new Declaracion(tabla);
                SimboloH sim = declara.declarar(raiz);
                if (sim != null) {
                    txtConsola.append("\n");
                    txtConsola.append(">" + sim.lista.getString());
                } else {
                    txtConsola.append("\n");
                    txtConsola.append("Ocurrio un error al momento de guardar la listas");
                }
                break;
            case "llamada":
                if (Inicio.interprete != null) {
                    FuncionH funcionActual = Inicio.interprete.llamada(raiz, tabla);
                    txtConsola.append("\n");
                    txtConsola.append(">" + funcionActual.retorno.valor);
                } else {
                    txtConsola.append("\n");
                    txtConsola.append(">No hay funciones Haskell++ cargadas");
                }
                break;
            case "acceso":
                SimboloH s = null;
                if (raiz.hijos.get(0).etiqueta.equals("id")) {
                    String id = raiz.hijos.get(0).valor;
                    s = tabla.getSimbolo(id);
                } else {
                    opN = new OperacionNativa(tabla);
                    Lista lista = opN.operacionLista(raiz.hijos.get(0));
                    s = new SimboloH("", lista);
                }

                if (s != null) {
                    Nodo valores = raiz.hijos.get(1);
                    ArrayList<Integer> index = new ArrayList<>();
                    for (Nodo nodo : valores.hijos) {
                        opN = new OperacionNativa(tabla);
                        ResultadoH r = opN.operar(nodo);
                        if (r != null) {
                            Double d = Double.parseDouble(r.valor);
                            index.add(d.intValue());
                        } else {
                            txtConsola.append("\n");
                            txtConsola.append("Indice incorrecto");
                        }
                    }
                    ResultadoH acceso = s.lista.getValor(index);
                    if (acceso != null) {
                        txtConsola.append("\n");
                        txtConsola.append(">" + acceso.valor);
                    } else {
                        txtConsola.append("\n");
                        txtConsola.append("Ocurrio un error al momento de acceder a la lista");
                    }
                } else {

                    txtConsola.append("\n");
                    txtConsola.append("Error semantico, la lista no existe");
                }
                break;
            default:
                opN = new OperacionNativa(tabla);
                ResultadoH r = opN.imprimirConsola(raiz);
                if (r != null) {
                    txtConsola.append("\n");
                    txtConsola.append(">" + r.valor);
                } else {
                    txtConsola.append("\n");
                    txtConsola.append("Ocurio un error al operar");
                }
        }
        return null;
    }

}
