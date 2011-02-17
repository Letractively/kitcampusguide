hideRoute();

function route() {
	hideRoute();
	current_route = getElementInnerHTML("route:current-route");
	if (current_route != null) {
		var newRoute = new Array();
		for (var i = 0; i < current_route.length; i++) {
			var ll = createLonLat(current_route[i].lon, current_route[i].lat);
			newRoute.push(new OpenLayers.Geometry.Point(ll.lon, ll.lat));
		}
		var routeStyle = {
				fillColor: "#0000FF",
				strokeColor: "#0000FF",
				strokeOpacity: 1,
				strokeWidth: 3,
				pointRadius: 6,
				pointerEvents: "visiblePainted"
			};
		var routeFeature = new OpenLayers.Feature.Vector(
			new OpenLayers.Geometry.LineString(newRoute, null, null), null, routeStyle);
		layer_route.addFeatures([ routeFeature ]);
		getElement("route:hide-route").style.visibility = 'visible';
		setMyCenter(current_route[Math.floor(current_route.length / 2)].lon, 
				current_route[Math.floor(current_route.length / 2)].lat, 16);
	} 
}

function hideRoute() {
	layer_route.removeAllFeatures();
	getElement("route:hide-route").style.visibility = 'hidden';
}