package edu.kit.cm.kitcampusguide.data;

import java.util.ArrayList;

/**
 * 
 * 
 * 
 * @author Michael Hauber (michael.hauber2{at}student.kit.edu)
 *
 */
public interface POILoader {

	/**
	 * 
	 * @param id
	 * @return
	 */
	POI getPOI(Integer id);	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<POI> getPOIsByName(String name);
	
	/**
	 * 
	 * @return
	 */
	List<POI> getAllPOIs();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	POICategory getPOICategory(Integer id);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<POICategory> getPOICategoryByName(String name);
	
	/**
	 * 
	 * @return
	 */
	List<POICategory> getAllPOICategory();
	
}
