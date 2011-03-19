/** OpenLayers map component */
var map;

/** layer holding the map images */
var mapLayer;

/** layer for displaying routes */
var routeLayer;

/** layer for displaying a position marker */
var positionMarkerLayer;

/** layers for displaying POI categories */
var POICategorieLayers = new Object();

/** holds all available POI categories */
var POICategories = new Object();

/** holds all OpenLayers features representing the different POI */
var POIFeatures = new Object();

/** holds the POI currently selected */
var currentPOI = null;

/** holds the context menu */
var contextmenu;

/** Coordinates in World Geodetic System 1984 */
var WGS1984 = new OpenLayers.Projection("EPSG:4326");

/** Coordinates in  Mercator cylindrical projection */
var MercatorProjection = new OpenLayers.Projection("EPSG:900913");

/**
 * Creates and sets up the OpenLayers map component. 
 */
function createMap() {
    // setup general things 
    OpenLayers.Lang.setCode('de');
    POICategories = createJSONObject(getElement("map-form:all-poi").innerHTML);
    
    // properties for map layout
    var lon = 8.41356;
    var lat = 49.01202;
    var zoom = 15;
    var minzoom = 15;
    var maxzoom = 18;
    var leftBottom = createLonLat(8.4025,49.008);
    var rightTop = createLonLat(8.426,49.0173);
    var extent = new OpenLayers.Bounds(leftBottom.lon, leftBottom.lat, rightTop.lon, rightTop.lat);
    
    // definition of map properties
    var options = {
        projection: MercatorProjection, 
        displayProjection: WGS1984, 
        maxExtent: extent, 
        restrictedExtent: extent, 
        numZoomLevel: 3, 
        units: 'meters'
    };
    
    // creating the map object
    var mouse = new OpenLayers.Control.MousePosition();
    map = new OpenLayers.Map('map', options);
    map.addControl(mouse);
    map.addControl(new OpenLayers.Control.ScaleLine());
    map.minZoomLevel = minzoom;
    map.maxZoomLevel = maxzoom;
    map.restrictedExtent = extent;

    // add Map-images as base-layer
    mapLayer = new OpenLayers.Layer.OSM("New Layer", "resources/tiles/campus/${z}/${x}/${y}.png");
    map.addLayer(mapLayer);
    setMyCenter(lon,lat,zoom);
    
    // add layer for displaying routes
    routeLayer = new OpenLayers.Layer.Vector("route", null);
    map.addLayer(routeLayer);
  
    // add layers for displaying POI
    for (var key in POICategories) {
        POICategorieLayers[POICategories[key].name] = createPOICategoryLayer(POICategories[key]);
        map.addLayer(POICategorieLayers[POICategories[key].name]);
    }
    
    // creating the right click menu (context menu)
    positionMarkerLayer = createPOICategoryLayer({pois: {}});
    map.addLayer(positionMarkerLayer);
    map.div.onclick = hideContextMenu;
    map.div.oncontextmenu = function(evt) {
    	showContextMenu(evt, mouse.div.innerHTML);
    };
}

/**
 * Creates a new layer for an OpenLayers map containing the specified POI category.
 * 
 * @param POICategory the POI category to be represented by the layer 
 * @returns a layer representing the specified POI category
 */
function createPOICategoryLayer(POICategory) {
    var options = {projection: new OpenLayers.Projection("EPSG:4326"), visibility: true, displayInLayerSwitcher: true}; 
    var layer = new OpenLayers.Layer.Markers(POICategory.name, options);

    for (var key in POICategory.pois) {
        layer.addMarker(createMarker(POICategory.pois[key]));
    }
    return layer;
}

/**
 * Creates a new marker, popup and tooltip representing the specified POI.
 * 
 * @param poi the POI to be represented by the marker
 * 
 * @returns
 */
