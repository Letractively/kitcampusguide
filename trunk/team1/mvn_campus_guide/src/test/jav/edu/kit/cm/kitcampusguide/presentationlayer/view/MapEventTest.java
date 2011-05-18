package edu.kit.cm.kitcampusguide.presentationlayer.view;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests all methods implemented by {@link MapEvent}.
 * @author Stefan
 *
 */
public class MapEventTest {

	/**
	 * Contains two test cases used for validating.
	 */
	private static MapEvent[]  events;

	/**
	 * Creates two test cases, one of them with <code>null</code> data.
	 */
	@BeforeClass
	public static void setUpTestData() {
		events = new MapEvent[] {
				new MapEvent("source1", "0", null),
				new MapEvent("source2", "1", "data")
				};
	}

	/**
	 * Tests {@link MapEvent#getType()}.
	 */
	@Test
	public void testGetType() {
		assertEquals(events[0].getType(), "0");
		assertEquals(events[1].getType(), "1");
	}

	/**
	 * Tests {@link MapEvent#getData()};
	 */
	@Test
	public void testGetData() {
		assertNull(events[0].getData());
		assertEquals("data", events[1].getData());
	}
	
	/**
	 * Tests {@link MapEvent#getSource()}.
	 */
	@Test
	public void testGetSource() {
		assertEquals(events[0].getSource(), "source1");
		assertEquals(events[1].getSource(), "source2");
	}

}
