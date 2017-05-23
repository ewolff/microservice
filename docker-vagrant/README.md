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
- Go to directory `microservice-demo` and run `mvn package` there
- Change to the directory `docker-vagrant` and run `vagrant
   up`. Each time you start the Vagrant VM the Docker containers will be started, too.

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

Additonal hints:

- Use `vagrant halt` to shut down the system or `vagrant destroy` to
  to delete the VM.
- If you wantt login to the VM, please use `vagrant ssh`.
- If you update the code, you need to rebuild it and then do a new
  provisioning using `vagrant provision` . Then the Docker containers in the Vagrant VM will be rebuild.

The following ports will be used on `localhost` :

- 18080 for the web application
- 18761 for Eureka
- 18989 for the Turbine server
