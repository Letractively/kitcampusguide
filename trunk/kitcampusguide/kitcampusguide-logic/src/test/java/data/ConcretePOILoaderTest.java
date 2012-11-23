package data;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.Poi;
import edu.kit.cm.kitcampusguide.model.PoiCategory;

/**
 * This class tests the ConcretePOILoader.
 * 
 * @author Michael Hauber
 */
public class ConcretePOILoaderTest {

	/**
	 * This tests the getters of the ConcreteMapLoader for exceptions.
	 */
	@Test
	public void testExceptionsofGetter() {
		POILoader poiloader = new ConcretePOILoader();

		try {
			final int negativeId = -2;
			poiloader.getPOI(negativeId);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			poiloader.getPOI(null);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			final String emptyString = "";
			poiloader.getPOIsByName(emptyString);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			poiloader.getPOIsByName(null);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			final int negativeId = -2;
			poiloader.getPOICategory(negativeId);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			poiloader.getPOICategory(null);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			final String emptyString = "";
			poiloader.getPOICategoryByName(emptyString);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}

		try {
			poiloader.getPOICategoryByName(null);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}
	}

	/**
	 * This test tests the method getPOI(Integer id) for correctness.
	 * 
	 * Attention: Works only with the status of the database created by the SQL
	 * Dumps in Google Docs.
	 */
	@Test
	public void testgetPOI() {
		Poi expectedPOI1 = new Poi("Gerthsen Hörsaal", 1, "../images/icons/hoersaal.jpg",
				"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...", 8.41152, 49.01258);

		POILoader poiloader = new ConcretePOILoader();

		Poi testPOI1 = poiloader.getPOI(1);

		Assert.assertEquals(expectedPOI1.getLongitude(), testPOI1.getLongitude(), 0.00001);
		Assert.assertEquals(expectedPOI1.getLatitude(), testPOI1.getLatitude(), 0.00001);
		Assert.assertEquals(expectedPOI1.getName(), testPOI1.getName());
		Assert.assertEquals(expectedPOI1.getIcon(), testPOI1.getIcon());
		Assert.assertEquals(expectedPOI1.getDescription(), testPOI1.getDescription());

		Poi expectedPOI2 = new Poi("Mensa am Adenauerring", 2, "../images/icons/mensa.jpg",
				"Das ist die Mensa. Freßtempel für alle Studenten.", 8.41684, 49.01198);

		Poi testPOI2 = poiloader.getPOI(2);

		Assert.assertEquals(expectedPOI2.getLongitude(), testPOI2.getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOI2.getLatitude(), testPOI2.getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOI2.getName(), testPOI2.getName());
		Assert.assertEquals(expectedPOI2.getIcon(), testPOI2.getIcon());
		Assert.assertEquals(expectedPOI2.getDescription(), testPOI2.getDescription());

		Poi expectedPOI3 = new Poi("Audimax", 3, "../hoersaal/audimax.jpg",
				"Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. "
						+ "Der Hörsaal hat zwei Beamerflächen.", 8.41583, 49.01272);

		Poi testPOI3 = poiloader.getPOI(3);

		Assert.assertEquals(expectedPOI3.getLongitude(), testPOI3.getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOI3.getLatitude(), testPOI3.getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOI3.getName(), testPOI3.getName());
		Assert.assertEquals(expectedPOI3.getIcon(), testPOI3.getIcon());
		Assert.assertEquals(expectedPOI3.getDescription(), testPOI3.getDescription());

		Poi expectedPOI4 = new Poi("Hörsaal am Fasanengarten", 4, "../icons/hsaf.jpg",
				"Der Hörsaal am Fasanengarten war früher eine Turnhalle.", 8.42036, 49.01481);

		Poi testPOI4 = poiloader.getPOI(4);

		Assert.assertEquals(expectedPOI4.getLongitude(), testPOI4.getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOI4.getLatitude(), testPOI4.getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOI4.getName(), testPOI4.getName());
		Assert.assertEquals(expectedPOI4.getIcon(), testPOI4.getIcon());
		Assert.assertEquals(expectedPOI4.getDescription(), testPOI4.getDescription());

	}

