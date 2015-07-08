# see https://registry.hub.docker.com/u/frolvlad/alpine-oraclejdk8/
FROM alpine:3.2

ENV JAVA_VERSION=8 \
    JAVA_UPDATE=45 \
    JAVA_BUILD=14 \
    JAVA_HOME=/usr/lib/jvm/default-jvm

# Here we use several hacks collected from https://github.com/gliderlabs/docker-alpine/issues/11:
# 1. install GLibc (which is not the cleanest solution at all)
# 2. hotfix /etc/nsswitch.conf, which is apperently required by glibc and is not used in Alpine Linux

RUN apk add --update wget ca-certificates && \
    cd /tmp && \
    wget -nv "https://circle-artifacts.com/gh/andyshinn/alpine-pkg-glibc/6/artifacts/0/home/ubuntu/alpine-pkg-glibc/packages/x86_64/glibc-2.21-r2.apk" \
         "https://circle-artifacts.com/gh/andyshinn/alpine-pkg-glibc/6/artifacts/0/home/ubuntu/alpine-pkg-glibc/packages/x86_64/glibc-bin-2.21-r2.apk" && \
    apk add --allow-untrusted glibc-2.21-r2.apk glibc-bin-2.21-r2.apk && \
    /usr/glibc/usr/bin/ldconfig /lib /usr/glibc/usr/lib && \
    echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf && \
    wget -nv --header "Cookie: oraclelicense=accept-securebackup-cookie;" \
        "http://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION}u${JAVA_UPDATE}-b${JAVA_BUILD}/jdk-${JAVA_VERSION}u${JAVA_UPDATE}-linux-x64.tar.gz" && \
    tar xzf "jdk-${JAVA_VERSION}u${JAVA_UPDATE}-linux-x64.tar.gz" && \
    mkdir -p /usr/lib/jvm && \
    mv "/tmp/jdk1.${JAVA_VERSION}.0_${JAVA_UPDATE}" "/usr/lib/jvm/java-${JAVA_VERSION}-oracle" && \
    ln -s "java-${JAVA_VERSION}-oracle" $JAVA_HOME && \
    ln -s $JAVA_HOME/bin/java /usr/bin/java && \
    ln -s $JAVA_HOME/bin/javac /usr/bin/javac && \
    rm -rf $JAVA_HOME/*src.zip && \
    rm -rf $JAVA_HOME/lib/missioncontrol \
           $JAVA_HOME/lib/visualvm \
           $JAVA_HOME/lib/*javafx* \
           $JAVA_HOME/jre/lib/plugin.jar \
           $JAVA_HOME/jre/lib/ext/jfxrt.jar \
           $JAVA_HOME/jre/bin/javaws \
           $JAVA_HOME/jre/lib/javaws.jar \
           $JAVA_HOME/jre/lib/desktop \
           $JAVA_HOME/jre/plugin \
           $JAVA_HOME/jre/lib/deploy* \
           $JAVA_HOME/jre/lib/*javafx* \
           $JAVA_HOME/jre/lib/*jfx* \
           $JAVA_HOME/jre/lib/amd64/libdecora_sse.so \
           $JAVA_HOME/jre/lib/amd64/libprism_*.so \
           $JAVA_HOME/jre/lib/amd64/libfxplugins.so \
           $JAVA_HOME/jre/lib/amd64/libglass.so \
           $JAVA_HOME/jre/lib/amd64/libgstreamer-lite.so \
           $JAVA_HOME/jre/lib/amd64/libjavafx*.so \
           $JAVA_HOME/jre/lib/amd64/libjfx*.so && \
    rm -rf $JAVA_HOME/jre/bin/jjs \
           $JAVA_HOME/jre/bin/keytool \
           $JAVA_HOME/jre/bin/orbd \
           $JAVA_HOME/jre/bin/pack200 \
           $JAVA_HOME/jre/bin/policytool \
           $JAVA_HOME/jre/bin/rmid \
           $JAVA_HOME/jre/bin/rmiregistry \
           $JAVA_HOME/jre/bin/servertool \
           $JAVA_HOME/jre/bin/tnameserv \
           $JAVA_HOME/jre/bin/unpack200 \
           $JAVA_HOME/jre/lib/ext/nashorn.jar \
           $JAVA_HOME/jre/lib/jfr.jar \
           $JAVA_HOME/jre/lib/jfr \
           $JAVA_HOME/jre/lib/oblique-fonts && \
    apk del wget ca-certificates && \
    rm /tmp/* /var/cache/apk/*