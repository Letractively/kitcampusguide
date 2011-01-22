/**
 * Stores all existing KITCampusMap objects, mapped by their client id.
 */
KITCampusMap.maps = new Object();

/**
 * Is called when the map is initiated after loading the page. The method loads
 * an OpenLayers map into a dom-Element (usually a div) with the relative id
 * ":map".
 * 
 * @param clientId
 *            the jsf map component's id
 * @returns {KITCampusMap} a newly created KITCampusMap instance.
 */
function KITCampusMap(clientId) {
	this.clientId = clientId;
	this.mapElement = document.getElementById(clientId + ":map");
	this.form = document.getElementById(clientId + ":form");
	this.model = new Object();
	KITCampusMap.maps[clientId] = this;
	// This initialization is mainly taken from Mapstraction
	this.map = new OpenLayers.Map(this.mapElement.id, {
		maxExtent : new OpenLayers.Bounds(-20037508.34, -20037508.34,
				20037508.34, 20037508.34),
		maxResolution : 156543,
		numZoomLevels : 19,
		units : 'meters',
		projection : "EPSG:41001",
		controls : []
	});
	this.map.addControl(new OpenLayers.Control.PanZoom());
	this.map.addControl(new OpenLayers.Control.Navigation({
		handleRightClicks : true
	}));

	// Create all necessary layers (only the map layer is created later)
	// Init vector layer for route
	this.routeLayer = new OpenLayers.Layer.Vector("route", null);
	this.map.addLayer(this.routeLayer);

	// Init marker layer for POIs
	this.poiMarkerLayer = new OpenLayers.Layer.Markers("poiMarkers");
	this.map.addLayer(this.poiMarkerLayer);

	// Init marker layer
	this.markerLayer = new OpenLayers.Layer.Markers("markers");
	this.map.addLayer(this.markerLayer);

	var thiss = this;	
	this.map.div.oncontextmenu = function (e) {
		e = e ? e : window.event;
		if (e.preventDefault) {
			e.preventDefault(); // For non-IE browsers.
		}
		var div = thiss.map.div;
		// Transform coordinates
		thiss.handleMenuOpen(e.clientX - div.offsets[0], e.clientY - div.offsets[1]);
		return false; // For IE browsers.
	};
	
	this.applyChanges();
}

/**
 * This methods reads all data from the input fields and updates the appropriate
 * map elements.
 */
KITCampusMap.prototype.applyChanges = function() {
	// Get all Attributes which need to be redrawn
	var changedItems = JSON
			.parse(this.getFormElement("changedProperties").firstChild.data);
	var changed = new Object();
	for ( var i in changedItems) {
		changed[changedItems[i]] = true;
	}

	if (changed['map'] || !this.model.map) {
		this.model.map = JSON.parse(this.getFormElement("map").firstChild.data);
		this.setMapLayer();
	}
	if (changed['mapLocator']) {
		this.model.mapLocator = JSON
				.parse(this.getFormElement("mapLocator").value);
		this.setMapLocator();
	}
	if (changed['POIs']) {
		this.model.pois = JSON
				.parse(this.getFormElement("POIs").firstChild.data);
		this.setPOIs();
	}

	if (changed['markerTo']) {
		var markerTo = this.getFormElement("markerTo").value;
		this.model.markerTo = (markerTo == "") ? null : JSON.parse(markerTo);
		this.setMarkerTo();
	}

	 if (changed['route']) {
		if (this.getFormElement("route").firstChild) {
			this.model.route = JSON
					.parse(this.getFormElement("route").firstChild.data);
		} else {
			this.model.route = null;
		}
		this.setRoute();
	}

};

/**
 * Returns the form element matching a given identifier (for example POIs,
 * mapSection)
 * 
 * @param relativeId
 *            a String
 * @returns the appropriate form element
 */
KITCampusMap.prototype.getFormElement = function(relativeId) {
	return document.getElementById(this.clientId + ":form:" + relativeId);
};

// Event handlers -------------------------------------------------------
/**
 * Is called when the user clicks with the right mouse.
 * 
 * @param x
 *            the x-coordinate as pixel relative to the map div
 * @param y
 *            the y-coordinate as pixel relative to the map div
 */
KITCampusMap.prototype.handleMenuOpen = function(x, y) {
	// TODO: Really open a menu, until now a "setMarkerTo" click is simulated
	var lonLat = this.map.getLonLatFromPixel(new OpenLayers.Pixel(
	x, y));
	var mapPosition = this.untransformLonLat(lonLat);

	var input = this.getFormElement("markerTo");
	mapPosition.map = this.model.map; // make the WorldPosition to a MapPosition
	input.value = JSON.stringify(mapPosition);
	this.requestUpdate(input.id);
};

/**
 * Restricts the allowed zoom levels to some given bounds.
 * 
 * @param event
 *            the event given by OpenLayers.
 */
KITCampusMap.prototype.handleZoomEnd = function(event) {
	// TODO: Store the max. min zoom level in the map objects. Done
	if (this.map.getZoom() < this.model.map.minZoom) {
		this.map.zoomTo(this.model.map.minZoom);
	} else if (this.map.getZoom() > this.model.map.maxZoom) {
		this.map.zoomTo(this.model.map.maxZoom);
	}
};

