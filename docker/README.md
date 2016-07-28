Run Microservices Demo with Docker Compose and Docker Machine
==============================================

Docker Machine can create virtual machines to run Docker on. It
supports Virtual Box but also many other technologies including
several clouds. Docker Compose create all the Docker containers needed
fot the systems.

To run the demo:

- Install Maven, see https://maven.apache.org/download.cgi
- Install Virtual Box from https://www.virtualbox.org/wiki/Downloads
- Install Docker Compose, see
https://docs.docker.com/compose/#installation-and-set-up
- Install Docker, see https://docs.docker.com/installation/
- Install Docker Machine, see https://docs.docker.com/machine/#installation
- Go to directory `microservice-demo` and run `mvn package` there
- Execute `docker-machine create  --virtualbox-memory "4096" --driver
  virtualbox dev` . This will create a new environment called `dev`with Docker
  Machine. It will be virtual machine in Virtual Box with 4GB RAM.
  - Execute `eval "$(docker-machine env dev)"` (Linux / Mac OS X). You
     might need to set your shell: `eval "$(docker-machine env --shell
     bash dev)"`. For Windows it's
    `docker-machine.exe env --shell powershell dev` (Windows,
    Powershell) /  `docker-machine.exe env --shell cmd dev` (Windows,
    cmd.exe). Now the docker tool will use the newly created virtual
    machine as environment.
- Change to the directory `docker` and run `docker-compose
   build`followed by `docker-compose up -d`. 


The result should be:

- Docker Compose builds the Docker images and runs them.
- Use `docker-machine ip dev` to find the IP adress of the virtual machine.
- You can access the application at http://ipadresss:8080/ . It has a
web page with links to all other services.
- You can find the Eureka dashboard at http://ipadress:8761/.
- The Hystrix dashboard can be found at http://ipadress:8080/turbine/hystrix .
- You can access the Turbine dashboard by follwing the link on the
page. The URL for the data stream of all Hystrix data of all Order
nodes is http://172.17.0.10:8989/turbine.stream?cluster=ORDER - the
IP-Adresse changes. Look it up in the Eureka dashboard for service
turbine. You can also connect to a Hystrix stream of an order service.
You need to use the address http://172.17.0.9:8080/hystrix.stream of
the Order App. The IP address can be found in the Eureka dashboard.

- Use `docker-machine rm dev` to destroy the virtual machine.
