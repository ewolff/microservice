Run Microservices Demo with Vagrant
==========================

Vagrant can set up virtual machines to install software on them. In
this example Vagrant install Docker on the virtual machine and deploys
Docker Containers afterwards.

To run the demo:

- Install Maven, see https://maven.apache.org/download.cgi
- Install Virtual Box from https://www.virtualbox.org/wiki/Downloads
- Install Vagrant as discussed at
  http://docs.vagrantup.com/v2/installation/index.html
- Go to directory `microservice-demo` and run `mvn install` there
- Change to the directory `docker-vagrant` and run `vagrant
   up`. Each time you start the Vagrant VM the Docker containers will be started, too.
- Use `vagrant halt` to shut down the system or `vagrant destroy` to
  to delete the VM. Login to the VM using `vagrant ssh`. Do a new
  provisioning using `vagrant provision` . Then the containers will be rebuild.

The result should be:

- A new VirtualBox VM is fired up by Vagrant
- Docker is installed in the VM
- You can access the application at http://127.0.0.1:18080/ . It has a
  web page with links to all other services.
- You can access the Eureka dashboard at http://127.0.0.1:18761/
- You can access the Turbine dashboard at
http://127.0.0.1:18080/turbine/hystrix . The URL for the data stream of all
Hystrix data of all Order nodes is
http://172.17.0.10:8989/turbine.stream?cluster=ORDER - the IP-Adresse
changes. Look it up in the Eureka dashboard for service turbine. You
can also connect to a Hystrix stream of an order service.  You need to
use the address http://172.17.0.9:8080/hystrix.stream of the Order
App. The IP address can be found in the Eureka dashboard.
