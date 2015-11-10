# Überblick #

Auf dieser Seite soll der Aufbau eines Subversion (SVN) Repositories und das Arbeiten mit SVN erklärt werden.



# Allgemeines #

Subversion wird zur Versionierung des Quellcodes und der Projectressourcen verwendet. Alle wichtigen Daten und das Passwort sollte man unter [Google Source](http://code.google.com/p/kitcampusguide/source/checkout) finden. Die Daten werden wie in einem Dateisystem in einer Baumstruktur abgelegt. Für Eclipse gibt es mehrere Clients, mehr dazu in der Installationsanleitung.

# Repository Aufbau #

## Grober Aufbau ##

Üblicherweise wird das Subversion Repository in 3 Teile aufgesplitet, den trunk, für die hauptsächliche Entwicklung, branches, für länger dauernde neben läufige Entwicklungen von Features und den tag, in dem funktionierende Versionen gesichert werden (z.B. v1.0, v2.0).
Ein Ablauf eines Projekts mit der Erstellung verschiedener branches und tags ist auf einem Bild aus Wikipedia zu finden:

![http://upload.wikimedia.org/wikipedia/commons/4/4e/Subversion_project_visualization.svg](http://upload.wikimedia.org/wikipedia/commons/4/4e/Subversion_project_visualization.svg)

Ein Merge ist dabei, das Einspielen von Änderungen in den trunk/einen branch, wie im Beispiel, dass die fertig entwickelten Änderungen eines branches in den trunk übernommen werden.
Weiteres zu dem Tag-Branchkonzept findet man in der Wikipedia http://de.wikipedia.org/wiki/Apache_Subversion

## trunk ##
Im trunk findet die Hauptentwicklung statt. Hier sollten ausschließlich funktionierende Versionen eingespielt/-checkt werden. Testet eure Version bevor ihr sie committet, mehr dazu auch in "[Arbeiten mit SVN](Subversion#Arbeiten_mit_SVN.md)".

## branch ##
Für größere Änderungen, die über mehrere Tage dauern können, kann man sich einen branch mit sinnvollem Namen passend zum Feature erzeugen. In diesem branch ist es nicht wichtig, dass jede eingecheckte Version komplett funktionsfähig ist, man hat ein bisschen mehr Spielraum. Ist man mit der Entwicklung des Features fertig und die Tests und das Programm funktioniert, so kann man seinen branch in den trunk mergen und den branch löschen. Bei dem merge ist darauf zu achten, dass der trunk nicht beeinträchtigt wurde und alle Tests durchlaufen.

## tag ##

Für funktionierende Versionen, die man dem "Kunden" ausliefern will, werden tags erzeugt. So verhindert man, dass die Weiterentwicklung die Kundenversion beeinflusst und weiß, und kann genau zuordnen wo welche Version installiert ist. Tags sollten nicht verändert werden.

# Arbeiten mit SVN #

## Neuerung einspielen / Commit ##
Ein paar Tipps, wie man beim Einspielen von Änderungen vorgehen sollte.

### Wann Commiten? ###
Die Änderungen einspielen/Commiten sollte man erst dann, wenn man eine voll funktionsfähige Version hat. Man arbeitet im Team und würde man nicht funktionierende Versionen einspielen, können die anderen Teammitglieder nicht mehr mit dem trunk arbeiten! Zudem sollte vor einem Commit noch ein Update gemacht werden, um Änderungen mitzubekommen, die evtl. die eigenen Änderungen beeinflussen und die Tests zum Scheitern bringen.

### Was Commiten? ###
Am besten sollte immer nur ein Feature pro Commit ins SVN wandern. Versucht dieses Paradigma einzuhalten. Wählt nur die Ressourcen, die für das aktuelle Feature notwendig sind, aber achtet darauf, dass das Programm noch funktioniert.
Verzeichnisse wie das target oder reports Verzeichnis sollen nicht im SVN landen! Diese werden schließlich von [Maven](Maven.md) generiert.

### Wie Commiten? ###
Mit Eclipse erst ein update auf das Projekt ausführen, das Projekt in Eclipse vollständig samt Tests ausführen. Gab es keine Fehler, das Projekt auswählen und und Team->commit wählen, die zu commitenden Ressourcen auswählen und einen Kommentar, der die Änderung beschreibt (evtl. mit Verweis auf das bearbeitete Issue), eintragen und auf commit klicken.

## Verzeichnisse verschieben ##
Beim verschieben von Verzeichnissen ist darauf zu achten, dass es wirklich ein svn move Befehl ist. Am besten die Move Version von Eclipse benutzen, da beim Verschieben sonst die versteckten ".svn" Verzeichnisse mit kopiert werden, was zu Fehlern führen wird!

## History lesen ##

## Ordner ignorieren ##

# Probleme/Lösungen #

## Konflikt nicht auflösbar ##

## Bitte erweitern ##