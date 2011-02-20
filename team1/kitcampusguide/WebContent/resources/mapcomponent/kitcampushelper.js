/**
 * Contains some static help functions.
 */
var KITCampusHelper = {
	
	/**
	 * Transforms a given MapSection into a OpenLayers Bounds object. The
	 * MapSection's coordinates are transformed into the open layers space.
	 * 
	 * @param mapSection
	 *            a MapSection
	 * @returns {OpenLayers.Bounds} an appropriate Bounds object
	 */
	transformMapSection: function(mapSection) {
		var result = new OpenLayers.Bounds();
		var p1 = this.transformWorldPosition(mapSection.northWest);
		result.extend(p1);
		var p2 = this.transformWorldPosition(mapSection.southEast);
		result.extend(p2);
		return result;
	},
	
	/**
	 * Transforms a given WorldPosition with GPS coordinates into the open layers
	 * coordinate system.
	 * 
	 * @param wp
	 *            a WorldPosition
	 * @returns {OpenLayers.LonLat} a newly created and transformed instance.
	 */
	transformWorldPosition: function(wp) {
		// Code taken from the Mapstraction project
		var ollon = wp.longitude * 20037508.34 / 180;
		var ollat = Math.log(Math.tan((90 + wp.latitude) * Math.PI / 360))
				/ (Math.PI / 180);
		ollat = ollat * 20037508.34 / 180;
		return new OpenLayers.LonLat(ollon, ollat);
	},

	/**
	 * Converts an OpenLayers.LonLat to a WorldPosition. The result can be formatted
	 * with JSON and sent to the server.
	 * 
	 * @returns a WorldPosition
	 */
	untransformBounds: function(bounds) {
		var coords = bounds.toArray();
		var nw = new OpenLayers.LonLat(coords[0], coords[1]);
		var se = new OpenLayers.LonLat(coords[2], coords[3]);
		return {
			northWest : this.untransformLonLat(nw),
			southEast : this.untransformLonLat(se)
		};	
	},
	
	/**
	 * Converts an OpenLayers.Bounds instance to an appropriate MapSection.
	 * 
	 * @param bounds
	 *            an Openlayers.Bounds instance
	 * @returns a MapSection
	 */
	untransformLonLat: function(lonlat) {
		// Code taken from the Mapstraction project
		var lon = (lonlat.lon / 20037508.34) * 180;
		var lat = (lonlat.lat / 20037508.34) * 180;

		lat = 180 / Math.PI
				* (2 * Math.atan(Math.exp(lat * Math.PI / 180)) - Math.PI / 2);

		return {
			longitude : lon,
			latitude : lat
		};
	},
	
	/**
	 * Returns true if two given WorldPositions specify (nearly) the same location.
	 * 
	 * @param pos1
	 *            a WorldPosition
	 * @param pos2
	 *            a WorldPosition
	 * @returns {Boolean} true if both positions seem to be identical
	 */
	positionEquals: function(pos1, pos2) {
		var EPS = 5E-7;
		return (Math.abs(pos1.longitude - pos2.longitude) < EPS
				&& Math.abs(pos1.latitude - pos2.latitude) < EPS);
	},
	
	
	
	getTranslation: function(label, clientId) {
		return document.getElementById(clientId + ":inputForm:" + label).innerHTML;
	},

	setSearchButtonLabel: function(clientId) {
		var routeFromField = document.getElementById(clientId + ':inputForm:routeFromField');
		var routeToField = document.getElementById(clientId + ':inputForm:routeToField');
		var label;
		if ((routeFromField == null || (routeFromField.value != null && routeFromField.value != ''))
				&& (routeToField == null || (routeToField.value != null && routeToField.value != ''))) {
			label = KITCampusHelper.getTranslation("calculateRouteLabel", clientId);
		} else {
			label = KITCampusHelper.getTranslation("searchLabel", clientId);
		}
		document.getElementById(clientId + ':inputForm:searchButton').value = label;
	}
};