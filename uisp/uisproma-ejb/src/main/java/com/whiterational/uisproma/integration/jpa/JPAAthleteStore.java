package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.Athlete_;
import com.whiterational.uisproma.business.entity.SportsClub_;
import com.whiterational.uisproma.business.filter.AthleteFilter;
import com.whiterational.uisproma.business.store.AthleteStore;

public class JPAAthleteStore extends JPAStore<Long, Athlete> implements AthleteStore {

  /**
   * 
   */
  private static final long serialVersionUID = -2933578877554811807L;

  @Override
  public Athlete findByCode(String code) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Athlete> criteria = cb.createQuery(Athlete.class);
    Root<Athlete> athlete = criteria.from(Athlete.class);
    criteria.where(cb.equal(athlete.get(Athlete_.uispCode), code));
    TypedQuery<Athlete> query = entityManager.createQuery(criteria);
    List<Athlete> resultList = query.getResultList();

    return (resultList.size() == 0) ? null : resultList.get(0);
  }

  @Override
  public List<Athlete> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Athlete> criteria = cb.createQuery(Athlete.class);
    Root<Athlete> athlete = criteria.from(Athlete.class);
    criteria.orderBy(cb.asc(athlete.get(Athlete_.surname)), cb.asc(athlete.get(Athlete_.name)));
    TypedQuery<Athlete> query = entityManager.createQuery(criteria);

    return query.getResultList();
  }

  public List<Athlete> findByFilter(int start, int size, AthleteFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Athlete> criteria = builder.createQuery(Athlete.class);
    Root<Athlete> athlete = criteria.from(Athlete.class);
    CriteriaQuery<Athlete> select = criteria.select(athlete);

    Predicate predicate = applyFilter(filter, builder, athlete);
    select.where(predicate);

    select.orderBy(builder.asc(athlete.get(Athlete_.surname)), builder.asc(athlete.get(Athlete_.name)), builder.asc(athlete.get(Athlete_.uispCode)));
    TypedQuery<Athlete> query = entityManager.createQuery(select);
    query.setFirstResult(start);
    query.setMaxResults(size);
    return query.getResultList();
  }

  public Long countByFilter(AthleteFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Athlete> athlete = criteria.from(Athlete.class);
    CriteriaQuery<Long> select = criteria.select(builder.count(athlete));

    Predicate predicate = applyFilter(filter, builder, athlete);
    select.where(predicate);
    TypedQuery<Long> query = entityManager.createQuery(select);

    return query.getSingleResult();
  }

  private Predicate applyFilter(AthleteFilter filter, CriteriaBuilder builder, Root<Athlete> athlete) {
    Predicate predicate = builder.conjunction();

    if (filter.getClub() > 0) {
      Predicate club = builder.equal(athlete.get(Athlete_.club).get(SportsClub_.id), filter.getClub());
      predicate = builder.and(predicate, club);
    }

    if (filter.getName() != null && !filter.getName().isEmpty()) {
      Predicate name = builder.like(athlete.get(Athlete_.name), "%" + filter.getName() + "%");
      predicate = builder.and(predicate, name);
    }
    
    if (filter.getSurname() != null && !filter.getSurname().isEmpty()) {
      Predicate surname = builder.like(athlete.get(Athlete_.surname), "%" + filter.getSurname() + "%");
      predicate = builder.and(predicate, surname);
    }
    
    if (filter.getUispCode() != null && !filter.getUispCode().isEmpty()) {
      Predicate uispCode = builder.like(athlete.get(Athlete_.uispCode), "%" + filter.getUispCode() + "%");
      predicate = builder.and(predicate, uispCode);
    }
    
    if (filter.getYear() > 0) {
      Predicate year = builder.equal(builder.function("year", Integer.class, athlete.get(Athlete_.birthDate)), filter.getYear());
      predicate = builder.and(predicate, year);
    }

    return predicate;
  }

}
