package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class MapTest {

	private static Map map;
	private static int mapID;
	
	/**
	 * Sets up the Map for every test.
	 */
	@BeforeClass
	public static void setUp() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		mapID = getFreeMapID();
		map = new Map(mapID, "map1", boundingbox, "null", 0, 0);
	}
	
	@Test(expected=NullPointerException.class)
	public void testBoundingBoxNull() {
		new Map(getFreeMapID(), "map1", null, "null", 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMinZoomGreaterMaxZoom() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(getFreeMapID(), "map1", boundingbox, "null", 1, 0);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNameNull() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(getFreeMapID(), null, boundingbox, "null", 0, 0);
	}
	
	@Test(expected=NullPointerException.class)
	public void testTilesUrlNull() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(getFreeMapID(), "map1", boundingbox, null, 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMapIDTaken() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(mapID, "map1", boundingbox, "null", 0, 0);
	}
	
	
	@Test
	public void testGetID() {
		assertEquals(mapID, map.getID());
	}

	@Test
	public void testGetName() {
		assertEquals("map1", map.getName());
	}

	@Test
	public void testGetBoundingBox() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		assertEquals(boundingbox, map.getBoundingBox());
	}
	

	@Test
	public void testGetTilesURL() {
		assertEquals("null", map.getTilesURL());
	}

	@Test
	public void testGetMinZoom() {
		assertEquals(0, map.getMinZoom());
	}

	@Test
	public void testGetMaxZoom() {
		assertEquals(0, map.getMaxZoom());
	}

	@Test
	public void testGetMapByID() {
		assertSame(map, Map.getMapByID(mapID));
	}

	@Test
	public void testGetBuilding() {
		assertNull(map.getBuilding());
		
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		Map map2 = new Map(getFreeMapID(), "map1", boundingbox, "null", 0, 0);
		Building b;
		int id = 0;
		do {
			b = Building.getBuildingByID(id);
			id++;
		} while (b != null);
		
		LinkedList<Map> floors = new LinkedList<Map>(
				Arrays.asList(	new Map(getFreeMapID(), "map1", boundingbox, "null", 0, 0),
								new Map(getFreeMapID(), "map1", boundingbox, "null", 0, 0),
								map2));
		Collection<Category> categories = new LinkedList<Category>();
		POI buildingPOI = new POI("poiid", "poi", "poi", pos1, map, id, categories);
		b = new Building(id, floors , 1, buildingPOI);
		
		assertSame(b, map2.getBuilding());
	}

	public static int getFreeMapID() {
		Map m;
		int i = 0;
		do {
			m = Map.getMapByID(++i);
		} while (m != null);
		return i;
	}
}
