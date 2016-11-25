#!/bin/bash
#
# wrapper script for jvm startup
# uses environment variables INSTANCE_NAME, EXTRA_JAVA_OPTS and ADDITIONAL_PARAMETERS for JVM start
#
# NOTE: This writes GC logs to /data/logs! Make sure to bind this folder to an external volume!
$JAR_NAME=

if [ -z "$PROFILE" ] ; then
  export PROFILE="dev"
  echo "No \$PROFILE variable found. Defaulting to profile ${PROFILE}"
fi
if [ -z "$JAR_NAME" ] ; then
  export JAR_NAME="microservice-demo-order.jar"
  echo "No \$JAR_NAME variable found. Defaulting to ${JAR_NAME}"
fi

if [ -z "$EXTRA_JAVA_OPTS" ] ; then
    # NOTE: The gc log is intentionally named *.gc-log, so it is not logrotated by unix logrotate on the host
    export EXTRA_JAVA_OPTS="-Xms128m -Xmx512m \
        -Djava.security.egd=file:/dev/./urandom -Dlog4j.configurationFile=log4j2.xml
        -Xloggc:/data/logs/microservice-$(date +%Y%m%d_%H%M).gc-log -XX:+UseGCLogFileRotation \
        -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=50M\
        -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/data/logs/ga-edge-heapdump-`date +%Y%m%d_%H%M`.hprof \
        -XX:+PrintGCDateStamps -verbose:gc -XX:+PrintGCDetails \
        -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime \
        -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true"
  echo "No \$EXTRA_JAVA_OPTS variable found. Using default EXTRA_JAVA_OPTS=$EXTRA_JAVA_OPTS"
fi

MIN_JAVA_OPTS="-D${INSTANCE_NAME} -server -XX:+PrintCommandLineFlags -XX:+PrintFlagsFinal \
-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8"

echo "\
Starting ${JAR_NAME}..."
exec /usr/bin/java -server ${MIN_JAVA_OPTS} ${EXTRA_JAVA_OPTS} -jar /data/${JAR_NAME} --spring.profiles.active=${PROFILE} ${ADDITIONAL_PARAMETERS}
