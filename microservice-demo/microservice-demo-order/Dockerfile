FROM ewolff/docker-java
EXPOSE 8080
WORKDIR /data
RUN mkdir /data/logs
ADD target/microservice-demo-order-0.0.1-SNAPSHOT.jar /data/microservice-demo-order.jar

# add wrapper script
ADD start.sh /data/start.sh
RUN chmod 0755 /data/start.sh

# start docker command
ENTRYPOINT ["/bin/sh", "/data/start.sh"]