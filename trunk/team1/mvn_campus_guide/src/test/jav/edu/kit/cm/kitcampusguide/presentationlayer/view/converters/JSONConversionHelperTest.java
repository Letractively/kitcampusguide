package edu.kit.cm.kitcampusguide.presentationlayer.view.converters;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POITest;
import edu.kit.cm.kitcampusguide.standardtypes.Route;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

/**
 * Tests all methods implemented by {@link JSONConversionHelper}.
 * 
 * @author Stefan
 * 
 */
public class JSONConversionHelperTest {

	/**
	 * Contains some buildings for testings.
	 */
	private static List<Building> buildings;

	/**
	 * Contains some categories for testing.
	 */
	private static List<Category> categories;

	/**
	 * Contains some maps used for testing.
	 */
	private static List<Map> maps;

	private static final Random random = new Random(3562);

	/**
	 * Creates all necessary test data.
	 */
	@BeforeClass
	public static void createData() {
		maps = new ArrayList<Map>();
		MapSection boundingBox = new MapSection(new WorldPosition(1, 2),
				new WorldPosition(2, 4));
		for (int i = 0; i < 10; i++) {
			maps.add(new Map(Idgenerator.getFreeMapID(), "", boundingBox, "", 0, 0));
		}

		categories = new ArrayList<Category>();
		for (int i = 0; i < 20; i++) {
			categories.add(new Category(Idgenerator.getFreeCategoryID(), ""));
		}

		POI theBuildingPOI = new POI("buildingPOI", "", null,
				new WorldPosition(1, 1), maps.get(0), null, categories);
		buildings = new ArrayList<Building>();
		for (int i = 0; i < 20; i++) {
			buildings.add(new Building(Idgenerator.getFreeBuildingID(), maps, 0, theBuildingPOI));
		}
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given building.
	 * 
	 * @param building
	 *            a building
	 * @param json
	 *            a {@link JSONObject} representing a building
	 */
	private static void assertBuildingEquals(Building building, JSONObject obj) {
		if (building == null) {
			assertNull(building);
			return;
		}
		assertEquals(building.getID(), obj.get("id"));
		JSONArray floors = (JSONArray) obj.get("floors");
		int i = 0;
		for (Map map : building.getFloors()) {
			assertMapEquals(map, (JSONObject) floors.get(i++));
		}
		assertEquals(building.getGroundFloorIndex(),
				obj.get("groundFloorIndex"));
		assertPOIEquals(building.getBuildingPOI(),
				(JSONObject) obj.get("buildingPOI"));
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given category.
	 * 
	 * @param category
	 *            a category
	 * @param json
	 *            a {@link JSONObject} representing a category
	 */
	private static void assertCategoryEquals(Category category,
			JSONObject jsonObj) {
		assertEquals(category.getID(), jsonObj.get("id"));
		assertEquals(category.getName(), jsonObj.get("name"));
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given map.
	 * 
	 * @param map
	 *            a map
	 * @param json
	 *            a {@link JSONObject} representing a map
	 */
	private static void assertMapEquals(Map map, JSONObject obj) {
		assertMapSectionEquals(map.getBoundingBox(),
				(JSONObject) obj.get("boundingBox"));
		assertEquals(map.getBuilding().getID(), obj.get("buildingID"));
		assertEquals(map.getMaxZoom(), obj.get("maxZoom"));
		assertEquals(map.getMinZoom(), obj.get("minZoom"));
		assertEquals(map.getTilesURL(), obj.get("tilesURL"));
		assertEquals(map.getName(), obj.get("name"));
		assertEquals(map.getID(), obj.get("id"));
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given map section.
	 * 
	 * @param section
	 *            a map section
	 * @param json
	 *            a {@link JSONObject} representing a map section
	 */
	private static void assertMapSectionEquals(MapSection section,
			JSONObject obj) {
		assertWorldPositionEquals(section.getNorthWest(),
				(JSONObject) obj.get("northWest"));
		assertWorldPositionEquals(section.getSouthEast(),
				(JSONObject) obj.get("southEast"));
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given POI.
	 * 
	 * @param poi
	 *            a POI
	 * @param json
	 *            a {@link JSONObject} representing a POI
	 */
	private static void assertPOIEquals(POI poi, JSONObject json) {
		assertEquals(poi.getID(), json.get("id"));

		assertEquals(poi.getName(), json.get("name"));

		assertTrue(json.containsKey("description"));
		assertEquals(poi.getDescription(), json.get("description"));

		assertWorldPositionEquals(poi.getPosition(),
				(JSONObject) json.get("position"));

		assertMapEquals(poi.getMap(), (JSONObject) json.get("map"));

		assertTrue(json.containsKey("description"));

		assertTrue(json.containsKey("buildingID"));
		assertEquals((poi.getBuilding() == null) ? null : poi.getBuilding()
				.getID(), json.get("buildingID"));

		List<Integer> containedIDs = new ArrayList<Integer>();
		JSONArray categories = (JSONArray) json.get("categories");
		for (Object o : categories) {
			JSONObject jsonObj = (JSONObject) o;
			Integer id = (Integer) jsonObj.get("id");
			assertNotNull(id);
			containedIDs.add((Integer) id);
			assertCategoryEquals(Category.getCategoryByID(id), jsonObj);
		}

		assertEquals(containedIDs.size(), poi.getCategories().size());
		for (Category c : poi.getCategories()) {
			assertTrue(containedIDs.remove((Integer) c.getID()));
		}
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given route.
	 * 
	 * @param route
	 *            a route
	 * @param json
	 *            a {@link JSONObject} representing a route
	 */
	private static void assertRouteEquals(Route route, JSONObject obj) {
		JSONArray jsonWaypoints = (JSONArray) obj.get("waypoints");
		int i = 0;
		for (MapPosition mp : route.getWaypoints()) {
			JSONObject curMapPosition = (JSONObject) jsonWaypoints.get(i++);
			assertWorldPositionEquals(mp, curMapPosition);
			assertMapEquals(mp.getMap(), (JSONObject) curMapPosition.get("map"));
		}
	}

	/**
	 * Throws an assertion exception if the given {@link JSONObject} does not
	 * represent the given world position.
	 * 
	 * @param pos
	 *            a world position
	 * @param json
	 *            a {@link JSONObject} representing a world position
	 */
	private static void assertWorldPositionEquals(WorldPosition pos,
			JSONObject object) {
		assertEquals(pos.getLatitude(), object.get("latitude"));
		assertEquals(pos.getLongitude(), object.get("longitude"));
	}

	/**
	 * Tests {@link JSONConversionHelper#convertBuilding(Building)}.
	 */
	@Test
	public void testConvertBuilding() {
		for (Building building : buildings) {
			assertBuildingEquals(building,
					JSONConversionHelper.convertBuilding(building));
		}
	}

	/**
	 * Tests {@link JSONConversionHelper#convertMap(Map)}.
	 */
	@Test
	public void testConvertMap() {
		for (Map map : maps) {
			assertMapEquals(map, JSONConversionHelper.convertMap(map));
		}
	}

	/**
	 * Tests
	 * {@link JSONConversionHelper#convertMapLocator(edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator)}
	 * .
	 */
	@Test
	public void testConvertMapLocator() {
		MapLocator ml1 = new MapLocator(new MapSection(new WorldPosition(
				random.nextDouble(), random.nextDouble()), new WorldPosition(
				random.nextDouble(), random.nextDouble())));
		JSONObject obj = JSONConversionHelper.convertMapLocator(ml1);
		assertMapSectionEquals(ml1.getMapSection(),
				(JSONObject) obj.get("mapSection"));
		assertTrue(obj.containsKey("center"));
		assertNull(obj.get("center"));

		MapLocator ml2 = new MapLocator(new WorldPosition(random.nextDouble(),
				random.nextDouble()));
		obj = JSONConversionHelper.convertMapLocator(ml2);
		assertWorldPositionEquals(ml2.getCenter(),
				(JSONObject) obj.get("center"));
		assertTrue(obj.containsKey("mapSection"));
		assertNull(obj.get("mapSection"));

	}

	/**
	 * Tests
	 * {@link JSONConversionHelper#convertMapPosition(edu.kit.cm.kitcampusguide.standardtypes.MapPosition)
	 * }
	 * .
	 */
	@Test
	public void testConvertMapPosition() {
		for (int i = 0; i < 1000; i++) {
			MapPosition mapPosition = new MapPosition(random.nextDouble(),
					random.nextDouble(), maps.get(random.nextInt(maps.size())));
			JSONObject jsonObj = JSONConversionHelper
					.convertMapPosition(mapPosition);
			assertWorldPositionEquals(mapPosition, jsonObj);
			assertMapEquals(mapPosition.getMap(),
					(JSONObject) jsonObj.get("map"));
		}
	}

	/**
	 * Tests {@link JSONConversionHelper#convertMapSection(MapSection)}.
	 */
	@Test
	public void testConvertMapSection() {
		MapSection ms = new MapSection(new WorldPosition(random.nextDouble(),
				random.nextDouble()), new WorldPosition(random.nextDouble(),
				random.nextDouble()));
		assertMapSectionEquals(ms, JSONConversionHelper.convertMapSection(ms));
	}

	/**
	 * Tests
	 * {@link JSONConversionHelper#convertPOI(edu.kit.cm.kitcampusguide.standardtypes.POI)}
	 * .
	 */
	@Test
	public void testConvertPOI() {
		for (int i = 0; i < 1000; i++) {
			String id = Idgenerator.getFreePOIID();
			String name = String.valueOf(random.nextInt());
			String description = random.nextDouble() > .8 ? null : String
					.valueOf(random.nextInt());
			WorldPosition position = new WorldPosition(random.nextDouble(),
					random.nextDouble());
			Map map = maps.get(random.nextInt(maps.size()));
			Integer buildingID = (random.nextDouble() > .8) ? buildings.get(
					random.nextInt(buildings.size())).getID() : null;
			List<Category> curCategories = POITest.randomSubset(categories);

			POI poi = new POI(id, name, description, position, map, buildingID,
					curCategories);

			JSONObject json = JSONConversionHelper.convertPOI(poi);

			assertPOIEquals(poi, json);
		}
	}

	/**
	 * Tests {@link JSONConversionHelper#convertPOIList(java.util.Collection)}.
	 */
	@Test
	public void testConvertPOIList() {
		POI poi = new POI(Idgenerator.getFreePOIID(), "", null, new WorldPosition(random.nextDouble(),
				random.nextDouble()), maps.get(0), null, Collections.EMPTY_LIST);
		JSONArray array = JSONConversionHelper
				.convertPOIList(Collections.EMPTY_LIST);
		assertTrue(array.isEmpty());
		array = JSONConversionHelper.convertPOIList(Arrays.asList(poi, poi,
				poi, poi));
		for (Object o : array) {
			assertPOIEquals(poi, (JSONObject) o);
		}
	}

	/**
	 * Tests
	 * {@link JSONConversionHelper#convertRoute(edu.kit.cm.kitcampusguide.standardtypes.Route)}
	 * .
	 */
	@Test
	public void testConvertRoute() {
		List<MapPosition> wp1 = new ArrayList<MapPosition>();
		List<MapPosition> wp2 = new ArrayList<MapPosition>();

		wp2.add(new MapPosition(random.nextDouble(), random.nextDouble(), maps
				.get(0)));
		wp2.add(new MapPosition(random.nextDouble(), random.nextDouble(), maps
				.get(1)));

		for (int i = 0; i < 500; i++) {
			wp1.add(new MapPosition(random.nextDouble(), random.nextDouble(),
					maps.get(random.nextInt(maps.size()))));
		}

		Route route1 = new Route(wp1);
		assertRouteEquals(route1, JSONConversionHelper.convertRoute(route1));

	}

	/**
	 * Tests {@link JSONConversionHelper#convertWorldPosition(WorldPosition)}.
	 */
	@Test
	public void testConvertWorldPosition() {
		for (int i = 0; i < 1000; i++) {
			WorldPosition position = new WorldPosition(random.nextDouble(),
					random.nextDouble());
			assertWorldPositionEquals(position,
					JSONConversionHelper.convertWorldPosition(position));
		}
	}

	/**
	 * Tests {@link JSONConversionHelper#getMapLocator(JSONObject)}. The tests
	 * needs {@link JSONConversionHelper#convertMapLocator(MapLocator)} to work
	 * properly.
	 */
	@Test
	public void testGetMapLocator() {
		WorldPosition wp1 = new WorldPosition(random.nextDouble(),
				random.nextDouble());
		WorldPosition wp2 = new WorldPosition(random.nextDouble(),
				random.nextDouble());
		MapLocator ml = new MapLocator(new MapSection(wp1, wp2));
		assertEquals(ml,
				JSONConversionHelper.getMapLocator(JSONConversionHelper
						.convertMapLocator(ml)));

		ml = new MapLocator(wp1);
		assertEquals(ml,
				JSONConversionHelper.getMapLocator(JSONConversionHelper
						.convertMapLocator(ml)));
	}

	/**
	 * Tests {@link JSONConversionHelper#getMapPosition(JSONObject)}.
	 * 
	 * @throws ParseException
	 *             if the test does not work
	 */
	@Test
	public void testGetMapPosition() throws ParseException {
		MapPosition mp = new MapPosition(random.nextDouble(),
				random.nextDouble(), maps.get(0));
		String json = "{ \"longitude\": " + mp.getLongitude()
				+ ", \"latitude\": " + mp.getLatitude()
				+ ", \"map\": {\"id\": " + mp.getMap().getID() + "}}";
		assertEquals(mp,
				JSONConversionHelper.getMapPosition((JSONObject) JSONValue
						.parseWithException(json)));
	}

	/**
	 * Tests {@link JSONConversionHelper#getMapSection(JSONObject)}. Needs
	 * {@link JSONConversionHelper#convertMapSection(MapSection)} to work
	 * properly.
	 */
	@Test
	public void testGetMapSection() {
		MapSection ms = new MapSection(new WorldPosition(random.nextDouble(),
				random.nextDouble()), new WorldPosition(random.nextDouble(),
				random.nextDouble()));
		assertEquals(ms,
				JSONConversionHelper.getMapSection(JSONConversionHelper
						.convertMapSection(ms)));
	}

	/**
	 * Tests {@link JSONConversionHelper#getWorldPosition(JSONObject)}.
	 */
	@Test
	public void testGetWorldPosition() {
		for (int i = 0; i < 1000; i++) {
			WorldPosition wp = new WorldPosition(random.nextDouble(),
					random.nextDouble());
			assertWorldPositionEquals(wp,
					JSONConversionHelper.convertWorldPosition(wp));
		}
	}

}
