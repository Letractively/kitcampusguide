package edu.kit.cm.kitcampusguide.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import edu.kit.cm.kitcampusguide.dao.IPoiCategoryDao;
import edu.kit.cm.kitcampusguide.model.POICategory;

@Repository
public class PoiCategoryDaoImpl extends PersistentEntityDaoImpl<POICategory> implements IPoiCategoryDao {

	@Autowired
	public PoiCategoryDaoImpl(JpaTemplate jpaTemplate) {
		super(jpaTemplate);
	}

	public List<POICategory> findByNameWithPrefix(String prefix) {
		return findByNameLike(prefix + "%");
	}

	public List<POICategory> findByNameWithSuffix(String suffix) {
		return findByNameLike("%" + suffix);
	}

	public List<POICategory> findByNameLike(String pattern) {
		List<POICategory> categories = new ArrayList<POICategory>();

		for (Object poiCategory : findByNamedQueryAndParams("poiCategory.findByNameLike", pattern)) {

			if (poiCategory instanceof POICategory) {
				categories.add((POICategory) poiCategory);
			}
		}

		return categories;
	}

}