function createMarker(poi) {
    // define properties for the popup 
    var size = new OpenLayers.Size(poi.nameSize * 6 + 25, 23);
    var position = createLonLat(poi.lon, poi.lat);
    var properties = {
        'autoSize': true, 
        'maxSize': new OpenLayers.Size(330, 400), 
        'minSize': new OpenLayers.Size(330, 110), 
        'panMapIfOutOfView': true, 
        'min-width': "300px"
    };
    
    // create the feature for marker & popup
    var feature = new OpenLayers.Feature(null, position, new Object());
    feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud, properties);
    feature.data.popupContentHTML = createPopupContent(poi);
    feature.closeBox = true;
    
    // create marker and popup
    var marker = feature.createMarker();
    var popup = feature.createPopup(feature.closeBox);
    
    // create the tooltip
    var tooltip = new OpenLayers.Popup(poi.name, position, size, poi.name, false);
    tooltip.minSize = new OpenLayers.Size(40, 22);
    tooltip.maxSize = new OpenLayers.Size(400, 22);
    tooltip.setBorder("1px solid #009682");
    tooltip.opacity = 0.7;
    
    // functions to show and hide the popups
    var togglePopup = function(evt) {
        if (currentPOI == poi) {
            currentPOI = null;
            popup.hide();
        } else {
            hidePOI();
            currentPOI = poi;
            popup.show();
        }
        OpenLayers.Event.stop(evt);
    };
    var showTooltip = function(evt) {
        tooltip.show();
    };
    var hideTooltip = function(evt) { 
        tooltip.hide();
    };
    
    // register event listeners
    marker.events.register("mousedown", marker, togglePopup);
    marker.events.register("mouseover", marker, showTooltip);
    marker.events.register("mouseout", marker, hideTooltip);

    // add popups to map
    POIFeatures[poi.name] = feature;
    map.addPopup(popup);
    map.addPopup(tooltip);
    popup.hide();
    popup.clicked = false;
    tooltip.hide();
    tooltip.div.style.height = "22px";
    setupPopup(popup);
    
    return marker;
}

/**
 * This function generates HTML code, representing a popup for the specified POI.
 * 
 * @param poi the POI to be represented
 * @returns the HTML code of the POIs popup
 */
function createPopupContent(poi) {
    var popup = document.getElementById("map-popup").cloneNode(true);
    popup.getElementsByTagName("input")[0].value = poi.name;
    popup.getElementsByTagName("div")[1].innerHTML = poi.name;
    popup.getElementsByTagName("div")[2].innerHTML = poi.description;
    return popup.innerHTML;
}

/**
 * Creates a new LonLat object in Mercator projection out of the specified coordinates in World Geodetic System 1984.
 * 
 * @param lon longitude in World Geodetic System 1984
 * @param lat latitude in World Geodetic System 1984
 * @returns a new LonLat object in Mercator projection
 */
function createLonLat(lon, lat) {
    return new OpenLayers.LonLat(lon, lat).transform(WGS1984, MercatorProjection);
}

/**
 * Sets up width and height of the specified popup.
 */
function setupPopup(popup) {
    var divs = popup.div.getElementsByTagName("div");
    divs[1].style.width = "100%";
    
	var height = parseInt(popup.div.style.height.replace(/px/g, ""));
	divs[2].style.height = (height - 35) + "px";
	
	divs[7].style.right = "6px";
	divs[7].style.height = "38px";
	divs[7].style.width = "20px";
	divs[7].style.top = (divs[7].style.top == "9px") ? "10px" : "42px";
	
}

/**
 * Moves the map to specified Position.
 * 
 * @param lon longitude of the new center
 * @param lat latitude of the new center
 * @param zoom new zomm value
 */
function setMyCenter(lon,lat,zoom) {
    map.setCenter(createLonLat(lon, lat), zoom);
}

/**
 * Sets the visibility of the specified layer to the given value.
 * 
 * @param show the new value for visibility
 * @param layer the layer to change its visibility
 */
function showMarkers(show, layer) {
    POICategorieLayers[layer].setVisibility(show);
}

/**
 * Shows the serch result.
 */
function showPOI() {
    hidePOI();
    currentPOI = createJSONObject(getElement("search:current-poi").innerHTML);
    if (currentPOI != null) {
        POIFeatures[currentPOI.name].pupup.show();
        setMyCenter(currentPOI.lon, currentPOI.lat, 16);
    }
}

/**
 * Disselects the current POI.
 */
function hidePOI() {
    if (currentPOI != null) {
        POIFeatures[currentPOI.name].popup.hide();
        currentPOI = null;
    }
}

