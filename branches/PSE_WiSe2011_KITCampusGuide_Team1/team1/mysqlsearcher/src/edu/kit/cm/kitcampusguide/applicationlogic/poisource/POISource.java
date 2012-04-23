package edu.kit.cm.kitcampusguide.applicationlogic.poisource;

import java.util.Collection;
import java.util.List;

import edu.kit.cm.kitcampusguide.standardtypes.Building;
import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;

/**
 * Declarates methods to retrieve POIs matching specific request types. 
 * @author Stefan
 *
 */
public interface POISource {

	/**
	 * Returns all POIs within a given {@link MapSection}, which belong to a
	 * given {@link Map} and a given {@link Category}. If one parameter is <code>null</code> it will be
	 * ignored.
	 * 
	 * @param section only POIs within this section will be returned.
	 * @param map only POIs on this map will be returned.
	 * @param categories only POIs belonging to at least on of this categories will be returned.
	 * @return a collection of POIs matching the given request parameters.
	 */
	public Collection<POI> getPOIsBySection(MapSection section, Map map, Collection<Category> categories);
	
	/**
	 * Returns all POIs matching a given search term. The POIs are ordered by relevance, the first entry is
	 * considered to be most important.
	 * @param searchTerm the text used for search
	 * @return an ordered List of POIs. If no result were found, the list is empty.
	 * @throws NullPointerException if <code>searchTerm</code> is <code>null</code>
	 */
	public List<POI> getPOIsBySearch(String searchTerm);
	
	/**
	 * Returns the {@link POI} matching a given ID.
	 * @param id an ID.
	 * @return a POI or <code>null</code>, if no appropriate POI was found
	 * @throws NullPointerException if id is <code>null</code>
	 */
	public POI getPOIByID(String id);
	
	/**
	 * Returns all POIs in a building. Additionally, a category filter will be applied.
	 * @param building a {@link Building}
	 * @param categories only POIs belonging to at least one of this categories will be returned
	 * @return a collection of POIs.
	 */
	public Collection<POI> getPOIsByBuilding(Building building, Collection<Category> categories);
}
