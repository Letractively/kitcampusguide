package edu.kit.cm.kitcampusguide.dao;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;

/**
 * Additional interface for accessing poi's.
 *
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface IPoiDao extends IPersistentEntityDao {

	List<POI> findPoisByCategory(String categoryName);
	
	List<POI> findPoisByNameWithPrefix(String prefix);
	
	List<POI> findPoisByNameWithSuffix(String suffix);
}
