function jumpTo(lon, lat, zoom) {
    var x = Lon2Merc(lon);
    var y = Lat2Merc(lat);
    map.setCenter(new OpenLayers.LonLat(x, y), zoom);
    return false;
}

function Lon2Merc(lon) {
    return 20037508.34 * lon / 180;
}
 
function Lat2Merc(lat) {
    var PI = 3.14159265358979323846;
    lat = Math.log(Math.tan( (90 + lat) * PI / 360)) / (PI / 180);
    return 20037508.34 * lat / 180;
}

function addMarker(layer, lon, lat, popupContentHTML, showPopupOnLoad, iconId) {
 
    // Koordinaten in LonLat umwandeln
	var ll = new OpenLayers.LonLat(Lon2Merc(lon), Lat2Merc(lat));

    // Feature erstellen und konfigurieren (Popup und Marker)
    var feature = new OpenLayers.Feature(layer, ll);
    feature.closeBox = true;
    feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {minSize: new OpenLayers.Size(200, 120) } );
    feature.data.popupContentHTML = popupContentHTML;
    feature.data.overflow = "auto";
    feature.data.icon = makeIcon(iconId);
 
    // Marker erstellen
    var marker = feature.createMarker();
 
    /*
     * Handler Funktionen für die Mouse-Events
     */
    // Click
    var markerClick = function(evt) {
	// Wenn das Popup nicht sichtbar ist, dann kann es nicht fest sichtbar sein
	if (!this.popup.visible())
		this.popup.clicked = false;
	if (this.popup.clicked == true) {
		this.popup.clicked = false;
		this.popup.hide();
    	}
    	else {
		this.popup.clicked = true;
		if (!this.popup.visible())
			this.popup.show();
	}
        OpenLayers.Event.stop(evt);
    };
    // Hover
    var markerHover = function(evt) {
	// Wenn das Popup nicht sichtbar ist, dann kann es nicht fest sichtbar sein
	if (!this.popup.visible())
		this.popup.clicked = false;
	if (!this.popup.clicked)
		this.popup.show();

	OpenLayers.Event.stop(evt);
    };
    // Hover End
    var markerHoverEnd = function(evt) {
	if (!this.popup.clicked) {
		this.popup.hide();
	}
	OpenLayers.Event.stop(evt);
    };

    // Events auf den Marker registrieren und als Objekt das Feature übergeben
    marker.events.register("mousedown", feature, markerClick);
    if (showPopupOnHover) {
    	marker.events.register("mouseover", feature, markerHover);
    	marker.events.register("mouseout", feature, markerHoverEnd);
    }

    // Erstellten Marker der Ebene hinzufügen
    layer.addMarker(marker);

    // Popup erstellen, der Karte hinzufügen und anzeigen, falls gewünscht
    map.addPopup(feature.createPopup(feature.closeBox));

    if (showPopupOnLoad != true) {
	    // Wenn das Popup nicht angezeigt werden soll, verstecken und auf 'nicht angeklickt' setzen
	    feature.popup.hide();
	    feature.popup.clicked = false;
    } else {
	    // Das Popup wird direkt angezeigt und zwar solange bis man es explizit schließt
	    feature.popup.clicked = true;
    }

    return marker;
}

function makeIcon() {
	var size = new OpenLayers.Size(21 ,25);
	var offset = new OpenLayers.Pixel(-(size.w*0.5), -(size.h));
	var icon = new OpenLayers.Icon('http://openlayers.org/api/img/marker.png',size,offset);
	return icon;
}