/**
 * This method is called whenever the current map section was changed, for
 * example by panning or zooming. The method will start an Ajax call to inform
 * the server about the change of the map section.
 * 
 * @param event
 *            the event object given by OpenLayers
 */
KITCampusMap.prototype.handleMove = function(event) {
	// Submit the new map section to the server
	var input = this.getFormElement("mapLocator");
	var newMapLocator = new Object();
	newMapLocator.mapSection = this.untransformBounds(this.map.getExtent());
	newMapLocator.center = null;

	input.value = JSON.stringify(newMapLocator);
	this.requestUpdate(input.id);
};

KITCampusMap.prototype.eventCallback = function(data) {
	if (data.status == "success") {
		// TODO: Maybe the ID could be retrieved a bit better
		var clientId = data.source.id.substring(0, data.source.id.length
				- ":form".length);
		var campusMap = KITCampusMap.maps[clientId];
		campusMap.applyChanges.call(campusMap);
	}
};

KITCampusMap.prototype.requestUpdate = function(executeIds) {
	var id = this.form.id;
	jsf.ajax.request(this.form, null, {
		execute : executeIds,
		// TODO: Add all existing attributes here (separated by spaces)
		render : id + ":POIs " + id + ":mapLocator " + id + ":map " + id
				+ ":route " + id + ":changedProperties " + id + ":markerTo "
				+ id + ":markerFrom " + id + ":buildingPOI " + id
				+ ":buildingPOIList " + id + ":highlightedPOIID",
		onevent : this.eventCallback
	});
};

// Property setters -------------------------------------------------------
/**
 * Sets the current map section which is set in the model. If the current map
 * extent is equal to the position in the model, nothing happens.
 */
KITCampusMap.prototype.setMapLocator = function() {
	var mapLocator = this.model.mapLocator;
	if (mapLocator.mapSection != null) {
		var curSection = this.untransformBounds(this.map.getExtent());
		// Check if the current section is equal to the new section
		if (!this.positionEquals(curSection.northWest,
				mapLocator.mapSection.northWest)
				|| !this.positionEquals(curSection.southEast,
						mapLocator.mapSection.southEast)) {
			this.disableMapEvents();
			this.map.zoomToExtent(this
					.transformMapSection(mapLocator.mapSection));
			this.enableMapEvents();
		}
	} else if (mapLocator.center != null) {
		this.map.setCenter(this.transformWorldPosition(mapLocator.center));
	}
};

/**
 * Returns true if two given WorldPositions point to (nearly) the same location.
 * 
 * @param pos1
 *            a WorldPosition
 * @param pos2
 *            a WorldPosition
 * @returns {Boolean} true if both positions seem to be identical
 */
KITCampusMap.prototype.positionEquals = function(pos1, pos2) {
	var EPS = 5E-7;
	if (Math.abs(pos1.longitude - pos2.longitude) < EPS
			&& Math.abs(pos1.latitude - pos2.latitude) < EPS)
		return true;
	return false;
};

/**
 * Sets markers for the given set of POIs. If no marker layer exists, a new
 * layer is created. The POI list must be stored in this.model.pois
 */
KITCampusMap.prototype.setPOIs = function() {
	var m = this.model;
	this.poiMarkerLayer.clearMarkers();
	for ( var index in m.pois) {
		this.createPOIMarker(m.pois[index]);
	}
};

/**
 * Creates an open layers marker describing a given POI.
 * 
 * @param poi
 *            a POI.
 * @returns {OpenLayers.Marker} a marker
 * 
 */
KITCampusMap.prototype.createPOIMarker = function(poi) {

	var feature = new OpenLayers.Feature(this.poiMarkerLayer, this
			.transformWorldPosition(poi.position));
	feature.popupClass = OpenLayers.Class(OpenLayers.Popup.AnchoredBubble, {
		'autoSize' : true
	});
	feature.data.popupContentHTML = poi.description + " und name:" + poi.name;
	feature.data.overflow = "auto";

	var marker = feature.createMarker();

	var markerClick = function(evt) {

		var input = this.getFormElement("highlightedPOIID");
		input.value = JSON.stringify(poi.id);
		this.requestUpdate(input.id);

		if (feature.popup == null) {
			var closeClick = function(evt) {
				var input = this.getFormElement("highlightedPOIID");
				input.value = " ";
				this.requestUpdate(input.id);
				this.map.popups[0].hide();
			};
			feature.popup = feature.createPopup(true, closeClick, this);

			this.map.addPopup(feature.popup, true);
			feature.popup.show();
		} else {
			this.map.addPopup(feature.popup, true);
			feature.popup.show();
		}
		currentPopup = feature.popup;
		OpenLayers.Event.stop(evt);
	};

	marker.events.register("mousedown", this, markerClick);
	this.poiMarkerLayer.addMarker(marker);
};

/**
 * Draws the current route in a vector layer. The vector layer is created if
 * necessary. The route must be set in this.model.route
 */
