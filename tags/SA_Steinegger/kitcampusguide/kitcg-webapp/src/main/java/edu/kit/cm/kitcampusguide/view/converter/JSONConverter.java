package edu.kit.cm.kitcampusguide.view.converter;

import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This utility class provides several methods to convert model objects to
 * JSONObjects using JSON simple.
 * 
 * @author Haoqian Zheng
 */
@SuppressWarnings("unchecked")
public final class JSONConverter {

	/**
	 * Private constructor to prevent instantiations.
	 */
	private JSONConverter() {
		assert false;
	}

	/**
	 * Converts a {@link POI} to a JSONObject.
	 * 
	 * @param p
	 *            the POI to be converted.
	 * @return the JSONObject the POI was converted to.
	 */
	public static JSONObject convertPOI(POI p) {
		JSONObject poi = new JSONObject();
		poi.put("id", p.getUid());
		poi.put("lon", p.getLongitude());
		poi.put("lat", p.getLatitude());
		poi.put("name", p.getName());
		poi.put("nameSize", p.getName().length());
		poi.put("description", p.getDescription());
		poi.put("icon", p.getIcon());
		return poi;
	}

	/**
	 * Converts a collection of {@link POI} to a JSONArray.
	 * 
	 * @param p
	 *            the collection to be converted.
	 * @return the JSONArray the collection was converted to.
	 */
	public static JSONArray convertPOIs(Collection<POI> p) {
		if (p == null) {
			return null;
		}
		JSONArray result = new JSONArray();
		for (POI poi : p) {
			result.add(convertPOI(poi));
		}
		return result;
	}

	/**
	 * Converts a {@link POICategory} to a JSONObject.
	 * 
	 * @param pc
	 *            the POICategory to be converted.
	 * @return the JSONObject the POICategory was converted to.
	 */
	public static JSONObject convertPOICategory(POICategory pc) {
		JSONObject result = new JSONObject();
		result.put("id", pc.getUid());
		result.put("icon", pc.getIcon());
		result.put("name", pc.getName());
		result.put("description", pc.getDescription());
		result.put("visible", pc.isVisible());
		result.put("pois", convertPOIs(pc.getPois()));
		return result;
	}

	/**
	 * Converts a collection of {@link POICategory} to a JSONArray.
	 * 
	 * @param pc
	 *            the collection to be converted.
	 * @return the JSONArray the collection was converted to.
	 */
	public static JSONArray convertPOICategories(Collection<POICategory> pc) {
		if (pc == null) {
			return null;
		}
		JSONArray result = new JSONArray();
		for (POICategory p : pc) {
			result.add(convertPOICategory(p));
		}
		return result;
	}

	/**
	 * Converts a {@link Point} to a JSONObject.
	 * 
	 * @param p
	 *            the Point to be converted.
	 * @return the JSONObject the Point was converted to.
	 */
	private static JSONObject convertPoint(Point p) {
		JSONObject result = new JSONObject();
		result.put("lon", p.getLongitude());
		result.put("lat", p.getLatitude());
		return result;
	}

	/**
	 * Converts a {@link Route} to an JSONArray.
	 * 
	 * @param r
	 *            the Route to be converted.
	 * @return the JSONArray the Route was converted to.
	 */
	public static JSONArray convertRoute(Route r) {
		if (r == null) {
			return null;
		}
		JSONArray result = new JSONArray();
		for (Point point : r.getRoute()) {
			result.add(convertPoint(point));
		}
		return result;
	}

}
