echo $CLASSPATH
export PATH=/usr/java5/bin:$PATH
export CLASSPATH=/home/aravind/HostSimulator/lib/cryptix32.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/cryptix32-pgp.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-lang-2.2.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-logging-1.0.4.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-beanutils-1.7.0.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-configuration-1.3.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-collections-3.2.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-dbutils-1.1.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-pool-1.3.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/commons-dbcp-1.2.1.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/msbase.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/msutil.jar:$CLASSPATH
export CLASSPATH=/home/aravind/HostSimulator/lib/mssqlserver.jar:$CLASSPATH:$PATH

javac  -classpath $CLASSPATH simulator/HostSimulator.java
