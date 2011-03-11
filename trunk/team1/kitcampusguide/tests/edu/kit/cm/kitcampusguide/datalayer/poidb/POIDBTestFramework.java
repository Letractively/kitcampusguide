package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import edu.kit.cm.kitcampusguide.datalayer.poidb.DefaultPOIDB;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDB;
import edu.kit.cm.kitcampusguide.datalayer.poidb.POIDBSearcher;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

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
	 *Adds two POIs, only difference is their name.
	 */
	private static void addPOIs() {
		Map map = new Map(1, "testMap", new MapSection(new WorldPosition(49.0179, 8.40232), new WorldPosition(49.0078, 8.42622)), "", 0, 1);
		DefaultPOIDB db = (DefaultPOIDB) DefaultPOIDB.getInstance();
		MapPosition pos = new MapPosition(49.012743, 8.415631, map);
		Collection<Category> catList= new ArrayList<Category>();
		
		db.addPOI("testPOI1", "", pos, null, catList);
		db.addPOI("testPOI2", "", pos, null, catList);
	}

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
		constructPOIDB();
		System.out.println(DefaultPOIDB.getInstance().getPOIByID("2").getName());
		
	}
	
}
