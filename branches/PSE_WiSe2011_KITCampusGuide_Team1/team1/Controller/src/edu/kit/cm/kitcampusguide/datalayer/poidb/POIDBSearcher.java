package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.util.List;

/**
 * Realizes a search algorithm which allows to search for POIs in a
 * {@link DefaultPOIDB}. The search should consider only the POIs names.
 * 
 * @author Stefan
 * @version 1.0
 */
public interface POIDBSearcher {

	/**
	 * Searches for POIs in the database and returns their IDs.<br>
	 * If an internal exception occurs, the exception should be logged and an
	 * empty list is returned.
	 * 
	 * @param text
	 *            the original String entered by the user.
	 * @param dbURL
	 *            the db's URL used for connection. An appropriate driver needs
	 *            to be setup before calling this method.
	 * @return the IDs of all POIs matching <code>text</code>. The list is
	 *         sorted by relevance, the first entry is considered to be most
	 *         important. If no entry could be found, an empty list is returned.
	 * @throws NullPointerException
	 *             if a parameter is <code>null</code>
	 */
	public List<Integer> getIDs(String text, String dbURL);
}
