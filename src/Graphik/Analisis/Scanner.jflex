package Graphik.Analisis;
import java_cup.runtime.Symbol;

%%
%cupsym sym
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
Comentario2 = "#" [^\r\n]* [^\r\n]
Comentario1 = "#/" [^/] ~"/#" | "#/" "/"+ "#"

entero =[0-9]+ 
decimal =[0-9]+ "."? [0-9]*
cadena =[\"] [^\"\n]* [\"\n]
letra =[a-zA-ZÑñ]+
iden ={letra}({letra}|{entero}|"_")*
idenImportar ={letra}({letra}|{entero}|"_")*".gk"
caracter="'"[^]"'"
bool=("verdadero"|"falso"|"1"|"0")



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
"%"              {imprimir(yytext(),"soy %");return new Symbol(sym.mod,yycolumn,yyline,yytext());}     
"^"              {imprimir(yytext(),"soy ^");return new Symbol(sym.pot,yycolumn,yyline,yytext());}

//SIMBOLOS DE OPERACIONES RELACIONALES
">"              {imprimir(yytext(),"soy mayor");return new Symbol(sym.mayor,yycolumn,yyline,yytext());} 
"<"              {imprimir(yytext(),"soy menor");return new Symbol(sym.menor,yycolumn,yyline,yytext());} 
">="             {imprimir(yytext(),"soy mayorIgual");return new Symbol(sym.mayorIgual,yycolumn,yyline,yytext());} 
"<="             {imprimir(yytext(),"soy menorIgual");return new Symbol(sym.menorIgual,yycolumn,yyline,yytext());} 
"=="             {imprimir(yytext(),"soy igualIgual");return new Symbol(sym.igualIgual,yycolumn,yyline,yytext());} 
"!="             {imprimir(yytext(),"soy noIgual");return new Symbol(sym.noIgual,yycolumn,yyline,yytext());} 

//SIMBOLOS DE OPERACIONES LOGICAS
"||"             {imprimir(yytext(),"soy or");return new Symbol(sym.or,yycolumn,yyline,yytext());} 
"&|"             {imprimir(yytext(),"soy xor");return new Symbol(sym.xor,yycolumn,yyline,yytext());}
"&&"             {imprimir(yytext(),"soy and");return new Symbol(sym.and,yycolumn,yyline,yytext());}
"!"              {imprimir(yytext(),"soy not");return new Symbol(sym.not,yycolumn,yyline,yytext());}

"++"             {imprimir(yytext(),"soy aumento");return new Symbol(sym.aumento,yycolumn,yyline,yytext());}    
"--"             {imprimir(yytext(),"soy disminucion");return new Symbol(sym.disminucion,yycolumn,yyline,yytext());}
"="              {imprimir(yytext(),"soy igual");return new Symbol(sym.igual,yycolumn,yyline,yytext());} 

"."              {imprimir(yytext(),"soy punto");return new Symbol(sym.punto,yycolumn,yyline,yytext());}  
":"              {imprimir(yytext(),"soy dosPuntos");return new Symbol(sym.dosPuntos,yycolumn,yyline,yytext());} 
";"              {imprimir(yytext(),"soy puntocoma");return new Symbol(sym.puntoComa,yycolumn,yyline,yytext());}  
","              {imprimir(yytext(),"soy coma");return new Symbol(sym.coma,yycolumn,yyline,yytext());}  
"("              {imprimir(yytext(),"soy parenInicio");return new Symbol(sym.parenInicio,yycolumn,yyline,yytext());}
")"              {imprimir(yytext(),"soy parenFin");return new Symbol(sym.parenFin,yycolumn,yyline,yytext());}
"["              {imprimir(yytext(),"soy corInicio");return new Symbol(sym.corInicio,yycolumn,yyline,yytext());}
"]"              {imprimir(yytext(),"soy corFin");return new Symbol(sym.corFin,yycolumn,yyline,yytext());}

"{"              {imprimir(yytext(),"soy llaveInicio");return new Symbol(sym.llaveInicio,yycolumn,yyline,yytext());}
"}"              {imprimir(yytext(),"soy llaveFin");return new Symbol(sym.llaveFin,yycolumn,yyline,yytext());}
"¿"              {imprimir(yytext(),"soy inteInicio");return new Symbol(sym.inteInicio,yycolumn,yyline,yytext());}
"?"              {imprimir(yytext(),"soy inteFin");return new Symbol(sym.inteFin,yycolumn,yyline,yytext());}

//PARABRAS RESERVADAS
"als"            {imprimir(yytext());return new Symbol(sym.als,yycolumn,yyline,yytext());}
"var"            {imprimir(yytext());return new Symbol(sym.var,yycolumn,yyline,yytext());}
"importar"       {imprimir(yytext());return new Symbol(sym.importar,yycolumn,yyline,yytext());}
"entero"         {imprimir(yytext());return new Symbol(sym.resEntero,yycolumn,yyline,yytext());}
"decimal"        {imprimir(yytext());return new Symbol(sym.resDecimal,yycolumn,yyline,yytext());}
"caracter"       {imprimir(yytext());return new Symbol(sym.resCaracter,yycolumn,yyline,yytext());}
"cadena"         {imprimir(yytext());return new Symbol(sym.resCadena,yycolumn,yyline,yytext());}
"bool"           {imprimir(yytext());return new Symbol(sym.resBool,yycolumn,yyline,yytext());}
"vacio"          {imprimir(yytext());return new Symbol(sym.vacio,yycolumn,yyline,yytext());}
"publico"        {imprimir(yytext());return new Symbol(sym.publico,yycolumn,yyline,yytext());}
"protegido"      {imprimir(yytext());return new Symbol(sym.protegido,yycolumn,yyline,yytext());}
"privado"        {imprimir(yytext());return new Symbol(sym.privado,yycolumn,yyline,yytext());}
"hereda"         {imprimir(yytext());return new Symbol(sym.hereda,yycolumn,yyline,yytext());}
"nuevo"          {imprimir(yytext());return new Symbol(sym.nuevo,yycolumn,yyline,yytext());}
"retornar"       {imprimir(yytext());return new Symbol(sym.retornar,yycolumn,yyline,yytext());}
"llamar"         {imprimir(yytext());return new Symbol(sym.llamar,yycolumn,yyline,yytext());}
"inicio"         {imprimir(yytext());return new Symbol(sym.inicio,yycolumn,yyline,yytext());}
"incluir_HK"     {imprimir(yytext());return new Symbol(sym.incluir_HK,yycolumn,yyline,yytext());}
"llamarHK"       {imprimir(yytext());return new Symbol(sym.llamarHK,yycolumn,yyline,yytext());}


//PALABRAS RESERVADAS DE SENTENCIAS DE CONTROL
"Si"             {imprimir(yytext());return new Symbol(sym.si,yycolumn,yyline,yytext());}
"Sino"           {imprimir(yytext());return new Symbol(sym.sino,yycolumn,yyline,yytext());}
"Seleccion"      {imprimir(yytext());return new Symbol(sym.seleccion,yycolumn,yyline,yytext());}
"Caso"           {imprimir(yytext());return new Symbol(sym.caso,yycolumn,yyline,yytext());}
"Defecto"        {imprimir(yytext());return new Symbol(sym.defecto,yycolumn,yyline,yytext());}
"Para"           {imprimir(yytext());return new Symbol(sym.para,yycolumn,yyline,yytext());}
"Mientras"       {imprimir(yytext());return new Symbol(sym.mientras,yycolumn,yyline,yytext());}
"Hacer"          {imprimir(yytext());return new Symbol(sym.hacer,yycolumn,yyline,yytext());}
"Continuar"      {imprimir(yytext());return new Symbol(sym.continuar,yycolumn,yyline,yytext());}
"Terminar"       {imprimir(yytext());return new Symbol(sym.terminar,yycolumn,yyline,yytext());}
"graphikar_funcion" {imprimir(yytext());return new Symbol(sym.graphikar_funcion,yycolumn,yyline,yytext());}
"Datos"          {imprimir(yytext());return new Symbol(sym.datos,yycolumn,yyline,yytext());}
"Columna"        {imprimir(yytext());return new Symbol(sym.columna,yycolumn,yyline,yytext());}
"Procesar"       {imprimir(yytext());return new Symbol(sym.procesar, yychar,yycolumn,yyline);}
"Donde"          {imprimir(yytext());return new Symbol(sym.donde,yycolumn,yyline,yytext());}
"DondeCada"      {imprimir(yytext());return new Symbol(sym.dondeCada,yycolumn,yyline,yytext());}
"DondeTodo"      {imprimir(yytext());return new Symbol(sym.dondeTodo,yycolumn,yyline,yytext());}
"imprimir"       {imprimir(yytext());return new Symbol(sym.imprimir,yycolumn,yyline,yytext());}

//RESERVADAS COMPUESTAS
{idenImportar}   {imprimir(yytext());return new Symbol(sym.idenImportar,yycolumn,yyline,yytext());}

{entero}         {imprimir(yytext(),"soy entero");return new Symbol(sym.entero,yycolumn,yyline,yytext());}
{decimal}        {imprimir(yytext(),"soy decimal");return new Symbol(sym.decimal,yycolumn,yyline,yytext());}
{cadena}         {imprimir(yytext(),"soy cadena");return new Symbol(sym.cadena,yycolumn,yyline,yytext());}
{bool}           {imprimir(yytext(),"soy bool");return new Symbol(sym.bool,yycolumn,yyline,yytext());}
{iden}           {imprimir(yytext(),"soy iden");return new Symbol(sym.iden,yycolumn,yyline,yytext());}
{caracter}       {imprimir(yytext(),"soy caracter");return new Symbol(sym.caracter,yycolumn,yyline,yytext());}

/* COMENTARIOS */
{Comentario2}    { /* Se ignoran */}
{Comentario1}     { /* Se ignoran */}

/* BLANCOS */
[ \t\r\f\n]+     {/* Se ignoran */}


/* Cualquier Otro */
.                {System.out.println("Error lexico: "+yytext()+ " Linea:"+(yyline+1)+" Columna:"+(yycolumn+1));}


