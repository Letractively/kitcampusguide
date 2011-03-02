package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.util.List;

import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POIQuery;

/**
 * POIDB is used to retrieve POIs stored in a database. Implementations must
 * realize three different request types:
 * <ol>
 * <li>The user wants to retrieve POIs within a specific region on the map</li>
 * <li>The user wants to retrieve the POI identified by a given id</li>
 * <li>The user wants to list all POIs matching a given search term</li>
 * </ol>
 * All methods must be implemented in a thread-safe manner.
 * 
 * @author Stefan
 * @version 1.0
 * @see POIQuery
 * @see POI
 */
public interface POIDB {
	
	/**
	 * Returns all POIs matching a given query.
	 * 
	 * @param query
	 *            the query which is used for filtering
	 * @return a list containing all POIs matching the query
	 * @throws NullPointerException
	 *             if <code>query</code> is <code>null</code>
	 */
	public List<POI> getPOIsByQuery(POIQuery query);

	/**
	 * Retrieves a POI matching a given identifier string.
	 * 
	 * @param id
	 *            a String.
	 * @return a <code>POI</code>-object or <code>null</code> if no POI with the
	 *         given id was found.
	 * @throws NullPointerException
	 *             if <code>id</code> is <code>null</code>
	 */
	public POI getPOIByID(String id);

	/**
	 * Returns all POIs matching a given search term. It should be possible to
	 * search for the stored POI names.
	 * 
	 * @param text
	 *            an arbitrary search string. The implementation might need to
	 *            escape the string correctly.
	 * @return a list with POIs matching the search term. The list's POIs are
	 *         ordered by their relevance, if no match was found an empty list
	 *         is returned.
	 * @throws NullPointerException
	 *             if text is <code>null</code>
	 */
	public List<POI> getPOIsBySearch(String text);
	
	// TODO
	public POI getBuildingPOI(int buildingID);
}