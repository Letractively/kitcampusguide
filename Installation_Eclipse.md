# Installation von Eclipse #

## Voraussetzungen für die Installation ##
Eclipse benötigt eine Java Runtime Environment (JRE) in der Version 1.4.1 oder höher `[`DF+04:11`]`. Da das JRE im Java Development Kit (JDK) enthalten ist und [automatisch bei der Installation des JDK konfiguriert wird](Installation_JDK.md), ist diese Bedingung durch das Installieren des JDK erfüllt. Diese Konfiguration kann durch das Eingeben von Parametern beim Starten von Eclipse geändert werden, wenn z.B. schon eine Version der JRE installiert ist. Es wird empfohlen, mit den neuesten Versionen des JRE und JDK zu arbeiten.

## Basisinstallation von Eclipse ##
  1. Eclipse basiert auf Java und ist dadurch auch plattformunabhängig. Die richtige Version für das installierte Betriebssystem ist herunterzuladen. Es wird die Version auf der 32bit-Architektur empfohlen, da diese stabiler ist. Die Architektur (32bit/64bit) von Eclipse muss mit der Version des JDK übereinstimmen, um mögliche Probleme beim Ausführen zu verhindern.
  1. Es stehen mehrere Varianten von Eclipse zur Auswahl. Das Paket "Eclipse IDE for Java Developers" ist ein Basis-Paket für die Entwicklung von Java-Programmen. Um weitere Funktionalitäten, wie z.B. eine Versionskontrolle zu bieten, wird Eclipse später mit Plug-ins erweitert.
  1. Die Installation von Eclipse auf dem USB-Stick liefert den Vorteil der Mobilität, hat aber als Nachteil die geringere Performanz, d.h. längere Antwortzeiten.
  1. Eclipse kann durch das Ausführen von Eclipse.exe gestartet werden. Mit Hilfe der Eingabeaufforderung oder durch Erstellen eines Shortcuts mit Parametereingabe lässt sich Eclipse konfigurieren. Man kann Eclipse eine andere JRE benutzen lassen, indem man auf die gewünschte Virtuelle Maschine zeigt (z.B. durch das Aufrufen in der Eingabeaufforderung: "C:\eclipse\Eclipse.exe - vm c:\jdk142\bin\javaw.exe"). Eine [Übersicht über die Startparameter](http://help.eclipse.org/indigo/topic/org.eclipse.platform.doc.user/tasks/running_eclipse.htm) befindet sich in der [Eclipse Hilfe](http://help.eclipse.org/indigo/index.jsp).
  1. Für Eclipse ist ein Workspace (Arbeitsbereich) eine logische Ansammlung von Projekten. Ein Workspace ist ein Verzeichnis auf der Festplatte oder einem USB-Stick, in dem Eclipse die Projekte speichert, die man erstellt. Man kann Eclipse auch einen anderen Workspace benutzen lassen, indem man auf den gewünschten Ordner zeigt (z.B. durch das Aufrufen von der Eingabeaufforderung: "C:\eclipse\Eclipse.exe -data c:\mein-Eclipse-workspace"). Auch aus der IDE heraus ist es möglich, den Workspace zu ändern ("File" -> "Set Workspace").

## Nächste Installationsschritte ##
Nach der Basisinstallation muss Eclipse für die Entwicklung des KITCampusGuides durch [weitere Plug-ins erweitert werden](Installation_Eclipse_Plugins.md). Eine wichtiges Plug-in ist die [Web Tools Platform (WTP)](Installation_WTP.md).


---


**Abkürzungen**
  * JDK - Java Development Kit
  * JRE - Java Runtime Environment
  * WTP - Web Tools Platform

**Ressourcen**
  * `[`DF+04`]` - Jim D'Anjou, Scott Fairbrother, Dan Kehn, John Kellerman, Pat McCarthy: [The Java Developer's Guide to Eclipse](http://www.ubka.uni-karlsruhe.de/hylib-bin/suche.cgi?opacdb=UBKA_OPAC&nd=284303542&session=1651076485). Addison-Wesley, 2004.