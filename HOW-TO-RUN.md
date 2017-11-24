# How to Run

This is a step-by-step guide how to run the example:

## Installation

* The example is implemented in Java. See
   https://www.java.com/en/download/help/download_options.xml . The
   examples need to be compiled so you need to install a JDK (Java
   Development Kit). A JRE (Java Runtime Environment) is not
   sufficient. After the installation you should be able to execute
   `java` and `javac` on the command line.

* The example run in Docker Containers. You need to install Docker
  Community Edition, see https://www.docker.com/community-edition/
  . You should be able to run `docker` after the installation.

* The example need a lot of RAM. You should configure Docker to use 4
  GB of RAM. Otherwise Docker containers might be killed due to lack
  of RAM. On Windows and macOS you can find the RAM setting in the
  Docker application under Preferences/ Advanced.
  
* After installing Docker you should also be able to run
  `docker-compose`. If this is not possible, you might need to install
  it separately. See https://docs.docker.com/compose/install/ .

## Build

Change to the directory `microservice-demo` and run `./mvnw clean
package` or `./mvnw.cmd clean package` (Windows). This will take a while:

```
[~/microservice/microservice-demo]./mvnw clean package
....
[INFO] 
[INFO] --- maven-jar-plugin:2.6:jar (default-jar) @ microservice-demo-zuul-server ---
[INFO] Building jar: /Users/wolff/Desktop/microservice/microservice-demo/microservice-demo-zuul-server/target/microservice-demo-zuul-server-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:1.5.2.RELEASE:repackage (default) @ microservice-demo-zuul-server ---
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] microservice-demo .................................. SUCCESS [  1.455 s]
[INFO] microservice-demo-eureka-server .................... SUCCESS [ 16.951 s]
[INFO] microservice-demo-turbine-server ................... SUCCESS [  0.454 s]
[INFO] microservice-demo-customer ......................... SUCCESS [ 17.268 s]
[INFO] microservice-demo-catalog .......................... SUCCESS [ 19.457 s]
[INFO] microservice-demo-order ............................ SUCCESS [ 18.629 s]
[INFO] microservice-demo-zuul-server ...................... SUCCESS [  0.266 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:15 min
[INFO] Finished at: 2017-09-07T22:02:55+02:00
[INFO] Final Memory: 62M/429M
[INFO] ------------------------------------------------------------------------
```

If this does not work:

* Ensure that `settings.xml` in the directory `.m2` in your home
directory contains no configuration for a specific Maven repo. If in
doubt: delete the file.

* The tests use some ports on the local machine. Make sure that no
server runs in the background.

* Skip the tests: `./mvnw clean package
  -Dmaven.test.skip=true` or `./mvnw.cmd clean package
  -Dmaven.test.skip=true` (Windows).

* In rare cases dependencies might not be downloaded correctly. In
  that case: Remove the directory `repository` in the directory `.m2`
  in your home directory. Note that this means all dependencies will
  be downloaded again.

## Run the containers

First you need to build the Docker images. Change to the directory
`docker` and run `docker-compose build`. This will download some base
images, install software into Docker images and will therefore take
its time:

```
[~/microservice/docker]docker-compose build 
....
Removing intermediate container 5ed9d5e47a14
Step 4/4 : EXPOSE 8080
 ---> Running in ea27ee231d2b
 ---> 7f7f51fc9fd4
Removing intermediate container ea27ee231d2b
Successfully built 7f7f51fc9fd4
Successfully tagged ms_order:latest
```

Afterwards the Docker images should have been created. They have the prefix
`ms`:

```
[~/microservice/docker]docker images
REPOSITORY                                      TAG                 IMAGE ID            CREATED              SIZE
ms_order                                        latest              7f7f51fc9fd4        50 seconds ago       228MB
ms_turbine                                      latest              8a5cfb2a7b01        54 seconds ago       210MB
ms_catalog                                      latest              395f7bdc04f3        58 seconds ago       228MB
ms_customer                                     latest              abeed8b882db        About a minute ago   228MB
ms_zuul                                         latest              dc240509b408        About a minute ago   210MB
ms_eureka                                       latest              6081c8f9204f        About a minute ago   210MB
```

Now you can start the containers using `docker-compose up -d`. The
`-d` option means that the containers will be started in the
background and won't output their stdout to the command line:

```
[~/microservice/docker]docker-compose up -d
Recreating ms_eureka_1 ... 
Recreating ms_eureka_1 ... done
Recreating ms_zuul_1 ... 
Recreating ms_customer_1 ... 
Recreating ms_zuul_1
Recreating ms_order_1 ... 
Recreating ms_customer_1
Recreating ms_turbine_1 ... 
Creating ms_catalog_1 ... 
Recreating ms_order_1
Recreating ms_turbine_1
Recreating ms_customer_1 ... done
```

Check wether all containers are running:

```
[~/microservice/docker]docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
1f66de9969c0        ms_turbine          "/bin/sh -c '/usr/..."   19 seconds ago      Up 16 seconds       0.0.0.0:8989->8989/tcp   ms_turbine_1
7fda8c451878        ms_customer         "/bin/sh -c '/usr/..."   19 seconds ago      Up 15 seconds       8080/tcp                 ms_customer_1
d26252586c7b        ms_order            "/bin/sh -c '/usr/..."   19 seconds ago      Up 16 seconds       8080/tcp                 ms_order_1
26c7f0d59922        ms_zuul             "/bin/sh -c '/usr/..."   19 seconds ago      Up 17 seconds       0.0.0.0:8080->8080/tcp   ms_zuul_1
45fd39ef1bff        ms_catalog          "/bin/sh -c '/usr/..."   19 seconds ago      Up 17 seconds       8080/tcp                 ms_catalog_1
39a1160c11cc        ms_eureka           "/bin/sh -c '/usr/..."   20 seconds ago      Up 19 seconds       0.0.0.0:8761->8761/tcp   ms_eureka_1
```
`docker ps -a`  also shows the terminated Docker containers. That is
useful to see Docker containers that crashed rigth after they started.

If one of the containers is not running, you can look at its logs using
e.g.  `docker logs ms_order_1`. The name of the container is
given in the last column of the output of `docker ps`. Looking at the
logs even works after the container has been
terminated. If the log says that the container has been `killed`, you
need to increase the RAM assigned to Docker to e.g. 4GB. On Windows
and macOS you can find the RAM setting in the Docker application under
Preferences/ Advanced.
  
If you need to do more trouble shooting open a shell in the container
using e.g. `docker exec -it ms_catalog_1 /bin/sh` or execute
command using `docker exec ms_catalog_1 /bin/ls`.

You can access:

* The application through Zuul at http://localhost:8080/
* The Eureka dashboard at http://localhost:8761/
* The Hystrix dashboard at http://localhost:8989/

You can terminate all containers using `docker-compose down`.
