function KITCampusMap(clientId) {
	this.clientId = clientId;
	this.mapElement = document.getElementById(clientId + ":map");
	this.form = document.getElementById(clientId + ":form");
	this.model = new Object();

	this.map = new OpenLayers.Map(this.mapElement.id, {
		maxExtent : new OpenLayers.Bounds(-20037508.34, -20037508.34,
				20037508.34, 20037508.34),
		maxResolution : 156543,
		numZoomLevels : 19,
		units : 'meters',
		projection : "EPSG:41001",
		eventListeners : {
			"moveend" : this.handleMove,
			"zoomend" : this.handleZoomEnd,
			scope : this
		}
	});

	// Init vector layer for route
	this.routeLayer = new OpenLayers.Layer.Vector("route", null);
	this.map.addLayer(this.routeLayer);

	this.applyChanges();
}

KITCampusMap.prototype.handleZoomEnd = function(event) {
	// TODO: Store the max. min zoom level in the map objects
	if (this.map.getZoom() < 14) {
		this.map.zoomTo(14);
	} else if (this.map.getZoom() > 18) {
		this.map.zoomTo(18);
	}
};

KITCampusMap.prototype.handleMove = function(event) {
	// Submit the new map section to the server
	var input = document.getElementById(this.form.id + ":mapSection");
	var mapSection = this.untransformBounds(this.map.getExtent());
	input.value = JSON.stringify(mapSection);
	// TODO: Review the update process
	input.value = jsf.ajax.request(this.form, null, {
		execute : input.id,
		render : this.form.id + ":POIs " + this.form.id + ":mapSection",
		onevent : this.applyChanges.call(this)

	});
};

KITCampusMap.prototype.applyChanges = function() {
	this.model.pois = JSON.parse(document.getElementById(this.clientId
			+ ":form:POIs").firstChild.data);
	this.model.map = JSON.parse(document.getElementById(this.clientId
			+ ":form:map").firstChild.data);
	this.model.route = JSON.parse(document.getElementById(this.clientId
			+ ":form:route").firstChild.data);

	if (!this.mapLayer)
		this.setMapLayer();
	this.setPOIs();
	this.setRoute();
};

/**
 * Sets all POI markers.
 */
KITCampusMap.prototype.setPOIs = function() {
	var m = this.model;
	if (!this.markerLayer) {
		// Init marker layer
		this.markerLayer = new OpenLayers.Layer.Markers("markers");
		this.map.addLayer(this.markerLayer);
	}
	this.markerLayer.clearMarkers();
	for ( var index in m.pois) {
		var marker = this.createPOIMarker(m.pois[index]);
		this.markerLayer.addMarker(marker);
	}
};

KITCampusMap.prototype.setRoute = function() {
	var pointList = [];
	var wp = this.model.route.waypoints;
	for ( var p = 0; p < wp.length; ++p) {
		var lonlat = this.transformWorldPosition(wp[p]);
		pointList.push(new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat));
	}

	var routeFeature = new OpenLayers.Feature.Vector(
			new OpenLayers.Geometry.LineString(pointList), null, null);

	this.routeLayer.removeAllFeatures();
	this.routeLayer.addFeatures([ routeFeature ]);
};

/**
 * Creates an open layers marker describing a given POI.
 * 
 * @param poi
 *            a POI.
 * @returns {OpenLayers.Marker} a marker
 */
KITCampusMap.prototype.createPOIMarker = function(poi) {
	// TODO: Reset when final icon is known
	var size = new OpenLayers.Size(21, 25);
	// TODO: Add info bubble hover effect
	var anchor = new OpenLayers.Pixel(-(size.w / 2), -size.h);
	var icon = new OpenLayers.Icon(
			'http://openlayers.org/dev/img/marker-gold.png', size, anchor);
	var position = this.transformWorldPosition(poi.position);
	return new OpenLayers.Marker(position.clone(), icon);
};

/**
 * Creates the main map layer. It will show the current map's tileset referenced
 * by its tiles URL. Navigation will be restricted to the current map's bounding
 * box.
 */
KITCampusMap.prototype.setMapLayer = function() {
	if (this.mapLayer) {
		this.map.removeLayer(this.mapLayer)
	}
	this.mapLayer = new OpenLayers.Layer.XYZ("XYZ-Layer",
			this.model.map.tilesURL);
	this.map.addLayer(this.mapLayer);
	this.map.setBaseLayer(this.mapLayer);
	this.map.restrictedExtent = this
			.transformMapSection(this.model.map.boundingBox);
	this.map.zoomToExtent(this.map.restrictedExtent);
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

	var result = new Object();
	result.longitude = lon;
	result.latitude = lat;
	return result;
};

/**
 * Converts an OpenLayers.Bounds instance to an appropriate MapSection.
 * @param bounds an Openlayers.Bounds instance
 * @returns a MapSection
 */
KITCampusMap.prototype.untransformBounds = function(bounds) {
	var coords = bounds.toArray();
	var nw = new OpenLayers.LonLat(coords[0], coords[3]);
	var se = new OpenLayers.LonLat(coords[2], coords[1]);
	var result = new Object();
	result.northWest = this.untransformLonLat(nw);
	result.southEast = this.untransformLonLat(se);
	return result;
};