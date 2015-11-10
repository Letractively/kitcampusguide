# Überblick #

Auf dieser Seite soll erklärt werden, wie man Maven benutzt und was es kann.


# Allgemeines #

Maven ist ein Java Projektmanagement Werkzeug, mit dem u.a. der Build Prozess automatisiert werden soll. Zudem können einige [Plugins](Maven#Plugins.md) das entwickeln erleichtern. Durch diese können z.B. Datenbankeinträge erzeugt oder das Projekt auf einem Server installiert werden.

## Module ##

Ein Projekt kann aus mehreren Modulen zusammengebaut sein, z.B. um das Prinzip "Seperation of Concern" umzusetzen. Im Fall des KITCampusGuide gibt es Module für die Daten, die Logik des Guides und die Webanwendung.

## Project Object Model (pom.xml) ##

Das Project Object Model (POM) wird in der pom.xml beschrieben. Mit dem POM werden grundlegende Projektinformationen beschrieben, darunter der Name, die Version, Module des Projekts und Abhängigkeiten zu anderen Projekten. Zudem werden in der pom.xml [Plugins](Maven#Plugins.md) konfiguriert.

## Repository ##

Alle Plugins und externen Bibliotheken liegen in einem sogenannten Repository und werden bei Bedarf, z.B. beim  Kompilieren, von dem Repository heruntergeladen. Dabei hat man auch immer ein lokales Repository (bei Linux normalerweise unter .m2 oder .maven), in dem die Daten zwischengespeichert werden, sodass nicht immer alle Daten neu geladen werden müssen.

## Goals ##

Kernstück von Maven sind die Goals, durch die Plugins aufgerufen werden. Zum Beispiel mit "install" wird das aktuelle Projekt kompiliert und in das lokale [Repository](Maven#Repository.md) installiert, der genaue Ablauf ist in [Maven#Build\_Lebenszyklus](Maven#Build_Lebenszyklus.md) beschrieben. Wie diese Goals in Eclipse aufgerufen werden, steht in [Maven#Eclipse](Maven#Eclipse.md).

# Build Lebenszyklus #

Beim Ausführen eines Maven Goals wie "install" oder "compile" werden verschiedene Phasen durchlaufen und andere Goals aufgerufen.
Folgend sind die wichtigsten Phasen aufgelistet. Jede Phase baut auf der vorherigen auf, das heißt, führt man "package" aus, so wird zuvor "validate", "compile" und "test" aufgerufen.
| validate | validate the project is correct and all necessary information is available |
|:---------|:---------------------------------------------------------------------------|
| compile  | compile the source code of the project                                     |
| test     | test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed |
| package  | take the compiled code and package it in its distributable format, such as a JAR. |
| integration-test | process and deploy the package if necessary into an environment where integration tests can be run |
| verify   | run any checks to verify the package is valid and meets quality criteria   |
| install  | install the package into the local repository, for use as a dependency in other projects locally |
| deploy   | done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects. |

Welche Goals genau aufgerufen werden ist in der Dokumentation zu finden http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html

# Plugins #

Standardmäßig benutzt man Plugins zum kompilieren und archivieren ("compile" und "package"). Zudem gibt es Erweiterungen, um die Daten direkt auf einem Server zu installieren ("tomcat:deploy") oder die Qualität des Quellcodes zu überprüfen ("pmd:pmd","checkstyle:checkstyle","findbugs:findbugs").<br />
Eine Liste von Plugins ist unter http://mojo.codehaus.org/plugins.html oder http://maven.apache.org/plugins/index.html zu finden.

# Eclipse #

In der neusten Eclipse Version (Indigo) ist Maven bereits in Eclipse integriert, ansonsten kann es in frühreren Versionen über den Yoxos Marketplace nachinstalliert werden. Eine genaue Anleitung ist im TDok zu Maven zu finden.<br />

## POM Editor ##

### Grafischen Editor starten ###

Hat man die Maven Integration installiert, so ist der grafische Editor standardmäßig für die pom.xml ausgewählt. Ist dies nicht mehr der Fall, kann der Editor über einen Rechtslkick auf die pom.xml -> "Open With" -> "Maven POM Editor" geöffnet werden.

### Standardeinstellunge grafisch bearbeiten ###

Eclipse bietet zum bearbeiten der pom.xml eine grafische Oberfläche an. Standardwerte wie Name, Version, etc. können mit diesem leicht angepasst werden. Ebenso ist es schnell möglich das benutzte Source Control Management oder Issue Tracking System anzugeben.<br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_pom_editor_overview_parent-11-06-20-steinegger.png' alt='Eclipse pom.xml Editor' />

### Dependencies grafisch anpassen ###

Der Editor bietet auch die Darstellung der Projektabhängigkeiten als Hierarchiebaum an und man kann nach Bibliotheken suchen, um diese als neue Abhängigkeiten dem Projekt hinzuzufügen.<br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_pom_editor_dependency_hierarchy-11-06-20-steinegger.png' alt='Eclipse Dependency Hierarchy' /><br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_dependency_suche-11-06-20-steinegger.png' alt='Eclipse Dependency suchen' /><br />

### Xml Darstellung ###

Neben der grafischen Darstellung bietet Eclipse auch wie gewohnt die Xml Ansicht der pom.xml<br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_pom_editor_pom_xml-11-06-20-steinegger.png' alt='Eclipse POM Editor Xml Ansicht' /><br />

## Quellcode kompilieren/ausführen ##

Eclipse bietet die Möglichkeit Maven Befehle (Goals) über Eclipse auszuführen. Einige vorgefertigte Goals sind in Eclipse direkt integriert und über "Run as"->"Maven [Goal](Goal.md)" ausführbar.
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_run_as_maven_menu-11-06-20-steinegger.png' alt='Maven Goal in Eclipse ausführen' /><br />
Konkret kann man zum Beispiel den Quellcode mit "Run As"->"Maven package" kompilieren und packen (.jar/.war/...). Wie unter [Lebenszyklus](Build.md) beschrieben, werden dabei auch die Tests ausgeführt. Dies kann man durch eine Anpassung der Konfiguration verhindern, siehe [Plugins/Custom Goals ausführen].

## Plugins/Custom Goals ausführen ##

Will man nicht die vorgefertigten Goals ausführen oder zusätzliche Konfiguration eingeben (zB die Tests beim kompilieren ausschalten), so geht dies auch wie oben beschrieben über "Run As". Allerdings wählt man diesmal "Run Configurations..". In dem erscheinenden Fenster erstellt man eine neue Maven Konfiguration, indem man "Maven Build" auswählt und auf "New Cofiguration" klickt.
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_run-configurations-11-07-02-steinegger.png' alt='Run Configurations' /><br />
Nun kann man dem neuen Build Befehl einen Namen geben und die Parameter wählen, wie zum Beispiel, dass die Tests übersprungen werden sollen, siehe Bild.
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_run-configurations_maven-goal-11-07-02-steinegger.png' alt='Build Configuration' /><br />
Der neue Befehl sollte in der "Run"->"Run History" im oberen Menu oder unter dem grünen Run Pfeil, siehe Bild oben, zu finden sein.

# Probleme/Lösungen #

## Versionskonflikt bei externer Bibliothek ##

Zum Beispiel bei einem Versionsupdate von externen Abhängigkeiten, kann es vorkommen, dass Maven merkwürdige Fehlermeldungen anzeigt. Hier kann es helfen den Inhalt des lokalen [Repositories](Maven#Repository.md) zu löschen, sodass Maven die Bibliotheken neu herunterlädt.

## Wie findet man das lokale Repository? Wie findet die lokalen Einstellungen? ##

Über die Eclipse Einstellungen findet man unter "Maven"->"User Settings" die Settings xml und das lokale Repository.<br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Maven.wiki-Bilder/eclipse_maven_settings_files-11-06-20-steinegger.png' alt='Maven User Settings' /><br />

## Tests ignorieren ##

Um die Tests zu ignorieren kann man maven mit der Option "-Dmaven.test.skip=true" aufrufen. Die Tests werden dann nicht ausgeführt. <br />
Grafisch kann man dies wie in [Plugins/Custom Goals ausführen] beschrieben erreichen