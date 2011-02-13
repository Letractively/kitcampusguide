package edu.kit.cm.kitcampusguide.view.converter;

import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.model.POI;

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

}
