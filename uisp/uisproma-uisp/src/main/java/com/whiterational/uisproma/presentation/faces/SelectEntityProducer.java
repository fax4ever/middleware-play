package com.whiterational.uisproma.presentation.faces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.service.UispAttacheService;
import com.whiterational.uisproma.business.service.UispChampionshipService;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.business.service.UispCompetitionService;

@Named
@RequestScoped
public class SelectEntityProducer {

  @Inject
  private UispAttacheService         attacheService;

  @Inject
  private UispClubService            clubService;

  @Inject
  private UispCompetitionService     competitionService;

  @Inject
  private UispChampionshipService    championshipService;

  private Map<Long, Attache>     attaches     = null;

  private Map<Long, SportsClub>  clubs        = null;

  private Map<Long, Competition> competitions = null;
  
  private Map<Long, Championship> championships = null;

  public List<SelectItem> getAttaches() {
    if (attaches == null)
      attaches = attacheService.getChoises();

    List<SelectItem> items = new ArrayList<SelectItem>();
    items.add(new SelectItem(0, ""));

    for (Entry<Long, Attache> entry : attaches.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getFullName()));
    }

    return items;
  }

  public List<SelectItem> getClubs() {
    if (clubs == null)
      clubs = clubService.getChoises();

    List<SelectItem> items = new ArrayList<SelectItem>();
    items.add(new SelectItem(0, ""));

    for (Entry<Long, SportsClub> entry : clubs.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }

    return items;
  }

  public List<SelectItem> getCompetitions() {
    if (competitions == null)
      competitions = competitionService.getChoises();

    List<SelectItem> items = new ArrayList<SelectItem>();
    items.add(new SelectItem(0, ""));

    for (Entry<Long, Competition> entry : competitions.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }

    return items;
  }
  
  public List<SelectItem> getChampionships() {
    if (championships == null)
      championships = championshipService.getChoises();

    List<SelectItem> items = new ArrayList<SelectItem>();
    items.add(new SelectItem(0, ""));

    for (Entry<Long, Championship> entry : championships.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }

    return items;
  }
  
  public String getAttacheName(Long id) {
    if (attaches == null)
      attaches = attacheService.getChoises();
    
    return (attaches.containsKey(id)) ? attaches.get(id).getFullName() : null;
  }
  
  public String getClubName(Long id) {
    if (clubs == null)
      clubs = clubService.getChoises();
    
    return (clubs.containsKey(id)) ? clubs.get(id).getName() : null;
  }
  
  public String getCompetitionName(Long id) {
    if (competitions == null)
      competitions = competitionService.getChoises();
    
    return (competitions.containsKey(id)) ? competitions.get(id).getName() : null;
  }
  
  public String getChampionshipName(Long id) {
    if (championships == null)
      championships = championshipService.getChoises();
    
    return (championships.containsKey(id)) ? championships.get(id).getName() : null;
  }
  
  public Map<Long, Attache> getAttacheMap() {
    if (attaches == null)
      attaches = attacheService.getChoises();
    
    return attaches;
  }
  
  public Map<Long, SportsClub> getClubMap() {
    if (clubs == null)
      clubs = clubService.getChoises();
    
    return clubs;
  }
  
  public Map<Long, Competition> getCompetitionMap() {
    if (competitions == null)
      competitions = competitionService.getChoises();
    
    return competitions;
  }
  
  public Map<Long, Championship> getChampionshipMap() {
    if (championships == null)
      championships = championshipService.getChoises();
    
    return championships;
  }

}
