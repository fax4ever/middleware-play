package com.whiterational.uisproma.presentation.home;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.service.UispClubService;

@Home
public class ClubHome implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1509641710515758116L;
  
  @Inject
  private UispClubService service;
  
  private List<SportsClub> clubs;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ClubHome.class);
  
  @PostConstruct
  private void init() {
    clubs = service.findAll();
  }

  @Produces
  public List<SportsClub> getClubs() {
    LOGGER.debug("getClubs");
    return clubs;
  }

}
