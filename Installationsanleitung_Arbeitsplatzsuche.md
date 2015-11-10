# Einführung #

Zur Entwicklung der Arbeitsplatzsuche werden verschiedene Werkzeuge benötigt. Wie man diese installiert und konfiguriert wird im Folgenden beschrieben.

# Verwendete Werkzeuge #

Folgende Werkzeuge bilden die Grundausstattung für die Entwicklung in diesem Projekt:

  1. [Java JDK installieren](Installation_JDK.md)
  1. [Eclipse IDE for Java Developers installieren](Installation_Eclipse.md)
  1. [WTP installieren](Installation_WTP.md)
  1. [Maven Plug-In (m2e) installieren](Installation_Eclipse_m2e.md)
  1. [Maven für WTP installieren](Installation_m2e_wtp.md)
  1. [Subversive installieren](Installation_Subversive.md)
  1. [SpringSource Tool Suite installieren](Installation_SpringToolSuite.md)
  1. [Tomcat Servlet installieren](Installation_Tomcat.md)

Diese zusätzlichen Werkzeuge vereinfachen die Einhaltung von Konventionen und unterstützen beim Testen:

  * [Checkstyle Plug-in installieren](#Checkstyle_Plug-in_installieren.md)
  * [eCobertura Plug-in installieren](#eCobertura_Plug-in_installieren.md)

## Checkstyle Plug-in installieren ##

  * `Help` -> `Eclipse Marketplace...`
  * Nach `Checkstyle` suchen
  * `Eclipse Checkstyle Plug-in` installieren
  * Lizenz akzeptieren
  * Sicherheitswarnung bestätigen
  * Eclipse neustarten

## eCobertura Plug-in installieren ##

  * `Help` -> `Install new Software` -> `Add...`
  * Work with: "http://ecobertura.johoop.de/update/"
  * Wähle `eCobertura Code Coverage` aus
  * Klicke auf `Next`
  * Lizenz akzeptieren
  * Sicherheitswarnung bestätigen
  * Eclipse neustarten

# Projekt einrichten #

## SVN auschecken ##

  * `File` -> `New` -> `Other...`
  * SVN -> `Project from SVN` auswählen
  * Klicke auf `Next`
  * URL: https://kitcampusguide.googlecode.com/svn/branches/PSE_WiSe2011_KITCampusGuide_Team1/arbeitsplatzsuche
  * Gebe unter `User` die E-mail Adresse deines Google Accounts ein.
  * Dein Passwort findest du unter https://code.google.com/hosting/settings
  * Klicke auf `Next`
  * Klicke auf `Finish`
  * Klicke erneut auf `Finish`

## eCobertura konfigurieren ##

  * Rechtsklick auf Projekt
  * `Cover As` -> `Coverage Configurations...`
  * Wähle den Tab `Filters`
  * Klicke auf `Add Exclude Filter`
  * Trage dort `*realdata*` ein und wiederhole den letzen Schritt mit folgenden Einträgen (beachte Groß-/Kleinschreibung):
    * `*layout*`
    * `*model*`
    * `*Test*`
  * Klicke auf `Apply`
  * Klicke auf `Close`

Den Coverage Report findet man dann unter: `Window` -> `Show View` -> `Other...` -> `eCobertura` -> `Coverage Session View`

## Projekt kompilieren ##

  * Rechtsklick auf Projekt -> `Run As` -> `Maven install`

## Projekt auf Tomcat Server starten ##

  * [Konfiguriere Tomcat](Konfiguration_Tomcat.md)
  * Rechtsklick auf das Projekt
  * `Run As` -> `Run on Server`
  * Wähle `Tomcat Server` aus
  * Klicke auf `Finish`