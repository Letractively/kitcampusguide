package edu.kit.cm.kitcampusguide.data;

import edu.kit.cm.kitcampusguide.model.*;
import java.util.List;

/**
 * Interface of a POILoader.
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 */
public interface POILoader {

	/**
	 * Returns the POI with the given Integer id as his id,
	 * returns null if none POI was found.
	 * 
	 * @param id id of the POI to search for.
	 * @return the found POI or null if none POI was found.
	 */
	POI getPOI(Integer id);	
	
	/**
	 * Returns the POIs with the given name in his name.
	 * returns null if none POI was found.
	 * 
	 * @param name name of the POIs to search for.
	 * @return the found POIs or null if none POI was found.
	 */
	List<POI> getPOIsByName(String name);
	
	/**
	 * Returns all POIs of the database in a List of POIs.
	 * returns null if none POI was found.
	 * 
	 * @return all POIs of the database in a List of POIs or
	 * 		   null if none POI was found.
	 */
	List<POI> getAllPOIs();
	
	/**
	 * Returns the POICategory with added POIs with the given Integer id,
	 * as his id. Returns null if none POICategory was found with that id.
	 * 
	 * @param id id of the POICategory to search for.
	 * @return POICategory with added POIs with the given Integer id or null
	 *         if none POICategory was found with that id.
	 */
	POICategory getPOICategory(Integer id);
	
	/**
	 * Returns the POICategorys with added POIS with the given String name
	 * in his name. Returns null if none POICategory was found with that name.
	 * 
	 * @param name name of the POICategory to search for.
	 * @return POICategorys with added POIs with the given String name or null
	 *         if non POICategory was found with that name.
	 */
	List<POICategory> getPOICategoryByName(String name);
	
	/**
	 * Returns all POICategorys of the database in a List of POICategorys.
	 * Returns null if none POICategory was found.
	 * 
	 * @return all POICategorys of the database in a List of POICategorys
	 * 		   or null if none POICategory was found.
	 */
	List<POICategory> getAllPOICategory();
	
}
