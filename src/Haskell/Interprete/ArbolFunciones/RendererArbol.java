/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell.Interprete.ArbolFunciones;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Jose2
 */
public class RendererArbol extends DefaultTreeCellRenderer {

    /**
     * Creates a new instance of RendererArbol
     */
    ImageIcon sentencia;
    ImageIcon funcion;

    public RendererArbol() {

        funcion = new ImageIcon("src/Media/haskell.png");
        sentencia = new ImageIcon("src/Media/ejecutar1.png");

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        String val = value.toString();

        ImageIcon i;

        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;

        //  if(val.compareTo("Conectados")!=0 && val.compareTo("No Conectados")!=0 && val.compareTo("Contactos")!=0){   
        //  TreeNode t = nodo.getParent();
        // if(t!=null){
        //  if(t.toString().compareTo("Conectados")==0){
        //setIcon(conectados);
        setLeafIcon(funcion);
        //setOpenIcon(funcion);
        //setClosedIcon(funcion);
        // }else if(t.toString().compareTo("No Conectados")==0){

        // setIcon(noConectados);
        // }
        // }
        //}                           
        return this;

    }

}
