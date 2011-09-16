package edu.kit.cm.kitcampusguide.dao;

import edu.kit.cm.kitcampusguide.model.AEntity;
import edu.kit.cm.kitcampusguide.model.Entity;

/**
 * Default interface to access persistent entities through crud pattern.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface IPersistentEntityDao {

	Entity findByUid(Integer uid);

	Entity merge(AEntity persistentEntity);

	void refresh(Entity persistentEntity);

	void remove(Entity persistentEntity);

	void save(AEntity persistentEntity);
}