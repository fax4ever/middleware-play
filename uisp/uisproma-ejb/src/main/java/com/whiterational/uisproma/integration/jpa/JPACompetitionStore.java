package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Sport_;
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.entity.competition.Competition_;
import com.whiterational.uisproma.business.store.CompetitionStore;

public class JPACompetitionStore extends JPAStore<Long, Competition> implements CompetitionStore {

  /**
   * 
   */
  private static final long serialVersionUID = 1040328719807933888L;
  
  @Override
  public List<Competition> findBySportId(Long sportId) {
	  CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	  CriteriaQuery<Competition> criteria = builder.createQuery(Competition.class);
	  Root<Competition> competion = criteria.from(Competition.class);
	  CriteriaQuery<Competition> select = criteria.select(competion);
	  
	  Predicate equal = builder.equal(competion.get(Competition_.sport).get(Sport_.id), sportId);
	  select.where(equal);
	  TypedQuery<Competition> query = entityManager.createQuery(select);
	  
	  return query.getResultList();
  }

}
