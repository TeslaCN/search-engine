#!/bin/sh

PWD=$(cd $(dirname $0); pwd)
cd $PWD

print_usage ()
{
  echo "Usage: sh run.sh COMMAND"
  echo "where COMMAND is one of the follows:"
  exit 1
}

if [ $# = 0 ] || [ $1 = "help" ]; then
  print_usage
fi

COMMAND=$1
shift

if [ "$JAVA_HOME" = "" ]; then
  echo "Error: JAVA_HOME is not set."
  exit 1
fi

JAVA=${JAVA_HOME}/bin/java

# HEAP_OPTS="-Xmx4g"

JAR_NAME=`ls |grep jar|grep -v original-`

CLASSPATH=${CLASSPATH}:${JAVA_HOME}/lib/tools.jar
CLASSPATH=${CLASSPATH}:conf
CLASSPATH=${CLASSPATH}:${JAR_NAME}
for f in lib/*.jar; do
    CLASSPATH=${CLASSPATH}:${f};
done

params=$@

if [ "$COMMAND" = "SearchApplication" ]; then
    CLASS=ltd.scau.search.SearchApplication
else
    CLASS=${COMMAND}
fi

# 这句放在最后定义
CLASSPATH=$PWD/conf:$CLASSPATH

"$JAVA" ${HEAP_OPTS} ${PERM_OPTS} -classpath "$CLASSPATH" ${CLASS} ${params}

# "$JAVA" ${HEAP_OPTS} ${PERM_OPTS} -classpath "$CLASSPATH" -jar ${JAR_NAME} ${params}

