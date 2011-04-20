package edu.kit.cm.kitcampusguide.presentationlayer.view.converters;

import java.util.Collections;

import javax.faces.component.UIComponent;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockViewHandler;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Before;

import edu.kit.cm.kitcampusguide.presentationlayer.view.MapLocator;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel;
import edu.kit.cm.kitcampusguide.presentationlayer.viewmodel.MapModel.MapProperty;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

/**
 * Tests {@link MapModelConverter}.
 * @author Stefan
 *
 */
public class MapModelConverterTest extends AbstractJsfTestCase {

	/**
	 * See {@link TestCase#TestCase(String)}.
	 * @param name the name.
	 */
	public MapModelConverterTest(String name) {
		super(name);
	}

	private MapModelConverter converter;
	private UIComponent testComponent;
	private Map map;

	/**
	 * Creates the test suite as specified by the JUnit framework
	 * @return a test suite.
	 */
	public static Test suite() {
	      return new TestSuite(MapModelConverterTest.class);
	  }
	
	/**
	 * Creates all necessary data.
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		converter = new MapModelConverter();
		testComponent = new MockViewHandler().createView(facesContext,
				"testConverter");
		WorldPosition wp = new WorldPosition(2, 2);
		map = new Map(Idgenerator.getFreeMapID(), "", new MapSection(wp, wp),
				"", 0, 0);
	}

	/**
	 * Checks if a single property is converted correctly.
	 */
	public void testPropertyConversion() {
		MapModel mapModel = new MapModel();
		mapModel.resetChangedProperties();
		assertTrue(getPropertiesMap(mapModel).isEmpty());

		mapModel.setMap(map);
		JSONObject converted = getPropertiesMap(mapModel);
		assertTrue(converted.containsKey("map"));
		assertEquals(1, converted.size());
		assertEquals(map.getID(),
				((Long) ((JSONObject) converted.get("map")).get("id"))
						.intValue());
		
		mapModel.resetChangedProperties();
		assertTrue(getPropertiesMap(mapModel).isEmpty());

	}

	/**
	 * Checks if all properties are converted if all properties are marked as
	 * changed.
	 */
	public void testAllPropertiesTest() {
		MapModel mapModel = new MapModel();
		mapModel.setMap(map);
		mapModel.setMapLocator(new MapLocator(new WorldPosition(0, 2)));
		mapModel.setPOIs(Collections.EMPTY_LIST);
		mapModel.addAllProperties();

		JSONObject converted = getPropertiesMap(mapModel);
		for (MapProperty prop : MapProperty.values()) {
			assertTrue("Expected " + prop.toString(),
					converted.containsKey(prop.toString()));
		}
		assertEquals(MapProperty.values().length, converted.size());
	}

	private JSONObject getPropertiesMap(Object obj) {
		return (JSONObject) JSONValue.parse(converter.getAsString(facesContext,
				testComponent, obj));
	}
}
