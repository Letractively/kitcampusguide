package data;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcretePOILoader;
import edu.kit.cm.kitcampusguide.data.POILoader;
import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.POICategory;

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
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetPOI() {
		POI expectedPOI1 = new POI(
				"Gerthsen H�rsaal",
				1,
				"../images/icons/hoersaal.jpg",
				"Gerthsen H�rsaal, 400 Sitzpl�tze, 3 Beamer, Physiker-H�rsaal, ...",
				1.41564654, 5.874897);
		
		POILoader poiloader = new ConcretePOILoader();	
		
		POI testPOI1 = poiloader.getPOI(1);
		
		Assert.assertEquals(expectedPOI1.getX(), testPOI1.getX(), 0.00001);
		Assert.assertEquals(expectedPOI1.getY(), testPOI1.getY(), 0.00001);
		Assert.assertEquals(expectedPOI1.getName(), testPOI1.getName());
		Assert.assertEquals(expectedPOI1.getIcon(), testPOI1.getIcon());
		Assert.assertEquals(expectedPOI1.getDescription(), testPOI1.getDescription());
		
		
		POI expectedPOI2 = new POI(
				"Mensa am Adenauerring",
				2,
				"../images/icons/mensa.jpg",
				"Das ist die Mensa. Fre�tempel f�r alle Studenten.",
				4.132654, 8.659753);
		
		POI testPOI2 = poiloader.getPOI(2);
		
		Assert.assertEquals(expectedPOI2.getX(), testPOI2.getX(), 0.0001);
		Assert.assertEquals(expectedPOI2.getY(), testPOI2.getY(), 0.0001);
		Assert.assertEquals(expectedPOI2.getName(), testPOI2.getName());
		Assert.assertEquals(expectedPOI2.getIcon(), testPOI2.getIcon());
		Assert.assertEquals(expectedPOI2.getDescription(), testPOI2.getDescription());
		
		
		POI expectedPOI3 = new POI(
				"Audimax",
				3,
				"../hoersaal/audimax.jpg",
				"Der gr��te H�rsaal am KIT. Fasst etwa 750 Studenten. Die Sitzpl�tze sind in zwei Halbkreisen angeordnet. "
					+ "Der H�rsaal hat zwei Beamerfl�chen.",
				8.41583, 49.01272);
		
		POI testPOI3 = poiloader.getPOI(3);
		
		Assert.assertEquals(expectedPOI3.getX(), testPOI3.getX(), 0.0001);
		Assert.assertEquals(expectedPOI3.getY(), testPOI3.getY(), 0.0001);
		Assert.assertEquals(expectedPOI3.getName(), testPOI3.getName());
		Assert.assertEquals(expectedPOI3.getIcon(), testPOI3.getIcon());
		Assert.assertEquals(expectedPOI3.getDescription(), testPOI3.getDescription());
		
		

		POI expectedPOI4 = new POI(
				"H�rsaal am Fasanengarten",
				4,
				"../icons/hsaf.jpg",
				"Der H�rsaal am Fasanengarten war fr�her eine Turnhalle.",
				8.42036, 49.01481);
		
		POI testPOI4 = poiloader.getPOI(4);
		
		Assert.assertEquals(expectedPOI4.getX(), testPOI4.getX(), 0.0001);
		Assert.assertEquals(expectedPOI4.getY(), testPOI4.getY(), 0.0001);
		Assert.assertEquals(expectedPOI4.getName(), testPOI4.getName());
		Assert.assertEquals(expectedPOI4.getIcon(), testPOI4.getIcon());
		Assert.assertEquals(expectedPOI4.getDescription(), testPOI4.getDescription());
		
	}
	 
	/**
	 * This test tests the method getPOIsByName(String name) for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetPOIsByName() {
		POI poi1 = new POI(
				"Gerthsen H�rsaal",
				1,
				"../images/icons/hoersaal.jpg",
				"Gerthsen H�rsaal, 400 Sitzpl�tze, 3 Beamer, Physiker-H�rsaal, ...",
				1.41564654, 5.874897);
		
		POI poi2 = new POI(
				"H�rsaal am Fasanengarten",
				4,
				"../icons/hsaf.jpg",
				"Der H�rsaal am Fasanengarten war fr�her eine Turnhalle.",
				8.42036, 49.01481);
		
	    List<POI> expectedPOIList = new ArrayList<POI>();
	    
		expectedPOIList.add(poi1);
		expectedPOIList.add(poi2);		
	    
		
		POILoader poiloader = new ConcretePOILoader();	
		
		List<POI> testPOIList = poiloader.getPOIsByName("H�rsaal");
		
		Assert.assertEquals(expectedPOIList.size(), testPOIList.size());
		
		Assert.assertEquals(expectedPOIList.get(0).getX(), testPOIList.get(0).getX(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getY(), testPOIList.get(0).getY(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getName(), testPOIList.get(0).getName());
		Assert.assertEquals(expectedPOIList.get(0).getIcon(), testPOIList.get(0).getIcon());
		Assert.assertEquals(expectedPOIList.get(0).getDescription(), testPOIList.get(0).getDescription());
		
		Assert.assertEquals(expectedPOIList.get(1).getX(), testPOIList.get(1).getX(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getY(), testPOIList.get(1).getY(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getName(), testPOIList.get(1).getName());
		Assert.assertEquals(expectedPOIList.get(1).getIcon(), testPOIList.get(1).getIcon());
		Assert.assertEquals(expectedPOIList.get(1).getDescription(), testPOIList.get(1).getDescription());
	}
	
	
	/**
	 * This test tests the method getAllPOIs() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetAllPOIs() {
		
		
		POI poi1 = new POI(
				"Gerthsen H�rsaal",
				1,
				"../images/icons/hoersaal.jpg",
				"Gerthsen H�rsaal, 400 Sitzpl�tze, 3 Beamer, Physiker-H�rsaal, ...",
				1.41564654, 5.874897);

		POI poi2 = new POI(
				"Mensa am Adenauerring",
				2,
				"../images/icons/mensa.jpg",
				"Das ist die Mensa. Fre�tempel f�r alle Studenten.",
				4.132654, 8.659753);

		POI poi3 = new POI(
				"Audimax",
				3,
				"../hoersaal/audimax.jpg",
				"Der gr��te H�rsaal am KIT. Fasst etwa 750 Studenten. Die Sitzpl�tze sind in zwei Halbkreisen angeordnet. "
					+ "Der H�rsaal hat zwei Beamerfl�chen.",
				8.41583, 49.01272);
		
		POI poi4 = new POI(
				"H�rsaal am Fasanengarten",
				4,
				"../icons/hsaf.jpg",
				"Der H�rsaal am Fasanengarten war fr�her eine Turnhalle.",
				8.42036, 49.01481);
		
	    List<POI> expectedPOIList = new ArrayList<POI>();
	    
		expectedPOIList.add(poi1);
		expectedPOIList.add(poi2);	
		expectedPOIList.add(poi3);
		expectedPOIList.add(poi4);
	    
		POILoader poiloader = new ConcretePOILoader();	
		
		List<POI> testPOIList = poiloader.getAllPOIs();
		
		Assert.assertEquals(expectedPOIList.size(), testPOIList.size());
		
		Assert.assertEquals(expectedPOIList.get(0).getX(), testPOIList.get(0).getX(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getY(), testPOIList.get(0).getY(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(0).getName(), testPOIList.get(0).getName());
		Assert.assertEquals(expectedPOIList.get(0).getIcon(), testPOIList.get(0).getIcon());
		Assert.assertEquals(expectedPOIList.get(0).getDescription(), testPOIList.get(0).getDescription());
		
		Assert.assertEquals(expectedPOIList.get(1).getX(), testPOIList.get(1).getX(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getY(), testPOIList.get(1).getY(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(1).getName(), testPOIList.get(1).getName());
		Assert.assertEquals(expectedPOIList.get(1).getIcon(), testPOIList.get(1).getIcon());
		Assert.assertEquals(expectedPOIList.get(1).getDescription(), testPOIList.get(1).getDescription());

		Assert.assertEquals(expectedPOIList.get(2).getX(), testPOIList.get(2).getX(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(2).getY(), testPOIList.get(2).getY(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(2).getName(), testPOIList.get(2).getName());
		Assert.assertEquals(expectedPOIList.get(2).getIcon(), testPOIList.get(2).getIcon());
		Assert.assertEquals(expectedPOIList.get(2).getDescription(), testPOIList.get(2).getDescription());
		
		Assert.assertEquals(expectedPOIList.get(3).getX(), testPOIList.get(3).getX(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(3).getY(), testPOIList.get(3).getY(), 0.0001);
		Assert.assertEquals(expectedPOIList.get(3).getName(), testPOIList.get(3).getName());
		Assert.assertEquals(expectedPOIList.get(3).getIcon(), testPOIList.get(3).getIcon());
		Assert.assertEquals(expectedPOIList.get(3).getDescription(), testPOIList.get(3).getDescription());
	}
	
	
	
	/**
	 * This test tests the method getPOICategory(Integer id) for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void getPOICategory() {
		POICategory poicat1 = new POICategory(
				"Mensen",
				1,
				"../images/icons/mensa.jpg",
				"Mensen die es auf dem Campus gibt.");

		POILoader poiloader = new ConcretePOILoader();		    
	    
		POICategory testPOI1 = poiloader.getPOICategory(1);
		
		Assert.assertEquals(testPOI1.getId(), poicat1.getId());
		Assert.assertEquals(testPOI1.getName(), poicat1.getName());
		Assert.assertEquals(testPOI1.getIcon(), poicat1.getIcon());
		Assert.assertEquals(testPOI1.getDescription(), poicat1.getDescription());

		
		POICategory poicat2 = new POICategory(
				"H�rs�le",
				2,
				"../images/icons/hoersaal.jpg",
				"Alle H�rs�le des KIT.");
	    
		POICategory testPOI2 = poiloader.getPOICategory(2);
		
		Assert.assertEquals(testPOI2.getId(), poicat2.getId());
		Assert.assertEquals(testPOI2.getName(), poicat2.getName());
		Assert.assertEquals(testPOI2.getIcon(), poicat2.getIcon());
		Assert.assertEquals(testPOI2.getDescription(), poicat2.getDescription());
		
	}
	 
	/**
	 * This test tests the method getPOICategoryByName(String name) for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetPOICategoryByName() {
		POICategory poicat1 = new POICategory(
				"Mensen",
				1,
				"../images/icons/mensa.jpg",
				"Mensen die es auf dem Campus gibt.");
		
	    List<POICategory> expectedPOICatList1 = new ArrayList<POICategory>();	
	    expectedPOICatList1.add(poicat1);	
	    
		POILoader poiloader = new ConcretePOILoader();		    
	    
		List<POICategory> testPOICatList1 = poiloader.getPOICategoryByName("Mens");
		
		
		Assert.assertEquals(expectedPOICatList1.size(), testPOICatList1.size());
		
		Assert.assertEquals(expectedPOICatList1.get(0).getId(), testPOICatList1.get(0).getId());
		Assert.assertEquals(expectedPOICatList1.get(0).getName(), testPOICatList1.get(0).getName());
		Assert.assertEquals(expectedPOICatList1.get(0).getIcon(), testPOICatList1.get(0).getIcon());
		Assert.assertEquals(expectedPOICatList1.get(0).getDescription(), testPOICatList1.get(0).getDescription());

		
		POICategory poicat2 = new POICategory(
				"H�rs�le",
				2,
				"../images/icons/hoersaal.jpg",
				"Alle H�rs�le des KIT.");

	    List<POICategory> expectedPOICatList2 = new ArrayList<POICategory>();
	    
	    expectedPOICatList2.add(poicat2);	
	    
		List<POICategory> testPOICatList2 = poiloader.getPOICategoryByName("H�r");
		
		Assert.assertEquals(expectedPOICatList2.size(), testPOICatList2.size());
		
		Assert.assertEquals(expectedPOICatList2.get(0).getId(), testPOICatList2.get(0).getId());
		Assert.assertEquals(expectedPOICatList2.get(0).getName(), testPOICatList2.get(0).getName());
		Assert.assertEquals(expectedPOICatList2.get(0).getIcon(), testPOICatList2.get(0).getIcon());
		Assert.assertEquals(expectedPOICatList2.get(0).getDescription(), testPOICatList2.get(0).getDescription());
	}
	
	
	/**
	 * This test tests the method getAllPOICategory() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetAllPOICategory() {
		POICategory poicat1 = new POICategory(
				"Mensen",
				1,
				"../images/icons/mensa.jpg",
				"Mensen die es auf dem Campus gibt.");

		POICategory poicat2 = new POICategory(
				"H�rs�le",
				2,
				"../images/icons/hoersaal.jpg",
				"Alle H�rs�le des KIT.");

	    List<POICategory> expectedPOICatList = new ArrayList<POICategory>();
	    
	    expectedPOICatList.add(poicat1);
	    expectedPOICatList.add(poicat2);	
	    
		POILoader poiloader = new ConcretePOILoader();	
		
		List<POICategory> testPOICatList = poiloader.getAllPOICategory();
		
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
