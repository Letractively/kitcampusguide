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

	@Override
	@SuppressWarnings("unchecked")
	public List<POI> findPoisByCategory(String categoryName) {
		return (List<POI>) findByNamedQueryAndParams("poi.findPoisByCategory", categoryName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<POI> findPoisByNameWithPrefix(String prefix) {
		return (List<POI>) findByNamedQueryAndParams("poi.findPoisByNameWithPrefix", prefix);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<POI> findPoisByNameWithSuffix(String suffix) {
		return (List<POI>) findByNamedQueryAndParams("poi.findPoisByNameWithSuffix", suffix);
	}

}
