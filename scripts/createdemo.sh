#!/bin/sh
cd microservice-demo
mvn package
cd ..
cd docker
docker-machine create --virtualbox-memory "4096" --driver virtualbox ms
eval "$(docker-machine env ms)"
docker-compose build
