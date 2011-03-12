package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.kit.cm.kitcampusguide.applicationlogic.poisource.POISourceImpl;
import edu.kit.cm.kitcampusguide.datalayer.poidb.DefaultPOIDB;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDB;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBSearcher;
import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;
import edu.kit.cm.kitcampusguide.testframework.Idgenerator;

/**
 * Utility class for functions of the POIDB which are used by several test classes.
 * @author Frederik
 *
 */
public class POIDBTestFramework {

	/**
	 * Initializes a POIDB + Searcher, Adds the POIs defined in addPOIs.
	 * 
	 * @throws a whole bunch of exceptions.
	 */
	public static void constructPOIDB() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		POIDBSearcher searcher;
		Class<POIDBSearcher> searcherClass;
		searcherClass = (Class<POIDBSearcher>) Class.forName("edu.kit.cm.kitcampusguide.datalayer.poidb.SimpleSearch");
		Constructor<POIDBSearcher> con = searcherClass.getConstructor();
		searcher = con.newInstance();
		String dbURL = "jdbc:sqlite:defaultpoidbtest.db";
		boolean create = true;
		Class.forName("org.sqlite.JDBC"); //TODO change, inside POIDB.init
		DefaultPOIDB.init(dbURL, searcher, create);
		addPOIs();
	}
	
	/**
	 *
	 */
	private static void addPOIs() {
		//Map with ID 1
		Map testMap = new Map(1, "testMap", new MapSection(new WorldPosition(49.0179, 8.40232), new WorldPosition(49.0078, 8.42622)), "", 0, 1);
		DefaultPOIDB db = (DefaultPOIDB) DefaultPOIDB.getInstance();
		MapPosition pos = new MapPosition(49.012743, 8.415631, testMap);
		Collection<Category> catList= new ArrayList<Category>();
		db.addPOI("testPOI1", "", pos, 1, catList); //new POI with ID 1, is buildingPOI of testBuilding
		db.addPOI("testPOI2", "", pos, null, catList); //new POI with ID 2
	
		//Floor with ID 2
		Map floor1 = new Map(2, "floor1", new MapSection(new WorldPosition(49.0179, 8.40232), new WorldPosition(49.0078, 8.42622)), "", 0, 1);
		//Floor with ID 3
		Map floor2 = new Map(3, "floor2", new MapSection(new WorldPosition(49.0179, 8.40232), new WorldPosition(49.0078, 8.42622)), "", 0, 1);
		List<Map> testFloors = Arrays.asList(floor1, floor2);
		 //new Building with ID 1 and "testPOI1" as buildingPOI
		Building testBuilding = new Building(1, testFloors, 0, POISourceImpl.getInstance().getPOIByID("1"));
		//POI in testBuilding with ID 3 auf floor1
		db.addPOI("POIInBuilding1", "", new MapPosition(49.012743, 8.415631, floor1), null, catList);
		//POI in testBuilding with ID 4 auf floor2
		db.addPOI("POIInBuilding2", "", new MapPosition(49.012743, 8.415631, floor2), null, catList);
		
	}
	
}
