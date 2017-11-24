@Title Simulator %Date% %Time%
@cls
@ECHO OFF
@set LOCALCLASSPATH=%LOCALCLASSPATH%;.\lib\commons-lang-2.2.jar
@java -classpath "%LOCALCLASSPATH%" simulator.PaymateSimulator %*