package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;

/**
 * This interface encapsulates the functionality of searching for points of
 * interest in the entire database.
 * 
 * @author Tobias Zündorf
 * 
 */
public interface QueryCalculator {

	/**
	 * Searches through the entire POI database and compares each POI-name and
	 * POI-description with the specified String. If the comparison shows a
	 * significant correspondence the POI is added to the returned collection.
	 * All POI in the returned collection are sorted downwards by their
	 * correspondence to the specified String.
	 * 
	 * @param name
	 *            the String to search for
	 * @return a collection containing all POI corresponding to the specified
	 *         String
	 */
	List<POI> getSuggestions(String name);

	/**
	 * Searches through the entire POI database and compares each POI-name and
	 * POI-description with the specified String. If the comparison shows a
	 * significant correspondence the POI is added to the returned collection.
	 * All POI in the returned collection are sorted downwards by their
	 * correspondence to the specified String.
	 * 
	 * @param name
	 *            the String to search for
	 * @return a collection containing all POI-name corresponding to the
	 *         specified String
	 */
	List<String> getSuggestionsNames(String name);

	/**
	 * Searches through the entire POI database and compares each POI-name with
	 * the specified String. After that the POI with the highest significant
	 * correspondence is returned. If no POI with significant correspondence
	 * exists null is returned.
	 * 
	 * @param name
	 *            the String to search for
	 * @return the POI with the highest correspondence to the specified String
	 */
	POI searchPOI(String name);

}
