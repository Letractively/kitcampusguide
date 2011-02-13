var map;
var clientID;
var layer_mapnik;
var routeLayer;
var layer_markers = new Object();
var current_poi;
var all_poi = new Object();
var all_cat;

function setClientID(id) {
	clientID = id;
}

function drawmap() {
    OpenLayers.Lang.setCode('de');
    // Position und Zoomstufe der Karte
    var lon = 8.41356;
    var lat = 49.01202;
    var zoom = 15;
    var minzoom = 15;
    var maxzoom = 18;
    
    var leftBottom = createLonLat(8.4025,49.008);
    var rightTop = createLonLat(8.426,49.0173);
    
    var options = {
        restrictedExtent: extent
    };
    var extent = new OpenLayers.Bounds(leftBottom.lon, leftBottom.lat, rightTop.lon, rightTop.lat);
    
    // Kartendefinition
    map = new OpenLayers.Map('map', {
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
//        controls: [
//            new OpenLayers.Control.Attribution(),
//            new OpenLayers.Control.MouseDefaults(),
//            new OpenLayers.Control.LayerSwitcher(),
//            new OpenLayers.Control.PanZoomBar(),
//            new OpenLayers.Control.MousePosition()],
        maxExtent: extent,
        restrictedExtent: extent,
        numZoomLevel: 3,
        units: 'meters'
    });
//    map.addControl(new OpenLayers.Control.Attribution());
//    map.addControl(new OpenLayers.Control.MouseDefaults());
    map.addControl(new OpenLayers.Control.MousePosition());
    map.addControl(new OpenLayers.Control.LayerSwitcher());
    map.minZoomLevel = minzoom;
    map.maxZoomLevel = maxzoom;
    map.restrictedExtent = extent;
    // Hier wird festgelegt, dass der Layer "Mapnik" verwendet wird;
    // testweise kann hier auch "Osmarender" oder "Cycle Map" eingesetzt werden
    layer_mapnik = new OpenLayers.Layer.OSM("New Layer", "resources/tiles/campus/${z}/${x}/${y}.png");
    map.addLayer(layer_mapnik);
    setMyCenter(lon,lat,zoom);
    // ende function drawmap
    
  routeLayer = new OpenLayers.Layer.Vector("route", null);
  map.addLayer(routeLayer);
  
  all_cat = getElement("map-form:all-poi");
  for (var i = 0; i < all_cat.length; i++) {
//	  layer_markers[allPOI[i].name] = new OpenLayers.Layer.Markers(allPOI[i].name, { projection: new OpenLayers.Projection("EPSG:4326"), 
//	      visibility: true, displayInLayerSwitcher: true});
//	  map.addLayer(layer_markers[allPOI[i].name]);
//	  for (var j = 0; j < allPOI[i].pois.length; j++) {
//		  addMarker(layer_markers[allPOI[i].name], allPOI[i].pois[j].lon, allPOI[i].pois[j].lat, createPopupContent(allPOI[i].pois[j]), allPOI[i].pois[j]);
//	  }
	  addPOILayer(all_cat[i]);
  }
//  layer_markers = new OpenLayers.Layer.Markers("Markers", { projection: new OpenLayers.Projection("EPSG:4326"), 
//      visibility: true, displayInLayerSwitcher: true});
//  map.addLayer(layer_markers);
  
  all_poi['current'] = null;

//  for (var i = 0; i < allPOI.length; i++) {
//	  addMarker(layer_markers['Mensen'], allPOI[i].lon, allPOI[i].lat, createPopupContent(allPOI[i]), allPOI[i].name);
//  }
}
function addPOILayer(poicat) {
	layer_markers[poicat.name] = new OpenLayers.Layer.Markers(poicat.name, { projection: new OpenLayers.Projection("EPSG:4326"), 
	      visibility: true, displayInLayerSwitcher: true});
	  map.addLayer(layer_markers[poicat.name]);
	  for (var j = 0; j < poicat.pois.length; j++) {
		  addMarker(layer_markers[poicat.name], poicat.pois[j].lon, poicat.pois[j].lat, createPopupContent(poicat.pois[j]), poicat.pois[j]);
	  }
}

function getPOICat(name) {
	for (var i = 0; i < all_cat.length; i++) {
		if (all_cat[i].name == name) {
			return all_cat[i];
		}
	}
}

function getElement(id) {
	var inner = getFormElement(id).innerHTML;
	return (inner == "") ? null : JSON.parse(inner);	
}

function getFormElement(id) {
	return document.getElementById(clientID + ":" + id);
}

function createPopupContent(poi) {
	return "<h2>" + poi.name + "</h2><p>" + poi.description + "</p>";
}


function createLonLat(lon, lat) {
	return new OpenLayers.LonLat(lon, lat) // Center of the map
    .transform(
    	new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
    	new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator Projection
    );
}

function setMyCenter(lo,la,zo) {
    var lonLat = new OpenLayers.LonLat(lo, la)
    .transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator Projection
    );
    map.setCenter (lonLat, zo);
}
  
function route() {
		routeLayer.removeAllFeatures();
		var routeFeature = new OpenLayers.Feature.Vector(
				new OpenLayers.Geometry.LineString([new OpenLayers.Geometry.Point(10, 10),
				                                    new OpenLayers.Geometry.Point(50, 50)]), null, null);
		routeLayer.addFeatures([ routeFeature ]);
			
}

function showMarkers(show, layer) {
//	layer_markers[layer].setOpacity(show ? 1.0 : 0.0);
	if (!show) {
		layer_markers[layer].destroy();
	} else {
		addPOILayer(getPOICat(layer));
	}
}

function showPOI() {
	if (all_poi['current'] != null && current_poi != null) {
		all_poi['current'].popup.hide();
		all_poi['current'].popup.clicked = false;
	}
	current_poi = getElement("search:current-poi");
	setMyCenter(current_poi.lon, current_poi.lat, map.getZoom());
	all_poi['current'] = all_poi[current_poi.name];
	all_poi['current'].popup.show();
	all_poi['current'].popup.clicked = true;
}

function addMarker(layer, lon, lat, popupContentHTML, poi) {

    var ll = createLonLat(lon, lat);
    var data = {};
	var feature = new OpenLayers.Feature(layer, ll, data);
    feature.closeBox = true;
    feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {
		'autoSize': true, 'maxSize': new OpenLayers.Size(320, 200), 'panMapIfOutOfView': true, minSize: new OpenLayers.Size(200, 120)
    });
    feature.data.popupContentHTML = popupContentHTML;
    feature.data.overflow = "hidden";
     
    var marker = feature.createMarker();
    all_poi[poi.name] = feature;
  
    var markerClick = function(evt) {
        if (this.popup == null) {
            this.popup = this.createPopup(this.closeBox);
            map.addPopup(this.popup);
            this.popup.show();
            all_poi['current'] = this;
        } else {
//            this.popup.toggle();
            if (all_poi['current'] == this) {
            	all_poi['current'].popup.hide();
            	all_poi['current'] = null;
            	current_poi = null;
            } else {
            	if (all_poi['current'] != null) {
                	all_poi['current'].popup.hide();
            	}
            	all_poi['current'] = this;
            	all_poi['current'].popup.show();
            	current_poi = poi;
            }
        }
        OpenLayers.Event.stop(evt);
    };
    
    marker.events.register("mousedown", feature, markerClick);
	
    layer.addMarker(marker);
    map.addPopup(feature.createPopup(feature.closeBox));
    
    feature.popup.hide();
    feature.popup.clicked = false;
}