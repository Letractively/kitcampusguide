# Einführung #

Zur Entwicklung des Projektes werden verschiedene Werkzeuge und Rahmenwerke genutzt. Auf dieser Seite sind Anleitungen verlinkt, welche bei der Einrichtung der Entwicklungsumgebung behilflich sind. Dies ist für diejenigen interessant, die das Projekt selbst kompilieren und weiterentwickeln möchten.

# Übersicht über verwendete Werkzeuge #

Für erfahrene Entwickler ist hier eine kurze Übersicht über die benötigten Werkzeuge und Rahmenwerke gegeben. Eine ausführliche Anleitung zur Installation der einzelnen Rahmenwerke erfolgt auf den verknüpften Wiki-Seiten.

  * Der KITCampusGuide ist eine [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)-basierte Anwendung. Das Java Development Kit (JDK) in der Version 1.6 oder höher wird benötigt ([Installationsanleitung für Java](Installation_JDK.md)).
  * Bei der Entwicklung des Projektes wird auf das Build-Werkzeug [Apache Maven](http://maven.apache.org) gesetzt, welches [eigenständig](Installation_Maven.md) oder durch eine [Integration in die Entwicklungsumgebung](Installation_Eclipse_m2e.md) installiert werden kann.
  * Als Entwicklungsumgebung (IDE) wird hauptsächlich [Eclipse](http://eclipse.org) eingesetzt. Es wird zunächst eine [Grundinstallation](Installation_Eclipse.md) der Eclipse-IDE für Java-Entwickler empfohlen, welche anschließend durch Installation benötigter Plug-ins erweitert wird.
    * Eine Integration von Maven und Eclipse bietet das Eclipse-Plug-in [Eclipse m2e](http://www.eclipse.org/m2e/) ([Installationsanleitung von Eclipse m2e](Installation_Eclipse_m2e.md)).
    * Die Eclipse-Erweiterung [Eclipse Web Tools Platform (WTP)](http://www.eclipse.org/webtools/) wird hauptsächlich für die Entwicklung der Web-Anwendung verwendet ([Installationsanleitung für Eclipse WTP](Installation_WTP.md)).
    * Eine Maven-Integration von WTP ist bei Verwendung von Eclipse m2e durch m2e-wtp auf dem Eclipse Marketplace erhältlich ([Installation\_m2e\_wtp](Installation_m2e_wtp.md)).
    * Die Quellcode-Verwaltung wird durch Subversion (SVN) durchgeführt. Als Eclipse Plug-in wird der SVN-Client [Subversive](http://www.eclipse.org/subversive/) empfohlen ([Installationsanleitung für Subversive](Installation_Subversive.md))
    * Der KITCampusGuide nutzt das Spring-Framework für Dependency Injection. Die Erweiterung der [Spring Tool Suite (STS)](http://www.springsource.com/developer/sts) für Eclipse bietet hierfür zahlreiche Werkzeuge ([Installationsanleitung für Spring Tool Suite](Installation_SpringToolSuite.md)).
  * Als Ausführungsumgebung für die Web-Anwendungen wird der Java Servlet-Container [Apache Tomcat](http://tomcat.apache.org) eingesetzt. ([Installation von Apache Tomcat](Installation_Tomcat.md))