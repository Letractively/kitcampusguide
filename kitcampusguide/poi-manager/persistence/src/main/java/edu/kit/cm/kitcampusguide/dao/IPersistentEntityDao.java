package edu.kit.cm.kitcampusguide.dao;

import edu.kit.cm.kitcampusguide.model.AEntity;

/**
 * Default interface to access persistent entities through crud pattern.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public interface IPersistentEntityDao {

	AEntity findByUid(Integer uid);

	AEntity merge(AEntity persistentEntity);

	void refresh(AEntity persistentEntity);

	void remove(AEntity persistentEntity);

	void save(AEntity persistentEntity);
}