Microservice Demo
==================

Dieses Projekt erzeugt eine VM mit der vollständigen Microservice-Demo in 
Docker-Containern. Die Services sind mit Java, Spring und Spring Cloud
implementiert.

Das System hat drei Microservices:
- Order um Bestellungen handzuhaben
- Customer für Kundendaten
- Catalog für die Waren

Technologien
------------

- Eureka für Service Discovery
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
- Spring Cloud Config wurde nicht genutzt - daher
  spring.cloud.config.enabled=false in den Bootstrap-Files.


Ausführen
---------

- Installiere Maven, siehe https://maven.apache.org/download.cgi
- Installiere Vagrant, siehe
  http://docs.vagrantup.com/v2/installation/index.html
- Installiere Virtual Box von https://www.virtualbox.org/wiki/Downloads
- Gehe zum Verzeichnis `microservice-demo`  und führe dort `mvn install` aus
- Wechsel zum Verzeichnis `docker` und führe `vagrant
   up` aus.

Das Ergebnis:

- Eine VirtualBox VM wird von Vagrant gestartet
- Docker wird in der VM gestartet
- Die Anwendung steht unter http://127.0.0.1:18080/ zur Verfügung.
- Das Eureka-Dashboard steht unter http://127.0.0.1:18761/ zur Verfügung.
- Das Hystrix-Dashboard steht unter http://127.0.0.1:18989/hystrix zur
  Verfügung. Die URL für einen einzelnen Order-Server dort lautet
  http://172.17.0.9:8989/hystrix.stream - wobei die IP-Adresse sich
  ändert. Sie kann im Eureka-Dashboard für den Dienst order-app
  ermittelt werden. Die Alternative ist der Turbine Stream des Order
  Services. Er enthält die Daten aller Order-Servives. Dazu muss die Adresse
  http://172.17.0.10:8989/turbine.stream?cluster=ORDER der Order App genutzt werden -
  wobei die IP-Adresse des Turbine Service sich aus dem Eureka Dashboard
  entnehmen lässt.


Hinweise zum Code
-----------------

Die Server für die Infrastruktur sind recht einfach - dank Spring Cloud:

- microservice-demo-eureka ist der Eureka-Server für Service Discovery.
- microservice-demo-zuul implementiert den Zuul-Server. Er verteilt die eingehenden Anfragen auf die drei Microservices.
- microservice-demo-turbine kann zum Konsolidieren der Hystrix-Metriken genutzt werden und bietet auerdem ein Hystrix-Dashboard an.

Die microservices sind: 
- microservice-demo-catalog verwaltet die verschiedenen Waren.
- microserivce-demo-customer ist für die Kunden zuständig
- microservice-demo-order implementiert die Bearbeitung der Bestellungen. Der Microservice nutzt microservice-demo-catalog und microservice-demo-customer. Der Service nutzt Ribbon für Load Balancing und Hystrix für Resilience.

Die Microservices haben ein Java-Hauptprogramm im src/test/java, um die Services einzeln zu betreiben - also ohne Eureka oder die anderen Services. Dann werden die anderen Services durch Stubs simuliert. Außerdem gibt es Tests, die Customer Driven Contract verwenden. Damit wird sichergestellt, dass die anderen Services die Schnittstellen korrekt implementieren. Außerdem werden die CDC-Tests in microserice-demo-order genutzt, um die Stubs zu überprüfen. In microserivce-demo-customer und microserivce-demo-catalog werden sie genutzt, um die korrekte Implementierung der REST-Services abzusichern.
