package edu.kit.cm.kitcampusguide.dao;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;

/**
 * Additional interface for accessing poi's.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface IPoiDao extends IPersistentEntityDao {

	List<POI> findByNameWithPrefix(String prefix);

	List<POI> findByNameWithSuffix(String suffix);

	List<POI> findByNameLike(String pattern);
}
