Microservice Demo
=============

Dieses Projekt ist eine Demo für das
[Microservices Buch](http://microservices-buch.de/) ([English](http://microservices-book.com/)).

Dieses Projekt erzeugt eine vollständigen Microservice-Demo in 
Docker-Containern. Die Services sind mit Java, Spring und Spring Cloud
implementiert.

Das System hat drei Microservices:
- Order um Bestellungen entgegenzunehmen
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

Das Beispiel kann entweder mit [Vagrant](docker-vagrant/LIESMICH.md) betrieben werden - oder mit [Docker Machine und Docker
Compose](docker/LIESMICH.md). Details finden sich in [Wie laufen](WIE-LAUFEN.md).

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
