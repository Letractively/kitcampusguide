package edu.kit.kitcampusguide.applicationlogic.coordinatemanager;

import static junit.framework.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManager;
import edu.kit.cm.kitcampusguide.applicationlogic.coordinatemanager.CoordinateManagerImpl;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Tests if {@link CoordinateManagerImpl} works properly.
 * @author Fabian
 *
 */
public class CoordinateManagerImplTest {

	private static CoordinateManager manager;
	/** The delta used for comparing doubles */
	private static final double delta = 1e-9;
	
	/**
	 * Initialize
	 */
	@BeforeClass
	public static void setUp() {
		manager = CoordinateManagerImpl.getInstance();
	}
	
	/**
	 * Test if there is correctly only one instance initialized.
	 */
	@Test
	public void testGetInstance() {
		assertSame(manager, CoordinateManagerImpl.getInstance());
	}

	/**
	 * Tests if {@link CoordinateManagerImpl#stringToCoordinate(String)} works properly.
	 */
	@Test
	public void testStringToCoordinate() {
		String input = "48.101231, 12.685834";
		WorldPosition output = manager.stringToCoordinate(input);
		assertEquals(48.101231, output.getLatitude(), delta);
		assertEquals(12.685834, output.getLongitude(), delta);
		
		input = "48.101231, 12.685834, 3.412312";
		output = manager.stringToCoordinate(input);
		assertNull(output);
		
		input = "";
		output = manager.stringToCoordinate(input);
		assertNull(output);
		
		input = "asdgqerf";
		output = manager.stringToCoordinate(input);
		assertNull(output);
		
		input = " 12.6858A34, 3.412312";
		output = manager.stringToCoordinate(input);
		assertNull(output);
		
		input = "48.101231, 1200.685834";
		output = manager.stringToCoordinate(input);
		assertNull(output);
	}

	/**
	 * Tests if {@link CoordinateManagerImpl#coordinateToString(WorldPosition)} works properly.
	 */
	@Test
	public void testCoordinateToString() {
		WorldPosition input = new WorldPosition(48.101231, 12.685834);
		String output = manager.coordinateToString(input);
		assertTrue(output.equals("48.101231, 12.685834"));
	}

}
