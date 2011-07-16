package edu.kit.cm.kitcampusguide.dao.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;

@Repository
public class PoiDaoImpl extends PersistentEntityDaoImpl implements IPoiDao {

	@Autowired
	public PoiDaoImpl(JpaTemplate jpaTemplate) {
		super(jpaTemplate);
	}

	@SuppressWarnings("unchecked")
	public List<POI> findByCategory(String categoryName) {
		return findByNamedQueryAndParams("poi.findPoisByCategory", categoryName);
	}

	public List<POI> findByNameWithPrefix(String prefix) {
		return findByNameLike(prefix + "%");
	}

	public List<POI> findByNameWithSuffix(String suffix) {
		return findByNameLike("%" + suffix);
	}

	@SuppressWarnings("unchecked")
	public List<POI> findByNameLike(String pattern) {

		return findByNamedQueryAndParams("poi.findPoisByNameLike", pattern);
	}

}
