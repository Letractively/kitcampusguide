# Konventionen im Umgang mit Subversion #


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