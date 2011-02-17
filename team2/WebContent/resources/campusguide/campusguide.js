var map;
var clientID;
var layer_mapnik;
var layer_route;
var layer_markers = new Object();
var current_poi;
var current_route;
var current_tooltip;
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
//            new OpenLayers.Control.MousePosition()
//		],
        maxExtent: extent,
        restrictedExtent: extent,
        numZoomLevel: 3,
        units: 'meters'
    });
//    map.addControl(new OpenLayers.Control.Attribution());
//    map.addControl(new OpenLayers.Control.MouseDefaults());
    map.addControl(new OpenLayers.Control.MousePosition());
//    map.addControl(new OpenLayers.Control.LayerSwitcher());
    map.addControl(new OpenLayers.Control.ScaleLine());
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

function setupPopups() {
	var divs = document.getElementsByTagName("div");
	for (var i = 0; i < divs.length; i++) {
		if (divs[i].className == "olFramedCloudPopupContent") {
			var height = parseInt(divs[i].style.height.replace(/px/g, ""));
			divs[i].style.overflow = "";
			divs[i].style.width = "100%";
			divs[i].style.height = (height + 17) + "px";
		}
	}
	repositionPopups();
}

function repositionPopups() {
	var divs = document.getElementsByTagName("div");
	for (var i = 0; i < divs.length; i++) {
		if (divs[i].className == "olPopupCloseBox") {
			divs[i].style.right = "6px";
			divs[i].style.height = "36px";
			divs[i].style.width = "19px";
		}
	}
}

function setRouteFrom(name) {
	showSidebar();
	getElement('route:from-field').value = name;
	getElement('route:from-field').focus();
	all_poi['current'].popup.hide();
	all_poi['current'] = null;
	current_poi = null;
}

function setRouteTo(name) {
	showSidebar();
	getElement('route:to-field').value = name;
	getElement('route:to-field').focus();
	all_poi['current'].popup.hide();
	all_poi['current'] = null;
	current_poi = null;
}

function hideSidebar() {
	var map = document.getElementById("map");
	var sidebar = document.getElementById("sidebar");
	var mapWidth = parseInt(map.style.width.replace(/px/g, ""));
	var sidebarWidth = parseInt(sidebar.style.width.replace(/px/g, "")) + 1;
	var sidebarLeft = parseInt(sidebar.style.left.replace(/px/g, ""));
	if (sidebarLeft == -1) {
		map.style.width = (mapWidth + sidebarWidth) + "px";
	}
	if (sidebarLeft == sidebarWidth) {
		document.getElementById("hide-sidebar").style.visibility = "hidden";
		document.getElementById("show-sidebar").style.visibility = "visible";
		document.getElementById("sidebar-pane").style.zIndex = 0;
	}
	if (sidebarLeft < sidebarWidth) {
		sidebar.style.left = (sidebarLeft + 1) + "px";
		setTimeout(hideSidebar, 2);
	}
}

function showSidebar() {
	var map = document.getElementById("map");
	var sidebar = document.getElementById("sidebar");
	var mapWidth = parseInt(map.style.width.replace(/px/g, ""));
	var sidebarWidth = parseInt(sidebar.style.width.replace(/px/g, "")) + 1;
	var sidebarLeft = parseInt(sidebar.style.left.replace(/px/g, ""));
	if (sidebarLeft == -1 && document.getElementById("show-sidebar").style.visibility != "hidden") {
		map.style.width = (mapWidth - sidebarWidth) + "px";
		document.getElementById("hide-sidebar").style.visibility = "visible";
		document.getElementById("show-sidebar").style.visibility = "hidden";
	}
	if (sidebarLeft == sidebarWidth) {
		document.getElementById("sidebar-pane").style.zIndex = 9999;
	}
	if (sidebarLeft > -1) {
		sidebar.style.left = (sidebarLeft - 1) + "px";
		setTimeout(showSidebar, 2);
	}
}

function addPOILayer(poicat) {
	layer_markers[poicat.name] = new OpenLayers.Layer.Markers(poicat.name, 
		{ projection: new OpenLayers.Projection("EPSG:4326"), 
	      visibility: true, displayInLayerSwitcher: true});
	  map.addLayer(layer_markers[poicat.name]);
	  for (var j = 0; j < poicat.pois.length; j++) {
		  addMarker(layer_markers[poicat.name], poicat.pois[j].lon, poicat.pois[j].lat, 
				  createPopupContent(poicat.pois[j]), poicat.pois[j]);
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
	var popup = document.getElementById("map-popup").cloneNode(true);
	popup.getElementsByTagName("input")[0].value = poi.name;
	popup.getElementsByTagName("div")[1].innerHTML = poi.name;
	popup.getElementsByTagName("div")[2].innerHTML = poi.description;
	return popup.innerHTML;
}

function setMyCenter(lo,la,zo) {
    var lonLat = new OpenLayers.LonLat(lo, la)
    .transform(new OpenLayers.Projection("EPSG:4326"), new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator Projection
    );
    map.setCenter(lonLat, zo);
}

function showMarkers(show, layer) {
	layer_markers[layer].setVisibility(show);
//	if (!show) {
//		layer_markers[layer].destroy();
//	} else {
//		addPOILayer(getPOICat(layer));
//	}
}

function showPOI() {
	if (all_poi['current'] != null && current_poi != null) {
		all_poi['current'].popup.hide();
		all_poi['current'].popup.clicked = false;
	}
	current_poi = getElementInnerHTML("search:current-poi");
	if (current_poi != null) {		
		setMyCenter(current_poi.lon, current_poi.lat, 16);
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
		'autoSize': true, 'maxSize': new OpenLayers.Size(320, 200), 'panMapIfOutOfView': true, 
		'minSize': new OpenLayers.Size(200, 120), 'opacity': 0.7, 'border': "1px solid #009682"
    });
    feature.data.popupContentHTML = popupContentHTML;
    feature.data.overflow = "hidden";
     
    var marker = feature.createMarker();
    all_poi[poi.name] = feature;
  
    var mouseDown = function(evt) {
        if (this.popup == null) {
            this.popup = this.createPopup(this.closeBox);        
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
    
    var tooltip = new OpenLayers.Popup(poi.name, ll, new OpenLayers.Size(poi.nameSize*6 + 25, 23), poi.name, false);
	tooltip.minSize = new OpenLayers.Size(50, 20);
	tooltip.setBorder("1px solid #009682");
	tooltip.opacity = 0.7;
	 
    var mouseOver = function(evt) {    	
    	if (current_tooltip == null) {
    		current_tooltip = tooltip;
    		current_tooltip.show();
    	}
    };
    
    var mouseOut = function(evt) {    
    	if (current_tooltip != null) {
    		current_tooltip.hide();
    		current_tooltip = null;
    	}
    };
    
    marker.events.register("mousedown", feature, mouseDown);
    marker.events.register("mouseover", this, mouseOver);
    marker.events.register("mouseout", this, mouseOut);
    
	
    layer.addMarker(marker);
    map.addPopup(feature.createPopup(feature.closeBox));
    map.addPopup(tooltip);
      
    feature.popup.hide();
    feature.popup.clicked = false;
    tooltip.hide();
}