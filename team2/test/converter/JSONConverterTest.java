package converter;

import org.junit.Assert;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.view.converter.JSONConverter;

/**
 * This class tests the JSONConverter class.
 * @author Kateryna Yurchenko
 */
public class JSONConverterTest {
	
	/**
	 * Tests of the convertPOI() method.
	 */
	@Test
	public void testConvertPOI() {
		
		POI poi = new POI("name", 0, "icon", "description", 10.0, 20.0);
		JSONObject converted = JSONConverter.convertPOI(poi);
		
		Assert.assertNotNull(converted);
		Assert.assertFalse(converted.isEmpty());
		Assert.assertEquals(7, converted.size());
		
		Assert.assertTrue(converted.containsKey("name"));
		Assert.assertTrue(converted.containsKey("id"));
		Assert.assertTrue(converted.containsKey("icon"));
		Assert.assertTrue(converted.containsKey("description"));
		Assert.assertTrue(converted.containsKey("lon"));
		Assert.assertTrue(converted.containsKey("lat"));
		Assert.assertTrue(converted.containsKey("nameSize"));
		
		Assert.assertTrue(converted.containsValue("name"));
		Assert.assertTrue(converted.containsValue(0));
		Assert.assertTrue(converted.containsValue("icon"));
		Assert.assertTrue(converted.containsValue("description"));
		Assert.assertTrue(converted.containsValue(10.0));
		Assert.assertTrue(converted.containsValue(20.0));
		Assert.assertTrue(converted.containsValue(4));
		
		try {
			converted = JSONConverter.convertPOI(null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
			Assert.assertEquals(JSONConverter.convertPOI(poi), converted);
		}
	}
	
	/**
	 * Tests of the convertPOIs() method.
	 */
	@Test
	public void testConvertPOIs() {
		List<POI> pois = new LinkedList<POI>();
		for (int i = 0; i < 10; i++) {
			pois.add(new POI("name" + i, i, "icon" + i, "description" + i, i + 10.0, i + 20.0));
		}
		
		JSONArray converted = JSONConverter.convertPOIs(pois);
		
		Assert.assertNotNull(converted);
		Assert.assertFalse(converted.isEmpty());
		Assert.assertEquals(10, converted.size());
		
		for (Object convertedPOI : converted) {
			int index = converted.indexOf(convertedPOI);
			
			Assert.assertTrue(convertedPOI instanceof JSONObject);
			JSONObject poi = (JSONObject) convertedPOI;

			Assert.assertFalse(poi.isEmpty());
			Assert.assertEquals(7, poi.size());
			
			Assert.assertTrue(poi.containsKey("name"));
			Assert.assertTrue(poi.containsKey("id"));
			Assert.assertTrue(poi.containsKey("icon"));
			Assert.assertTrue(poi.containsKey("description"));
			Assert.assertTrue(poi.containsKey("lon"));
			Assert.assertTrue(poi.containsKey("lat"));
			Assert.assertTrue(poi.containsKey("nameSize"));
			
			Assert.assertTrue(poi.containsValue("name" + index));
			Assert.assertTrue(poi.containsValue(index));
			Assert.assertTrue(poi.containsValue("icon" + index));
			Assert.assertTrue(poi.containsValue("description" + index));
			Assert.assertTrue(poi.containsValue(10.0 + index));
			Assert.assertTrue(poi.containsValue(20.0 + index));
			Assert.assertTrue(poi.containsValue(5));			
		}
		
		Assert.assertNull(JSONConverter.convertPOIs(null));
		
		pois.add(null);
		
		try {
			converted = JSONConverter.convertPOIs(pois);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
			Assert.assertFalse(converted.isEmpty());
		}
		
	}
	
	/**
	 * Tests of the convertPOICategory() method.
	 */
	@Test
	public void testConvertPOICategory() {
		Assert.fail("Not yet implemented");
	}
	
	/**
	 * Tests of the convertPOICategories() method.
	 */
	@Test
	public void testConvertPOICategories() {
		Assert.fail("Not yet implemented");
	}
	
	/**
	 * Tests of the convertRoute() method.
	 */
	@Test
	public void testConvertRoute() {
		Assert.fail("Not yet implemented");
	}

}
