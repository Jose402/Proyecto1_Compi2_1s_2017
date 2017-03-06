package Haskell.Analisis;
import java_cup.runtime.Symbol;

%%
%cupsym Simbolo
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
    	System.out.println(dato+" : "+cadena);
    }
    public void imprimir(String cadena){
    	System.out.println(cadena+" soy reservada");
    }
%}

%%



//SIMBOLOS
"+"              {imprimir(yytext(),"soy +");return new Symbol(Simbolo.mas,yycolumn,yyline,yytext());}     
"-"              {imprimir(yytext(),"soy -");return new Symbol(Simbolo.menos,yycolumn,yyline,yytext());}     
"*"              {imprimir(yytext(),"soy *");return new Symbol(Simbolo.mul,yycolumn,yyline,yytext());}     
"/"              {imprimir(yytext(),"soy /");return new Symbol(Simbolo.div,yycolumn,yyline,yytext());}     
"'mod'"            {imprimir(yytext(),"soy %");return new Symbol(Simbolo.mod,yycolumn,yyline,yytext());}     
"'pot'"            {imprimir(yytext(),"soy ^");return new Symbol(Simbolo.pot,yycolumn,yyline,yytext());}
"'sqrt'"           {imprimir(yytext(),"soy ^");return new Symbol(Simbolo.sqrt,yycolumn,yyline,yytext());}

//SIMBOLOS DE OPERACIONES RELACIONALES
">"              {imprimir(yytext(),"soy mayor");return new Symbol(Simbolo.mayor,yycolumn,yyline,yytext());} 
"<"              {imprimir(yytext(),"soy menor");return new Symbol(Simbolo.menor,yycolumn,yyline,yytext());} 
">="             {imprimir(yytext(),"soy mayorIgual");return new Symbol(Simbolo.mayorIgual,yycolumn,yyline,yytext());} 
"<="             {imprimir(yytext(),"soy menorIgual");return new Symbol(Simbolo.menorIgual,yycolumn,yyline,yytext());} 
"=="             {imprimir(yytext(),"soy igualIgual");return new Symbol(Simbolo.igualIgual,yycolumn,yyline,yytext());} 
"!="             {imprimir(yytext(),"soy noIgual");return new Symbol(Simbolo.noIgual,yycolumn,yyline,yytext());} 

//SIMBOLOS DE OPERACIONES LOGICAS
"||"             {imprimir(yytext(),"soy or");return new Symbol(Simbolo.or,yycolumn,yyline,yytext());}
"&&"             {imprimir(yytext(),"soy and");return new Symbol(Simbolo.and,yycolumn,yyline,yytext());}


//SIMBOLOS
"\n"             {imprimir(yytext(),"salto");return new Symbol(Simbolo.salto,yycolumn,yyline,yytext());}
"="              {imprimir(yytext());return new Symbol(Simbolo.igual,yycolumn,yyline,yytext());}
"["              {imprimir(yytext(),"soy corInicio");return new Symbol(Simbolo.corInicio,yycolumn,yyline,yytext());}
"]"              {imprimir(yytext(),"soy corFin");return new Symbol(Simbolo.corFin,yycolumn,yyline,yytext());}
"$"              {imprimir(yytext(),"soy dolar");return new Symbol(Simbolo.dolar,yycolumn,yyline,yytext());}
"{"              {imprimir(yytext(),"soy llaveInicio");return new Symbol(Simbolo.llaveInicio,yycolumn,yyline,yytext());}
"}"              {imprimir(yytext(),"soy llaveFin");return new Symbol(Simbolo.llaveFin,yycolumn,yyline,yytext());}
":"              {imprimir(yytext(),"soy dosPuntos");return new Symbol(Simbolo.dosPuntos,yycolumn,yyline,yytext());}  
","              {imprimir(yytext(),"soy coma");return new Symbol(Simbolo.coma,yycolumn,yyline,yytext());}
"("              {imprimir(yytext(),"soy parenInicio");return new Symbol(Simbolo.parenInicio,yycolumn,yyline,yytext());}
")"              {imprimir(yytext(),"soy parenFin");return new Symbol(Simbolo.parenFin,yycolumn,yyline,yytext());}
";"              {imprimir(yytext(),"soy puntoComa");return new Symbol(Simbolo.puntoComa,yycolumn,yyline,yytext());}

