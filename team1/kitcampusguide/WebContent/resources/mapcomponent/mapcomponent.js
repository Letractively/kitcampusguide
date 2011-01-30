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
		numZoomLevels : 22,
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
		if (!div.offsets) {
			// TODO: Maybe find a better solution then just ignoring the click
			return;
		}
		
		if (e.pageX) {
			// Transform coordinates
			thiss.handleMenuOpen(e.pageX - div.offsets[0], e.pageY - div.offsets[1]);
		} else {
			// IE > 4
			var scrollX = document.body.scrollLeft;
			var scrollY = document.body.scrollHeight;
			if (!scrollX) {
				// IE > 6
				scrollX = document.documentElement.scrollLeft;
				scrollY = document.documentElement.scrollTop;
			}
			thiss.handleMenuOpen(e.clientX + scrollX - div.offsets[0], 
					e.clientY + scrollY - div.offsets[1]);
		}
		return false; // For IE browsers.
	};
	
	this.additionalRenderIDs = this.getFormElement("additionalRenderIDs").innerHTML;
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
	
	if (changed['highlightedPOI']) {
		if (this.getFormElement("highlightedPOI").firstChild) {
			this.model.highlightedPOI = JSON.parse(this
					.getFormElement("highlightedPOI").firstChild.data);
		} else {
			this.model.highlightedPOI = null;
		}
		this.setHighlightedPOI();
	}
	
	if (changed['POIs']) {
		this.model.pois = JSON
				.parse(this.getFormElement("POIs").innerHTML);
	}
	
	// The POIs must be updated if the highlighted POI changes hence the old 
	// marker needs to be deleted
	if (changed['POIs'] || changed['highlightedPOI']) {
		this.setPOIs();
	}

	if (changed['buildingPOIList']) {
		if (this.getFormElement("buildingPOIList").firstChild) {
			this.model.buildingPOIList = JSON
					.parse(this.getFormElement("buildingPOIList").firstChild.data);
		} else {
			this.model.buildingPOIList = null;
		}
		if (this.getFormElement("buildingPOI").firstChild) {
			this.model.buildingPOI = JSON
					.parse(this.getFormElement("buildingPOI").firstChild.data);
		} else {
			this.model.buildingPOI = null;
		}
		this.setBuildingPOIList();
	}
	
	if (changed['markerTo']) {
		var markerTo = this.getFormElement("markerTo").value;
		this.model.markerTo = (markerTo == "") ? null : JSON.parse(markerTo);
		this.setMarker('markerTo');
	}
	
	if (changed['markerFrom']) {
		var markerFrom = this.getFormElement("markerFrom").value;
		this.model.markerFrom = (markerFrom == "") ? null : JSON.parse(markerFrom);
		this.setMarker('markerFrom');
	}
	
	 if (changed['route']) {
		var inner = this.getFormElement("route").innerHTML;
		if (inner != "") {
			this.model.route = JSON.parse(inner);
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
	var lonLat = this.map.getLonLatFromPixel(new OpenLayers.Pixel(
	x, y));
	var mapPosition = this.untransformLonLat(lonLat);
	//TODO: Set Marker on Click of Menu Entry and fill Inputelements with data
	//TODO: Fix bug of popup not disappearing in IE9
	if (this.rightClickMenu) {
		this.rightClickMenu.destroy();
		this.rightClickMenu = null;
	}
	var clientId = this.clientId;
	function createRouteFromToDiv(text, id) {
		return "<div id=\"" + clientId + ":" + id
				+ "\" onclick=\"KITCampusMap.maps['" + clientId
				+ "'].handleRouteFromToClick('" + id + "')\""
				+ "onmouseout=\"KITCampusMap.maps['" + clientId
				+ "'].uncolorMenu(this.id);\""
				+ " class='mapContextMenuEntry' "
				+ "onmouseover=\"KITCampusMap.maps['" + clientId
				+ "'].colorMenu(this.id);\">" + text + "</div>";
	}
	var menuHTML = createRouteFromToDiv(this
			.getTranslation("setRouteFromLabel"), "markerFrom");
	menuHTML += createRouteFromToDiv(this.getTranslation("setRouteToLabel"),
			"markerTo");
	
	this.rightClickMenuPosition = mapPosition; // make the WorldPosition to a MapPosition
	this.rightClickMenuPosition.map = this.model.map;
	this.rightClickMenu =  new OpenLayers.Popup(null,this.transformWorldPosition(mapPosition), null, menuHTML, false);
//    this.rightClickMenu.maxSize = new OpenLayers.Size(166, 60);
    this.rightClickMenu.autoSize = true;
    this.map.addPopup(this.rightClickMenu);
};

KITCampusMap.prototype.handleRouteFromToClick = function(fromTo) {
	this.rightClickMenu.hide();
	var input = this.getFormElement(fromTo);
	input.value = JSON.stringify(this.rightClickMenuPosition);
	this.requestUpdate(input.id);
};

/**
 *  This function colors the menu entry passed by id.
 *  
 *  @param id
 *  		the id of the div container to be colored
 */
KITCampusMap.prototype.colorMenu = function (id) {
	var menu = document.getElementById(id);
	menu.className = 'mapContextMenuEntryHighlighted';
	this.rightClickMenu.updateSize();
};

/**
 *  This function sets the color values of the menu entry passed by id
 *  back to standard values.
 *  
 *  @param id
 *  		the id of the div container to be resetted.
 */
KITCampusMap.prototype.uncolorMenu = function(id) {
	var menu = document.getElementById(id);
	menu.className = 'mapContextMenuEntry';
	this.rightClickMenu.updateSize();
};

/**
 * Restricts the allowed zoom levels to some given bounds.
 * 
 * @param event
 *            the event given by OpenLayers.
 */
KITCampusMap.prototype.handleZoomEnd = function(event) {
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
				+ ":buildingPOIList " + id + ":highlightedPOI "
				+ this.additionalRenderIDs,
		onevent : this.eventCallback
	});
	
	// Reset all listener input fields. Otherwise JSF will cause a value change event on every request
	// hence the serverside components are always set to null
	ids = [ "buildingIDListener", "highlightedPOIIDListener", "buildingPOIsListListener" ];
	for ( var id in ids) {
		// Any numerical value can be entered
		this.getFormElement(ids[id]).value = "123";
	}
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
			if (this.map.getZoom() < this.model.map.minZoom) {
				this.map.zoomTo(this.model.map.minZoom);
			}
			this.enableMapEvents();
		}
	} else if (mapLocator.center != null) {
		this.map.panTo(this.transformWorldPosition(mapLocator.center));
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
	this.poiMarkers = new Array();
	for ( var index in m.pois) {
		if (m.highlightedPOI == null || m.pois[index].id != m.highlightedPOI.id) {
			var marker = this.createPOIMarker(m.pois[index]);
			this.poiMarkerLayer.addMarker(marker);
		}
	}
	if (this.highlightedMarker) {
		var marker = this.highlightedMarker;
		this.poiMarkerLayer.addMarker(marker);
		marker.setUrl("resources/mapcomponent/openlayers/img/marker-gold.png");
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
KITCampusMap.prototype.createPOIMarker = function(poi, higlighted) {
    
    var feature = new OpenLayers.Feature(this.poiMarkerLayer, this.transformWorldPosition(poi.position)); 
    var marker = feature.createMarker();
    var markerClick = function (evt) {
    	var input = this.getFormElement("highlightedPOIIDListener");
    	input.value = poi.id;
    	this.requestUpdate(input.id);
        OpenLayers.Event.stop(evt);
    };
    
    var markerMouseOver = function (evt) {
    	if (!this.tooltip) {
    		var tooltip = new OpenLayers.Popup(null,this.transformWorldPosition(poi.position), null, this.getTooltipContentHTML(poi), false);
    		tooltip.maxSize = new OpenLayers.Size(400, 20);
    		tooltip.opacity = .7;
    		tooltip.setBorder("1px solid #009d82");
    		tooltip.autoSize = true;
    		tooltip.updateSize();
    		this.tooltip = tooltip;
    		this.map.addPopup(tooltip);
    	};
    };
    
    var markerMouseOut = function (evt) {
    	if (this.tooltip) {
    		this.tooltip.hide();
    		this.tooltip = null;
    	}
    };

	marker.events.register("mousedown", this, markerClick);
	marker.events.register("mouseover", this, markerMouseOver);
	marker.events.register("mouseout", this, markerMouseOut);
	return marker;
};

KITCampusMap.prototype.getPOIContentHTML = function (poi){
	var result = "<div class='mapPopupHeader'>" + poi.name + "</div>";
	result += "<div class='mapPopupPOIInfo'>" + unescape(poi.description) + "</div>";

		if (poi.buildingID) {
		result += "<div class='mapBuildingPOILinks'>"
				+ "<a href=\"javascript:KITCampusMap.maps['" + this.clientId
				+ "'].handleSwitchToBuilding()\">"
				+ this.getTranslation("showBuildingMapLabel") + "</a>";
		result += "<br /><a href=\"javascript:KITCampusMap.maps['"
				+ this.clientId + "'].handleShowPOIsInBuilding()\">"
				+ this.getTranslation("showPOIsInBuildingLabel") + "</a></div>";
	}
	return result;
};

KITCampusMap.prototype.getTranslation = function(label) {
	return document.getElementById(this.clientId
			+ ":" + label).innerHTML;
};

KITCampusMap.prototype.handleSwitchToBuilding = function() {
	var input = this.getFormElement("buildingIDListener");
	input.value = this.popupPOI.buildingID;
	this.requestUpdate(input.id);
};

KITCampusMap.prototype.handleShowPOIsInBuilding = function() {
	var input = this.getFormElement("buildingPOIsListListener");
	input.value = this.popupPOI.buildingID;
	this.requestUpdate(input.id);
};

/**
 * Draws the current route in a vector layer. The vector layer is created if
 * necessary. The route must be set in this.model.route
 */
KITCampusMap.prototype.setRoute = function() {
	this.routeLayer.removeAllFeatures();
	// This offsets is added to each point of the route. Otherwise, the route
	// will be rendered slightly beneath the routes from the osm tiles.
	var offsetX = -5;
	var offsetY = -5.4;
	
	if (this.model.route != null) {
		var pointList = [];
		var wp = this.model.route.waypoints;
		for ( var p = 0; p < wp.length; ++p) {
			var lonlat = this.transformWorldPosition(wp[p]);
			pointList.push(new OpenLayers.Geometry.Point(lonlat.lon + offsetX,
					lonlat.lat + offsetY));
		}

		var style_red = {
			strokeColor : "#FF0000",
			strokeWidth : 3
		};

		var routeFeature = new OpenLayers.Feature.Vector(
				new OpenLayers.Geometry.LineString(pointList), null, style_red);
		this.routeLayer.addFeatures([ routeFeature ]);
	}
};

KITCampusMap.prototype.setMarker = function(markerFromTo) {
	var m = this.model;
	if (this[markerFromTo + 'Marker']) {
		this.markerLayer.removeMarker(this[markerFromTo + 'Marker']);
	}
	if (m[markerFromTo] != null) {
		var size = new OpenLayers.Size(21, 25);
		var anchor = new OpenLayers.Pixel(-(size.w / 2), -size.h);
		var iconColor = (markerFromTo == "markerTo" ? 'green' : 'blue');
		var icon = new OpenLayers.Icon('http://openlayers.org/dev/img/marker-'
				+ iconColor + '.png', size, anchor);
		var position = this.transformWorldPosition(m[markerFromTo]);
		this[markerFromTo + 'Marker'] = new OpenLayers.Marker(position, icon);
		this.markerLayer.addMarker(this[markerFromTo + 'Marker']);
	}
};

KITCampusMap.prototype.setHighlightedPOI = function() {
	var poi = this.model.highlightedPOI;
	if (this.highlightedMarker) {
		this.highlightedMarker = null;
	}
	if (this.highlightedPOIPopup) {
		this.highlightedPOIPopup.hide();
		this.highlightedPOIPopup = null;
	} 
	if (poi != null) {
		// Set new highlighted POI
		var marker = this.createPOIMarker(poi, true);
		var feature = new OpenLayers.Feature(this.poiMarkerLayer, this.transformWorldPosition(poi.position)); 
		feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {
			'autoSize': true, 'maxSize': new OpenLayers.Size(340, 370)
		});
		feature.data.popupContentHTML = this.getPOIContentHTML(poi);
		feature.data.overflow = "auto";
		
		if (feature.popup == null) {
			var closeClick = function (evt) {
				var input = this.getFormElement("highlightedPOIIDListener");
				input.value = "";
				this.requestUpdate(input.id);
				this.popupPOI = null;
				feature.popup.hide();
			};
			var p = feature.popup = feature.createPopup(true, closeClick, this);
			
			this.map.addPopup(feature.popup, true);
			feature.popup.show();
		} else {
			this.map.addPopup(feature.popup, true);
			feature.popup.show();
		}
		
		this.popupPOI = poi;
		this.highlightedMarker = marker;
		this.highlightedPOIPopup = feature.popup;
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
	var closeContextMenu = function(evt) {
		if (this.rightClickMenu) {
			this.rightClickMenu.destroy();
			this.rightClickMenu = null;
		};
	};
	this.routeLayer.events.register("click", this, closeContextMenu);
	this.map.addLayer(this.mapLayer);
	this.map.setBaseLayer(this.mapLayer);
	this.map.restrictedExtent = this
			.transformMapSection(this.model.map.boundingBox);
	this.map.zoomToExtent(this.map.restrictedExtent);
	this.map.zoomTo(this.model.map.minZoom);
	this.enableMapEvents();
};

KITCampusMap.prototype.setBuildingPOIList = function() {
	if (!this.model.highlightedPOI || !this.highlightedPOIPopup) {
		// TODO: Discuss BuildingPOIList without an existing HiglightedPOI
		return;
	}
	if (this.model.buildingPOIList == null) {
		// TODO: Discuss what happens if the buildingPOIList is set to null
	}
	else {
		this.highlightedPOIPopup.setContentHTML(this.createBuildingPOIList(this.model.buildingPOIList));
	}
	
};

KITCampusMap.prototype.createBuildingPOIList = function(poiList) {
	var result = new String("<ul>");
	for ( var index in poiList) {
		var poi = poiList[index];
		result += "<li><a href='javascript:KITCampusMap.maps[\"" + this.clientId
				+ "\"].handleBuildingPOIListClick(\"" + poi.id + "\")'>"
				+ poi.name + "</a></li>\n";
	}
	result += "</ul>";
	return result;
};

KITCampusMap.prototype.handleBuildingPOIListClick = function(poiID) {
	var input1 = this.getFormElement("buildingIDListener");
	input1.value = this.popupPOI.buildingID;
	
	var input2 = this.getFormElement("highlightedPOIIDListener");
	input2.value = poiID;
	this.requestUpdate(input1.id + " " + input2.id);
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

/**
 * 
 */
KITCampusMap.prototype.getTooltipContentHTML = function(poi) {
	return "<div>" + poi.name + "</div>";
};



