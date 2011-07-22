package edu.kit.cm.kitcampusguide.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTemplate;

import edu.kit.cm.kitcampusguide.dao.IPersistentEntityDao;
import edu.kit.cm.kitcampusguide.model.AEntity;

public abstract class PersistentEntityDaoImpl<E extends AEntity> implements IPersistentEntityDao {

	private Class<E> entityClass;

	private final JpaTemplate jpaTemplate;

	@SuppressWarnings("unchecked")
	public PersistentEntityDaoImpl(JpaTemplate jpaTemplate) {
		this.jpaTemplate = jpaTemplate;

		try {
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
		} catch (ClassCastException ex) {
			Logger.getLogger(getClass()).error(
					"There must be an configuration error, because Dao Entity class could not be resolved.");
			this.entityClass = null;
		}
	}

	public final AEntity findByUid(Integer uid) {
		return jpaTemplate.find(entityClass, uid);
	}

	public final AEntity merge(AEntity persistentAEntity) {
		return jpaTemplate.merge(persistentAEntity);
	}

	public final void refresh(AEntity persistentAEntity) {
		jpaTemplate.refresh(persistentAEntity);
	}

	public final void remove(AEntity persistentAEntity) {
		jpaTemplate.remove(persistentAEntity);
	}

	public final void save(AEntity persistentAEntity) {
		if (jpaTemplate.contains(persistentAEntity)) {
			jpaTemplate.merge(persistentAEntity);
		} else {
			jpaTemplate.persist(persistentAEntity);
		}
	}

	@SuppressWarnings("rawtypes")
	public final List findByNamedQuery(String queryName) {
		return jpaTemplate.findByNamedQuery(queryName);
	}

	@SuppressWarnings("rawtypes")
	public final List findByNamedQueryAndParams(String queryName, Object... params) {
		return jpaTemplate.findByNamedQuery(queryName, params);
	}

	@SuppressWarnings("rawtypes")
	public final AEntity findUniqueByNamedQuery(String queryName) {
		List results = findByNamedQuery(queryName);

		if (results.isEmpty()) {
			return null;
		} else {
			return (AEntity) results.get(0);
		}
	}

	@SuppressWarnings("rawtypes")
	public final AEntity findUniqueByNamedQueryAndParams(String queryName, Object... params) {
		List results = findByNamedQueryAndParams(queryName, params);

		if (results.isEmpty()) {
			return null;
		} else {
			return (AEntity) results.get(0);
		}
	}
}