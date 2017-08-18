package com.whiterational.uisproma.business.stateless;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.remote.RemoteAttacheService;
import com.whiterational.uisproma.business.service.UispAttacheService;
import com.whiterational.uisproma.business.store.AttacheStore;

@Stateless
public class AttacheService implements UispAttacheService, RemoteAttacheService {
  
  @Inject
  private AttacheStore store;
  
  public void create(Attache sport) {
    store.create(sport);
  }
  
  public Attache read(Long id) {
    return store.findById(id);
  }
  
  public void update(Attache sport) {
    store.update(sport);
  }
  
  public void delete(Attache sport) {
    store.remove(sport);
  }
  
  public List<Attache> findAll() {
    return store.findAll();
  }
  
  public Long count() {
    return store.count();
  }
  
  @Produces
  public Map<Long, Attache> getChoises() {
    HashMap<Long, Attache> choises = new LinkedHashMap<Long, Attache>();
    for (Attache club : findAll()) {
      choises.put(club.getId(), club);
    }
    return choises;
  }
  
  public Attache findByUsername(String username) {
	  return store.findByUsername(username);
  }
  
}
