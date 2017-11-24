# Beispiel starten

Die ist eine Schritt-für-Schritt-Anleitung zum Starten der Beispiele.
Informationen zu Maven und Docker finden sich im
[Cheatsheet-Projekt](https://github.com/ewolff/cheatsheets-DE).

## Installation

* Die Beispiele sind in Java implementiert. Daher muss Java
  installiert werden. Die Anleitung findet sich unter
  https://www.java.com/en/download/help/download_options.xml . Da die
  Beispiele kompiliert werden müssen, muss ein JDK (Java Development
  Kit) installiert werden. Das JRE (Java Runtime Environment) reicht
  nicht aus. Nach der Installation sollte sowohl `java` und `javac` in
  der Eingabeaufforderung möglich sein.

* Die Beispiele laufen in Docker Containern. Dazu ist eine
  Installation von Docker Community Edition notwendig, siehe
  https://www.docker.com/community-edition/ . Docker kann mit
  `docker` aufgerufen werden. Das sollte nach der Installation ohne
  Fehler möglich sein.

* Die Beispiele benötigen zum Teil sehr viel Speicher. Daher sollte
  Docker ca. 4 GB zur Verfügung haben. Sonst kann es vorkommen, dass
  Docker Container aus Speichermangel beendet werden. Unter Windows
  und macOS findet sich die Einstellung dafür in der Docker-Anwendung
  unter Preferences/ Advanced.

* Nach der Installation von Docker sollte `docker-compose` aufrufbar
  sein. Wenn Docker Compose nicht aufgerufen werden kann, ist es nicht
  als Teil der Docker Community Edition installiert worden. Dann ist
  eine separate Installation notwendig, siehe
  https://docs.docker.com/compose/install/ .

## Build

Wechsel in das Verzeichnis `microservice-demo` und starte `./mvnw clean
package` bzw. `mvnw.cmd clean package` (Windows). Das wird einige Zeit dauern:

```
[~/microservice/microservice-demo]./mvnw clean package
....
[INFO] 
[INFO] --- maven-jar-plugin:2.6:jar (default-jar) @ microservice-demo-zuul-server ---
[INFO] Building jar: /Users/wolff/Desktop/microservice/microservice-demo/microservice-demo-zuul-server/target/microservice-demo-zuul-server-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:1.5.2.RELEASE:repackage (default) @ microservice-demo-zuul-server ---
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] microservice-demo .................................. SUCCESS [  1.455 s]
[INFO] microservice-demo-eureka-server .................... SUCCESS [ 16.951 s]
[INFO] microservice-demo-turbine-server ................... SUCCESS [  0.454 s]
[INFO] microservice-demo-customer ......................... SUCCESS [ 17.268 s]
[INFO] microservice-demo-catalog .......................... SUCCESS [ 19.457 s]
[INFO] microservice-demo-order ............................ SUCCESS [ 18.629 s]
[INFO] microservice-demo-zuul-server ...................... SUCCESS [  0.266 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:15 min
[INFO] Finished at: 2017-09-07T22:02:55+02:00
[INFO] Final Memory: 62M/429M
[INFO] ------------------------------------------------------------------------
```

Weitere Information zu Maven gibt es im
[Maven Cheatsheet](https://github.com/ewolff/cheatsheets-DE/blob/master/MavenCheatSheet.md).

Falls es dabei zu Fehlern kommt:

* Stelle sicher, dass die Datei `settings.xml` im Verzeichnis  `.m2`
in deinem Heimatverzeichnis keine Konfiguration für ein spezielles
Maven Repository enthalten. Im Zweifelsfall kannst du die Datei
einfach löschen.

* Die Tests nutzen einige Ports auf dem Rechner. Stelle sicher, dass
  im Hintergrund keine Server laufen.

* Führe die Tests beim Build nicht aus: `./mvnw clean package
  -Dmaven.test.skip=true` bzw. `mvnw.cmd clean package 
  -Dmaven.test.skip=true`.

* In einigen selten Fällen kann es vorkommen, dass die Abhängigkeiten
  nicht korrekt heruntergeladen werden. Wenn du das Verzeichnis
  `repository` im Verzeichnis `.m2` löscht, werden alle Abhängigkeiten
  erneut heruntergeladen.

## Docker Container starten

Weitere Information zu Docker gibt es im
[Docker Cheatsheet](https://github.com/ewolff/cheatsheets-DE/blob/master/DockerCheatSheet.md).

Zunächst musst du die Docker Images bauen. Wechsel in das Verzeichnis 
`docker` und starte `docker-compose build`. Das lädt die Basis-Images
herunter und installiert die Software in die Docker Images:

```
[~/microservice/docker]docker-compose build 
....
Removing intermediate container 5ed9d5e47a14
Step 4/4 : EXPOSE 8080
 ---> Running in ea27ee231d2b
 ---> 7f7f51fc9fd4
Removing intermediate container ea27ee231d2b
Successfully built 7f7f51fc9fd4
Successfully tagged ms_order:latest
```

Danach sollten die Docker Images erzeugt worden sein. Sie haben das
Präfix `ms`:

```
[~/microservice/docker]docker images
REPOSITORY                                      TAG                 IMAGE ID            CREATED              SIZE
ms_order                                        latest              7f7f51fc9fd4        50 seconds ago       228MB
ms_turbine                                      latest              8a5cfb2a7b01        54 seconds ago       210MB
ms_catalog                                      latest              395f7bdc04f3        58 seconds ago       228MB
ms_customer                                     latest              abeed8b882db        About a minute ago   228MB
ms_zuul                                         latest              dc240509b408        About a minute ago   210MB
ms_eureka                                       latest              6081c8f9204f        About a minute ago   210MB
```

Nun kannst Du die Container mit `docker-compose up -d` starten. Die
Option `-d` bedeutet, dass die Container im Hintergrund gestartet
werden und keine Ausgabe auf der Kommandozeile erzeugen.

```
[~/microservice/docker]docker-compose up -d
Recreating ms_eureka_1 ... 
Recreating ms_eureka_1 ... done
Recreating ms_zuul_1 ... 
Recreating ms_customer_1 ... 
Recreating ms_zuul_1
Recreating ms_order_1 ... 
Recreating ms_customer_1
Recreating ms_turbine_1 ... 
Creating ms_catalog_1 ... 
Recreating ms_order_1
Recreating ms_turbine_1
Recreating ms_customer_1 ... done
```

Du kannst nun überprüfen, ob alle Docker Container laufen:

```
[~/microservice/docker]docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
1f66de9969c0        ms_turbine          "/bin/sh -c '/usr/..."   19 seconds ago      Up 16 seconds       0.0.0.0:8989->8989/tcp   ms_turbine_1
7fda8c451878        ms_customer         "/bin/sh -c '/usr/..."   19 seconds ago      Up 15 seconds       8080/tcp                 ms_customer_1
d26252586c7b        ms_order            "/bin/sh -c '/usr/..."   19 seconds ago      Up 16 seconds       8080/tcp                 ms_order_1
26c7f0d59922        ms_zuul             "/bin/sh -c '/usr/..."   19 seconds ago      Up 17 seconds       0.0.0.0:8080->8080/tcp   ms_zuul_1
45fd39ef1bff        ms_catalog          "/bin/sh -c '/usr/..."   19 seconds ago      Up 17 seconds       8080/tcp                 ms_catalog_1
39a1160c11cc        ms_eureka           "/bin/sh -c '/usr/..."   20 seconds ago      Up 19 seconds       0.0.0.0:8761->8761/tcp   ms_eureka_1
```

`docker ps -a`  zeigt auch die terminierten Docker Container an. Das
ist nützlich, wenn ein Docker Container sich sofort nach dem Start
wieder beendet..

Wenn einer der Docker Container nicht läuft, kannst du dir die Logs
beispielsweise mit `docker logs ms_apache_1` anschauen. Der Name
der Container steht in der letzten Spalte der Ausgabe von `docker
ps`. Das Anzeigen der Logs funktioniert auch dann, wenn der Container
bereits beendet worden ist. Falls im Log steht, dass der Container
`killed` ist, dann hat Docker den Container wegen Speichermangel
beendet. Du solltest Docker mehr RAM zuweisen z.B. 4GB. Unter Windows
und macOS findet sich die RAM-Einstellung in der Docker application
unter Preferences/ Advanced.

Um einen Container genauer zu untersuchen, kannst du eine Shell in dem
Container starten. Beispielsweise mit `docker exec -it
ms_catalog_1 /bin/sh` oder du kannst in dem Container ein
Kommando mit `docker exec ms_catalog_1 /bin/ls` ausführen.

Es stehen nun bereit:

* Die Anwendung selber durch Zuul unter http://localhost:8080/
* Das Eureka Dashboard unter http://localhost:8761/
* Das Hystrix Dashboard unter http://localhost:8989/

Mit `docker-compose down` kannst Du alle Container beenden.

