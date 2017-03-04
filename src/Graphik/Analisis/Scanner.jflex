package Graphik.Analisis;
import java_cup.runtime.Symbol;

%%
%cupsym Simbolo
%class Lexico
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

entero =[0-9]+ 
decimal =[0-9]+ "."? [0-9]*
cadena =[\"] [^\"\n]+ [\"\n]
letra =[a-zA-ZÑñ]+
iden ={letra}({letra}|{entero}|"_")*
idenImportar ={letra}({letra}|{entero}|"_")*".gk"
caracter="'"[^]"'"
bool=("verdadero"|"false"|"1"|"0")



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
"%"              {imprimir(yytext(),"soy %");return new Symbol(Simbolo.mod,yycolumn,yyline,yytext());}     
"^"              {imprimir(yytext(),"soy ^");return new Symbol(Simbolo.pot,yycolumn,yyline,yytext());}

//SIMBOLOS DE OPERACIONES RELACIONALES
">"              {imprimir(yytext(),"soy mayor");return new Symbol(Simbolo.mayor,yycolumn,yyline,yytext());} 
"<"              {imprimir(yytext(),"soy menor");return new Symbol(Simbolo.menor,yycolumn,yyline,yytext());} 
">="             {imprimir(yytext(),"soy mayorIgual");return new Symbol(Simbolo.mayorIgual,yycolumn,yyline,yytext());} 
"<="             {imprimir(yytext(),"soy menorIgual");return new Symbol(Simbolo.menorIgual,yycolumn,yyline,yytext());} 
"=="             {imprimir(yytext(),"soy igualIgual");return new Symbol(Simbolo.igualIgual,yycolumn,yyline,yytext());} 
"!="             {imprimir(yytext(),"soy noIgual");return new Symbol(Simbolo.noIgual,yycolumn,yyline,yytext());} 

//SIMBOLOS DE OPERACIONES LOGICAS
"||"             {imprimir(yytext(),"soy or");return new Symbol(Simbolo.or,yycolumn,yyline,yytext());} 
"&|"             {imprimir(yytext(),"soy xor");return new Symbol(Simbolo.xor,yycolumn,yyline,yytext());}
"&&"             {imprimir(yytext(),"soy and");return new Symbol(Simbolo.and,yycolumn,yyline,yytext());}
"!"              {imprimir(yytext(),"soy not");return new Symbol(Simbolo.not,yycolumn,yyline,yytext());}

"++"             {imprimir(yytext(),"soy aumento");return new Symbol(Simbolo.aumento,yycolumn,yyline,yytext());}    
"--"             {imprimir(yytext(),"soy disminucion");return new Symbol(Simbolo.disminucion,yycolumn,yyline,yytext());}
"="              {imprimir(yytext(),"soy igual");return new Symbol(Simbolo.igual,yycolumn,yyline,yytext());} 

"."              {imprimir(yytext(),"soy punto");return new Symbol(Simbolo.punto,yycolumn,yyline,yytext());}  
":"              {imprimir(yytext(),"soy dosPuntos");return new Symbol(Simbolo.dosPuntos,yycolumn,yyline,yytext());} 
";"              {imprimir(yytext(),"soy puntocoma");return new Symbol(Simbolo.puntoComa,yycolumn,yyline,yytext());}  
","              {imprimir(yytext(),"soy coma");return new Symbol(Simbolo.coma,yycolumn,yyline,yytext());}  
"("              {imprimir(yytext(),"soy parenInicio");return new Symbol(Simbolo.parenInicio,yycolumn,yyline,yytext());}
")"              {imprimir(yytext(),"soy parenFin");return new Symbol(Simbolo.parenFin,yycolumn,yyline,yytext());}
"["              {imprimir(yytext(),"soy corInicio");return new Symbol(Simbolo.corInicio,yycolumn,yyline,yytext());}
"]"              {imprimir(yytext(),"soy corFin");return new Symbol(Simbolo.corFin,yycolumn,yyline,yytext());}

"{"              {imprimir(yytext(),"soy llaveInicio");return new Symbol(Simbolo.llaveInicio,yycolumn,yyline,yytext());}
"}"              {imprimir(yytext(),"soy llaveFin");return new Symbol(Simbolo.llaveFin,yycolumn,yyline,yytext());}
"¿"              {imprimir(yytext(),"soy inteInicio");return new Symbol(Simbolo.inteInicio,yycolumn,yyline,yytext());}
"?"              {imprimir(yytext(),"soy inteFin");return new Symbol(Simbolo.inteFin,yycolumn,yyline,yytext());}

//PARABRAS RESERVADAS
"als"            {imprimir(yytext());return new Symbol(Simbolo.als,yycolumn,yyline,yytext());}
"var"            {imprimir(yytext());return new Symbol(Simbolo.var,yycolumn,yyline,yytext());}
"importar"       {imprimir(yytext());return new Symbol(Simbolo.importar,yycolumn,yyline,yytext());}
"entero"         {imprimir(yytext());return new Symbol(Simbolo.resEntero,yycolumn,yyline,yytext());}
"decimal"        {imprimir(yytext());return new Symbol(Simbolo.resDecimal,yycolumn,yyline,yytext());}
"caracter"       {imprimir(yytext());return new Symbol(Simbolo.resCaracter,yycolumn,yyline,yytext());}
"cadena"         {imprimir(yytext());return new Symbol(Simbolo.resCadena,yycolumn,yyline,yytext());}
"bool"           {imprimir(yytext());return new Symbol(Simbolo.resBool,yycolumn,yyline,yytext());}
"vacio"          {imprimir(yytext());return new Symbol(Simbolo.vacio,yycolumn,yyline,yytext());}
"publico"        {imprimir(yytext());return new Symbol(Simbolo.publico,yycolumn,yyline,yytext());}
"protegido"      {imprimir(yytext());return new Symbol(Simbolo.protegido,yycolumn,yyline,yytext());}
"privado"        {imprimir(yytext());return new Symbol(Simbolo.privado,yycolumn,yyline,yytext());}
"hereda"         {imprimir(yytext());return new Symbol(Simbolo.hereda,yycolumn,yyline,yytext());}
"nuevo"          {imprimir(yytext());return new Symbol(Simbolo.nuevo,yycolumn,yyline,yytext());}
"retornar"       {imprimir(yytext());return new Symbol(Simbolo.retornar,yycolumn,yyline,yytext());}
"llamar"         {imprimir(yytext());return new Symbol(Simbolo.llamar,yycolumn,yyline,yytext());}
"inicio"         {imprimir(yytext());return new Symbol(Simbolo.inicio,yycolumn,yyline,yytext());}
"incluir_HK"     {imprimir(yytext());return new Symbol(Simbolo.incluir_HK,yycolumn,yyline,yytext());}
"llamarHK"       {imprimir(yytext());return new Symbol(Simbolo.llamarHK,yycolumn,yyline,yytext());}


//PALABRAS RESERVADAS DE SENTENCIAS DE CONTROL
"Si"             {imprimir(yytext());return new Symbol(Simbolo.si,yycolumn,yyline,yytext());}
"Sino"           {imprimir(yytext());return new Symbol(Simbolo.sino,yycolumn,yyline,yytext());}
"Seleccion"      {imprimir(yytext());return new Symbol(Simbolo.seleccion,yycolumn,yyline,yytext());}
"Caso"           {imprimir(yytext());return new Symbol(Simbolo.caso,yycolumn,yyline,yytext());}
"Defecto"        {imprimir(yytext());return new Symbol(Simbolo.defecto,yycolumn,yyline,yytext());}
"Para"           {imprimir(yytext());return new Symbol(Simbolo.para,yycolumn,yyline,yytext());}
"Mientras"       {imprimir(yytext());return new Symbol(Simbolo.mientras,yycolumn,yyline,yytext());}
"Hacer"          {imprimir(yytext());return new Symbol(Simbolo.hacer,yycolumn,yyline,yytext());}
"Continuar"      {imprimir(yytext());return new Symbol(Simbolo.continuar,yycolumn,yyline,yytext());}
"Terminar"       {imprimir(yytext());return new Symbol(Simbolo.terminar,yycolumn,yyline,yytext());}
"graphikar_funcion" {imprimir(yytext());return new Symbol(Simbolo.graphikar_funcion,yycolumn,yyline,yytext());}
"Datos"          {imprimir(yytext());return new Symbol(Simbolo.datos,yycolumn,yyline,yytext());}
"Columna"        {imprimir(yytext());return new Symbol(Simbolo.columna,yycolumn,yyline,yytext());}
"Procesar"       {imprimir(yytext());return new Symbol(Simbolo.procesar, yychar,yycolumn,yyline);}
"Donde"          {imprimir(yytext());return new Symbol(Simbolo.donde,yycolumn,yyline,yytext());}
"DondeCada"      {imprimir(yytext());return new Symbol(Simbolo.dondeCada,yycolumn,yyline,yytext());}
"DondeTodo"      {imprimir(yytext());return new Symbol(Simbolo.dondeTodo,yycolumn,yyline,yytext());}
"imprimir"       {imprimir(yytext());return new Symbol(Simbolo.imprimir,yycolumn,yyline,yytext());}

//RESERVADAS COMPUESTAS
{idenImportar}   {imprimir(yytext());return new Symbol(Simbolo.idenImportar,yycolumn,yyline,yytext());}

{entero}         {imprimir(yytext(),"soy entero");return new Symbol(Simbolo.entero,yycolumn,yyline,yytext());}
{decimal}        {imprimir(yytext(),"soy decimal");return new Symbol(Simbolo.decimal,yycolumn,yyline,yytext());}
{cadena}         {imprimir(yytext(),"soy cadena");return new Symbol(Simbolo.cadena,yycolumn,yyline,yytext());}
{bool}           {imprimir(yytext(),"soy bool");return new Symbol(Simbolo.bool,yycolumn,yyline,yytext());}
{iden}           {imprimir(yytext(),"soy iden");return new Symbol(Simbolo.iden,yycolumn,yyline,yytext());}
{caracter}       {imprimir(yytext(),"soy caracter");return new Symbol(Simbolo.caracter,yycolumn,yyline,yytext());}

/* COMENTARIOS */
{Comentario2}    { /* Se ignoran */}
{Comentario1}     { /* Se ignoran */}

/* BLANCOS */
[ \t\r\f\n]+     {/* Se ignoran */}


/* Cualquier Otro */
.                {System.out.println("Error lexico: "+yytext()+ " Linea:"+(yyline+1)+" Columna:"+(yycolumn+1));}


