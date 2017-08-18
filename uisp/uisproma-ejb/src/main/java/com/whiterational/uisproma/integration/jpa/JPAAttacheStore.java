package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Attache_;
import com.whiterational.uisproma.business.entity.User_;
import com.whiterational.uisproma.business.store.AttacheStore;

public class JPAAttacheStore extends JPAStore<Long, Attache> implements AttacheStore {

	/**
   * 
   */
	private static final long serialVersionUID = 8216143337168895333L;

	@Override
	public Attache findByUsername(String username) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Attache> criteria = cb.createQuery(Attache.class);
		Root<Attache> attache = criteria.from(Attache.class);
		criteria.where(cb.equal(attache.get(Attache_.user).get(User_.username), username));
		TypedQuery<Attache> query = entityManager.createQuery(criteria);
		
		List<Attache> result = query.getResultList();
		
		return (result == null || result.size() == 0) ? null:
			result.get(0);
	}

}
