SET JAVA_HOME="C:\Program Files\Java\jdk1.7.0_80\bin"
SET PATH=%JAVA_HOME%;%PATH%
SET CLASSPATH=%JAVA_HOME%;
SET JFLEX_HOME= C:\fuentes\jflex-1.6.0

cd C:\Users\Jose2\Documents\NetBeansProjects\Proyecto1_Compi2_1s_2017\src\Graphik\Analisis
java  -jar %JFLEX_HOME%\lib\jflex-1.6.0.jar Scanner.jflex
pause
