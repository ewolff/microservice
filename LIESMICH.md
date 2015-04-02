Micro Service Demo
==============

Dieses Projekt erzeugt eine VM mit der vollständigen Micro-Service-Demo in 
Docker-Containern. Die Services sind mit Java, Spring und Spring Cloud
implementiert.

Das System hat drei Microservices:
- Order um Bestellungen handzuhaben
- Customer für Kundendaten
- Catalog für die Waren

Folgende Technologien sind in dem System verwendet worden:
- Eureka für Discovery
- Ribbon für Load Balancing. Siehe die Klassen CatalogClient und
  CustomerClient in com.ewolff.microservice.order.clients im Projekt
  microservice-demo-order .
- Hystrix für Resilience. Siehe CatalogClient in
  com.ewolff.microservice.order.clients im microservice-demo-order
  Projekt . Der CustomerClient nutzt Hystrix nicht. So ist
  nachvollziehbar, welche Probleme der crash des Customer microservice
  verursacht - der Order microservice wird praktisch unbrauchbar.
  - Hystrix hat ein Dashboard. Turbine kann genutzt werden, um die
  Hystrix-Daten aus mehreren Quellen zu kombinieren. Allerdings
  funktioniert das noch nicht. 
- Zuul wird dazu genutzt, um HTTP-Requests von außen auf die
  verschiedenen Dienste zu verteilen.

Zum Ausführen:

- Installiere Vagrant, siehe
  (http://docs.vagrantup.com/v2/installation/index.html)
- Installiere Virtual Box von (https://www.virtualbox.org/wiki/Downloads)
- Gehe zum Verzeichnis `microservice-demo`  und führe dort `mvn install` aus
- Wechsel zum Verzeichnis `docker` und führe `vagrant
   up` aus.

Das Ergebnis:

- Eine VirtualBox VM wird von Vagrant gestartet
- Docker wird in der VM gestartet
- Die Anwendung steht unter (http://localhost:18080/) zur Verfügung.
- Das Monitoring der Anwendung steht unter (http://localhost:18080/monitor/) zur Verfügung.
- Das Eureka-Dashboard steht unter (http://localhost:18761/) zur Verfügung.
- Das Turbine-Dashboard steht unter (http://localhost:18989/hystrix) zur
  Verfügung. Die URL dort lautet
  (http://172.17.0.9:8989/turbine.stream) - wobei die IP-Adresse sich
  ändert. Sie kann im Eureka-Dashboard für den Dienst turbine
  ermittelt werden. Turbine funktioniert im Moment nicht
  korrekt, siehe
  (https://github.com/spring-cloud/spring-cloud-netflix/issues/292). Daher
  muss die Adresse  (http://172.17.0.9:8989/hystrix.stream) der Order
  App genutzt werden - wobei die IP-Adresse sich aus dem Eureka
  Dashboard entnehmen lässt.

