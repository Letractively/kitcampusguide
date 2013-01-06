package edu.kit.cm.kitcampusguide.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import edu.kit.cm.kitcampusguide.dao.PoiDao;
import edu.kit.cm.kitcampusguide.model.Entity;
import edu.kit.cm.kitcampusguide.model.POI;

@Repository
public class PoiDaoImpl extends PersistentEntityDaoImpl<POI> implements PoiDao {

    @Autowired
    public PoiDaoImpl(JpaTemplate jpaTemplate) {
        super(jpaTemplate);
    }

    public List<POI> findByNameWithPrefix(String prefix) {
        return convertResultListToPois(findByNameLike(prefix + "%"));
    }

    public List<POI> findByNameWithSuffix(String suffix) {
        return convertResultListToPois(findByNameLike("%" + suffix));
    }

    public List<POI> findByNameLike(String pattern) {
        return convertResultListToPois(findByNamedQueryAndParams("poi.findByNameLike", pattern));
    }

	public List<POI> findByParentId(Integer pid) {
		return convertResultListToPois(findByNamedQueryAndParams("poi.findByParentId", pid));
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
