Micro Service Demo
==============

Dieses Projekt erzeugt eine VM mit der vollständigen Micro-Service-Demo in 
Docker-Containern. 

Zum Ausführen:

- Installiere Vagrant, siehe
  http://docs.vagrantup.com/v2/installation/index.html
- Installiere Virtual Box von https://www.virtualbox.org/wiki/Downloads
- Gehe zum Verzeichnis `microservice-demo`  und führe dort `mvn install` aus
- Wechsel zum Verzeichnis `docker` und führe `vagrant
   up` aus.

Das Ergebnis:

- Eine VirtualBox VM wird von Vagrant gestartet
- Docker wird in der VM gestartet
- Die Anwendung steht unter http://localhost:18080/ zur Verfügung.
- Das Monitoring der Anwendung steht unter http://localhost:18080/monitor/ zur Verfügung.
- Das Eureka-Dashboard steht unter http://localhost:18761/ zur Verfügung.
- Das Turbine-Dashboard steht unter http://localhost:18989/hystrix zur
  Verfügung. Die URL dort lautet
  http://172.17.0.9:8989/turbine.stream - wobei die IP-Adresse sich
  ändert. Sie kann im Eureka-Dashboard für den Dienst turbine
  ermittelt werden. Turbine funktioniert im Moment nicht korrekt.

