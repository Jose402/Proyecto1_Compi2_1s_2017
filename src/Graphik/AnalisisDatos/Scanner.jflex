package Graphik.AnalisisDatos;
import java_cup.runtime.Symbol;

%%
%cupsym sym
%class LexicoD
%cup
%public
%unicode
%line
%column
%char
%ignorecase

//------------------------------------------------------------------------
//EXPRESIONES REGULARES
entero =[0-9]+ 
decimal =[0-9]+ "."? [0-9]*
cadena =[\"] [^\"\n]* [\"\n]



//-------------------------------------------------------------------------

%{
    //codigo de java
    String nombre;
    public void imprimir(String dato,String cadena){
    	//System.out.println(dato+" : "+cadena);
    }
    public void imprimir(String cadena){
    	//System.out.println(cadena+" soy reservada");
    }
%}

%%

","              {imprimir(yytext(),"soy coma");return new Symbol(sym.coma,yycolumn,yyline,yytext());}  
"["              {imprimir(yytext(),"soy corInicio");return new Symbol(sym.corInicio,yycolumn,yyline,yytext());}
"]"              {imprimir(yytext(),"soy corFin");return new Symbol(sym.corFin,yycolumn,yyline,yytext());}

"\n"              {imprimir(yytext(),"soy corFin");return new Symbol(sym.salto,yycolumn,yyline,yytext());}

"{"              {imprimir(yytext(),"soy llaveInicio");return new Symbol(sym.llaveInicio,yycolumn,yyline,yytext());}
"}"              {imprimir(yytext(),"soy llaveFin");return new Symbol(sym.llaveFin,yycolumn,yyline,yytext());}

{entero}         {imprimir(yytext(),"soy entero");return new Symbol(sym.entero,yycolumn,yyline,yytext());}
{decimal}        {imprimir(yytext(),"soy decimal");return new Symbol(sym.decimal,yycolumn,yyline,yytext());}
{cadena}         {imprimir(yytext(),"soy cadena");return new Symbol(sym.cadena,yycolumn,yyline,yytext());}

/* BLANCOS */
[ \t\r\f]+     {/* Se ignoran */}


/* Cualquier Otro */
.                {System.out.println("Error lexico: "+yytext()+ " Linea:"+(yyline+1)+" Columna:"+(yycolumn+1));}


