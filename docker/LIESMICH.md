Microservice Demo mit Docker Machine und Dokcer 
============================

Vagrant kann virtuelle Machine erstellen, auf denen dann Software
installiert werden kann. In diesem Beispiel wird in der virtuellen
Maschine Docker installiert und anschließend die Docker Container deployt.

Um das Beispiel ablaufen zu lassen:

- Installiere Docker Compose, siehe
https://docs.docker.com/compose/#installation-and-set-up
- Installiere Docker Machine, siehe https://docs.docker.com/machine/#installation
- Installiere Virtual Box von https://www.virtualbox.org/wiki/Downloads
- Gehe zum Verzeichnis `microservice-demo`  und führe dort `mvn package` aus
- Führe `docker-machine create  --virtualbox-memory "4096" --driver
  virtualbox dev` aus. Das erzeugt eine neue Umgebung names`dev`mit Docker
  Machine. Es wird eine virtuelle Machine in Virtual Box mit 4GB RAM sein.
 - Führe `eval "$(docker-machine env dev)"` (Linux / Mac OS X) oder
    `docker-machine.exe env --shell powershell dev` (Windows,
    Powershell) /  `docker-machine.exe env --shell cmd dev` (Windows,
    cmd.exe) aus. Das docker Kommando nutzt nun die neue virtuelle Maschine als Umgebung.
 - Führe im Verzeichnis `docker` das Kommando `docker-compose
   build` aus und dann `docker-compose up`. 

Das Ergebnis:

- Docker Compose erzeugt die Docker Images und startet sie.
- Find mit `docker-machine ip dev`die IP address der vrituellen Maschine
- Eine VirtualBox VM wird von Vagrant gestartet
- Docker wird in der VM gestartet
- Die Anwendung steht unter http://ipadresss:8080/ zur
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
- Nutze `docker-machine rm dev`, um die virtuelle Maschine wieder zu zerstören.
