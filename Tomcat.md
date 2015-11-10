# Überblick #

Diese Seite soll eine kurze Einführung in Tomcat und die Benutzung innerhalb Eclipse bieten.


# Allgemeines #

Tomcat ist ein Webcontainer, der Java Projekte ausführen kann. Er implementiert die Java Servlet und JavaServer Pages Technologien, kann also .JSPs kompilieren und man kann Servlets auf einem Tomcat Server installieren/deployen. Seit Version 7 unterstützt Tomcat auch die Servlet 3.0 Spezifikation und er kann somit annotierte Servlet interpretieren und ausführen.<br />
Weitere Infos unter http://tomcat.apache.org/

# Eclipse #

Eclipse bietet direkt die Möglichkeit Tomcat herunterzuladen und zu "installieren". Entweder man wird beim ausführen eines Webprojekts aufgefordert einen Server auszuwählen/installieren oder man kommt über die Projekt Properties->"Target Runtime"->"New" zur Auswahl des Servers.<br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Tomcat.wiki-Bilder/eclipse_settings_target_runtime-11-07-02-steinegger.png' alt='Target Runtime auswählen' width='40%' /><br />
Und durch einen Klick auf Weiter erhält man die Möglichkeit Tomcat herunterladen und installierne zu lassen.<br />
<img src='http://kitcampusguide.googlecode.com/svn/wiki/Tomcat.wiki-Bilder/eclipse_settings_target_runtime_download_install_tomcat6-11-06-20-steinegger.png' alt='Tomcat Installation durch Eclipse' width='60%' />

# Probleme/Lösungen #

## Fehlermeldungen in der Console ##

Tauchen Fehler in der Console aus, so hilft es meistens nach der Fehlermeldung zu googlen. Meist erhält man dadurch direkt eine Lösung.<br />
Allgemein ist es hilfreich die Fehler in der Console ausgiebig zu begutachten um das Problem zu lösen. Meist ist der erste Fehler der wichtige und der Rest sind Folgefehler, also zuerst hier ansetzen.