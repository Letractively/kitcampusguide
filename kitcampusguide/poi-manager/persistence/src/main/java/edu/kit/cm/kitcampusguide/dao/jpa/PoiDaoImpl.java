package edu.kit.cm.kitcampusguide.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import edu.kit.cm.kitcampusguide.dao.IPoiDao;
import edu.kit.cm.kitcampusguide.model.POI;

@Repository
public class PoiDaoImpl extends PersistentEntityDaoImpl<POI> implements IPoiDao {

	@Autowired
	public PoiDaoImpl(JpaTemplate jpaTemplate) {
		super(jpaTemplate);
	}

	@Override
	public List<POI> findByNameWithPrefix(String prefix) {
		return findByNameLike(prefix + "%");
	}

	@Override
	public List<POI> findByNameWithSuffix(String suffix) {
		return findByNameLike("%" + suffix);
	}

	@Override
	public List<POI> findByNameLike(String pattern) {
		return convertResultListToPois(findByNamedQueryAndParams("poi.findByNameLike", pattern));
	}

	@SuppressWarnings("rawtypes")
	private List<POI> convertResultListToPois(List result) {
		List<POI> pois = new ArrayList<POI>();

		for (Object poi : result) {

			if (poi instanceof POI) {
				pois.add((POI) poi);
			}
		}

		return pois;
	}

}
