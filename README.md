Micro Service Demo
==============

[German / Deutsch](LIESMICH.md)

This project creates a VM with the complete micro service demo system
in Docker containers.

To run:

- Install Vagrant as discussed at
  http://docs.vagrantup.com/v2/installation/index.html
- Install Virtual Box from https://www.virtualbox.org/wiki/Downloads
- Go to directory `microservice-demo` and run `mvn install` there
- Change to the directory `docker` and run `vagrant
   up`

The result should be:

- A new VirtualBox VM is fired up by Vagrant
- Docker is installed in the VM
- You can access the application at http://localhost:18080/
- You can access the monitoring for the application at http://localhost:18080/monitor/
- You can access the Eureka dashboard at http://localhost:18761/
- You can access the Turbine dashboard at
http://localhost:18989/hystrix . The URL for the data stream is
http://172.17.0.9:8989/turbine.stream - the IP-Adresse changes. Look
it up in the Eureka dashboard for service turbine. Turbine doesn't
work at the moment.

