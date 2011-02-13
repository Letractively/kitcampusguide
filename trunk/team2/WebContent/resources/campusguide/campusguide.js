var map;
var clientID;
var layer_mapnik;
var routeLayer;
var layer_markers;
var current_poi;
var all_poi = new Object();

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

  layer_markers = new OpenLayers.Layer.Markers("Markers", { projection: new OpenLayers.Projection("EPSG:4326"), 
      visibility: true, displayInLayerSwitcher: true});
  map.addLayer(layer_markers);

  var allPOI = getElement("map-form:all-poi");
  for (var i = 0; i < allPOI.length; i++) {
	  addMarker(layer_markers, allPOI[i].lon, allPOI[i].lat, createPopupContent(allPOI[i]), allPOI[i].name);
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

function showMarkers(show) {
	layer_markers.setOpacity(show ? 1.0 : 0.0);
}

function showPOI() {
	if (current_poi != null) {
		all_poi[current_poi.name].popup.hide();
	}
	current_poi = getElement("search:current-poi");
	setMyCenter(current_poi.lon, current_poi.lat, map.getZoom());
	all_poi[current_poi.name].popup.show();
}

function addMarker(layer, lon, lat, popupContentHTML, name) {

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
    all_poi[name] = feature;
  
    var markerClick = function(evt) {
        if (this.popup == null) {
            this.popup = this.createPopup(this.closeBox);
            map.addPopup(this.popup);
            this.popup.show();
        } else {
            this.popup.toggle();
        }
        OpenLayers.Event.stop(evt);
    };
    
    marker.events.register("mousedown", feature, markerClick);
	
    layer.addMarker(marker);
    map.addPopup(feature.createPopup(feature.closeBox));
    
    feature.popup.hide();
    feature.popup.clicked = false;
}