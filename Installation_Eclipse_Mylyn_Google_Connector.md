# Installation des Konnektors für Google Code für Eclipse Mylyn #

## Voraussetzung ##
  * [Basisinstallation von Eclipse](Installation_Eclipse.md)
  * [Eclipse-Plug-in Mylyn Task-Manager](Installation_Eclipse_Mylyn.md)

## Installation ##
  1. Öffnen der Eclipse-Entwicklungsumgebung
  1. Öffnen des Plug-In Managers unter "Hilfe->Installation neuer Software..."
  1. Eintrag von "http://download.eclipse.org/mylyn/incubator/3.6" (Wenn Mylyn 3.6 installiert ist)  in dem Auswahlfeld "Arbeiten mit" und Bestätigung mit Enter-Taste
  1. Öffnen der Menupunktes "Mylyn Incubator" und Auswahl der Plug-ins "Mylyn Tasks Connector: Web Templates (Advanced)"
  1. Weiter mit der Betätigung von "Installieren"
  1. Annahme der Lizenzbestimmungen
  1. Bestätigung der Installation
  1. Neustart von Eclipse

## Konfiguration ##
  1. In der geöffneten Eclipse-Umgebung die Perspektive "Task Repositories" öffnen ("Fenster"->"Ansicht"->"Andere")
  1. In der "Task Repositories"-Ansicht eines neue Repository hinzufügen (Rechtklick->"Repository Hinzufügen"
  1. "Web Template (Advanced)" auswählen und mit "Weiter" bestätigen
  1. Unter der "Server"-Auswahlbox die Option "Eclipse Outliner (Google Code)" auswählen.
  1. Anpassung der Server-URL durch "http://code.google.com/p/kitcampusguide/issues"
  1. Optional kann die Benennung des Repositories geändert werden, z.B. "KITCampusGuide (Google Code)"
  1. Nach der Bestätigung kann eine erste Suchanfrage an das Issues-Tracking-System von Google Code gestellt werden. Die Standardanfrage holt alle Aufgaben bzw. Issues aus dem Tracking-System.

## Nächste Schritte ##
  * [Spezifische Filterung der Mylyn-Taskliste nach Aufgabe](Konfiguration_Mylyn_Google_Code_Task_Filter.md)
  * [Installation eines weiteren Eclipse Plug-ins](Installation_Eclipse_Plugins.md)

---

**Referenzen**
  * Lars Vogel, [Eclipse Mylyn - Tutorial](http://www.vogella.de/articles/Mylyn/article.html#d5517e132), 14.06.2011