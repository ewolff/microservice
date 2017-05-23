Microservice Demo mit Vagrant betreiben
============================

Vagrant kann virtuelle Machine erstellen, auf denen dann Software
installiert werden kann. In diesem Beispiel wird in der virtuellen
Maschine Docker installiert und anschließend die Docker Container deployt.

Um das Beispiel ablaufen zu lassen:

- Installiere Maven, siehe https://maven.apache.org/download.cgi
- Installiere Vagrant, siehe
  http://docs.vagrantup.com/v2/installation/index.html
- Installiere Virtual Box von https://www.virtualbox.org/wiki/Downloads
- Gehe zum Verzeichnis `microservice-demo`  und führe dort `mvn package` aus
- Wechsel zum Verzeichnis `docker-vagrant` und führe `vagrant
   up` aus. Beim jedem Start der Vagrant VM werden die Docker Container mitgestartet.
- Mit `vagrant halt` kann die Vagrnat VM beendet werden. `vagrant destroy` löscht
  alle Dateien der VM. Mit `vagrant ssh` kann man sich in die VM einloggen und mit
 `vagrant provision` die Provisionierung erneut starten. Dann werden die Docker container
  neu gebaut.

Das Ergebnis:

- Eine VirtualBox VM wird von Vagrant gestartet
- Docker wird in der VM gestartet
- Die Anwendung steht unter http://127.0.0.1:18080/ zur
  Verfügung. Dort gibt es auch eine Seite mit Links zu den anderen
  Diensten.
- Das Eureka-Dashboard steht unter http://127.0.0.1:18761/ zur Verfügung.
- Das Hystrix-Dashboard steht unter http://127.0.0.1:18080/turbine/hystrix zur
  Verfügung. Die URL für einen einzelnen Order-Server dort lautet
  http://172.17.0.9:8080/hystrix.stream - wobei die IP-Adresse sich
  ändert. Sie kann im Eureka-Dashboard für den Dienst order-app
  ermittelt werden. Die Alternative ist der Turbine Stream des Order
  Services. Er enthält die Daten aller Order-Servives. Dazu muss die Adresse
  http://172.17.0.10:8989/turbine.stream?cluster=ORDER der Order App genutzt werden -
  wobei die IP-Adresse des Turbine Service sich aus dem Eureka Dashboard
  entnehmen lässt.

Die folgenden Port werden auf `localhost` genutzt:

- 18080 für die Web-Anwendung
- 18761 für Eureka
- 18989 für den Turbine-Server

