package edu.kit.cm.kitcampusguide.dao;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POICategory;

/**
 * Additional interface for accessing poi categories.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface IPoiCategoryDao extends IPersistentEntityDao {

	List<POICategory> findByNameWithPrefix(String prefix);

	List<POICategory> findByNameWithSuffix(String suffix);

	List<POICategory> findByNameLike(String pattern);
}
