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
- Spring Cloud Config isn't used. It is disabled with
  spring.cloud.config.enabled=false in the bootstrap files.


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
- You can access the application at http://127.0.0.1:18080/
- You can access the Eureka dashboard at http://127.0.0.1:18761/
- You can access the Turbine dashboard at
http://127.0.0.1:18989/hystrix . The URL for the data stream is
http://172.17.0.10:8989/turbine.stream?cluster=ORDER - the IP-Adresse
changes. Look it up in the Eureka dashboard for service turbine. You
can also connect to a Hystrix stream of an order service.  You need t
use the address http://172.17.0.9:8989/hystrix.stream of the Order
App. The IP address can be found in the Eureka dashboard.

Some remarks on the code. The servers for the infrastruture are pretty simple thanks to Spring Cloud:

- microservice-demo-eureka is the Eureka server for service discovery.
- microservice-demo-zuul is the Zuul server. It distributes the requests to the three microservices.
- microservice-demo-turbine can be used to consolidate the Hystrix metrics and has a Hystrix dashboard.

The microservices are: 
- microservice-demo-catalog is the application to take care of items.
- microserivce-demo-customer is responsible for customers.
- microservice-demo-order does order processing. It uses microservice-demo-catalog and microservice-demo-customer. Ribbon is used for load balancing and Hystrix for resilience.


The microservices have an Java main application in src/test/java to run them stand alone. microservice-demo-order uses a stub for the other services then. Also there are tests that use customer driven contracts. That wy it is ensured that the services provide the correct interface. These CDC tests are used in microservice-demo-order to verify the stubs. In microserivce-demo-customer and microserivce-demo-catalog they are used to verify the implemented REST services.
