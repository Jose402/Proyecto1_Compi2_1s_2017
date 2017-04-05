/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Grafica;

import java.util.ArrayList;

/**
 *
 * @author Jose2
 */
public class Coordenadas {

    private ArrayList<ArrayList<Object>> x;
    private ArrayList<ArrayList<Object>> y;

    public Coordenadas() {
        x = new ArrayList<>();
        y = new ArrayList<>();
    }

    public void setCoordenada(ArrayList<Object> x, ArrayList<Object> y) {
        this.x.add(x);
        this.y.add(y);
    }

    public ArrayList<Object> getX(int indice) {
        return x.get(indice);
    }

    public ArrayList<Object> getY(int indice) {
        return y.get(indice);
    }

    public boolean vacio() {
        if (x.size() > 0 && y.size() > 0) {
            return false;
        }
        return true;
    }

    public int size() {
        return x.size();
    }

}
