package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Athlete_;
import com.whiterational.uisproma.business.entity.Attache_;
import com.whiterational.uisproma.business.entity.Payment;
import com.whiterational.uisproma.business.entity.SportsClub_;
import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance_;
import com.whiterational.uisproma.business.entity.competition.CompetitionFee_;
import com.whiterational.uisproma.business.entity.competition.Competition_;
import com.whiterational.uisproma.business.entity.competition.Solution_;
import com.whiterational.uisproma.business.filter.CompetitionAttendanceFilter;
import com.whiterational.uisproma.business.store.CompetitionAttendanceStore;

public class JPACompetitionAttendanceStore extends JPAStore<Long, CompetitionAttendance> implements CompetitionAttendanceStore {

  /**
   * 
   */
  private static final long serialVersionUID = -5316232056976888615L;
  
  public List<CompetitionAttendance> findByFilter(int start, int size, CompetitionAttendanceFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<CompetitionAttendance> criteria = builder.createQuery(CompetitionAttendance.class);
    Root<CompetitionAttendance> attendance = criteria.from(CompetitionAttendance.class);
    CriteriaQuery<CompetitionAttendance> select = criteria.select(attendance);
    
    Predicate predicate = applyFilter(filter, builder, attendance);
    select.where(predicate);
    
    select.orderBy(builder.asc(attendance.get(CompetitionAttendance_.competition).get(Competition_.name)), builder.asc(attendance.get(CompetitionAttendance_.athlete).get(Athlete_.surname)), builder.asc(attendance.get(CompetitionAttendance_.athlete).get(Athlete_.name)), builder.asc(attendance.get(CompetitionAttendance_.athlete).get(Athlete_.uispCode)));
    TypedQuery<CompetitionAttendance> query = entityManager.createQuery(select);
    query.setFirstResult(start);
    query.setMaxResults(size);
    return query.getResultList();
  }
  
  public Long countByFilter(CompetitionAttendanceFilter filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<CompetitionAttendance> attendance = criteria.from(CompetitionAttendance.class);
    CriteriaQuery<Long> select = criteria.select(builder.count(attendance));

    Predicate predicate = applyFilter(filter, builder, attendance);
    select.where(predicate);
    TypedQuery<Long> query = entityManager.createQuery(select);

    return query.getSingleResult();
  }
  
  private Predicate applyFilter(CompetitionAttendanceFilter filter, CriteriaBuilder builder, Root<CompetitionAttendance> attendance) {
    Predicate predicate = builder.conjunction();

    if (filter.getUispCode() != null && !filter.getUispCode().isEmpty()) {
      Predicate uispCode = builder.like(attendance.get(CompetitionAttendance_.athlete).get(Athlete_.uispCode), "%" + filter.getUispCode() + "%");
      predicate = builder.and(predicate, uispCode);
    }
    
    if (filter.getSurname() != null && !filter.getSurname().isEmpty()) {
      Predicate surname = builder.like(attendance.get(CompetitionAttendance_.athlete).get(Athlete_.surname), "%" + filter.getSurname() + "%");
      predicate = builder.and(predicate, surname);
    }
    
    if (filter.getSolutionName() != null && !filter.getSolutionName().isEmpty()) {
      Predicate solution = builder.like(attendance.get(CompetitionAttendance_.solution).get(Solution_.name), "%" + filter.getSolutionName() + "%");
      predicate = builder.and(predicate, solution);
    }
    
    if (filter.getPaid() != null) {
      Path<Payment> path = attendance.get(CompetitionAttendance_.fee).get(CompetitionFee_.payment);
      Predicate paid = (filter.getPaid()) ? builder.isNotNull(path) : builder.isNull(path);
      predicate = builder.and(predicate, paid);
    }
    
    if (filter.getAttache() != null && filter.getAttache() > 0) {
      Predicate attache = builder.equal(attendance.get(CompetitionAttendance_.attache).get(Attache_.id), filter.getAttache());
      predicate = builder.and(predicate, attache);
    }
    
    if (filter.getClub() != null && filter.getClub() > 0) {
      Predicate club = builder.equal(attendance.get(CompetitionAttendance_.attache).get(Attache_.club).get(SportsClub_.id), filter.getClub());
      predicate = builder.and(predicate, club);
    }
    
    if (filter.getCompetition() != null && filter.getCompetition() > 0) {
      Predicate competition = builder.equal(attendance.get(CompetitionAttendance_.competition).get(Competition_.id), filter.getCompetition());
      predicate = builder.and(predicate, competition);
    }

    return predicate;
  }

}
