package edu.kit.cm.kitcampusguide.presentationlayer.view.converters;

import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Utility class to convert some standard types to a JSON string.
 * The retrieved strings can be parsed with a JSON parser (like
 * the <code>JSON.parse</code> method in java script). Notice that
 * a call of the "get-methods" (like {@link #getMapLocator}) will
 * cause some runtime exceptions if a JSON object is passed without
 * all necessary properties.
 * 
 * @see <a href="http://www.json.org">http://www.json.org</a>
 * @author Stefan
 * 
 */
@SuppressWarnings("unchecked")
public final class JSONConversionHelper {

	/**
	 * Converts a POI to a JSONObject. The POI's attributes will be mapped
	 * directly as other JSON objects, see the other <code>convert</code>
	 * methods for details. If the given POI is a building POI, only an ID to
	 * the building will be stored in the JSON object. The building cannot be
	 * stored with <code>convertBuilding</code> hence it stores a reference to
	 * this POI, resulting in a circular dependency.
	 * 
	 * @param poi
	 *            a POI
	 * @return a JSONObject representing the POI
	 * @see POI
	 */
	static JSONObject convertPOI(POI poi) {
		if (poi == null) {
			return null;
		}
		JSONArray categories = new JSONArray();
		for (Category c : poi.getCategories()) {
			JSONObject category = new JSONObject();
			category.put("id", c.getID());
			categories.add(category);
		}

		JSONObject result = new JSONObject();
		result.put("id", poi.getID());
		result.put("name", poi.getName());
		result.put("description", poi.getDescription());
		result.put("map", convertMap(poi.getMap()));
		result.put("categories", categories);
		result.put("position", convertWorldPosition(poi.getPosition()));
		if (poi.getBuilding() != null) {
			// only store the buildingID and not the building object,
			// otherwise a stack overflow will occur
			result.put("buildingID",poi.getBuilding().getID());
		}
		return result;
	}

	/**
	 * Converts a world position into a <code>JSONObject</code>. The coordinates
	 * will be stored as JSON double values with the keys &quot;longitude&quot;
	 * and &quot;latitude&quot;.
	 * 
	 * @param position
	 *            a <code>WorldPosition</code> which will be converted
	 * @return a <code>JSONObject</code> representing the given position or
	 *         <code>null</code>, if the given <code>WorldPosition</code> is
	 *         <code>null</code>.
	 * @see WorldPosition
	 */
	static JSONObject convertWorldPosition(WorldPosition position) {
		if (position == null) {
			return null;
		}
		JSONObject result = new JSONObject();
		result.put("longitude", position.getLongitude());
		result.put("latitude", position.getLatitude());
		return result;
	}

	/**
	 * Converts a given {@link MapPosition} into a {@link JSONObject}.
	 * Essentially this method does the same as
	 * <code>convertWorldPosition</code>, it just puts an additional {@link Map}
	 * object into the resulting <code>JSONObject</code>.
	 * 
	 * @param pos
	 *            a <code>MapPosition</code> which will be converted
	 * @return an appropriate <code>JSONObject</code> or <code>null</code>, if
	 *         the given <code>MapPosition</code> is <code>null</code>
	 * @see #convertWorldPosition(WorldPosition)
	 * @see #convertMap(Map)
	 */
	static JSONObject convertMapPosition(MapPosition pos) {
		if (pos == null) {
			return null;
		}
		JSONObject position = convertWorldPosition(pos);
		position.put("map", convertMap(pos.getMap()));
		return position;
	}

	/**
	 * Converts a given collection of POIs into a a {@link JSONArray}. Each POI
	 * will be converted using <code>convertPOI</code>. The order of the array
	 * is determined by the iteration order of the given collection.
	 * 
	 * @param pois
	 *            a collection of POIs which will be converted
	 * @return a <code>JSONArray</code> containing all given POIs or
	 *         <code>null</code> if the given collection is <code>null</code>
	 * @see POI
	 * @see #convertPOI(POI)
	 */
	static JSONArray convertPOIList(Collection<POI> pois) {
		if (pois == null) {
			return null;
		}
		JSONArray result = new JSONArray();
		for (POI poi: pois) {
			result.add(convertPOI(poi));
		}
		return result;
	}

	/**
	 * Converts a given {@link Route} to a {@link JSONObject}. Notice that
	 * currently only the <code>waypoints</code> property will be converted.
	 * Each waypoints is converted via {@link #convertMapPosition(MapPosition)}
	 * 
	 * @param route
	 *            a route object which will be converted
	 * @return an appropriate {@link JSONObject} for the given {@link Route} or
	 *         <code>null</code>, if <code>route</code> is <code>null</code>
	 */
	static JSONObject convertRoute(Route route) {
		if (route == null) {
			return null;
		}
		JSONObject result = new JSONObject();
		JSONArray waypoints = new JSONArray();
		result.put("waypoints", waypoints);
		for (MapPosition pos: ((route).getWaypoints())) {
			waypoints.add(JSONConversionHelper.convertMapPosition(pos));
		}
		return result;
	}
	
	/**
	 * Converts a given {@link Map} into a {@link JSONObject}. All properties of
	 * the {@link Map} will be stored.
	 * 
	 * @param map
	 *            a <code>Map</code> which will be converted
	 * @return a {@link JSONObject} representing the given map.
	 * @throws NullPointerException
	 *             if <code>map</code> is <code>null</code>.
	 */
	static JSONObject convertMap(Map map) {
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("id", map.getID());
		jsonMap.put("name", map.getName());
		jsonMap.put("tilesURL", map.getTilesURL());
		jsonMap.put("boundingBox", convertMapSection(map.getBoundingBox()));
		jsonMap.put("minZoom", map.getMinZoom());
		jsonMap.put("maxZoom", map.getMaxZoom());
		return jsonMap;
	}

	/**
	 * Converts a given {@link JSONObject} representing a {@link MapSection}
	 * into the actual <code>MapSection</code>. The attributes
	 * &quot;southEast&quot; and &quot;northWest&quot; will be converted to
	 * {@link WorldPosition}s via {@link #getWorldPosition(WorldPosition)}.
	 * 
	 * @param object
	 *            an <code>JSONObject</code> representing a MapSection
	 * @return the MapSection represented
	 * @throws NullPointerException
	 *             if <code>section</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if one of the attributes "southEast" or "northWest" does not
	 *             represent a legal {@link WorldPosition}.
	 */
	static MapSection getMapSection(JSONObject object) {
		WorldPosition pos1 = getWorldPosition((JSONObject) object
				.get("southEast"));
		WorldPosition pos2 = getWorldPosition((JSONObject) object
				.get("northWest"));
		return new MapSection(pos1, pos2);
	}

	/**
	 * Converts a given JSON object representing a {@link WorldPosition} into
	 * the actual <code>WorldPosition</code> object.
	 * 
	 * @param object
	 *            a {@link JSONObject} with the attributes "latitude" and
	 *            "longitude", both double
	 * @return the {@link WorldPosition} represented by <code>object</code>
	 * @throws NullPointerException
	 *             if <code>object</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if "latitude" or "longitude" is out of bounds, see
	 *             {@link WorldPosition#WorldPosition(double, double)} for
	 *             details
	 * @see #convertWorldPosition(WorldPosition)
	 * @see WorldPosition
	 */
	static WorldPosition getWorldPosition(JSONObject object) {
		double latitude = (Double) object.get("latitude");
		double longitude = (Double) object.get("longitude");
		return new WorldPosition(latitude, longitude);
	}

	/**
	 * Converts a given {@link MapSection} into a {@link JSONObject}. The
	 * <code>MapSection</code>'s <code>northWest</code> and
	 * <code>southEast</code> properties will be converted using
	 * {@link #convertWorldPosition(WorldPosition)}.
	 * 
	 * @param mapSection
	 *            a {@link MapSection} which will be converted
	 * @return a {@link JSONObject} representing the given
	 *         <code>MapSection</code> or <code>null</code> if
	 *         <code>mapSection</code> is <code>null</code>
	 * @throws NullPointerException
	 *             if <code>mapSection</code> is <code>null</code>
	 */
	static JSONObject convertMapSection(MapSection mapSection) {
		if (mapSection == null) {
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("northWest", convertWorldPosition(mapSection.getNorthWest()));
		obj.put("southEast", convertWorldPosition(mapSection.getSouthEast()));
		return obj;
	}

	/**
	 * Converts a given {@link Building} into a {@link JSONObject}. The
	 * <code>buildingPOI</code> attribute will be converted via {@link #convertPOI(POI)}
	 * 
	 * @param building a building to convert
	 * @return a {@link JSONObject} representing the given building or <code>null</code>
	 * if <code>building</code> is <code>null</code>
	 */
	static JSONObject convertBuilding(Building building) {
		if (building == null) {
			return null;
		}
		JSONObject obj = new JSONObject();
		obj.put("groundFloorIndex", building.getGroundFloorIndex());
		JSONArray floors = new JSONArray();
		for (Map m : building.getFloors()) {
			floors.add(convertMap(m));
		}
		obj.put("floors", floors);
		obj.put("buildingPOI", convertPOI(building.getBuildingPOI()));
		obj.put("id", building.getID());
		return obj;
	}

	/**
	 * Converts a {@link JSONObject} representing a {@link MapPosition} into the
	 * actual <code>MapPosition</code>.
	 * 
	 * @param object
	 *            a {@link JSONObject} representing a {@link MapPosition}
	 * @return the {@link MapPosition} represented by this JSON object.
	 * @throws NullPointerException if <code>object</code> is <code>null</code>
	 *         or if a property could not be retrieved
	 */
	static MapPosition getMapPosition(JSONObject object) {
		double latitude = (Double) object.get("latitude");
		double longitude = (Double) object.get("longitude");
		int id = (int) ((Long) ((JSONObject) object.get("map")).get("id"))
				.longValue();
		return new MapPosition(latitude, longitude, Map.getMapByID(id));
	}

	/**
	 * Converts a given {@link MapLocator} into a {@link JSONObject}.
	 * 
	 * @param locator
	 *            a {@link MapLocator} to convert
	 * @return a {@link JSONObject} representing the given {@link MapLocator}
	 * @throws NullPointerException
	 *             if locator is <code>null</code>
	 */
	static JSONObject convertMapLocator(MapLocator locator) {
		JSONObject result = new JSONObject();
		result.put("center", convertWorldPosition(locator.getCenter()));
		result.put("mapSection", convertMapSection(locator.getMapSection()));
		return result;
	}

	/**
	 * Converts a given {@link JSONObject} representing a {@link MapLocator}
	 * into the actual <code>MapLocator</code> object.
	 * 
	 * @param object
	 *            a {@link JSONObject} representing a {@link MapLocator}
	 * @return the {@link MapLocator} represented by <code>object</code>
	 * @throws NullPointerException
	 *             if a necessary attribute could not be found or if
	 *             <code>object</code> is <code>null</code>
	 */
	static MapLocator getMapLocator(JSONObject object) {
		JSONObject mapSection = (JSONObject) object.get("mapSection");
		if (mapSection != null) {
			return new MapLocator(getMapSection(mapSection));
		} else {
			JSONObject center = (JSONObject) object.get("center");
			return new MapLocator(getWorldPosition(center));
		}
	}
}
