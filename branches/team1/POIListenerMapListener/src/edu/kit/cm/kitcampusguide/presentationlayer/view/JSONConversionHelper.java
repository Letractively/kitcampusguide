package edu.kit.cm.kitcampusguide.presentationlayer.view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Utility class to convert some standard types to a JSON string.
 * @see http://www.json.org
 * @author Stefan
 *
 */
@SuppressWarnings("unchecked")
public class JSONConversionHelper {

	/**
	 * Converts a POI to a JSONObject.
	 * 
	 * @param p
	 *            a POI
	 * @return a JSONObject representing the POI
	 */
	static JSONObject convertPOI(POI p) {

		JSONArray categories = new JSONArray();
		for (Category c : p.getCategories()) {
			JSONObject category = new JSONObject();
			category.put("id", c.getID());
			categories.add(category);
		}

		JSONObject result = new JSONObject();
		result.put("id", p.getID());
		result.put("name", p.getName());
		result.put("description", p.getDescription());
		result.put("map", convertMap(p.getMap()));
		result.put("categories", categories);
		result.put("position", convertWorldPosition(p.getPosition()));
		result.put("building", null); // TODO
		return result;
	}

	static JSONObject convertWorldPosition(WorldPosition p) {
		JSONObject position = new JSONObject();
		position.put("longitude", p.getLongitude());
		position.put("latitude", p.getLatitude());
		return position;
	}

	static JSONObject convertMapPosition(MapPosition p) {
		JSONObject position = convertWorldPosition(p);
		position.put("map", convertMap(p.getMap()));
		return position;
	}

	static JSONObject convertMap(Map m) {
		JSONObject map = new JSONObject();
		map.put("id", m.getID());
		map.put("name", m.getName());
		map.put("tilesURL", m.getTilesURL());
		map.put("boundingBox", convertMapSection(m.getBoundingBox()));
		map.put("minZoom", m.getMinZoom());
		map.put("maxZoom", m.getMaxZoom());
		return map;
	}

	static MapSection getMapSection(JSONObject obj) {
		WorldPosition pos1 = getWorldPosition((JSONObject) obj.get("southEast"));
		WorldPosition pos2 = getWorldPosition((JSONObject) obj.get("northWest"));
		return new MapSection(pos1, pos2);
	}

	static WorldPosition getWorldPosition(JSONObject object) {
		double latitude = (Double) object.get("latitude");
		double longitude = (Double) object.get("longitude");
		return new WorldPosition(latitude, longitude);
	}

	static JSONObject convertMapSection(MapSection mapSection) {
		JSONObject obj = new JSONObject();
		obj.put("northWest", convertWorldPosition(mapSection.getNorthWest()));
		obj.put("southEast", convertWorldPosition(mapSection.getSouthEast()));
		return obj;
	}

	static JSONObject convertBuilding(Building building) {
		JSONObject obj = new JSONObject();
		obj.put("groundFloorIndex", building.getGroundFloorIndex());
		JSONArray floors = new JSONArray();
		for (Map m : building.getFloors()) {
			floors.add(convertMap(m));
		}
		obj.put("floors", floors);
		obj.put("buildingPOI", building.getBuildingPOI());
		obj.put("id", building.getID());
		return obj;
	}
}
