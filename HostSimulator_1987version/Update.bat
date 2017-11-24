@Title FSS - Statement Update Simulator -Arun %Date% %Time%
@set path=C:\Program Files\Java\jdk1.5.0_06\bin
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
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\msbase.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\msutil.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\ojdbc14.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\mssqlserver.jar
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\M24log4j-1.2.15.jar
@set LOCALCLASSPATH=%CLASSDIR%;%LOCALCLASSPATH%;
:x
@cls
@java -server -classpath "%LOCALCLASSPATH%" api.BalanceInsert -Xincgc -Xms512M -Xmx512M -Xincgc -XSS:256M
pause