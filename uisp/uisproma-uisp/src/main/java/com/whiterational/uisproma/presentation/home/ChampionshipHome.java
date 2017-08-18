package com.whiterational.uisproma.presentation.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.service.UispChampionshipService;
import com.whiterational.uisproma.business.valueobject.ChampionshipValue;

@Home
public class ChampionshipHome implements Serializable {
 
  /**
   * 
   */
  private static final long serialVersionUID = -7993340957967969610L;

  @Inject
  private UispChampionshipService service;
  
  private List<Championship> championships = null;

  private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipHome.class);
  
  @PostConstruct
  private void init() {
    championships = service.findAll();
  }
  
  @Produces
  public List<Championship> getChampionships() {
    LOGGER.debug("getChampionships");
    return championships;
  }
  
  @Produces
  public List<ChampionshipValue> getValues() {
    ArrayList<ChampionshipValue> result = new ArrayList<ChampionshipValue>();
    
    LOGGER.debug("getValues");
    for (Championship champ : championships) {
      result.add(new ChampionshipValue(champ));
    }
    return result;
  }

}
