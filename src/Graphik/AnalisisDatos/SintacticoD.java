//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Sat Mar 25 15:17:37 GMT-12:00 2017
//----------------------------------------------------
package Graphik.AnalisisDatos;

import Graphik.Datos.*;
import Ast.*;
import Interfaz.Inicio;
import java_cup.runtime.*;

/**
 * CUP v0.11a beta 20060608 generated parser.
 *
 * @version Sat Mar 25 15:17:37 GMT-12:00 2017
 */
public class SintacticoD extends java_cup.runtime.lr_parser {

    /**
     * Default constructor.
     */
    public SintacticoD() {
        super();
    }

    /**
     * Constructor which sets the default scanner.
     */
    public SintacticoD(java_cup.runtime.Scanner s) {
        super(s);
    }

    /**
     * Constructor which sets the default scanner.
     */
    public SintacticoD(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {
        super(s, sf);
    }

    /**
     * Production table.
     */
    protected static final short _production_table[][]
            = unpackFromStrings(new String[]{
        "\000\020\000\002\002\004\000\002\002\006\000\002\007"
        + "\007\000\002\007\005\000\002\011\002\000\002\004\006"
        + "\000\002\004\003\000\002\003\007\000\002\003\005\000"
        + "\002\010\003\000\002\010\003\000\002\010\003\000\002"
        + "\006\003\000\002\006\002\000\002\005\004\000\002\005"
        + "\003"});

    /**
     * Access to production table.
     */
    public short[][] production_table() {
        return _production_table;
    }

    /**
     * Parse-action table.
     */
    protected static final short[][] _action_table
            = unpackFromStrings(new String[]{
        "\000\036\000\004\011\004\001\002\000\004\006\037\001"
        + "\002\000\006\007\010\010\012\001\002\000\004\002\007"
        + "\001\002\000\004\002\001\001\002\000\004\011\034\001"
        + "\002\000\006\010\016\013\014\001\002\000\010\002\ufff2"
        + "\010\ufff2\013\ufff2\001\002\000\010\002\ufffb\007\023\010"
        + "\ufffb\001\002\000\010\004\025\005\027\006\026\001\002"
        + "\000\006\002\ufff4\010\012\001\002\000\010\002\ufff3\010"
        + "\ufff3\013\ufff3\001\002\000\004\002\000\001\002\000\010"
        + "\002\ufff5\010\016\013\ufffd\001\002\000\004\013\014\001"
        + "\002\000\010\002\ufffc\007\023\010\ufffc\001\002\000\004"
        + "\013\024\001\002\000\010\004\025\005\027\006\026\001"
        + "\002\000\004\014\ufff6\001\002\000\004\014\ufff8\001\002"
        + "\000\004\014\ufff7\001\002\000\004\014\031\001\002\000"
        + "\010\002\ufffa\007\ufffa\010\ufffa\001\002\000\004\014\033"
        + "\001\002\000\010\002\ufff9\007\ufff9\010\ufff9\001\002\000"
        + "\004\006\035\001\002\000\004\012\036\001\002\000\006"
        + "\007\uffff\010\uffff\001\002\000\004\012\040\001\002\000"
        + "\006\007\ufffe\010\ufffe\001\002"});

    /**
     * Access to parse-action table.
     */
    public short[][] action_table() {
        return _action_table;
    }

    /**
     * <code>reduce_goto</code> table.
     */
    protected static final short[][] _reduce_table
            = unpackFromStrings(new String[]{
        "\000\036\000\006\002\005\007\004\001\001\000\002\001"
        + "\001\000\004\005\010\001\001\000\002\001\001\000\002"
        + "\001\001\000\002\001\001\000\006\003\012\004\014\001"
        + "\001\000\002\001\001\000\002\001\001\000\004\010\031"
        + "\001\001\000\006\005\017\006\016\001\001\000\002\001"
        + "\001\000\002\001\001\000\004\011\020\001\001\000\004"
        + "\003\021\001\001\000\002\001\001\000\002\001\001\000"
        + "\004\010\027\001\001\000\002\001\001\000\002\001\001"
        + "\000\002\001\001\000\002\001\001\000\002\001\001\000"
        + "\002\001\001\000\002\001\001\000\002\001\001\000\002"
        + "\001\001\000\002\001\001\000\002\001\001\000\002\001"
        + "\001"});

    /**
     * Access to <code>reduce_goto</code> table.
     */
    public short[][] reduce_table() {
        return _reduce_table;
    }

    /**
     * Instance of action encapsulation class.
     */
    protected CUP$SintacticoD$actions action_obj;

    /**
     * Action encapsulation object initializer.
     */
    protected void init_actions() {
        action_obj = new CUP$SintacticoD$actions(this);
    }

    /**
     * Invoke a user supplied parse action.
     */
    public java_cup.runtime.Symbol do_action(
            int act_num,
            java_cup.runtime.lr_parser parser,
            java.util.Stack stack,
            int top)
            throws java.lang.Exception {
        /* call code in generated class */
        return action_obj.CUP$SintacticoD$do_action(act_num, parser, stack, top);
    }

    /**
     * Indicates start state.
     */
    public int start_state() {
        return 0;
    }

    /**
     * Indicates start production.
     */
    public int start_production() {
        return 0;
    }

    /**
     * <code>EOF</code> Symbol index.
     */
    public int EOF_sym() {
        return 0;
    }

    /**
     * <code>error</code> Symbol index.
     */
    public int error_sym() {
        return 1;
    }

    /**
     * Metodo al que se llama automáticamente ante algún error sintactico.
     */
    public void syntax_error(Symbol s) {
        System.out.println("Error en la Línea " + (s.right + 1) + " Columna " + (s.left + 1) + ". Identificador "
                + s.value + " no reconocido.");
    }

    /**
     * Metodo al que se llama en el momento en que ya no es posible una
     * recuperación de errores.
     */
    public void unrecovered_syntax_error(Symbol s) throws java.lang.Exception {
        System.out.println("Error en la Línea " + (s.right + 1) + "Columna " + (s.left + 1) + ". Identificador "
                + s.value + " no reconocido.");
    }

}

/**
 * Cup generated class to encapsulate user supplied action code.
 */
class CUP$SintacticoD$actions {