	/**
	 * This test tests the method getPOIsByName(String name) for correctness.
	 * 
	 * Attention: Works only with the status of the database created by the SQL
	 * Dumps in Google Docs.
	 */
	@Test
	public void testgetPOIsByName() {
		Poi poi1 = new Poi("Gerthsen Hörsaal", 1, "../images/icons/hoersaal.jpg",
				"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...", 8.41152, 49.01258);

		Poi poi2 = new Poi("Hörsaal am Fasanengarten", 4, "../icons/hsaf.jpg",
				"Der Hörsaal am Fasanengarten war früher eine Turnhalle.", 8.42036, 49.01481);

		List<Poi> expectedPOIList = new ArrayList<Poi>();

		expectedPOIList.add(poi1);
		expectedPOIList.add(poi2);

		POILoader poiloader = new ConcretePOILoader();

		List<Poi> testPOIList = poiloader.getPOIsByName("Hörsaal");

		Assert.assertEquals(expectedPOIList.size(), testPOIList.size());

		Assert.assertEquals(expectedPOIList.get(0).getLongitude(), testPOIList.get(0).getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getLatitude(), testPOIList.get(0).getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getName(), testPOIList.get(0).getName());
		Assert.assertEquals(expectedPOIList.get(0).getIcon(), testPOIList.get(0).getIcon());
		Assert.assertEquals(expectedPOIList.get(0).getDescription(), testPOIList.get(0).getDescription());

		Assert.assertEquals(expectedPOIList.get(1).getLongitude(), testPOIList.get(1).getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getLatitude(), testPOIList.get(1).getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getName(), testPOIList.get(1).getName());
		Assert.assertEquals(expectedPOIList.get(1).getIcon(), testPOIList.get(1).getIcon());
		Assert.assertEquals(expectedPOIList.get(1).getDescription(), testPOIList.get(1).getDescription());
	}

