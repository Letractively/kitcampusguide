package edu.kit.cm.kitcampusguide.view.converter;

import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;

/**
 * This utility class provides several methods to convert model objects to a JSON String.
 *
 * @author Haoqian Zheng
 */
@SuppressWarnings("unchecked")
public final class JSONConverter {
	
	private JSONConverter() {
		assert false;
	}
	

	public static JSONObject convertPOI(POI p) {
		JSONObject poi = new JSONObject();
		poi.put("id", p.getId());
		poi.put("lon", p.getX());
		poi.put("lat", p.getY());
		poi.put("name", p.getName());
		poi.put("nameSize", p.getName().length());
		poi.put("description", p.getDescription());
		poi.put("icon", p.getIcon());
		
		return poi;
	}
	
	public static JSONArray convertPOIs(Collection<POI> p) {
		if (p == null) {
			return null;
		}
		JSONArray result = new JSONArray();
		for (POI poi: p) {
			result.add(convertPOI(poi));
		}
		return result;
	}
	
	public static JSONObject convertPOICategory(POICategory pc) {
		if (pc == null) {
			return null;
		}
		JSONObject result = new JSONObject();
		result.put("id", pc.getId());
		result.put("icon", pc.getIcon());
		result.put("name", pc.getName());
		result.put("description", pc.getDescription());
		result.put("visible", pc.getVisible());
		result.put("pois", convertPOIs(pc.getAllPOI()));
		return result;
	}
	
	public static JSONArray convertPOICategories(Collection<POICategory> pc) {
		if (pc == null) {
			return null;
		}
		JSONArray result = new JSONArray();
		for (POICategory p: pc) {
			result.add(convertPOICategory(p));
		}
		return result;
	}
	
	private static JSONObject convertPoint(Point p) {
		JSONObject result = new JSONObject();
		if (p != null) {
			result.put("lon", p.getX());
			result.put("lat", p.getY());
		}
		return result;
	}
	
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
