package com.whiterational.uisproma.presentation.presenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.whiterational.uisproma.business.service.UispAthleteService;
import com.whiterational.uisproma.business.service.UispAttacheService;
import com.whiterational.uisproma.business.service.UispChampionshipAttendanceService;
import com.whiterational.uisproma.business.service.UispChampionshipService;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.business.service.UispCompetitionAttendanceService;
import com.whiterational.uisproma.business.service.UispCompetitionService;
import com.whiterational.uisproma.business.service.UispCuratorService;
import com.whiterational.uisproma.business.service.UispSportService;
import com.whiterational.uisproma.business.service.UispUserService;

@Presenter
public class HomePresenter implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -3001304511691895085L;

  @Inject
  private UispUserService user;
  
  @Inject
  private UispCuratorService curator;
  
  @Inject
  private UispAttacheService attache;
  
  @Inject
  private UispAthleteService athlete;
  
  @Inject
  private UispSportService sport;
  
  @Inject
  private UispClubService club;
  
  @Inject
  private UispChampionshipService champ;
  
  @Inject
  private UispCompetitionService comp;
  
  @Inject
  private UispChampionshipAttendanceService att;
  
  @Inject
  private UispCompetitionAttendanceService coAtt;
  
  private Map<String, Long> cache = new HashMap<String, Long>();
  
  @PostConstruct
  private void init() {
    cache.put("user", user.count());
    cache.put("curator", curator.count());
    cache.put("attache", attache.count());
    cache.put("athlete", athlete.count());
    cache.put("sport", sport.count());
    cache.put("club", club.count());
    cache.put("champ", champ.count());
    cache.put("comp", comp.count());
    cache.put("att", att.count());
    cache.put("coAtt", coAtt.count());
  }
  
  public Long getTimes() {
    return 0l;
  }

  public Long getUser() {
    return cache.get("user");
  }
  
  public Long getCurator() {
    return cache.get("curator");
  }
  
  public Long getAttache() {
    return cache.get("attache");
  }
  
  public Long getAthlete() {
    return cache.get("athlete");
  }

  public Long getSport() {
    return cache.get("sport");
  }
  
  public Long getClub() {
    return cache.get("club");
  }

  public Long getChamp() {
    return cache.get("champ");
  }
  
  public Long getComp() {
    return cache.get("comp");
  }

  public Long getAtt() {
    return cache.get("att");
  }
  
  public Long getCoAtt() {
    return cache.get("coAtt");
  }
  
}
