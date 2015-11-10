# Grundlagen zu Subversion #

Subversion wird zur Versionierung des Quellcodes und der Projectressourcen verwendet. Alle wichtigen Daten und das Passwort sollte man unter [Google Source](http://code.google.com/p/kitcampusguide/source/checkout) finden. Die Daten werden wie in einem Dateisystem in einer Baumstruktur abgelegt. Für Eclipse gibt es mehrere Clients, mehr dazu in der Installationsanleitung.

## Aufbau von SVN ##

Üblicherweise wird ein Subversion-Repository in 3 separate Bereich aufgeteilt,
  * den **Trunk** (dt. Stamm), für die hauptsächliche, fortwährend Entwicklung am Projekt, sie stellt den Hauptentwicklungsstrang des Projektes dar. Im Trunk findet die Hauptentwicklung statt. Hier sollten ausschließlich funktionierende Versionen eingespielt/-checkt werden. Die Arbeit im Trunk ist zunächst nur für den Fall vorgesehen, dass stabile Funktionen aus den Branches in integriert werden. Für Arbeiten von Studierende werden die Branches verwendet. Mehr dazu auch in "[Arbeiten mit SVN](Subversion#Arbeiten_mit_SVN.md)".
  * **Branches** (dt. Zweit), für länger dauernde neben läufige Entwicklungen von Funktionalitäten, insbesondere wird dieser Teilgenutzt um Studierenden einen eigenen Bereich in der Entwicklung von KCG-nahen Demonstratoren etc. im Rahmen von Abschlussarbeiten und Praktika zu ermögliche (siehe die Konventionen im Umgang mit der SVN-Ablage). Üblicherweise werden erfolgreiche Implementierungen in den Branches in den Hauptentwicklungstrang eingegliedert und werden Teil des Projektes. Nach Beendigung des Branches wird die Arbeit an einem Branch nicht weitergeführt, d.h. der Branch wird eingestellt. Für größere Änderungen, die über mehrere Tage dauern können, kann man sich einen branch mit sinnvollem Namen passend zum Feature erzeugen (ein Branch von einem Branch ist somit möglich). Allerdings sollte diese Möglichst sind zu tief geschachtelt werden, da man sonst schnell den Überblick verliert . In einem Branch ist es nicht wichtig, dass jede eingecheckte Version komplett funktionsfähig ist, man hat ein bisschen mehr Spielraum. Ist man mit der Entwicklung des Features fertig und die Tests und das Programm funktioniert, so kann man seinen Branch in den Trunk mergen und den Branch löschen bzw. versionieren und taggen . Bei dem merge ist darauf zu achten, dass der trunk nicht beeinträchtigt wurde und alle Tests durchlaufen.
  * den **Tag** (dt. Etikett) in dem funktionierende Versionen gesichert werden (z.B. v1.0, v2.0). Zusätzlich zu den Versionen des Hauptstranges werden stabile Versionen, welche aus den Studierendenarbeiten im Branch hervorgegangen sind, hier zum Nachvollziehen abgelegt. Im Gegensatz zu einem Branch und dem Trunk wird in einem Tag nicht die gesamte Historie der geänderten Artefakte gespeichert, sondern nur der punktuelle Stand des Quellcodes zu einem bestimmten Zeitpunkt. Tags werden aufgrund ihrer Natur nicht aktiv weiterentwickelt.

Jeder dieser drei Bereiche wird als Ordner in der SVN-Ablage repräsentiert.

Ein Ablauf eines Projekts mit der Erstellung verschiedener branches und tags ist auf einem Bild aus Wikipedia zu finden:

![http://upload.wikimedia.org/wikipedia/commons/4/4e/Subversion_project_visualization.svg](http://upload.wikimedia.org/wikipedia/commons/4/4e/Subversion_project_visualization.svg)

## Grundlegende Operationen von Subversion ##

### Share ###

### Commit ###

### Update ###

### Branch ###

### Tag ###

### Merge ###

### Synchronize ###