/**
 * Shows the result of a route request.
 */
function showRoute() {
    // remove old routes
    hideRoute();
    
    // definition of route properties
    var routeStyle = {
        fillColor: "#0000FF",
        strokeColor: "#0000FF",
        strokeOpacity: 1,
        strokeWidth: 3,
        pointRadius: 6,
        pointerEvents: "visiblePainted"
    };
    
    // create new route
    var route = createJSONObject(getElement("route:current-route").innerHTML);
    if (route != null) {
        var routePoints = new Array();
        for (var i = 0; i < route.length; i++) {
            var point = createLonLat(route[i].lon, route[i].lat);
            routePoints.push(new OpenLayers.Geometry.Point(point.lon, point.lat));
        }
        var lineString = new OpenLayers.Geometry.LineString(routePoints, null, null);
        var feature = new OpenLayers.Feature.Vector(lineString, null, routeStyle);
        routeLayer.addFeatures([feature]);
        getElement("route:hide-route").style.visibility = 'visible';
        
        // set new center
        var lon = (route[0].lon + route[route.length - 1].lon) / 2;
        var lat = (route[0].lat + route[route.length - 1].lat) / 2;
        var distance = getDistance(route[0], route[route.length - 1]);
        var zoom = Math.min(18, Math.max(15, Math.round(-0.0033 * distance + 18.279)));
        setMyCenter(lon, lat, zoom);
    } 
}

/**
 * removes the current route.
 */
function hideRoute() {
    routeLayer.removeAllFeatures();
    getElement("route:hide-route").style.visibility = 'hidden';
}

/**
 * Shows a context menu triggered by the specified event.
 * 
 * @param evt the trigger event
 */
function showContextMenu(evt, lonlat) {
	hideContextMenu();
    var content = document.getElementById("map-contextmenu").cloneNode(true);
    var lon = parseFloat(lonlat.substring(0, lonlat.indexOf(',')));
    var lat = parseFloat(lonlat.substring(lonlat.indexOf(',') + 2));
    var size = new OpenLayers.Size(146, 87);
    content.getElementsByTagName("input")[0].value = "Positionsmarker (" + lonlat + ")";
    content.getElementsByTagName("input")[1].value = lon;
    content.getElementsByTagName("input")[2].value = lat;
    contextmenu = new OpenLayers.Popup("contextmenu", createLonLat(lon, lat), size, content.innerHTML, false);
    contextmenu.panMapIfOutOfView = true;
    contextmenu.setBorder("1px solid #009682");
    contextmenu.minSize = size;
    contextmenu.maxSize = size;
    contextmenu.opacity = 0.8;
    map.addPopup(contextmenu);
    document.getElementById("contextmenu_contentDiv").removeAttribute("style");
    OpenLayers.Event.stop(evt);
}

/**
 * Hides the contextmenu if currently visible.
 */
function hideContextMenu() {
	if (contextmenu != null) {
		map.removePopup(contextmenu);
	}
}

function createPositionMarker(name, lon, lat) {
	var newPOI = {
		name: name,
		lon: lon,
		lat: lat,
		nameSize: 32,
		description: ""
	};
    positionMarkerLayer.clearMarkers();
    positionMarkerLayer.addMarker(createMarker(newPOI));
}

/**
 * Transform the the given radius into kartesian coordinates
 */
function getLengthByPolarCoordinates(radius) {
    return radius / 180 * Math.PI;
}

/**
 * Returns the distance in meters between two points on the map with help of their geographical coordinates.
 * 
 * @param pointFrom is the point, from which we calculate the distance.
 * @param pointTo is the point, to which we calculate the distance.
 * @return the distance between the two points in meters.
 */
function getDistance(pointFrom, pointTo){
    var heightFrom = getLengthByPolarCoordinates(pointFrom.lon);
    var heightTo = getLengthByPolarCoordinates(pointTo.lon);
    var widthFrom = getLengthByPolarCoordinates(pointFrom.lat);
    var widthTo = getLengthByPolarCoordinates(pointTo.lat);
    return  (Math.acos(Math.sin(widthFrom) * Math.sin(widthTo) + Math.cos(widthFrom) * Math.cos(widthTo) * Math.cos(heightTo - heightFrom))) * 6378137;
}
