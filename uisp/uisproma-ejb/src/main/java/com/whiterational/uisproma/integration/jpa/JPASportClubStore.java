package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.entity.SportsClub_;
import com.whiterational.uisproma.business.filter.ClubFilter;
import com.whiterational.uisproma.business.store.SportClubStore;

public class JPASportClubStore extends JPAStore<Long, SportsClub> implements SportClubStore {

  /**
   * 
   */
  private static final long serialVersionUID = -843678990132386613L;

  @Override
  public SportsClub findByCode(String code) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<SportsClub> criteria = cb.createQuery(SportsClub.class);
    Root<SportsClub> club = criteria.from(SportsClub.class);
    criteria.where(cb.equal(club.get(SportsClub_.freshman), code));
    TypedQuery<SportsClub> query = entityManager.createQuery(criteria);
    List<SportsClub> resultList = query.getResultList();
    
    return (resultList.size() == 0) ? null : resultList.get(0);
  }

  @Override
  public List<SportsClub> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<SportsClub> criteria = cb.createQuery(SportsClub.class);
    Root<SportsClub> club = criteria.from(SportsClub.class);
    criteria.orderBy(cb.asc(club.get(SportsClub_.name)));
    TypedQuery<SportsClub> query = entityManager.createQuery(criteria);
    
    return query.getResultList();
  }
  
  public List<SportsClub> findByFilter(int start, int size, ClubFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<SportsClub> criteria = builder.createQuery(SportsClub.class);
    Root<SportsClub> athlete = criteria.from(SportsClub.class);
    CriteriaQuery<SportsClub> select = criteria.select(athlete);

    Predicate predicate = applyFilter(filter, builder, athlete);
    select.where(predicate);

    select.orderBy(builder.asc(athlete.get(SportsClub_.name)));
    TypedQuery<SportsClub> query = entityManager.createQuery(select);
    query.setFirstResult(start);
    query.setMaxResults(size);
    return query.getResultList();
  }

  public Long countByFilter(ClubFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<SportsClub> athlete = criteria.from(SportsClub.class);
    CriteriaQuery<Long> select = criteria.select(builder.count(athlete));

    Predicate predicate = applyFilter(filter, builder, athlete);
    select.where(predicate);
    TypedQuery<Long> query = entityManager.createQuery(select);

    return query.getSingleResult();
  }

  private Predicate applyFilter(ClubFilter filter, CriteriaBuilder builder, Root<SportsClub> athlete) {
    Predicate predicate = builder.conjunction();

    if (filter.getName() != null && !filter.getName().isEmpty()) {
      Predicate name = builder.like(athlete.get(SportsClub_.name), "%" + filter.getName() + "%");
      predicate = builder.and(predicate, name);
    }

    return predicate;
  }

}
