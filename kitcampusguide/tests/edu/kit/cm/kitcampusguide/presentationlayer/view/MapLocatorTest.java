package edu.kit.cm.kitcampusguide.presentationlayer.view;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBTestFramework;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Tests whether {@link MapLocator} works properly
 * @author Isa
 *
 */
public class MapLocatorTest {

	private MapLocator mapLocator;
	private MapLocator mapLocator1;
	private MapLocator mapLocator2;
	private MapSection testMapSection;
	private WorldPosition testWorldPosition;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		POIDBTestFramework.constructPOIDB();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testMapSection = new MapSection(new WorldPosition(0.0, 0.0), new WorldPosition(49.012743, 8.415631));
		testWorldPosition = new WorldPosition(49.0, 8.5);
		mapLocator1 = new MapLocator(testMapSection);
		mapLocator2 = new MapLocator(testWorldPosition);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mapLocator = null;
		mapLocator1 = null;
		mapLocator2 = null;
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator#MapLocator(edu.kit.cm.kitcampusguide.standardtypes.MapSection)}.
	 */
	@Test
	public void testMapLocatorMapSection() {
		mapLocator = new MapLocator(testMapSection);
		assertEquals(testMapSection, mapLocator.getMapSection());
		assertNull(mapLocator.getCenter());
	}
	
	@Test (expected=NullPointerException.class)
	public void testMapLocatorMapSection_withNullArgument() {
		testMapSection = null;
		mapLocator = new MapLocator(testMapSection);
	}
	
	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator#MapLocator(edu.kit.cm.kitcampusguide.standardtypes.WorldPosition)}.
	 */
	@Test
	public void testMapLocatorWorldPosition() {
		mapLocator = new MapLocator(testWorldPosition);
		assertEquals(testWorldPosition, mapLocator.getCenter());
		assertNull(mapLocator.getMapSection());
	}
	
	@Test (expected=NullPointerException.class)
	public void testMapLocatorWorldPosition_withNullArgument() {
		testWorldPosition = null;
		mapLocator = new MapLocator(testWorldPosition);
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator#getMapSection()}.
	 */
	@Test
	public void testGetMapSection() {
		assertEquals(testMapSection, mapLocator1.getMapSection());
		assertNull(mapLocator2.getMapSection());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator#getCenter()}.
	 */
	@Test
	public void testGetCenter() {
		assertNull(mapLocator1.getCenter());
		assertEquals(testWorldPosition, mapLocator2.getCenter());
	}

	/**
	 * Test method for {@link edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertFalse(mapLocator1.equals(null));
		assertFalse(mapLocator1.equals("mapLocator"));
		assertFalse(mapLocator1.equals(mapLocator2));
		assertFalse(mapLocator1.equals(new MapLocator(new MapSection(
				new WorldPosition(0.0, 0.0), new WorldPosition(1.0, 1.0)))));
		assertFalse(mapLocator2.equals(mapLocator1));
		assertFalse(mapLocator2.equals(new MapLocator(new WorldPosition(1.0, 1.0))));
		assertTrue(mapLocator1.equals(new MapLocator(testMapSection)));
		assertTrue(mapLocator2.equals(new MapLocator(testWorldPosition)));
	}
}
