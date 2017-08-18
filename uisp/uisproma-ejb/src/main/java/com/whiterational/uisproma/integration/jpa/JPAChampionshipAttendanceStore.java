package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.whiterational.uisproma.business.entity.Athlete_;
import com.whiterational.uisproma.business.entity.Attache_;
import com.whiterational.uisproma.business.entity.SportsClub_;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance_;
import com.whiterational.uisproma.business.entity.championship.ChampionshipFee;
import com.whiterational.uisproma.business.entity.championship.ChampionshipFee_;
import com.whiterational.uisproma.business.entity.championship.Championship_;
import com.whiterational.uisproma.business.entity.championship.Step;
import com.whiterational.uisproma.business.filter.ChampionshipAttendanceFilter;
import com.whiterational.uisproma.business.store.ChampionshipAttendanceStore;

public class JPAChampionshipAttendanceStore extends JPAStore<Long, ChampionshipAttendance> implements ChampionshipAttendanceStore {

  /**
   * 
   */
  private static final long serialVersionUID = -1906720471680298097L;
  
  public List<ChampionshipAttendance> findByFilter(int start, int size, ChampionshipAttendanceFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<ChampionshipAttendance> criteria = builder.createQuery(ChampionshipAttendance.class);
    Root<ChampionshipAttendance> attendance = criteria.from(ChampionshipAttendance.class);
    CriteriaQuery<ChampionshipAttendance> select = criteria.select(attendance);
    Subquery<Long> subquery = criteria.subquery(Long.class);
    
    Predicate predicate = applyFilter(filter, builder, attendance, subquery);
    select.where(predicate);
    
    select.orderBy(builder.asc(attendance.get(ChampionshipAttendance_.championship).get(Championship_.name)), builder.asc(attendance.get(ChampionshipAttendance_.athlete).get(Athlete_.surname)), builder.asc(attendance.get(ChampionshipAttendance_.athlete).get(Athlete_.name)), builder.asc(attendance.get(ChampionshipAttendance_.athlete).get(Athlete_.uispCode)));
    TypedQuery<ChampionshipAttendance> query = entityManager.createQuery(select);
    query.setFirstResult(start);
    query.setMaxResults(size);
    return query.getResultList();
  }
  
  public Long countByFilter(ChampionshipAttendanceFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<ChampionshipAttendance> attendance = criteria.from(ChampionshipAttendance.class);
    CriteriaQuery<Long> select = criteria.select(builder.count(attendance));
    Subquery<Long> subquery = criteria.subquery(Long.class);

    Predicate predicate = applyFilter(filter, builder, attendance, subquery);
    select.where(predicate);
    TypedQuery<Long> query = entityManager.createQuery(select);

    return query.getSingleResult();
  }
  
  private Predicate applyFilter(ChampionshipAttendanceFilter filter, CriteriaBuilder builder, Root<ChampionshipAttendance> attendance, Subquery<Long> subquery) {
    Predicate predicate = builder.conjunction();

    if (filter.getUispCode() != null && !filter.getUispCode().isEmpty()) {
      Predicate uispCode = builder.like(attendance.get(ChampionshipAttendance_.athlete).get(Athlete_.uispCode), "%" + filter.getUispCode() + "%");
      predicate = builder.and(predicate, uispCode);
    }
    
    if (filter.getSurname() != null && !filter.getSurname().isEmpty()) {
      Predicate surname = builder.like(attendance.get(ChampionshipAttendance_.athlete).get(Athlete_.surname), "%" + filter.getSurname() + "%");
      predicate = builder.and(predicate, surname);
    }
    
    if (filter.getStepsNumber() != null && filter.getStepsNumber() > 0) {
      Predicate stepsNumber = builder.equal(builder.size(attendance.get(ChampionshipAttendance_.championship).get(Championship_.steps)), filter.getStepsNumber());
      predicate = builder.and(predicate, stepsNumber);
    }
    
    if ((filter.getStepsPaid() != null && filter.getStepsPaid() > 0) || (filter.getPaidAll() != null)) {
      Root<ChampionshipAttendance> correlate = subquery.correlate(attendance);
      MapJoin<ChampionshipAttendance,Step,ChampionshipFee> fees = correlate.join(ChampionshipAttendance_.fees);
      Subquery<Long> select = subquery.select(builder.count(fees));
      select.where(builder.isNotNull(fees.get(ChampionshipFee_.payment)));
      
      if (filter.getStepsPaid() != null && filter.getStepsPaid() > 0) {
        Predicate stepsPaid = builder.ge(subquery, filter.getStepsPaid());
        predicate = builder.and(predicate, stepsPaid);
      }
      
      if (filter.getPaidAll() != null) {
        final Predicate paidAll;
        
        if (filter.getPaidAll())
          paidAll = builder.ge(subquery, builder.size(attendance.get(ChampionshipAttendance_.championship).get(Championship_.steps)));
        else
          paidAll = builder.notEqual(subquery, builder.size(attendance.get(ChampionshipAttendance_.championship).get(Championship_.steps)));
        
        predicate = builder.and(predicate, paidAll);
      }
    }
    
    if (filter.getAttache() != null && filter.getAttache() > 0) {
      Predicate attache = builder.equal(attendance.get(ChampionshipAttendance_.attache).get(Attache_.id), filter.getAttache());
      predicate = builder.and(predicate, attache);
    }
    
    if (filter.getClub() != null && filter.getClub() > 0) {
      Predicate club = builder.equal(attendance.get(ChampionshipAttendance_.attache).get(Attache_.club).get(SportsClub_.id), filter.getClub());
      predicate = builder.and(predicate, club);
    }
    
    if (filter.getChampionship() != null && filter.getChampionship() > 0) {
      Predicate championship = builder.equal(attendance.get(ChampionshipAttendance_.championship).get(Championship_.id), filter.getChampionship());
      predicate = builder.and(predicate, championship);
    }

    return predicate;
  }

}
