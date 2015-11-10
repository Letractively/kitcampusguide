# Einführung #

Um Anwendungen für Android entwickeln zu können, müssen bestimmte Werkzeuge installiert sein. Diese Anleitung zeigt die Installation und Konfiguration der erforderlichen Werkzeuge um auf einem System mit Windows 7 entwickeln zu können.

# Downloads #

Es müssen das [Android-SDK](http://developer.android.com/sdk/index.html) und eine IDE, in diesem Fall [Eclipse](http://www.eclipse.org/downloads), heruntergeladen werden.

# Android-SDK #
## Systemvoraussetzungen ##

  * Betriebssystem
    * Windows XP (32-bit), Vista (32- oder 64-bit) oder Windows 7 (32- oder 64-bit)
    * Mac OS X 10.5.8 oder neuer (x86 only)
    * Linux
      * GNU C Library (glibc) 2.7 oder neuer
      * Bei Ubuntu Linux, Version 8.04 oder neuer
      * 64-bit Distributionen müssen in der Lage sein 32-bit Anwendungen auszuführen
  * Entwicklungsumgebung
    * Eclipse 3.6.2 (Helios) oder höher
    * JDK 6
    * Android Development Tools plugin

## Installation ##

Unter http://developer.android.com/sdk/installing.html ist eine Installationsanleitung für das Android-SDK zu finden.

Durch das Ausführen des heruntergeladenen Installers wird die Installation des Android-SDK gestartet. Beim Ausführen des Installers kann eine Warnung von Windows aufgehen, da eine Anwendung installiert werden soll, welche von einem unbekannten Herausgeber (Publisher) ist. Diese Warnung sollte aber mit ok bestätigt werden, damit das SDK installiert werden kann. Während der Installation sollte der Installer automatisch die installierte Java Development Kit (JDK) Version feststellen. Außerdem muss der Installationsordner und der Startmenüordner angegeben werden. Am Ende der Installation kann der SDK Manager gestartet werden.

Beim Start sucht der Android SDK-Manager nach Updates und zeigt alle verfügbaren Updates an. Hier können alle gewünschten Update ausgewählt werden. Durch einen Klick auf den "Install x packages..."-Button öffnet sich ein Dialog-Fenster zum Bestätigen der Lizenzen der zu installierenden Pakete. Durch die Bestätigung der Lizenzen wird die Installation der Updates gestartet. Die Installation der Updates kann einige Zeit in Anspruch nehmen.

## Android Virtual Device (AVD) Manager ##

Um eine entwickelte Android Anwendung ohne Android Gerät testen zu können gibt es Emulatoren. Diese Emulatoren heißen Android Virtual Device, kurz AVD. Diese AVDs werden im AVD Manager erstellt und verwaltet.

Über den Button "New" kann ein neues AVD angelegt werden. Für das neue AVD müssen verschiedene Informationen angegeben werden:
  * **Name** - Ein Name für das neue AVD
  * **Target** - Die Android-Version
  * **SD Card** - Die Größe für die zu emulierende Speicherkarte oder eine Datei (optional)
  * **Snapshot** - Wenn Snapshot aktiviert ist, dann wird der Zustand des AVD beim beenden persistiert und bei einem erneuten Start geladen
  * **Skin** - Hier können verschiedene vordefinierte Auflösungen ausgewählt werden oder eine bestimmte Auflösung angegeben werden
  * **Hardware** - Unter dem Punkt Hardware ist die für das neue AVD ausgewählte Hardware aufgelistet. Durch einen Klick auf „New“ kann hier weitere Hardware hinzugefügt werden (unter anderem Beschleunigungs-Sensor, GPS, etc.). Hardware kann hier auch entfernt werden.
Durch eine Klick auf "Create AVD" wird das neue AVD erstellt.

# Eclipse #
## Installation ##

Eclipse muss nicht installiert werden. Nachdem das heruntergeladen ZIP-Archiv entpackt wurde kann Eclipse einfach gestartet werden.

## Konfiguration ##

Um mit Eclipse Android Anwendungen entwickeln zu können muss das ADT Plugin installiert werden. Dieses Plugin sorgt für eine Zusammenarbeit von Eclipse und dem Android-SDK.

Um das ADT Plugin zu installieren muss in Eclipse "Help" > "Install new Software" aus dem Menü ausgewählt werden. Anschließend, durch einen Klick auf "Add", ein neues Repository anlegen. Für das neue Repository muss ein Name sowie eine Location angegeben werden. Der Name kann frei gewählt werden und die Location für das ADT Plugin ist "https://dl-ssl.google.com/android/eclipse/". Eclipse lädt nun Informationen für die verfügbare Software aus dem Reposiory. Hier muss "Developer Tools" ausgewählt werden. In nächsten Dialog wird eine List der herunterzuladenden Software angezeigt. Im letzten Dialog muss die Lizenz für das ADT Plugin akzeptiert werden. Während der Installation des Plugins wird eine Sicherheitswarnung (Security Warning) angezeigt.

> Warning: You are installing software that contains unsigned content.
> The authenticity or validity of this software cannot be established.
> Do you want to continue with the installation.

Um das ADT Plugin zu installieren muss diese Sicherheitswarnung mit "OK" bestätigt werden. Nach der Installation muss Eclipse neu gestartet und anschließend das ADT Plugin konfiguriert werden.

Die Konfigurationsansicht des ADT Plugins sollte sich nach dem Neustart von Eclipse öffnen. Da das Android-SDK bereits installiert wurde kann hier "Use existing SDK" ausgewählt werden. Es muss dann noch der Pfad zu dem zuvor installierten SDK angegeben werden. Anschließend wird der Nutzer noch gefragt, ob Nutzungsstatistiken an Google gesendet werden sollen oder nicht. Die Wahl hat keine Auswirkung auf die Arbeitsfähigkeit des Plugins.

Unter http://developer.android.com/sdk/eclipse-adt.html#installing ist eine Installationsanleitung für das ADT-Plugin zu finden.

## Kompilieren ##

Um ein Anwendungspakte (**.apk) einer entwickelten Android Anwendung zu erstellen kann im Kontextmenü (Rechtsklick) des Projektes unter "Android Tools" "Export Signed Application Package" oder "Export Unsigned Application Package" genutzt werden. Zum Testen reicht ein nicht signiertes (unsigned) Android Application Package. Für ein Release ist ein signiertes (signed) Package nötig.**

## Debugging ##

Das Debugging eines Android Projektes läuft ähnlich wie das Debugging eines Java Projektes. Durch eine Klick auf das Debug-Symbol wird das Dialog-Fenster "Debug As" geöffnet. Hier "Android Application" auswählen. Anschließend startet ein AVD (dies kann einige Minuten dauern). Eclipse versucht nun den Debugger mit dem AVD zu verbinden. Wenn die Verbindung erfolgreich hergestellt wurde wechselt Eclipse in die Debug-Ansicht. Nun kann debuggt werden.

Durch die Modifizierung der (automatisch) erstellten Debug Konfiguration kann eine bestimmte AVD oder auch ein angeschlossenes Android Gerät für das Debugging gewählt werden. Im Dropdown-Menü des Debug-Buttons kann durch den Punkt "Debug Configurations" ein Dialog-Fenster zur Konfiguration der Debug Konfigurationen geöffnet werden. Hier kann im Tab "Target" durch die Auswahl von "Manual" dafür gesorgt werden, dass bei jedem Start des Debuggings ein Auswahlfenster geöffnet wird. In diesem Auswahlfenster kann zwischen allen verfügbaren AVDs und allen angeschlossenen Android Geräten gewählt werden.

Durch die Auswahl eines Android Gerätes wird die entwickelte Android Anwendung auf dem Gerät installiert und gestartet. Das Debuggen funktioniert genauso wie mit einem AVD.