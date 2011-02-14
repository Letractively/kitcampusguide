function route() {
	routeLayer.removeAllFeatures();
	current_route = getElement("route:current-route");
	if (current_route != null) {
		var newRoute = new Array();
		for (var i = 0; i < current_route.length; i++) {
			var ll = createLonLat(current_route[i].lon, current_route[i].lat);
			newRoute.push(new OpenLayers.Geometry.Point(ll.lon, ll.lat));
		}
		var routeFeature = new OpenLayers.Feature.Vector(
			new OpenLayers.Geometry.LineString(newRoute, null, null));
		routeLayer.addFeatures([ routeFeature ]);	
	} 
}