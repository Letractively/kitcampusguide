var map;
var clientID;
var layer_mapnik;
var layer_route;
var layer_markers = new Object();
var current_poi;
var current_route;
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

    layer_mapnik = new OpenLayers.Layer.OSM("New Layer", "resources/tiles/campus/${z}/${x}/${y}.png");
    map.addLayer(layer_mapnik);
    setMyCenter(lon,lat,zoom);
    
    layer_route = new OpenLayers.Layer.Vector("route", null);
    map.addLayer(layer_route);
  
    all_cat = getElementInnerHTML("map-form:all-poi");
    for (var i = 0; i < all_cat.length; i++) {
    	addPOILayer(all_cat[i]);
    }
  
    all_poi['current'] = null;
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

function createPopupContent(poi) {
	return "<h2>" + poi.name + "</h2><p>" + poi.description + "</p>";
}

function setMyCenter(lo,la,zo) {
    var lonLat = new OpenLayers.LonLat(lo, la)
    .transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator Projection
    );
    map.setCenter (lonLat, zo);
}

function showMarkers(show, layer) {
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
	current_poi = getElementInnerHTML("search:current-poi");
	if (current_poi != null) {		
		setMyCenter(current_poi.lon, current_poi.lat, map.getZoom());
		all_poi['current'] = all_poi[current_poi.name];
		all_poi['current'].popup.show();
		all_poi['current'].popup.clicked = true;
	}
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