KITCampusMap.prototype.setRoute = function() {
	this.routeLayer.removeAllFeatures();
	if (this.model.route != null) {
		var pointList = [];
		var wp = this.model.route.waypoints;
		for ( var p = 0; p < wp.length; ++p) {
			var lonlat = this.transformWorldPosition(wp[p]);
			pointList
					.push(new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat));
		}

		var routeFeature = new OpenLayers.Feature.Vector(
				new OpenLayers.Geometry.LineString(pointList), null, null);
		this.routeLayer.addFeatures([ routeFeature ]);
	}
};

KITCampusMap.prototype.setMarkerTo = function() {
	var m = this.model;
	if (this.markerToMarker) {
		this.markerLayer.removeMarker(this.markerToMarker);
	}
	if (m.markerTo != null) {
		var size = new OpenLayers.Size(21, 25);
		var anchor = new OpenLayers.Pixel(-(size.w / 2), -size.h);
		var icon = new OpenLayers.Icon(
				'http://openlayers.org/dev/img/marker-blue.png', size, anchor);
		var position = this.transformWorldPosition(m.markerTo);
		this.markerToMarker = new OpenLayers.Marker(position, icon);
		this.markerLayer.addMarker(this.markerToMarker);
	}
};

/**
 * Creates the main map layer. It will show the current map's tileset referenced
 * by its tiles URL. Navigation will be restricted to the current map's bounding
 * box. The current map must be stored in this.model.map.
 */
KITCampusMap.prototype.setMapLayer = function() {
	this.disableMapEvents();
	if (this.mapLayer) {
		this.map.removeLayer(this.mapLayer);
	}
	this.mapLayer = new OpenLayers.Layer.XYZ("XYZ-Layer",
			this.model.map.tilesURL);
	this.map.addLayer(this.mapLayer);
	this.map.setBaseLayer(this.mapLayer);
	this.map.restrictedExtent = this
			.transformMapSection(this.model.map.boundingBox);
	this.map.zoomToExtent(this.map.restrictedExtent);
	this.enableMapEvents();
};

// Help functions ------------------------------------------------------------

/**
 * Enables all event listeners. The handler methods will be called when an event
 * occurs.
 */
KITCampusMap.prototype.enableMapEvents = function() {
	this.map.events.on({
		"moveend" : this.handleMove,
		"zoomend" : this.handleZoomEnd,
		scope : this
	});
};

/**
 * Disables all event listeners. The event handling methods will not be called.
 */
KITCampusMap.prototype.disableMapEvents = function() {
	this.map.events.un({
		"moveend" : this.handleMove,
		"zoomend" : this.handleZoomEnd,
		scope : this
	});
};

/**
 * Transforms a given MapSection into a OpenLayers Bounds object. The
 * MapSection's coordinates are transformed into the open layers space.
 * 
 * @param mapSection
 *            a MapSection
 * @returns {OpenLayers.Bounds} an appropriate Bounds object
 */
KITCampusMap.prototype.transformMapSection = function(mapSection) {
	var result = new OpenLayers.Bounds();
	var p1 = this.transformWorldPosition(mapSection.northWest);
	result.extend(p1);
	var p2 = this.transformWorldPosition(mapSection.southEast);
	result.extend(p2);
	return result;
};

/**
 * Transforms a given WorldPosition with GPS coordinates into the open layers
 * coordinate system.
 * 
 * @param wp
 *            a WorldPosition
 * @returns {OpenLayers.LonLat} a newly created and transformed instance.
 */
KITCampusMap.prototype.transformWorldPosition = function(wp) {
	// Code taken from the Mapstraction project
	var ollon = wp.longitude * 20037508.34 / 180;
	var ollat = Math.log(Math.tan((90 + wp.latitude) * Math.PI / 360))
			/ (Math.PI / 180);
	ollat = ollat * 20037508.34 / 180;
	return new OpenLayers.LonLat(ollon, ollat);
};

/**
 * Converts an OpenLayers.LonLat to a WorldPosition. The result can be formatted
 * with JSON and sent to the server.
 * 
 * @returns a WorldPosition
 */
KITCampusMap.prototype.untransformLonLat = function(lonlat) {
	// Code taken from the Mapstraction project
	var lon = (lonlat.lon / 20037508.34) * 180;
	var lat = (lonlat.lat / 20037508.34) * 180;

	lat = 180 / Math.PI
			* (2 * Math.atan(Math.exp(lat * Math.PI / 180)) - Math.PI / 2);

	return {
		longitude : lon,
		latitude : lat
	};
};

/**
 * Converts an OpenLayers.Bounds instance to an appropriate MapSection.
 * 
 * @param bounds
 *            an Openlayers.Bounds instance
 * @returns a MapSection
 */
KITCampusMap.prototype.untransformBounds = function(bounds) {
	var coords = bounds.toArray();
	var nw = new OpenLayers.LonLat(coords[0], coords[1]);
	var se = new OpenLayers.LonLat(coords[2], coords[3]);
	return {
		northWest : this.untransformLonLat(nw),
		southEast : this.untransformLonLat(se)
	};
};
