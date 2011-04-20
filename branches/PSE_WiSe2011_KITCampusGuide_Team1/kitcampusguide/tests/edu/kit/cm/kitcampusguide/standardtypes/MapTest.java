package edu.kit.cm.kitcampusguide.standardtypes;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;
import static edu.kit.cm.kitcampusguide.testframework.Idgenerator.*;

/**
 * Tests the implementation of Map.
 * Note: Not thread safe.
 * @author Fabian
 *
 */
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
	
	/**
	 * Tests if the constructor correctly throws a <code>NullPointerException</code> 
	 * in the event that <code>boundingBox</code> is <code>null</code>.
	 */
	@Test(expected=NullPointerException.class)
	public void testBoundingBoxNull() {
		new Map(getFreeMapID(), "map1", null, "null", 0, 0);
	}
	
	/**
	 * Tests if the constructor correctly throws an <code>IllegalArgumentException</code>
	 * in the event that <code>minZoom</code> is greater than <code>maxZoom</code>.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMinZoomGreaterMaxZoom() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(getFreeMapID(), "map1", boundingbox, "null", 1, 0);
	}
	
	/**
	 * Tests if the constructor correctly throws a <code>NullPointerException</code> 
	 * in the event that <code>name</code> is <code>null</code>.
	 */
	@Test(expected=NullPointerException.class)
	public void testNameNull() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(getFreeMapID(), null, boundingbox, "null", 0, 0);
	}
	
	/**
	 * Tests if the constructor correctly throws a <code>NullPointerException</code> 
	 * in the event that <code>tilesURL</code> is <code>null</code>.
	 */
	@Test(expected=NullPointerException.class)
	public void testTilesUrlNull() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(getFreeMapID(), "map1", boundingbox, null, 0, 0);
	}
	
	/**
	 * Tests if the constructor correctly throws an <code>IllegalArgumentException</code>
	 * in the event that the ID provide by <code>id</code> is already taken.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMapIDTaken() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		new Map(mapID, "map1", boundingbox, "null", 0, 0);
	}
	
	/**
	 * Tests {@link Map#getID()}
	 */
	@Test
	public void testGetID() {
		assertEquals(mapID, map.getID());
	}

	/**
	 * Tests {@link Map#getName()}
	 */
	@Test
	public void testGetName() {
		assertEquals("map1", map.getName());
	}

	/**
	 * Tests {@link Map#getBoundingBox()}
	 */
	@Test
	public void testGetBoundingBox() {
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		assertEquals(boundingbox, map.getBoundingBox());
	}
	

	/**
	 * Tests {@link Map#getTilesURL()}
	 */
	@Test
	public void testGetTilesURL() {
		assertEquals("null", map.getTilesURL());
	}

	/**
	 * Tests {@link Map#getMinZoom()}
	 */
	@Test
	public void testGetMinZoom() {
		assertEquals(0, map.getMinZoom());
	}

	/**
	 * Tests {@link Map#getMaxZoom()}
	 */
	@Test
	public void testGetMaxZoom() {
		assertEquals(0, map.getMaxZoom());
	}

	/**
	 * Tests {@link Map#getMapByID(int)}
	 */
	@Test
	public void testGetMapByID() {
		assertSame(map, Map.getMapByID(mapID));
	}

	/**
	 * Tests {@link Map#getBuilding()} with and without building associated to the map.
	 */
	@Test
	public void testGetBuilding() {
		assertNull(map.getBuilding());
		
		WorldPosition pos1 = new WorldPosition(0, 0);
		WorldPosition pos2 = pos1;
		MapSection boundingbox = new MapSection(pos1, pos2);
		Map map2 = new Map(getFreeMapID(), "map1", boundingbox, "null", 0, 0);
		int bID = getFreeBuildingID();
		LinkedList<Map> floors = new LinkedList<Map>(
				Arrays.asList(	new Map(getFreeMapID(), "map1", boundingbox, "null", 0, 0),
								new Map(getFreeMapID(), "map1", boundingbox, "null", 0, 0),
								map2));
		Collection<Category> categories = new LinkedList<Category>();
		POI buildingPOI = new POI(getFreePOIID(), "poi", "poi", pos1, map, bID, categories);
		Building b = new Building(bID, floors , 1, buildingPOI);
		
		assertSame(b, map2.getBuilding());
	}
}
