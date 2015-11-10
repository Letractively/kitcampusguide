# Installation der GRASS GIS Analyse Software #

## Installationsanweisungen für Mac OS X (Snow Leopard) ##

Wie auf der [GRASS-Homepage](http://grass.osgeo.org) bzw. auf der [GRASS-Download-Seite](http://grass.osgeo.org/download/index.php) ersichtlich, werden Installationspakete für GRASS für Mac OS X durch einen externe Provider (http://www.kyngchaos.com/software/unixport) bereitgestellt. Zur Installation von GRASS unter Mac OS X sind zudem verschiedene Pakete zu installieren. Diese Installationsanweisung zeigt die Installation der verschiedenen Pakete für GRASS unter Mac OS X Snow Leopard

### Installation der vorausgesetzten Pakete ###

GRASS benötigt
  * ActiveTcl 8.5 für die TclTk GUI, oder
  * Python 2.5 and wxPython 2.8.10 (optional)
sowie verschiedene Rahmenwerke für die Verarbeitung von Geo-Daten
  * Geospatial Data Abstraction Library (GDAL/OGR)  is a cross platform C++ translator library for raster and vector geospatial data formats
  * FreeType
  * cairo

#### Installation der Rahmenwerke ####

Alle hier angegebenen Rahmenwerke sind unter http://www.kyngchaos.com/software/frameworks verfügbar.

##### Installation des FreeType-Rahmenwerkes #####

Das FreeType-Rahmenwerk wird von GDAL zur Darstellung von Text verwendet.

  * Herunterladen der aktuellsten Version des "FreeType framework".
  * Öffnen des Disk-Images
  * Öffnen des "FreeType Framework"-Paketes startet des Installationsprogramm
  * Akzeptieren der Lizenzvereinbarungen installiert das Rahmenwerk (Administratorrechte werden benötigt)
  * Unter /Library/Frameworks/ sollten der Ordner "FreeType.framework" zu finden sein

##### Installation von GDAL und unterstützenden Rahmenwerken #####

In dem bereitgestellten GDAL-Rahmenwerk sind die Bibliotheken UnixImageIO, GEOS, PROJ, und SQLite3 enthalten.
  * Herunterladen der aktuellen Version des "GDAL Complete" Rahmenwerkes
  * Öffnen des Disk-Images
  * Öffnen des "GDAL Complete"-Paketes startet das Installationsprogramm
  * Akzeptieren der Lizenzvereinbarungen installiert die Rahmenwerke (Administratorrechte werden benötigt)
  * Unter /Library/Frameworks/ sollten die folgenden Ordner zu finden sein
    * GDAL.framework
    * GEOS.framework
    * PROJ.framework
    * SQLite.framework
    * UnixImageIO.framework

##### Installation des cairo-Rahmenwerkes #####

Cairo ist eine 2D-Grafikbibliothek.
  * Herunterladen der aktuellsten Version des "cairo frameworks"
  * Öffnen des Disk-Images
  * Öffnen des "cairo Framework"-Paketes startet das Installationsprogramm
  * Akzeptieren der Lizenzvereinbarungen installiert das Rahmenwerk (Administratorrechte werden benötigt)
  * Unter /Library/Frameworks/ sollten die folgenden Ordner zu finden sein

### Installation der GRASS-Anwendung ###

  * InstallationÖffnen der GRASS-Webseite unter http://grass.osgeo.org
  * Navigation zur Download-Seite (http://grass.osgeo.org/download/index.php)
  * Navigation zur Download-Seite für Mac OS X


---

**Abkürzungen**
  * GRASS - Geographic Resources Analysis Support System
**Referenzen**
  * GRASS Development Team, 2010. Geographic Resources Analysis Support System (GRASS) Software, Version 6.4.0. Open Source Geospatial Foundation. http://grass.osgeo.org