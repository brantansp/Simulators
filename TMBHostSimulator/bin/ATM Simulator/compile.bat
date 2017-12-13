@set PATH=C:\Program Files\Java\jdk1.5.0_17\bin
@set CLASSDIR=.\classes;
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-lang-2.2.jar
@set LOCALCLASSPATH=%CLASSDIR%;%LOCALCLASSPATH%;
@javac -classpath "%LOCALCLASSPATH%" -nowarn %1 %2 %3 %4 %5 %6 %7 %8 %9 
@echo ---Compiled Successfully---