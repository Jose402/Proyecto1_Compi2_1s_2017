package Haskell.Analisis;
import java_cup.runtime.Symbol;

%%
%cupsym sym
%class LexicoH
%cup
%public
%unicode
%line
%column
%char
%ignorecase

//------------------------------------------------------------------------
//EXPRESIONES REGULARES

Comentario2 = "//" ([^\r\n])* \r|\n
Comentario1 ="/"[*] [^*]+ [*]"/" | "/"[*] [*]+ "/"

cadena =[\"] [^\"\n]+ [\"\n]
numero =[0-9]+ "."? [0-9]*

entero =[0-9]+ 
letra =[a-zA-ZÑñ]+
iden ={letra}({letra}|{entero}|"_")*
caracter="'"[^*]"'"




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



//SIMBOLOS
"+"              {imprimir(yytext(),"soy +");return new Symbol(sym.mas,yycolumn,yyline,yytext());}     
"-"              {imprimir(yytext(),"soy -");return new Symbol(sym.menos,yycolumn,yyline,yytext());}     
"*"              {imprimir(yytext(),"soy *");return new Symbol(sym.mul,yycolumn,yyline,yytext());}     
"/"              {imprimir(yytext(),"soy /");return new Symbol(sym.div,yycolumn,yyline,yytext());}     
"'mod'"            {imprimir(yytext(),"soy %");return new Symbol(sym.mod,yycolumn,yyline,yytext());}     
"'pot'"            {imprimir(yytext(),"soy ^");return new Symbol(sym.pot,yycolumn,yyline,yytext());}
"'sqrt'"           {imprimir(yytext(),"soy ^");return new Symbol(sym.sqrt,yycolumn,yyline,yytext());}

//SIMBOLOS DE OPERACIONES RELACIONALES
">"              {imprimir(yytext(),"soy mayor");return new Symbol(sym.mayor,yycolumn,yyline,yytext());} 
"<"              {imprimir(yytext(),"soy menor");return new Symbol(sym.menor,yycolumn,yyline,yytext());} 
">="             {imprimir(yytext(),"soy mayorIgual");return new Symbol(sym.mayorIgual,yycolumn,yyline,yytext());} 
"<="             {imprimir(yytext(),"soy menorIgual");return new Symbol(sym.menorIgual,yycolumn,yyline,yytext());} 
"=="             {imprimir(yytext(),"soy igualIgual");return new Symbol(sym.igualIgual,yycolumn,yyline,yytext());} 
"!="             {imprimir(yytext(),"soy noIgual");return new Symbol(sym.noIgual,yycolumn,yyline,yytext());} 

//SIMBOLOS DE OPERACIONES LOGICAS
"||"             {imprimir(yytext(),"soy or");return new Symbol(sym.or,yycolumn,yyline,yytext());}
"&&"             {imprimir(yytext(),"soy and");return new Symbol(sym.and,yycolumn,yyline,yytext());}


//SIMBOLOS
"\n"             {imprimir(yytext(),"salto");return new Symbol(sym.salto,yycolumn,yyline,yytext());}
"="              {imprimir(yytext());return new Symbol(sym.igual,yycolumn,yyline,yytext());}
"["              {imprimir(yytext(),"soy corInicio");return new Symbol(sym.corInicio,yycolumn,yyline,yytext());}
"]"              {imprimir(yytext(),"soy corFin");return new Symbol(sym.corFin,yycolumn,yyline,yytext());}
"$"              {imprimir(yytext(),"soy dolar");return new Symbol(sym.dolar,yycolumn,yyline,yytext());}
"{"              {imprimir(yytext(),"soy llaveInicio");return new Symbol(sym.llaveInicio,yycolumn,yyline,yytext());}
"}"              {imprimir(yytext(),"soy llaveFin");return new Symbol(sym.llaveFin,yycolumn,yyline,yytext());}
":"              {imprimir(yytext(),"soy dosPuntos");return new Symbol(sym.dosPuntos,yycolumn,yyline,yytext());}  
","              {imprimir(yytext(),"soy coma");return new Symbol(sym.coma,yycolumn,yyline,yytext());}
"("              {imprimir(yytext(),"soy parenInicio");return new Symbol(sym.parenInicio,yycolumn,yyline,yytext());}
")"              {imprimir(yytext(),"soy parenFin");return new Symbol(sym.parenFin,yycolumn,yyline,yytext());}
";"              {imprimir(yytext(),"soy puntoComa");return new Symbol(sym.puntoComa,yycolumn,yyline,yytext());}

//PALABRAS RESERVADAS
"let"       {imprimir(yytext());return new Symbol(sym.let,yycolumn,yyline,yytext());}
"end"       {imprimir(yytext());return new Symbol(sym.end,yycolumn,yyline,yytext());}

//RESERVADAS DE FUNCIONES PROPIAS DEL LENGUAJE
"calcular"       {imprimir(yytext());return new Symbol(sym.calcular,yycolumn,yyline,yytext());}
"ans"            {imprimir(yytext());return new Symbol(sym.ans,yycolumn,yyline,yytext());}
"succ"           {imprimir(yytext());return new Symbol(sym.succ,yycolumn,yyline,yytext());}
"decc"           {imprimir(yytext());return new Symbol(sym.decc,yycolumn,yyline,yytext());}
"min"            {imprimir(yytext());return new Symbol(sym.min,yycolumn,yyline,yytext());}
"max"            {imprimir(yytext());return new Symbol(sym.max,yycolumn,yyline,yytext());}
"++"             {imprimir(yytext());return new Symbol(sym.concatenar,yycolumn,yyline,yytext());}
"!!"             {imprimir(yytext());return new Symbol(sym.indice,yycolumn,yyline,yytext());}

//operaciones en listas
"sum"            {imprimir(yytext());return new Symbol(sym.sum,yycolumn,yyline,yytext());}
"impr"           {imprimir(yytext());return new Symbol(sym.impr,yycolumn,yyline,yytext());}
"product"        {imprimir(yytext());return new Symbol(sym.product,yycolumn,yyline,yytext());}
"revers"         {imprimir(yytext());return new Symbol(sym.revers,yycolumn,yyline,yytext());}
"par"            {imprimir(yytext());return new Symbol(sym.par,yycolumn,yyline,yytext());}
"asc"            {imprimir(yytext());return new Symbol(sym.asc,yycolumn,yyline,yytext());}
"desc"           {imprimir(yytext());return new Symbol(sym.desc,yycolumn,yyline,yytext());}
"length"         {imprimir(yytext());return new Symbol(sym.length,yycolumn,yyline,yytext());}

"if"             {imprimir(yytext());return new Symbol(sym.si,yycolumn,yyline,yytext());}
"then"           {imprimir(yytext());return new Symbol(sym.then,yycolumn,yyline,yytext());}
"else"           {imprimir(yytext());return new Symbol(sym.sino,yycolumn,yyline,yytext());}
"Case"           {imprimir(yytext());return new Symbol(sym.caso,yycolumn,yyline,yytext());}


{numero}         {imprimir(yytext(),"soy numero");return new Symbol(sym.numero,yycolumn,yyline,yytext());}
{cadena}         {imprimir(yytext(),"soy cadena");return new Symbol(sym.cadena,yycolumn,yyline,yytext());}
{iden}           {imprimir(yytext(),"soy iden");return new Symbol(sym.iden,yycolumn,yyline,yytext());}
{caracter}           {imprimir(yytext(),"soy caracter");return new Symbol(sym.caracter,yycolumn,yyline,yytext());}

/* COMENTARIOS */
{Comentario2}    { /* Se ignoran */}
{Comentario1}     { /* Se ignoran */}

/* BLANCOS */
[ \r\t\f]+     {/* Se ignoran */}


/* Cualquier Otro */
.                {System.out.println("Error lexico: "+yytext()+ " Linea:"+(yyline+1)+" Columna:"+(yycolumn+1));}


