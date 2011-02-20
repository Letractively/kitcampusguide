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
	
	/**
	 * Stores the JSF client id of the map composite component.
	 */
	this.clientId = clientId;
	
	/**
	 * Stores the OpenLayers "div" containing the map.
	 */
	this.mapElement = document.getElementById(clientId + ":map");
	
	/**
	 * Stores the form element which is used to retrieve and send data from and to 
	 * the server.
	 */
	this.form = document.getElementById(clientId + ":form");

	/**
	 * Stores a "copy" of the current map model, all data retrieved from the
	 * server is written into this model. The Attributes are nearly the same as
	 * the attributes of MapModel.
	 */
	this.model = new Object();
	
	// Register map as global variable
	KITCampusMap.maps[clientId] = this;
	
	/**
	 * Stores all information directly relevant to OpenLayers, such as various layers or the map.
	 */
	this.olData = new Object();
	
	// This initialization is mainly taken from Mapstraction
	/**
	 * Stores the openlayers map instance.
	 */
	this.olData.map = new OpenLayers.Map(this.mapElement.id, {
		maxExtent : new OpenLayers.Bounds(-20037508.34, -20037508.34,
				20037508.34, 20037508.34),
		maxResolution : 156543,
		numZoomLevels : 22,
		units : 'meters',
		projection : "EPSG:41001",
		controls : []
	});
	this.olData.map.addControl(new OpenLayers.Control.PanZoom());
	this.olData.map.addControl(new OpenLayers.Control.Navigation({
		handleRightClicks : true
	}));

	// Create all necessary layers (only the map layer is created later)
	// Init vector layer for route
	/**
	 * Contains the OpenLayers layer displaying the route.
	 */
	this.olData.routeLayer = new OpenLayers.Layer.Vector("route", null);
	this.olData.map.addLayer(this.olData.routeLayer);
	
	// Init marker layer for POIs
	/**
	 * Contains the layer displaying the markers for all POIs.
	 */
	this.olData.poiMarkerLayer = new OpenLayers.Layer.Markers("poiMarkers");
	this.olData.map.addLayer(this.olData.poiMarkerLayer);

	// Init marker layer
	/**
	 * Stores the layer displaying the "routeFrom" and "routeTo" marker.
	 */
	this.olData.markerLayer = new OpenLayers.Layer.Markers("markers");
	this.olData.map.addLayer(this.olData.markerLayer);

	var thiss = this;	
	this.olData.map.div.oncontextmenu = function (e) {
		e = e ? e : window.event;
		if (e.preventDefault) {
			e.preventDefault(); // For non-IE browsers.
		}
		var div = thiss.olData.map.div;
		if (!div.offsets) {
			// can happen in some very rare cases, prevent an error
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
	
	// Read all ids which should be rerendered as well on an ajax request
	/**
	 * Contains a white space separated list of client IDs. These ID's will be added to the "execute" and "render" IDs on 
	 * every AJAX request (see jsf.ajax.request for more information).
	 */
	this.additionalRenderIDs = this.getFormElement("additionalRenderIDs").innerHTML;
	this.applyChanges();
}

/**
 * This methods reads all data from the input fields and calls the update methods of the
 * appropriate data. Only properties marked as changed (via the changedProperties-property)
 * will be read and updated.<br />
 * The method will take care to update the data in the right order, e.g. the map is always
 * updated first.
 */
KITCampusMap.prototype.applyChanges = function() {
	// Get all Attributes which need to be redrawn
	var inner = this.getFormElement("mapModel").innerHTML;
	var mapModel = (inner == "") ? null : JSON.parse(inner);
	
	// Fetch all changed properties
	this.changed = new Object();
	for (var prop in mapModel) {
		this.changed[prop] = true;
	}
	
	if (this.changed['map']) {
		this.model.map = mapModel.map;
		this.setMapLayer();
	}
	if (this.changed['highlightedPOI']) {
		this.model.highlightedPOI = mapModel.highlightedPOI;
	}
	
	if (this.changed['mapLocator']) {
		this.model.mapLocator = mapModel.mapLocator;
		this.setMapLocator();
	}
	
	if (this.changed['highlightedPOI']) {
		this.setHighlightedPOI();
	}
	
	if (this.changed['POIs']) {
		this.model.pois = mapModel.POIs;
	}
	
	// The POIs must be updated if the highlighted POI changes hence the old 
	// marker needs to be deleted
	if (this.changed['POIs'] || this.changed['highlightedPOI']) {
		this.setPOIs();
	}

	if (this.changed['buildingPOIList']) {
		this.model.buildingPOIList = mapModel.buildingPOIList;
		this.model.buildingPOI = mapModel.buildingPOIList;
		this.setBuildingPOIList();
	}
	
	if (this.changed['markerTo']) {
		this.model.markerTo = mapModel.markerTo;
		this.setMarker('markerTo');
	}
	
	if (this.changed['markerFrom']) {
		this.model.markerFrom = mapModel.markerFrom;
		this.setMarker('markerFrom');
	}
	
	 if (this.changed['route']) {
		this.model.route = mapModel.route;
		this.setRoute();
	}
};

/**
 * Returns the form element matching a given identifier (for example 'POIs',
 * 'mapSection').
 * 
 * @param relativeId
 *            the id of the element which should be retrieved.
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
	var lonLat = this.olData.map.getLonLatFromPixel(new OpenLayers.Pixel(
	x, y));
	var mapPosition = KITCampusHelper.untransformLonLat(lonLat);
	if (this.olData.rightClickMenu) {
		this.olData.rightClickMenu.destroy();
		this.olData.rightClickMenu = null;
	}
	var clientId = this.clientId;
	function createRouteFromToDiv(text, id) {
		return "<div id=\"" + clientId + ":" + id
				+ "\" onclick=\"KITCampusMap.maps['" + clientId
				+ "'].handleRouteFromToClick('" + id + "')\""
				+ "onmouseout=\"KITCampusMap.maps['" + clientId
				+ "'].colorMenu(this.id, false);\""
				+ " class='mapContextMenuEntry' "
				+ "onmouseover=\"KITCampusMap.maps['" + clientId
				+ "'].colorMenu(this.id, true);\">" + text + "</div>";
	}
	var menuHTML = createRouteFromToDiv(this
			.getTranslation("setRouteFromLabel"), "markerFrom");
	menuHTML += createRouteFromToDiv(this.getTranslation("setRouteToLabel"),
			"markerTo");
	
	this.olData.rightClickMenuPosition = mapPosition; // make the WorldPosition to a MapPosition
	this.olData.rightClickMenuPosition.map = this.model.map;
	this.olData.rightClickMenu = new OpenLayers.Popup(null, KITCampusHelper.transformWorldPosition(mapPosition), null, menuHTML, false);
    this.olData.rightClickMenu.autoSize = true;
    this.olData.map.addPopup(this.olData.rightClickMenu);
};

/**
 * Is called when the context menu entry "Route from" or "Route to" is choosen.
 * The method will send the event to the server.
 * 
 * @param fromTo
 *            either "markerFrom" or "markerTo", depending on which entry was
 *            clicked.
 */
KITCampusMap.prototype.handleRouteFromToClick = function(fromTo) {
	this.olData.rightClickMenu.hide();
	var eventType = (fromTo == "markerFrom") ? "setRouteFromByContextMenu" : 
		"setRouteToByContextMenu";
	this.requestUpdate(new KITCampusEvent(eventType, this.olData.rightClickMenuPosition, "MapPosition"));
};

/**
 *  This function sets the color values of a menu entry matching a given id.
 *  
 *  @param id
 *  		the id of the div container to be resetted.
 *  @param color <code>true</code> if the entry should be highlighted and colored
 */
KITCampusMap.prototype.colorMenu = function(id, color) {
	var menu = document.getElementById(id);
	menu.className = color ? 'mapContextMenuEntryHighlighted'
			: 'mapContextMenuEntry';
};

/**
 * Restricts the allowed zoom levels to the bounds specified by the current map.
 * The method is called after every zoom event.
 * @param event
 *            the event given by OpenLayers.
 */
KITCampusMap.prototype.handleZoomEnd = function(event) {
	if (this.olData.map.getZoom() < this.model.map.minZoom) {
		this.olData.map.zoomTo(this.model.map.minZoom);
	} else if (this.olData.map.getZoom() > this.model.map.maxZoom) {
		this.olData.map.zoomTo(this.model.map.maxZoom);
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
	var newMapLocator = new Object();
	newMapLocator.mapSection = KITCampusHelper.untransformBounds(this.olData.map.getExtent());
	newMapLocator.center = null;
	this.requestUpdate(new KITCampusEvent("mapLocatorChanged", newMapLocator, "MapLocator"));
};

/**
 * This method is set as callback method for a jsf.ajax.request call. On
 * success, apply changes is called to render the new data.
 * 
 * @param data
 *            data argument generated by JSF, see JSF javascript documentation
 */
KITCampusMap.prototype.eventCallback = function(data) {
	if (data.status == "success") {
		var id;
		// TODO: Find a legal way to retrieve the id, not just a working one...
		for (clientId in KITCampusMap.maps) {
			id = clientId;
			break;
		}
//		var clientId = data.source.id.substring(0, data.source.id.length
//				- ":form".length);
		var campusMap = KITCampusMap.maps[id];
		campusMap.applyChanges.call(campusMap);
	}
};

/**
 * This method sends an AJAX request to the server and sets
 * <code>eventCallback</code> as callback method. Aditional fields which
 * should be updated can be specified via <code>this.additionalRenderIDs</code>.
 * The given MapEvents will be retrieved by the class MapEventsListener
 * 
 * @param events
 *            can be either a list or a single KITCampusEvent. The given
 *            events will be submitted to the server, the controller will be
 *            notified.
 */
KITCampusMap.prototype.requestUpdate = function(events) {
	if (!(events instanceof Array))
		events = [events];
	
	var outputField = this.getFormElement("outputField");
	outputField.value = JSON.stringify(events);
	var id = this.form.id;
	var additionalIDs = this.clientId + ":inputForm:routeFromField " +
			this.clientId + ":inputForm:routeToField " + this.clientId
			+ ":lateralBar";
	
	jsf.ajax.request(this.form, null, {
		execute : id + ":outputField ",
		render : id + ":mapModel " + additionalIDs,
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
		var curSection = KITCampusHelper.untransformBounds(this.olData.map.getExtent());
		// Check if the current section is equal to the new section
		if (!KITCampusHelper.positionEquals(curSection.northWest,
				mapLocator.mapSection.northWest)
				|| !KITCampusHelper.positionEquals(curSection.southEast,
						mapLocator.mapSection.southEast)) {
			this.disableMapEvents();
			this.olData.map.zoomToExtent(KITCampusHelper
					.transformMapSection(mapLocator.mapSection));
			if (this.olData.map.getZoom() < this.model.map.minZoom) {
				this.olData.map.zoomTo(this.model.map.minZoom);
			} else if (this.olData.map.getZoom() > this.model.map.maxZoom) {
				this.olData.map.zoomTo(this.model.map.maxZoom);
			}
			this.enableMapEvents();
		}
	} else if (mapLocator.center != null) {
		if (!this.changed['highlightedPOI'] && this.model.highlightedPOI != null) {
			// Only change the map position if no highlighted POI was set. Instead, the map
			// section will be panned until the higlightedPOIPopup fits inside
			// (see setHighlightedPopup).
			if (!this.changed['map']) {
				// Panning is only used when the map hasn't changed for this
				// request.
				this.olData.map.panTo(KITCampusHelper
						.transformWorldPosition(mapLocator.center));
			} else {
				this.olData.map.setCenter(KITCampusHelper
						.transformWorldPosition(mapLocator.center));
			}
		}
	}
};

/**
 * Sets openlayer markers for the current list of POIs specified by
 * <code>this.model.pois</code>. Additionally, the highlighted POI marker (if
 * existent) is rendered with a different icon.
 */
KITCampusMap.prototype.setPOIs = function() {
	var m = this.model;
	this.olData.poiMarkerLayer.clearMarkers();
	this.olData.poiMarkers = new Array();
	for ( var index in m.pois) {
		if (m.highlightedPOI == null || m.pois[index].id != m.highlightedPOI.id) {
			var marker = this.createPOIMarker(m.pois[index]);
			this.olData.poiMarkerLayer.addMarker(marker);
		}
	}
	if (this.olData.highlightedMarker) {
		var marker = this.olData.highlightedMarker;
		this.olData.poiMarkerLayer.addMarker(marker);
		marker.setUrl("resources/mapcomponent/openlayers/img/marker-gold.png");
	}
};

/**
 * Creates an open layers marker describing a given POI. The marker will have
 * click handlers which call a highlightedPOIChanged event. A tooltip is drawn
 * when the user hovers over the marker. The tooltip content is determined by
 * <code>getTooltipContentHTML</code>
 * 
 * @param poi
 *            a POI.
 * @returns {OpenLayers.Marker} a marker
 * 
 */
KITCampusMap.prototype.createPOIMarker = function(poi) {
	var feature = new OpenLayers.Feature(this.olData.poiMarkerLayer,
			KITCampusHelper.transformWorldPosition(poi.position));
	var marker = feature.createMarker();
	var markerClick = function(evt) {
		var event = new KITCampusEvent("clickOnPOI", poi.id);
		this.requestUpdate(event);
		OpenLayers.Event.stop(evt);
	};
    
    // This method draws a small tooltip menu
    var markerMouseOver = function (evt) {
    	if (!this.olData.tooltip) {
    		var tooltip = new OpenLayers.Popup(null,KITCampusHelper.transformWorldPosition(poi.position), null, this.getTooltipContentHTML(poi), false);
    		tooltip.maxSize = new OpenLayers.Size(400, 20);
    		tooltip.opacity = .7;
    		tooltip.setBorder("1px solid #009d82");
    		tooltip.autoSize = true;
    		tooltip.updateSize();
    		this.olData.tooltip = tooltip;
    		this.olData.map.addPopup(tooltip);
    	};
    };
    
    var markerMouseOut = function (evt) {
    	if (this.olData.tooltip) {
    		this.olData.tooltip.hide();
    		this.olData.tooltip = null;
    	}
    };

	marker.events.register("mousedown", this, markerClick);
	marker.events.register("mouseover", this, markerMouseOver);
	marker.events.register("mouseout", this, markerMouseOut);
	return marker;
};

/**
 * Returns the HTML snippet which is rendered as a POI description. If the POI
 * is a building POI, some extra links are added to switch to the building map
 * and to show the POIs in the building.
 * 
 * @param poi
 *            the POI which should be described
 * @returns {String} a HTML snippet
 */
KITCampusMap.prototype.getPOIContentHTML = function (poi){
	var result = "<div class='mapPopupHeader'>" + poi.name + "</div>";
	result += "<div class='mapPopupPOIInfo'>" + Encoder.htmlDecode(poi.description) + "</div>";

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

/**
 * Gets the translation for a given label. All translation fields must have the
 * id <code>{clientID}:{label}</code>. An exception is thrown if the label
 * could not found in the component.
 * 
 * @param label
 *            a String
 * @returns the translated String.
 */
KITCampusMap.prototype.getTranslation = function(label) {
	return document.getElementById(this.clientId
			+ ":" + label).innerHTML;
};

/**
 * Is called when the "Switch to building" link of a building POI was clicked.
 */
KITCampusMap.prototype.handleSwitchToBuilding = function() {
	this.requestUpdate(new KITCampusEvent("changeToBuildingMap",
			this.olData.popupPOI.buildingID, "Integer"));
};

/**
 * Is called when the "Show points in building" link of a building POI was
 * clicked.
 */
KITCampusMap.prototype.handleShowPOIsInBuilding = function() {
	this.requestUpdate(new KITCampusEvent("showPOIsInBuilding",
			this.olData.popupPOI.buildingID, "Integer"));
};

/**
 * Draws the current route in a vector layer. The route must be set in this.model.route. If the
 * route is <code>null</code> the old route will be removed from the map.
 */
KITCampusMap.prototype.setRoute = function() {
	this.olData.routeLayer.removeAllFeatures();
	// This offsets is added to each point of the route. Otherwise, the route
	// will be rendered slightly beneath the routes from the osm tiles.
	var offsetX = -5;
	var offsetY = -5.4;
	
	if (this.model.route != null) {
		var pointList = [];
		var wp = this.model.route.waypoints;
		for ( var p = 0; p < wp.length; ++p) {
			var lonlat = KITCampusHelper.transformWorldPosition(wp[p]);
			pointList.push(new OpenLayers.Geometry.Point(lonlat.lon + offsetX,
					lonlat.lat + offsetY));
		}

		var style_red = {
			strokeColor : "#FF0000",
			strokeWidth : 3
		};

		var routeFeature = new OpenLayers.Feature.Vector(
				new OpenLayers.Geometry.LineString(pointList), null, style_red);
		this.olData.routeLayer.addFeatures([ routeFeature ]);
	}
};

/**
 * Sets the markers indicating the start or the end point of a route
 * calculation. The markers must be stored in <code>this.model.markerFrom</code>
 * or <code>this.model.markerTo</code>
 * 
 * @param markerFromTo
 *            must be either "markerFrom" or "markerTo", depending on the marker
 *            which should be set.
 */
KITCampusMap.prototype.setMarker = function(markerFromTo) {
	var m = this.model;
	if (this[markerFromTo + 'Marker']) {
		this.olData.markerLayer.removeMarker(this[markerFromTo + 'Marker']);
	}
	if (m[markerFromTo] != null) {
		var size = new OpenLayers.Size(21, 25);
		var anchor = new OpenLayers.Pixel(-(size.w / 2), -size.h);
		var iconColor = (markerFromTo == "markerTo" ? 'green' : 'blue');
		var icon = new OpenLayers.Icon('http://openlayers.org/dev/img/marker-'
				+ iconColor + '.png', size, anchor);
		var position = KITCampusHelper.transformWorldPosition(m[markerFromTo]);
		this[markerFromTo + 'Marker'] = new OpenLayers.Marker(position, icon);
		this.olData.markerLayer.addMarker(this[markerFromTo + 'Marker']);
	}
};

/**
 * Sets or removes the highlighted POI. The highlighted POI must be stored in
 * <code>this.model.highlightedPOI</code>, if set to <code>null</code> the
 * old highlighted POI marker will be removed. Otherwise, a popup will show up
 * showing the description for the highlighted POI. The description is
 * determined by <code>getPOIContentHTML</code>.
 */
KITCampusMap.prototype.setHighlightedPOI = function() {
	var poi = this.model.highlightedPOI;
	if (this.olData.highlightedMarker) {
		this.olData.highlightedMarker = null;
	}
	if (this.olData.highlightedPOIPopup) {
		this.olData.highlightedPOIPopup.hide();
		this.olData.highlightedPOIPopup = null;
	} 
	if (poi != null) {
		// Set new highlighted POI
		var marker = this.createPOIMarker(poi, true);
		var feature = new OpenLayers.Feature(this.olData.poiMarkerLayer, KITCampusHelper.transformWorldPosition(poi.position)); 
		feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {
			'autoSize': true, 'maxSize': new OpenLayers.Size(320, 200), 'panMapIfOutOfView': true
		});
		feature.data.popupContentHTML = this.getPOIContentHTML(poi);
		feature.data.overflow = "auto";

		var closeClick = function (evt) {
			this.requestUpdate(new KITCampusEvent("clickOnPOI", null));
			this.olData.popupPOI = null;
			feature.popup.hide();
		};
		var p = feature.popup = feature.createPopup(true, closeClick, this);
		
		this.olData.map.addPopup(feature.popup, true);
//		feature.popup.show();
		
		this.olData.popupPOI = poi;
		this.olData.highlightedMarker = marker;
		this.olData.highlightedPOIPopup = feature.popup;
	}
};

/**
 * Creates the main map layer. It will show the current map's tileset referenced
 * by its tiles URL. Navigation will be restricted to the current map's bounding
 * box. The current map must be stored in this.model.map.
 */
KITCampusMap.prototype.setMapLayer = function() {
	this.disableMapEvents();
	if (this.olData.mapLayer) {
		this.olData.map.removeLayer(this.olData.mapLayer);
	}
	this.olData.mapLayer = new OpenLayers.Layer.XYZ("XYZ-Layer",
			this.model.map.tilesURL);
	var closeContextMenu = function(evt) {
		if (this.olData.rightClickMenu) {
			this.olData.rightClickMenu.destroy();
			this.olData.rightClickMenu = null;
		};
	};
	this.olData.routeLayer.events.register("click", this, closeContextMenu);
	this.olData.mapLayer.events.register("click", this, closeContextMenu);
	this.olData.map.addLayer(this.olData.mapLayer);
	this.olData.map.setBaseLayer(this.olData.mapLayer);
	this.olData.map.restrictedExtent = KITCampusHelper
			.transformMapSection(this.model.map.boundingBox);
	this.olData.map.zoomToExtent(this.olData.map.restrictedExtent);
	this.olData.map.zoomTo(this.model.map.minZoom);
	this.enableMapEvents();
};

/**
 * Modifies the current highlighted poi's popup to display a list with pois in a
 * building. The list must be stored in <code>this.model.buildingPOIList</code>,
 * if set to <code>null</code>, the default text describing the POI will be set.
 */
KITCampusMap.prototype.setBuildingPOIList = function() {
	if (!this.model.highlightedPOI || !this.olData.highlightedPOIPopup) {
		return;
	}
	if (this.model.buildingPOIList == null) {
		// Set the normal description
		this.olData.highlightedPOIPopup.setContentHTML(this.getPOIContentHTML(this.model.highlightedPOI));
	}
	else {
		this.olData.highlightedPOIPopup.setContentHTML(this.createBuildingPOIList(this.model.buildingPOIList));
	}
	
};

/**
 * Returns a HTML snippet describing a list of POIs. The POIs are rendered as an
 * <code>li</code> Element. For each POI, the name is used as description, a
 * click on the name will call the method
 * <code>handleBuildingPOIListClick</code>.
 * 
 * @param poiList
 *            a list of POIs
 * @returns {String} a HTML snippet
 */
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

/**
 * Is called when the user clicks on a link of a building list. The ID of the
 * poi which was clicked is passed as argument. The method sends an Ajax request
 * and informs the server that the user wants to visit a POI inside a building.
 * 
 * @param poiID
 *            the id of the POI which was clicked.
 */
KITCampusMap.prototype.handleBuildingPOIListClick = function(poiID) {
	this.requestUpdate(new KITCampusEvent("listEntryClicked", poiID));
};

// Help functions ------------------------------------------------------------

/**
 * Enables all event listeners. The handler methods will be called when an event
 * occurs.
 */
KITCampusMap.prototype.enableMapEvents = function() {
	this.olData.map.events.on({
		"moveend" : this.handleMove,
		"zoomend" : this.handleZoomEnd,
		scope : this
	});
};

/**
 * Disables all event listeners. The event handling methods will not be called.
 */
KITCampusMap.prototype.disableMapEvents = function() {
	this.olData.map.events.un({
		"moveend" : this.handleMove,
		"zoomend" : this.handleZoomEnd,
		scope : this
	});
};

/**
 * Returns the HTML snippet used to render a tooltip 
 */
KITCampusMap.prototype.getTooltipContentHTML = function(poi) {
	return "<div>" + poi.name + "</div>";
};

/**
 * Represents an event occuring on the map. This can be one of the events
 * specified in the <code>POIListener</code> and <code>MapListener</code>.
 * 
 * @param type
 *            identifies which event occured, can be "clickOnPOI" or
 *            "setRouteFromByContextMenu" for example.
 * @param data
 *            an arbitrary data argument which will be passed to the server as
 *            well
 * @param dataType
 *            the type of the <code>data</code> argument. See
 *            <code>OutputConverter</code> for a list of supported types.
 * @returns {KITCampusEvent} a newly created <code>KITCampusEvent</code>
 */
function KITCampusEvent(type, data, dataType) {
	this.type = type;
	this.dataType = dataType ? dataType : "String";
	this.data = data;
}