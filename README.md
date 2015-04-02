Micro Service Demo
==============

[German / Deutsch](LIESMICH.md)

This project creates a VM with the complete micro service demo system
in Docker containers inside a Vagrant VM. The services are implemented
in Java using Spring and Spring Cloud.

It uses three microservices:
- Order to process orders.
- Customer to handle customer data.
- Catalog to handle the items in the catalog.

The technologies used:
- Eureka for Lookup
- Ribbon for Load Balancing. See the classes CatalogClient and
  CustomerClient in com.ewolff.microservice.order.clients in the
  microservice-demo-order project.
- Hystrix is used for resilience. See CatalogClient in
  com.ewolff.microservice.order.clients in the microservice-demo-order
  project . Note that the CustomerClient won't use Hystrix. This way
  you can see how a crash of the Customer microservices makes the
  Order microservice useless.
- Hystrix has a dashboard. Turbine can be used to combine the data
from multiple sources. However, this does not work at the moment.
- Zuul is used to route HTTP requests from the outside to the
  different services.


To run:

- Install Vagrant as discussed at
  (http://docs.vagrantup.com/v2/installation/index.html)
- Install Virtual Box from (https://www.virtualbox.org/wiki/Downloads)
- Go to directory `microservice-demo` and run `mvn install` there
- Change to the directory `docker` and run `vagrant
   up`

The result should be:

- A new VirtualBox VM is fired up by Vagrant
- Docker is installed in the VM
- You can access the application at (http://localhost:18080/)
- You can access the Eureka dashboard at (http://localhost:18761/)
- You can access the Turbine dashboard at
(http://localhost:18989/hystrix) . The URL for the data stream is
(http://172.17.0.9:8989/turbine.stream) - the IP-Adresse changes. Look
it up in the Eureka dashboard for service turbine. Turbine doesn't
work at the moment, see
(https://github.com/spring-cloud/spring-cloud-netflix/issues/292) . So
you need t use the address (http://172.17.0.9:8989/hystrix.stream) of
the Order App. The IP address can be found in the Eureka dashboard.


