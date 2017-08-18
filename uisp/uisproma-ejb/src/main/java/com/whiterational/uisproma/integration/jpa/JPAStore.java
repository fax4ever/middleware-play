package com.whiterational.uisproma.integration.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.whiterational.uisproma.business.store.Store;

public abstract class JPAStore<K, E> implements Store<K, E>, Serializable {

	private static final long	serialVersionUID	= -4942511887424157244L;

	protected Class<E>			entityClass;

	@PersistenceContext
	protected EntityManager	entityManager;

	@SuppressWarnings("unchecked")
	public JPAStore() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public void create(E entity) {
		entityManager.persist(entity);
	}
	
	public void update(E entity) {
		entityManager.merge(entity);
	}
	
	public void refresh(E entity) {
		entityManager.refresh(entity);
	}

	public void remove(E entity) {
		E merge = entityManager.merge(entity);
		entityManager.remove(merge);
	}

	public E findById(K id) {
		return entityManager.find(entityClass, id);
	}
	
	public List<E> findAll() {
		@SuppressWarnings("unchecked")
		List<E> resultList = (List<E>) entityManager.createQuery("Select f from " + entityClass.getSimpleName() + " f").getResultList();
		return resultList;
	}
	
	public Long count() {
	  return (Long) entityManager.createQuery("Select COUNT(f) from " + entityClass.getSimpleName() + " f").getSingleResult();
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

}
