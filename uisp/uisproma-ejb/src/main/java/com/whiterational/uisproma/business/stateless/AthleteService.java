package com.whiterational.uisproma.business.stateless;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.filter.AthleteFilter;
import com.whiterational.uisproma.business.filter.AthletePage;
import com.whiterational.uisproma.business.remote.RemoteAthleteService;
import com.whiterational.uisproma.business.service.UispAthleteService;
import com.whiterational.uisproma.business.store.AthleteStore;

@Stateless
public class AthleteService implements UispAthleteService, RemoteAthleteService {

  @Inject
  private AthleteStore        store;

  private static final Logger LOGGER = LoggerFactory.getLogger(AthleteService.class);

  public void create(Athlete athlete) {
    store.create(athlete);
  }

  public Athlete read(Long id) {
    return store.findById(id);
  }

  public void refresh(Athlete athlete) {
    store.refresh(athlete);
  }

  public void update(Athlete athlete) {
    store.update(athlete);
  }

  public void delete(Athlete athlete) {
    store.remove(athlete);
  }

  @Produces
  public List<Athlete> findAll() {
    LOGGER.info("getAll");

    return store.getAll();
  }

  public Long count() {
    return store.count();
  }

  public boolean exist(String code) {
    return (store.findByCode(code) != null);
  }
  
  public AthletePage getPage(int start, int size, AthleteFilter filter) {
    List<Athlete> athletes = store.findByFilter(start, size, filter);
    Long total = store.countByFilter(filter);
    return new AthletePage(athletes, total);
  }

}
