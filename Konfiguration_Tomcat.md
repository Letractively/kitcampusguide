# Vorraussetzungen #

Existierende Eclipse und Tomcat Installation.

# Anleitung #

Falls der Server-Tab nicht existiert, füge ihn hinzu:
  * `Window` -> `Show View` -> `Other...`
  * `Server` -> `Servers`
  * Klicke `Ok`

Ändere den Deploy-Path und den Start-Timeout
  * `Server` Tab öffnen
  * Doppelklick auf `Tomcat Server`
  * Alle vorhandenen Projekte vom Server löschen (Rechtsklick -> `Remove`)
  * Rechtsklick auf den `Server` -> `Clean...`
  * Setze Deploy Path auf `webapps`
  * Setze `Start-Timeout` auf 120 sec