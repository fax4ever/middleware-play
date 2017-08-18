package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Event;
import com.whiterational.uisproma.business.entity.Sport_;
import com.whiterational.uisproma.business.entity.competition.Competition_;
import com.whiterational.uisproma.business.store.EventStore;

public class JPAEventStore extends JPAStore<Long, Event> implements EventStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3712016206733427336L;

	@Override
	public List<Event> findBySportId(Long sportId) {
		  CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		  CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
		  Root<Event> event = criteria.from(Event.class);
		  CriteriaQuery<Event> select = criteria.select(event);
		  
		  Predicate equal = builder.equal(event.get(Competition_.sport).get(Sport_.id), sportId);
		  select.where(equal);
		  TypedQuery<Event> query = entityManager.createQuery(select);
		  
		  return query.getResultList();
	  }
	

}
