package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;
import edu.kit.cm.kitcampusguide.model.Point;
import edu.kit.cm.kitcampusguide.model.Route;
import edu.kit.cm.kitcampusguide.view.CampusGuide;

/**
 * This interface represents a facade of the map algorithms package and encapsulates all functionality provided by this
 * package.  
 * 
 * @author Tobias Zündorf
 *
 */
/*
 * This package is primary used to handle route and search requests.
 */
public interface MapAlgorithms {

	/**
	 * Searches through the entire POI database and compares each POI-name and POI-description
	 * with the specified String. If the comparison shows a significant correspondence the POI 
	 * is added to the returned collection. All POI in the returned collection are sorted 
	 * downwards by their correspondence to the specified String.
	 * 
	 * @param name the String to search for
	 * @return a collection containing all POI corresponding to the specified String
	 */
	public List<POI> getSuggestions(String name);
	
	// TODO
	public List<String> getSuggestionsNames(String name);
	
	/**
	 * Searches through the entire POI database and compares each POI-name with the specified 
	 * String. After that the POI with the highest correspondence is returned
	 * 
	 * @param name the String to search for
	 * @return the POI with the highest correspondence to the specified String
	 */
	public POI searchPOI(String name);

	/**
	 * Calculates and Returns the shortest route between the two specified Points within
	 * the map. 
	 * 
	 * @param from	the start point of the route
	 * @param to the end point of the route
	 * @return the shortest route between the two specified Points
	 */
	public Route calculateRoute(Point from, Point to);
	
	/**
	 * Generates a unique String, identifying the specified CampusGuide.
	 * 
	 * @param website the CampusGuide to encode
	 * @return  a unique String, identifying the specified CampusGuide
	 */
	public String calculateURL(CampusGuide website);
	
	/**
	 * Builds a CampusGuide instance out of the specified String.
	 * 
	 * @param url the String to Build the CampusGuide
	 * @return a CampusGuide instance build out of the specified String
	 */
	public CampusGuide calculateWebsite(String url);
	
}