	/**
	 * This test tests the method getAllPOIs() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by the SQL
	 * Dumps in Google Docs.
	 */
	@Test
	public void testgetAllPOIs() {

		Poi poi1 = new Poi("Gerthsen Hörsaal", 1, "../images/icons/hoersaal.jpg",
				"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...", 8.41152, 49.01258);

		Poi poi2 = new Poi("Mensa am Adenauerring", 2, "../images/icons/mensa.jpg",
				"Das ist die Mensa. Freßtempel f�r alle Studenten.", 8.41684, 49.01198);

		Poi poi3 = new Poi("Audimax", 3, "../hoersaal/audimax.jpg",
				"Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. "
						+ "Der Hörsaal hat zwei Beamerflächen.", 8.41583, 49.01272);

		Poi poi4 = new Poi("Hörsaal am Fasanengarten", 4, "../icons/hsaf.jpg",
				"Der Hörsaal am Fasanengarten war früher eine Turnhalle.", 8.42036, 49.01481);

		List<Poi> expectedPOIList = new ArrayList<Poi>();

		expectedPOIList.add(poi1);
		expectedPOIList.add(poi2);
		expectedPOIList.add(poi3);
		expectedPOIList.add(poi4);

		POILoader poiloader = new ConcretePOILoader();

		List<Poi> testPOIList = poiloader.getAllPOIs();

		Assert.assertEquals(expectedPOIList.size(), testPOIList.size());

		Assert.assertEquals(expectedPOIList.get(0).getLongitude(), testPOIList.get(0).getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getLatitude(), testPOIList.get(0).getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getName(), testPOIList.get(0).getName());
		Assert.assertEquals(expectedPOIList.get(0).getIcon(), testPOIList.get(0).getIcon());
		Assert.assertEquals(expectedPOIList.get(0).getDescription(), testPOIList.get(0).getDescription());

		Assert.assertEquals(expectedPOIList.get(1).getLongitude(), testPOIList.get(1).getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getLatitude(), testPOIList.get(1).getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getName(), testPOIList.get(1).getName());
		Assert.assertEquals(expectedPOIList.get(1).getIcon(), testPOIList.get(1).getIcon());
		Assert.assertEquals(expectedPOIList.get(1).getDescription(), testPOIList.get(1).getDescription());

		Assert.assertEquals(expectedPOIList.get(2).getLongitude(), testPOIList.get(2).getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(2).getLatitude(), testPOIList.get(2).getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(2).getName(), testPOIList.get(2).getName());
		Assert.assertEquals(expectedPOIList.get(2).getIcon(), testPOIList.get(2).getIcon());
		Assert.assertEquals(expectedPOIList.get(2).getDescription(), testPOIList.get(2).getDescription());

		Assert.assertEquals(expectedPOIList.get(3).getLongitude(), testPOIList.get(3).getLongitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(3).getLatitude(), testPOIList.get(3).getLatitude(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(3).getName(), testPOIList.get(3).getName());
		Assert.assertEquals(expectedPOIList.get(3).getIcon(), testPOIList.get(3).getIcon());
		Assert.assertEquals(expectedPOIList.get(3).getDescription(), testPOIList.get(3).getDescription());
	}

	/**
	 * This test tests the method getPOICategory(Integer id) for correctness.
	 * 
	 * Attention: Works only with the status of the database created by the SQL
	 * Dumps in Google Docs.
	 */
	@Test
	public void getPOICategory() {
		PoiCategory poicat1 = new PoiCategory("Mensen", 1, "../images/icons/mensa.jpg",
				"Mensen die es auf dem Campus gibt.");

		POILoader poiloader = new ConcretePOILoader();

		PoiCategory testPOI1 = poiloader.getPOICategory(1);

		Assert.assertEquals(testPOI1.getId(), poicat1.getId());
		Assert.assertEquals(testPOI1.getName(), poicat1.getName());
		Assert.assertEquals(testPOI1.getIcon(), poicat1.getIcon());
		Assert.assertEquals(testPOI1.getDescription(), poicat1.getDescription());

		PoiCategory poicat2 = new PoiCategory("Hörsäle", 2, "../images/icons/hoersaal.jpg", "Alle Hörsäle des KIT.");

		PoiCategory testPOI2 = poiloader.getPOICategory(2);

		Assert.assertEquals(testPOI2.getId(), poicat2.getId());
		Assert.assertEquals(testPOI2.getName(), poicat2.getName());
		Assert.assertEquals(testPOI2.getIcon(), poicat2.getIcon());
		Assert.assertEquals(testPOI2.getDescription(), poicat2.getDescription());

	}

	/**
	 * This test tests the method getPOICategoryByName(String name) for
	 * correctness.
	 * 
	 * Attention: Works only with the status of the database created by the SQL
	 * Dumps in Google Docs.
	 */
	@Test
	public void testgetPOICategoryByName() {
		PoiCategory poicat1 = new PoiCategory("Mensen", 1, "../images/icons/mensa.jpg",
				"Mensen die es auf dem Campus gibt.");

		List<PoiCategory> expectedPOICatList1 = new ArrayList<PoiCategory>();
		expectedPOICatList1.add(poicat1);

		POILoader poiloader = new ConcretePOILoader();

		List<PoiCategory> testPOICatList1 = poiloader.getPOICategoryByName("Mens");

		Assert.assertEquals(expectedPOICatList1.size(), testPOICatList1.size());

		Assert.assertEquals(expectedPOICatList1.get(0).getId(), testPOICatList1.get(0).getId());
		Assert.assertEquals(expectedPOICatList1.get(0).getName(), testPOICatList1.get(0).getName());
		Assert.assertEquals(expectedPOICatList1.get(0).getIcon(), testPOICatList1.get(0).getIcon());
		Assert.assertEquals(expectedPOICatList1.get(0).getDescription(), testPOICatList1.get(0).getDescription());

		PoiCategory poicat2 = new PoiCategory("Hörsäle", 2, "../images/icons/hoersaal.jpg", "Alle Hörsäle des KIT.");

		List<PoiCategory> expectedPOICatList2 = new ArrayList<PoiCategory>();

		expectedPOICatList2.add(poicat2);

		List<PoiCategory> testPOICatList2 = poiloader.getPOICategoryByName("Hör");

		Assert.assertEquals(expectedPOICatList2.size(), testPOICatList2.size());

		Assert.assertEquals(expectedPOICatList2.get(0).getId(), testPOICatList2.get(0).getId());
		Assert.assertEquals(expectedPOICatList2.get(0).getName(), testPOICatList2.get(0).getName());
		Assert.assertEquals(expectedPOICatList2.get(0).getIcon(), testPOICatList2.get(0).getIcon());
		Assert.assertEquals(expectedPOICatList2.get(0).getDescription(), testPOICatList2.get(0).getDescription());
	}

	/**
	 * This test tests the method getAllPOICategory() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by the SQL
	 * Dumps in Google Docs.
	 */
	@Test
	public void testgetAllPOICategory() {
		PoiCategory poicat1 = new PoiCategory("Mensen", 1, "../images/icons/mensa.jpg",
				"Mensen die es auf dem Campus gibt.");

		PoiCategory poicat2 = new PoiCategory("H�rs�le", 2, "../images/icons/hoersaal.jpg", "Alle H�rs�le des KIT.");

		List<PoiCategory> expectedPOICatList = new ArrayList<PoiCategory>();

		expectedPOICatList.add(poicat1);
		expectedPOICatList.add(poicat2);

		POILoader poiloader = new ConcretePOILoader();

		List<PoiCategory> testPOICatList = poiloader.getAllPOICategory();

		Assert.assertEquals(expectedPOICatList.size(), testPOICatList.size());

		Assert.assertEquals(expectedPOICatList.get(0).getId(), testPOICatList.get(0).getId());
		Assert.assertEquals(expectedPOICatList.get(0).getName(), testPOICatList.get(0).getName());
		Assert.assertEquals(expectedPOICatList.get(0).getIcon(), testPOICatList.get(0).getIcon());
		Assert.assertEquals(expectedPOICatList.get(0).getDescription(), testPOICatList.get(0).getDescription());

		Assert.assertEquals(expectedPOICatList.get(1).getId(), testPOICatList.get(1).getId());
		Assert.assertEquals(expectedPOICatList.get(1).getName(), testPOICatList.get(1).getName());
		Assert.assertEquals(expectedPOICatList.get(1).getIcon(), testPOICatList.get(1).getIcon());
		Assert.assertEquals(expectedPOICatList.get(1).getDescription(), testPOICatList.get(1).getDescription());
	}

}