    private final SintacticoD parser;

    /**
     * Constructor
     */
    CUP$SintacticoD$actions(SintacticoD parser) {
        this.parser = parser;
    }

    /**
     * Method with the actual generated action code.
     */
    public final java_cup.runtime.Symbol CUP$SintacticoD$do_action(
            int CUP$SintacticoD$act_num,
            java_cup.runtime.lr_parser CUP$SintacticoD$parser,
            java.util.Stack CUP$SintacticoD$stack,
            int CUP$SintacticoD$top)
            throws java.lang.Exception {
        /* Symbol object for return from actions */
        java_cup.runtime.Symbol CUP$SintacticoD$result;

        /* select the action based on the action number */
        switch (CUP$SintacticoD$act_num) {
            /*. . . . . . . . . . . . . . . . . . . .*/
            case 15: // SALTO ::= salto 
            {
                Object RESULT = null;

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("SALTO", 3, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 14: // SALTO ::= SALTO salto 
            {
                Object RESULT = null;

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("SALTO", 3, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 13: // SALTOS ::= 
            {
                Object RESULT = null;

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("SALTOS", 4, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 12: // SALTOS ::= SALTO 
            {
                Object RESULT = null;

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("SALTOS", 4, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 11: // DATO ::= decimal 
            {
                Celda RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).right;
                String c = (String) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).value;
                RESULT = new Celda("decimal", Double.parseDouble(c));
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("DATO", 6, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 10: // DATO ::= entero 
            {
                Celda RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).right;
                String c = (String) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).value;
                RESULT = new Celda("entero", Integer.parseInt(c));
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("DATO", 6, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 9: // DATO ::= cadena 
            {
                Celda RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).right;
                String c = (String) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()).value;
                RESULT = new Celda("cadena", c);
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("DATO", 6, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 8: // FILA ::= llaveInicio DATO llaveFin 
            {
                Object RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).right;
                Celda c = (Celda) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).value;
                Inicio.datos.addCelda(c);
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("FILA", 1, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 2)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 7: // FILA ::= FILA coma llaveInicio DATO llaveFin 
            {
                Object RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).right;
                Celda c = (Celda) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).value;
                Inicio.datos.addCelda(c);
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("FILA", 1, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 4)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 6: // FILAS ::= FILA 
            {
                Object RESULT = null;
                Inicio.datos.indice = 0;
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("FILAS", 2, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 5: // FILAS ::= FILAS SALTO NT$0 FILA 
            {
                Object RESULT = null;
                // propagate RESULT from NT$0
                RESULT = (Object) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).value;

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("FILAS", 2, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 3)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 4: // NT$0 ::= 
            {
                Object RESULT = null;
                Inicio.datos.indice = 0;
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("NT$0", 7, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 3: // ENCABEZADO ::= corInicio cadena corFin 
            {
                Datos RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).right;
                String c = (String) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).value;

                Columna col = new Columna(c);
                Inicio.datos.add(col);

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("ENCABEZADO", 5, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 2)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 2: // ENCABEZADO ::= ENCABEZADO coma corInicio cadena corFin 
            {
                Datos RESULT = null;
                int cleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).left;
                int cright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).right;
                String c = (String) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).value;

                Columna col = new Columna(c);
                Inicio.datos.add(col);

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("ENCABEZADO", 5, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 4)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 1: // INICIO ::= ENCABEZADO SALTO FILAS SALTOS 
            {
                Object RESULT = null;

                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("INICIO", 0, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 3)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            return CUP$SintacticoD$result;

            /*. . . . . . . . . . . . . . . . . . . .*/
            case 0: // $START ::= INICIO EOF 
            {
                Object RESULT = null;
                int start_valleft = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).left;
                int start_valright = ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).right;
                Object start_val = (Object) ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)).value;
                RESULT = start_val;
                CUP$SintacticoD$result = parser.getSymbolFactory().newSymbol("$START", 0, ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.elementAt(CUP$SintacticoD$top - 1)), ((java_cup.runtime.Symbol) CUP$SintacticoD$stack.peek()), RESULT);
            }
            /* ACCEPT */
            CUP$SintacticoD$parser.done_parsing();
            return CUP$SintacticoD$result;

            /* . . . . . .*/
            default:
                throw new Exception(
                        "Invalid action number found in internal parse table");

        }
    }
}