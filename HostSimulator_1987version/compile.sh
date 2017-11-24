echo $CLASSPATH
export PATH=/usr/java5/bin:$PATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/cryptix32.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/cryptix32-pgp.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-lang-2.2.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-logging-1.0.4.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-beanutils-1.7.0.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-configuration-1.3.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-collections-3.2.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-dbutils-1.1.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-pool-1.3.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/commons-dbcp-1.2.1.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/msbase.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/msutil.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/ojdbc14.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/log4j-1.2.9.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/M24log4j-1.2.15.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/classes12.jar:$CLASSPATH
export CLASSPATH=/home/qctms103/bankhostsimulator_iso87/lib/mssqlserver.jar:$CLASSPATH:$PATH

javac  -classpath $CLASSPATH simulator/HostSimulator.java
