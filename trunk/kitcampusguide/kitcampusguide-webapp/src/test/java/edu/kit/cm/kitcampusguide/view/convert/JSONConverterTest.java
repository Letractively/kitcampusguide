package edu.kit.cm.kitcampusguide.view.convert;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.model.Poi;
import edu.kit.cm.kitcampusguide.model.PoiCategory;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.view.converter.JSONConverter;

/**
 * This class tests the JSONConverter class.
 * 
 * @author Kateryna Yurchenko
 */
public class JSONConverterTest {

    /**
     * Tests of the convertPOI() method.
     */
    @Test
    public void testConvertPOI() {

        Poi poi = new Poi("name", 0, "icon", "description", 10.0, 20.0, null, true);
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
        List<Poi> pois = new LinkedList<Poi>();
        for (int i = 0; i < 10; i++) {
            pois.add(new Poi("name" + i, i, "icon" + i, "description" + i, i + 10.0, i + 20.0, null, true));
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

        PoiCategory poiCategoryWithoutPoi = new PoiCategory("name_1", 0, "icon_1", "description_1");

        List<Poi> pois = new LinkedList<Poi>();
        for (int i = 0; i < 10; i++) {
            pois.add(new Poi("name" + i, i, "icon" + i, "description" + i, i + 10.0, i + 20.0, null, true));
        }

        PoiCategory poiCategoryWithPoi = new PoiCategory("name_2", 1, "icon_2", "description_2", pois);

        JSONObject convertedWithoutPoi = JSONConverter.convertPOICategory(poiCategoryWithoutPoi);
        JSONObject convertedWithPoi = JSONConverter.convertPOICategory(poiCategoryWithPoi);

        /* Tests of convertedWithoutPoi */

        Assert.assertNotNull(convertedWithoutPoi);
        Assert.assertFalse(convertedWithoutPoi.isEmpty());
        Assert.assertEquals(6, convertedWithoutPoi.size());

        Assert.assertTrue(convertedWithoutPoi.containsKey("name"));
        Assert.assertTrue(convertedWithoutPoi.containsKey("id"));
        Assert.assertTrue(convertedWithoutPoi.containsKey("icon"));
        Assert.assertTrue(convertedWithoutPoi.containsKey("description"));
        Assert.assertTrue(convertedWithoutPoi.containsKey("visible"));
        Assert.assertTrue(convertedWithoutPoi.containsKey("pois"));

        Assert.assertTrue(convertedWithoutPoi.containsValue("name_1"));
        Assert.assertTrue(convertedWithoutPoi.containsValue(0));
        Assert.assertTrue(convertedWithoutPoi.containsValue("icon_1"));
        Assert.assertTrue(convertedWithoutPoi.containsValue("description_1"));
        Assert.assertTrue(convertedWithoutPoi.containsValue(false));

        poiCategoryWithoutPoi.add(null);

        try {
            convertedWithoutPoi = JSONConverter.convertPOICategory(poiCategoryWithoutPoi);
            Assert.fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            Assert.assertFalse(convertedWithoutPoi.isEmpty());
        }

        /* Tests of convertedWithPoi */

        Assert.assertNotNull(convertedWithPoi);
        Assert.assertFalse(convertedWithPoi.isEmpty());
        Assert.assertEquals(6, convertedWithPoi.size());

        Assert.assertTrue(convertedWithPoi.containsKey("name"));
        Assert.assertTrue(convertedWithPoi.containsKey("id"));
        Assert.assertTrue(convertedWithPoi.containsKey("icon"));
        Assert.assertTrue(convertedWithPoi.containsKey("description"));
        Assert.assertTrue(convertedWithPoi.containsKey("visible"));
        Assert.assertTrue(convertedWithPoi.containsKey("pois"));

        Assert.assertTrue(convertedWithPoi.containsValue("name_2"));
        Assert.assertTrue(convertedWithPoi.containsValue(1));
        Assert.assertTrue(convertedWithPoi.containsValue("icon_2"));
        Assert.assertTrue(convertedWithPoi.containsValue("description_2"));
        Assert.assertTrue(convertedWithPoi.containsValue(false));
        Assert.assertTrue(convertedWithPoi.containsValue(JSONConverter.convertPOIs(pois)));

        poiCategoryWithPoi.add(null);

        try {
            convertedWithPoi = JSONConverter.convertPOICategory(poiCategoryWithPoi);
            Assert.fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            Assert.assertFalse(convertedWithPoi.isEmpty());
        }

        try {
            convertedWithPoi = JSONConverter.convertPOICategory(null);
            Assert.fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            Assert.assertFalse(convertedWithPoi.isEmpty());
        }

    }

    /**
     * Tests of the convertPOICategories() method.
     */
    @Test
    public void testConvertPOICategories() {
        List<Poi> pois = new LinkedList<Poi>();
        for (int i = 0; i < 10; i++) {
            pois.add(new Poi("name" + i, i, "icon" + i, "description" + i, i + 10.0, i + 20.0, null, true));
        }

        List<PoiCategory> poiCategories = new LinkedList<PoiCategory>();
        for (int i = 0; i < 10; i++) {
            poiCategories.add(new PoiCategory("name" + i, i, "icon" + i, "description" + i, pois));
        }

        JSONArray converted = JSONConverter.convertPOICategories(poiCategories);

        Assert.assertNotNull(converted);
        Assert.assertFalse(converted.isEmpty());
        Assert.assertEquals(10, converted.size());

        for (Object convertedPOICategories : converted) {
            int index = converted.indexOf(convertedPOICategories);

            Assert.assertTrue(convertedPOICategories instanceof JSONObject);
            JSONObject poiCategory = (JSONObject) convertedPOICategories;

            Assert.assertFalse(poiCategory.isEmpty());
            Assert.assertEquals(6, poiCategory.size());

            Assert.assertTrue(poiCategory.containsKey("name"));
            Assert.assertTrue(poiCategory.containsKey("id"));
            Assert.assertTrue(poiCategory.containsKey("icon"));
            Assert.assertTrue(poiCategory.containsKey("description"));
            Assert.assertTrue(poiCategory.containsKey("visible"));
            Assert.assertTrue(poiCategory.containsKey("pois"));

            Assert.assertTrue(poiCategory.containsValue("name" + index));
            Assert.assertTrue(poiCategory.containsValue(index));
            Assert.assertTrue(poiCategory.containsValue("icon" + index));
            Assert.assertTrue(poiCategory.containsValue("description" + index));
            Assert.assertTrue(poiCategory.containsValue(false));
            Assert.assertTrue(poiCategory.containsValue(JSONConverter.convertPOIs(pois)));
        }

        Assert.assertNull(JSONConverter.convertPOICategories(null));

        poiCategories.add(null);

        try {
            converted = JSONConverter.convertPOICategories(poiCategories);
            Assert.fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            Assert.assertFalse(converted.isEmpty());
        }

    }

    /**
     * Tests of the convertRoute() method.
     */
    @Test
    public void testConvertRoute() {
        List<Point> points = new LinkedList<Point>();
        for (int i = 0; i < 10; i++) {
            points.add(new Point(10.0 + i, 20.0 + i));
        }

        Route route = new Route(points);
        JSONArray converted = JSONConverter.convertRoute(route);

        Assert.assertNotNull(converted);
        Assert.assertFalse(converted.isEmpty());
        Assert.assertEquals(10, converted.size());

        for (Object convertedRoutePoints : converted) {
            int index = converted.indexOf(convertedRoutePoints);

            Assert.assertTrue(convertedRoutePoints instanceof JSONObject);
            JSONObject convertedRoute = (JSONObject) convertedRoutePoints;

            Assert.assertFalse(convertedRoute.isEmpty());
            Assert.assertEquals(2, convertedRoute.size());

            Assert.assertTrue(convertedRoute.containsKey("lon"));
            Assert.assertTrue(convertedRoute.containsKey("lat"));

            Assert.assertTrue(convertedRoute.containsValue(10.0 + index));
            Assert.assertTrue(convertedRoute.containsValue(20.0 + index));
        }

        Assert.assertNull(JSONConverter.convertRoute(null));

        points.add(null);
        route = new Route(points);

        try {
            converted = JSONConverter.convertRoute(route);
            Assert.fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            Assert.assertFalse(converted.isEmpty());
        }

    }

}