//PALABRAS RESERVADAS
"let"       {imprimir(yytext());return new Symbol(Simbolo.let,yycolumn,yyline,yytext());}
"end"       {imprimir(yytext());return new Symbol(Simbolo.end,yycolumn,yyline,yytext());}

//RESERVADAS DE FUNCIONES PROPIAS DEL LENGUAJE
"calcular"       {imprimir(yytext());return new Symbol(Simbolo.calcular,yycolumn,yyline,yytext());}
"ans"            {imprimir(yytext());return new Symbol(Simbolo.ans,yycolumn,yyline,yytext());}
"succ"           {imprimir(yytext());return new Symbol(Simbolo.succ,yycolumn,yyline,yytext());}
"decc"           {imprimir(yytext());return new Symbol(Simbolo.decc,yycolumn,yyline,yytext());}
"min"            {imprimir(yytext());return new Symbol(Simbolo.min,yycolumn,yyline,yytext());}
"max"            {imprimir(yytext());return new Symbol(Simbolo.max,yycolumn,yyline,yytext());}
"++"             {imprimir(yytext());return new Symbol(Simbolo.concatenar,yycolumn,yyline,yytext());}
"!!"             {imprimir(yytext());return new Symbol(Simbolo.indice,yycolumn,yyline,yytext());}

//operaciones en listas
"sum"            {imprimir(yytext());return new Symbol(Simbolo.sum,yycolumn,yyline,yytext());}
"impr"           {imprimir(yytext());return new Symbol(Simbolo.impr,yycolumn,yyline,yytext());}
"product"        {imprimir(yytext());return new Symbol(Simbolo.product,yycolumn,yyline,yytext());}
"revers"         {imprimir(yytext());return new Symbol(Simbolo.revers,yycolumn,yyline,yytext());}
"par"            {imprimir(yytext());return new Symbol(Simbolo.par,yycolumn,yyline,yytext());}
"asc"            {imprimir(yytext());return new Symbol(Simbolo.asc,yycolumn,yyline,yytext());}
"desc"           {imprimir(yytext());return new Symbol(Simbolo.desc,yycolumn,yyline,yytext());}
"length"         {imprimir(yytext());return new Symbol(Simbolo.length,yycolumn,yyline,yytext());}

"if"             {imprimir(yytext());return new Symbol(Simbolo.si,yycolumn,yyline,yytext());}
"then"           {imprimir(yytext());return new Symbol(Simbolo.then,yycolumn,yyline,yytext());}
"else"           {imprimir(yytext());return new Symbol(Simbolo.sino,yycolumn,yyline,yytext());}
"Case"           {imprimir(yytext());return new Symbol(Simbolo.caso,yycolumn,yyline,yytext());}


{numero}         {imprimir(yytext(),"soy numero");return new Symbol(Simbolo.numero,yycolumn,yyline,yytext());}
{cadena}         {imprimir(yytext(),"soy cadena");return new Symbol(Simbolo.cadena,yycolumn,yyline,yytext());}
{iden}           {imprimir(yytext(),"soy iden");return new Symbol(Simbolo.iden,yycolumn,yyline,yytext());}
{caracter}           {imprimir(yytext(),"soy caracter");return new Symbol(Simbolo.caracter,yycolumn,yyline,yytext());}

/* COMENTARIOS */
{Comentario2}    { /* Se ignoran */}
{Comentario1}     { /* Se ignoran */}

/* BLANCOS */
[ \r\t\f]+     {/* Se ignoran */}


/* Cualquier Otro */
.                {System.out.println("Error lexico: "+yytext()+ " Linea:"+(yyline+1)+" Columna:"+(yycolumn+1));}


