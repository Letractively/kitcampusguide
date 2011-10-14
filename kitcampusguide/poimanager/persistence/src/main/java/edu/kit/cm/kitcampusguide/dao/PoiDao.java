package edu.kit.cm.kitcampusguide.dao;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.POI;

/**
 * Additional interface for accessing pois.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface PoiDao extends PersistentEntityDao {

    List<POI> findByNameWithPrefix(String prefix);

    List<POI> findByNameWithSuffix(String suffix);

    List<POI> findByNameLike(String pattern);
}
