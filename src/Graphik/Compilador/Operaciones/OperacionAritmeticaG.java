/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik.Compilador.Operaciones;

import Ast.Nodo;
import Graphik.Compilador.*;
import Haskell.Interprete.Operaciones.OperacionLogica;
import Interfaz.Inicio;

/**
 *
 * @author Jose2
 */
public class OperacionAritmeticaG {
    
    private TablaSimboloG tabla;
    public OperacionAritmeticaG(){
        tabla=Inicio.compilador.tabla;
    }
    
    public OperacionAritmeticaG(TablaSimboloG tabla){
        this.tabla=tabla;
    }
    
    public ResultadoG operar(Nodo raiz){
        ResultadoG resultado1=null;
        ResultadoG resultado2=null;
        switch(raiz.etiqueta){
            case "==":
            case "!=":
            case ">":
            case "<":
            case ">=":
            case "<=":
            case "!":
            case "&&":
            case "||":
            case "&|":
                OperacionLogicaG opL=new OperacionLogicaG();
                resultado1=opL.operar(raiz);
                if((Boolean)resultado1.valor){
                    return new ResultadoG("entero",1);
                }else{
                    return new ResultadoG("entero",0);
                }
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case "^":
                resultado1=operar(raiz.hijos.get(0));
                resultado2=operar(raiz.hijos.get(1));
                break;
            case "unitario":
                resultado1=operar(raiz.hijos.get(0));
                switch(resultado1.tipo){
                    case "entero":
                        return new ResultadoG(resultado1.tipo,(int)resultado1.valor*-1);
                    case "decimal":
                        return new ResultadoG(resultado1.tipo,(Double)resultado1.valor*-1);
                    case "caracter":
                        return new ResultadoG(resultado1.tipo,(char)resultado1.valor*-1);
                }
                
            case "entero":
                return new ResultadoG(raiz.etiqueta,Integer.parseInt(raiz.valor));
            case "decimal":
                return new ResultadoG(raiz.etiqueta,Double.parseDouble(raiz.valor));
            case "caracter":
                return new ResultadoG(raiz.etiqueta,(raiz.valor.charAt(0)));
            case "bool":
                if(raiz.valor.equals("verdadero")||raiz.valor.equals("1")){
                    return new ResultadoG(raiz.etiqueta,true);
                }else{
                    return new ResultadoG(raiz.etiqueta,false);
                }
            case "cadena":
                return new ResultadoG(raiz.etiqueta,raiz.valor+"");
            case "++":
            case "--":
                return operacionSimplificada(raiz.etiqueta,raiz.hijos.get(0));
            case "accesoVar":
                int a=2+4;
                break;
        }
        
        //------------------------operaciones-------------------------
        Object valor=new Object();
        switch(raiz.etiqueta){
            case "+":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(int)resultado1.valor+(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(int)resultado1.valor+(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=(int)resultado1.valor+getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(int)resultado1.valor+(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                valor=(int)resultado1.valor+(String)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(Double)resultado1.valor+(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(Double)resultado1.valor+(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=(Double)resultado1.valor+getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(Double)resultado1.valor+(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                valor=(double)resultado1.valor+(String)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=getBoolValor(resultado1.valor)+(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=getBoolValor(resultado1.valor)+(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=getBoolValor(resultado1.valor)+getBoolValor(resultado2.valor);
                                if((int)valor==2||(int)valor==1){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                                
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede sumar datos tipo Bool con Cadenas y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(char)resultado1.valor+(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(char)resultado1.valor+(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "caracter":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede sumar datos tipo Caracter con Caracteres y Booleanos");
                                break;
                            case "cadena":
                                valor=(char)resultado1.valor+(String)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(String)resultado1.valor+(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(String)resultado1.valor+(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede sumar datos tipo Cadena con Booleanos");
                                break;
                            case "caracter":
                                valor=(String)resultado1.valor+(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                valor=(String)resultado1.valor+(String)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            default:
                        }
                    default:
                }
                
                
                
                
                
                break;
            case "-":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(int)resultado1.valor-(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(int)resultado1.valor-(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=(int)resultado1.valor-getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(int)resultado1.valor-(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede restar datos tipo entero con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(Double)resultado1.valor-(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(Double)resultado1.valor-(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=(Double)resultado1.valor-getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(int)resultado1.valor-(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede restar datos tipo Decimal con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=getBoolValor(resultado1.valor)-(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=getBoolValor(resultado1.valor)-(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede restar datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(char)resultado1.valor-(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(char)resultado1.valor-(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(char)resultado1.valor-(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede restar datos tipo Caracter con Cadenas y Booleanos");
                                break;
                            default:
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede restar datos tipo Cadena con cualquier otro tipo de dato");
                                break;
                            default:
                        }
                    default:
                }
                
                
                
                
                
                
                
                break;
            case "*":
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(int)resultado1.valor*(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(int)resultado1.valor*(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=(int)resultado1.valor*getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(int)resultado1.valor*(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede multiplicar datos tipo entero con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(Double)resultado1.valor*(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(Double)resultado1.valor*(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=(Double)resultado1.valor*getBoolValor(resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(int)resultado1.valor*(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede multiplicar datos tipo Decimal con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=getBoolValor(resultado1.valor)*(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=getBoolValor(resultado1.valor)*(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=getBoolValor(resultado1.valor)*getBoolValor(resultado2.valor);
                                if((int)valor==1){
                                    return new ResultadoG("bool",true);
                                }else{
                                    return new ResultadoG("bool",false);
                                }
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede multiplicar datos tipo Bool con Cadenas y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=(char)resultado1.valor*(int)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=(char)resultado1.valor*(Double)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=(char)resultado1.valor*(char)resultado2.valor;
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede multiplicar datos tipo Caracter con Cadenas y Booleanos");
                                break;
                            default:
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede multiplicar datos tipo Cadena con cualquier otro tipo de dato");
                                break;
                            default:
                        }
                    default:
                }
                
                
                
                
                
                
                
                break;
            case "/":
                try {
                    switch(resultado1.tipo){
                        case "entero":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=(int)resultado1.valor/(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=(int)resultado1.valor/(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    valor=(int)resultado1.valor/getBoolValor(resultado2.valor);
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    valor=(int)resultado1.valor/(char)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir datos tipo entero con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "decimal":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=(Double)resultado1.valor/(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=(Double)resultado1.valor/(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    valor=(Double)resultado1.valor/getBoolValor(resultado2.valor);
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    valor=(int)resultado1.valor/(char)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir datos tipo Decimal con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "bool":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=getBoolValor(resultado1.valor)/(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=getBoolValor(resultado1.valor)/(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                    break;
                                default:
                            }
                            break;
                        case "caracter":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=(char)resultado1.valor/(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=(char)resultado1.valor/(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    valor=(char)resultado1.valor/(char)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir datos tipo Caracter con Cadenas y Booleanos");
                                    break;
                                default:
                            }
                            break;
                        case "cadena":
                            switch(resultado2.tipo){
                                case "entero":
                                case "decimal":
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir datos tipo Cadena con cualquier otro tipo de dato");
                                    break;
                                default:
                            }
                        default:
                    }
                }catch(Exception e){
                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir un numero con 0");
                }
                
                
                
                
                
                
                
                
                
                
                break;
            case "%":
                try {
                    switch(resultado1.tipo){
                        case "entero":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=(int)resultado1.valor%(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=(int)resultado1.valor%(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    valor=(int)resultado1.valor%getBoolValor(resultado2.valor);
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    valor=(int)resultado1.valor%(char)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Modulo en datos tipo entero con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "decimal":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=(Double)resultado1.valor%(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=(Double)resultado1.valor%(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                    valor=(Double)resultado1.valor%getBoolValor(resultado2.valor);
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    valor=(int)resultado1.valor%(char)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Modulo en datos tipo Decimal con Cadenas");
                                    break;
                                default:
                            }
                            break;
                        case "bool":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=getBoolValor(resultado1.valor)%(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=getBoolValor(resultado1.valor)%(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Modulo en datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                    break;
                                default:
                            }
                            break;
                        case "caracter":
                            switch(resultado2.tipo){
                                case "entero":
                                    valor=(char)resultado1.valor%(int)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "decimal":
                                    valor=(char)resultado1.valor%(Double)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "caracter":
                                    valor=(char)resultado1.valor%(char)resultado2.valor;
                                    return new ResultadoG(getTipo(valor), valor);
                                case "bool":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Modulo en datos tipo Caracter con Cadenas y Booleanos");
                                    break;
                                default:
                            }
                            break;
                        case "cadena":
                            switch(resultado2.tipo){
                                case "entero":
                                case "decimal":
                                case "bool":
                                case "caracter":
                                case "cadena":
                                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Modulo  en datos tipo Cadena con cualquier otro tipo de dato");
                                    break;
                                default:
                            }
                        default:
                    }
                }catch(Exception e){
                    Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede dividir un numero con 0");
                }
                
                
                
                
                
                
                break;
            case "^":
                Double doubleVal;
                switch(resultado1.tipo){
                    case "entero":
                        switch(resultado2.tipo){
                            case "entero":
                                doubleVal=Math.pow((int)resultado1.valor,(int)resultado2.valor);
                                valor=doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=Math.pow((int)resultado1.valor,(Double)resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                doubleVal=Math.pow((int)resultado1.valor,getBoolValor(resultado2.valor));
                                valor=doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                doubleVal=Math.pow((int)resultado1.valor,(char)resultado2.valor);
                                valor=doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Potencia en datos tipo entero con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "decimal":
                        switch(resultado2.tipo){
                            case "entero":
                                valor=Math.pow((Double)resultado1.valor,(int)resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=Math.pow((Double)resultado1.valor,(Double)resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                                valor=Math.pow((Double)resultado1.valor,getBoolValor(resultado2.valor));
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                valor=Math.pow((Double)resultado1.valor,(char)resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Potencia en datos tipo Decimal con Cadenas");
                                break;
                            default:
                        }
                        break;
                    case "bool":
                        switch(resultado2.tipo){
                            case "entero":
                                doubleVal=Math.pow(getBoolValor(resultado1.valor),(int)resultado2.valor);
                                valor=doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=Math.pow(getBoolValor(resultado1.valor),(Double)resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Potencia en datos tipo Bool con Cadenas,Booleanos y Caracteres");
                                break;
                            default:
                        }
                        break;
                    case "caracter":
                        switch(resultado2.tipo){
                            case "entero":
                                doubleVal=Math.pow((char)resultado1.valor,(int)resultado2.valor);
                                valor=doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "decimal":
                                valor=Math.pow((char)resultado1.valor,(Double)resultado2.valor);
                                return new ResultadoG(getTipo(valor), valor);
                            case "caracter":
                                doubleVal=Math.pow((char)resultado1.valor,(char)resultado2.valor);
                                valor=doubleVal.intValue();
                                return new ResultadoG(getTipo(valor), valor);
                            case "bool":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Potencia en datos tipo Caracter con Cadenas y Booleanos");
                                break;
                            default:
                        }
                        break;
                    case "cadena":
                        switch(resultado2.tipo){
                            case "entero":
                            case "decimal":
                            case "bool":
                            case "caracter":
                            case "cadena":
                                Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se puede usar el operador Potencia en datos tipo Cadena con cualquier otro tipo de dato");
                                break;
                            default:
                        }
                    default:
                }
                
                
                
                
                
                
            default:
        }
        
        //------------------------fin operaciones----------------------
        return new ResultadoG("-1",null);
    }
    
    
    private ResultadoG operacionSimplificada(String tipo,Nodo raiz){
        ResultadoG resultado=operar(raiz);
        Object valor=resultado.valor;
        if(resultado.tipo.equals("entero")||resultado.tipo.equals("decimal")||resultado.tipo.equals("caracter")){
            if(tipo.equals("++")){
                switch(resultado.tipo){
                    case "entero":
                        valor=(int)resultado.valor+1;
                        break;
                    case "decimal":
                        valor=(Double)resultado.valor+1;
                        break;
                    case "caracter":
                        valor=(char)resultado.valor+1;
                }    
                return  new ResultadoG(getTipo(valor),valor);   
            }else{//si es --
                switch(resultado.tipo){
                    case "entero":
                        valor=(int)resultado.valor-1;
                        break;
                    case "decimal":
                        valor=(Double)resultado.valor-1;
                        break;
                    case "caracter":
                        valor=(char)resultado.valor-1;
                }
                return  new ResultadoG(getTipo(valor),valor);
            }
        }else{
            Inicio.reporteError.agregar("Semantico",raiz.linea,raiz.columna,"No se pueden realizar operaciones simplificadas sobre datos tipo cadena y bool");
            return new ResultadoG("-1",null);
        }
    }
 
private String getTipo(Object objeto){
    String tipo=objeto.getClass().getSimpleName();
    switch(tipo){
        case "Integer":
            return "entero";
        case "String":
            return "cadena";
        case "Character":
            return "caracter";
        case "Double":
            return "decimal";
        case "Boolean":
            return "bool";
        default:
            return tipo;
    }
}
   
private int getBoolValor(Object objeto){
    Boolean valor=(Boolean)objeto;
    if(valor){
        return 1;
    }else{
        return 0;
    }
}

}
