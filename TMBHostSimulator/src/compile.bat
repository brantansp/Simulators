@set path=C:\Program Files\Java\jdk1.6.0_16\bin
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\cryptix32.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\cryptix32-pgp.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-lang-2.2.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-logging-1.0.4.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-beanutils-1.7.0.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-configuration-1.3.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-collections-3.2.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-dbutils-1.1.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-pool-1.3.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-dbcp-1.2.1.jar
@set LOCALCLASSPATH=%CLASSDIR%;%LOCALCLASSPATH%;
@javac -classpath "%LOCALCLASSPATH%" -nowarn %1 %2 %3 %4 %5 %6 %7 %8 %9 
@echo ---Compiled Successfully---
