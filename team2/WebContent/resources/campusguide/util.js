function getElementInnerHTML(id) {
	var inner = getElement(id).innerHTML;
	return (inner == "") ? null : JSON.parse(inner);	
}

function getElement(id) {
	return document.getElementById(clientID + ":" + id);
}

function createLonLat(lon, lat) {
	return new OpenLayers.LonLat(lon, lat)
    .transform(
    	new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
    	new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator Projection
    );
}

function click(element) {
	var event = document.createEvent("MouseEvents");
	event.initMouseEvent("click", true, true, window,0, 0, 0, 0, 0,
		false, false, false, false, 0, null);
	element.dispatchEvent(event);
}