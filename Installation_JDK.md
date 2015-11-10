# Installation des Java Development Kit für Windows #

Das Java Development Kit (JDK) ist ein Software Development Kit (SDK), mit dem [Java](http://java.com)-Anwendungen erstellt werden können. Es ist ein Paket von Ressourcen und besteht u.a. aus
  * dem Java-Compiler
  * Werkzeugen,
  * Java-Bibliotheken

  1. Das offizielle JDK wird von [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html) bereitgestellt. Hierbei können neben dem JDK noch weitere Werkzeuge, z.B. die Entwicklungsumgebung Netbeans und weitergehende Java-Bibliotheken sowie Java-Frameworks, heruntergeladen werden. Es reicht aus, nur das JDK in der aktuellen Version auszuwählen und zu installieren.
  1. Da Java eine plattformübergreifende Technologie ist, gibt es verschiedene Versionen von Java, die für bestimmte Betriebssysteme wie Windows oder Linux und in verschiedenen Architekturen (32 bit / 64 bit) zur Verfügung stehen. Hier ist es wichtig, die passende Plattform und Architektur für den Entwicklungsrechner auszuwählen. Die 32bit Version ist zu empfehlen, da sie etwas stabiler ist. Mac-Benutzer erhalten die neueste JDK- Version durch das Software-Update-System von Apple.
  1. Das JDK-Installationsprogramm sorgt dafür, dass die Dateien in die richtigen Ordner kopiert werden und konfiguriert zugleich auch Windows für die Benutzung des JDK und JRE (z.B. Setzen der Umgebungsvariable "PATH"; siehe in Windows 7 mittels "Start" -> "Systemsteuerung" -> "System und Sicherheit" -> "System" -> "Erweiterte Systemeinstellungen" -> "Umgebungsvariablen"). Es wird die Möglichkeit angeboten, den Zielordner zu ändern, wobei die Benutzung des Standardordners empfohlen wird.
  1. In dem JDK ist auch das JRE (Java Runtime Environment) beinhaltet. Das JRE ist die Virtuelle Maschine, die das plattformunabhängige Ausführen von Programmen in Java ermöglicht. Um Java-Programme ausführen zu können, muss auf dem Zielsystem eine Version der JRE vorhanden sein, die die notwendigen Funktionalitäten bereitstellt. Zum Beispiel benötigt Eclipse die JRE 1.4.1 oder höher, um lauffähig zu sein. [DF+04:11].

## Nächste Schritte ##
Nach der Installation von Java kann als [nächstes die Entwicklungsumgebung Eclipse installiert werden](Installation_Eclipse.md).

---

**Abkürzungen**
  * DK - Java Development Kit
  * RE -  Java Runtime Environment
  * DK - Software Development Kit

**Referenzen**
  * `[`DF+04`]`- Jim D'Anjou, Scott Fairbrother, Dan Kehn, John Kellerman, Pat McCarthy: [The Java Developer's Guide to Eclipse](http://www.ubka.uni-karlsruhe.de/hylib-bin/suche.cgi?opacdb=UBKA_OPAC&nd=284303542&session=779876614). Addison-Wesley, 2004.