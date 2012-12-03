package edu.kit.cm.kitcampusguide.dao;

import edu.kit.cm.kitcampusguide.dao.exception.PoiDaoException;
import edu.kit.cm.kitcampusguide.model.AbstractEntity;
import edu.kit.cm.kitcampusguide.model.Entity;

/**
 * Default interface to access persistent entities through crud pattern.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface PersistentEntityDao {

    Entity findByUid(Integer uid) throws PoiDaoException;

    Entity merge(AbstractEntity persistentEntity) throws PoiDaoException;

    void refresh(Entity persistentEntity) throws PoiDaoException;

    void remove(Entity persistentEntity) throws PoiDaoException;

    void save(AbstractEntity persistentEntity) throws PoiDaoException;
}