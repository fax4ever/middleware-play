package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.service.UispChampionshipAttendanceService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name="champattendance")
@ViewScoped
public class ChampionshipAttendanceCommand implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 6574405603305414243L;
  
  private static final String HOME_PAGE = "champattendancehome?faces-redirect=true";
  private static final String MSG_PREFIX = "champAttendanceHome";

  @Inject
  private UispChampionshipAttendanceService service;
  
  @Inject
  private Map<Long, SportsClub>  clubs;
  
  @Inject
  private Map<Long, Championship> champs;
  
  private Map<Long, Attache> attaches = new LinkedHashMap<Long, Attache>();
  private Map<Long, Athlete> athletes = new LinkedHashMap<Long, Athlete>();
  
  private ChampionshipAttendance entity = new ChampionshipAttendance();
  private SportsClub club;
  
  @Inject
  private transient FacesContext ctx;
  
  @Inject
  private transient ResourceBundle bundle;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipAttendanceCommand.class);
  
  public ChampionshipAttendance getEntity() {
    return entity;
  }

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());
    if (ctx.isPostback())
      return "";
    
    if (entity.getId() == null) {
      club = clubs.values().iterator().next();
      
      initMaps();
      return "";
    }
      
    entity = service.read(entity.getId());
    if (entity.getAthlete() == null)
      return "";
    
    club = entity.getAthlete().getClub();
    if (club == null)
      return "";
    
    initMaps();
    return "";
  }

  private void persist() {
    String user = ctx.getExternalContext().getRemoteUser();
    LOGGER.debug("save by: " + user);
    
    if (ViewMode.CREATE.equals(this.getMode()))
      service.create(entity);
    else
      service.update(entity);
  }
  
  public String save() {
    persist();
    
    return HOME_PAGE;
  }
  
  public String apply() {
    persist();
    
    return "";
  }

  public String delete() {
    LOGGER.debug("delete");

    if (ViewMode.UPDATE.equals(this.getMode()))
      service.delete(entity);

    return HOME_PAGE;
  }

  public ViewMode getMode() {
    return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }
  
  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
  }
  
  public String getTitle() {
    return bundle.getString(MSG_PREFIX + "." + getMode().getText() + ".title");
  }
  
  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }
  
  public List<SelectItem> getClubs() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    //items.add(new SelectItem(0, ""));
    
    for (Entry<Long, SportsClub> club : clubs.entrySet()) {
      items.add(new SelectItem(club.getKey(), club.getValue().getName()));
    }

    return items;
  }
  
  public List<SelectItem> getChamps() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    //items.add(new SelectItem(0, ""));
    
    for (Entry<Long, Championship> champ : champs.entrySet()) {
      items.add(new SelectItem(champ.getKey(), champ.getValue().getName()));
    }

    return items;
  }
  
  public List<SelectItem> getAttaches() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    //items.add(new SelectItem(0, ""));
    
    for (Entry<Long, Attache> att : attaches.entrySet()) {
      items.add(new SelectItem(att.getKey(), att.getValue().getFullName()));
    }

    return items;
  }
  
  public List<SelectItem> getAthletes() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    //items.add(new SelectItem(0, ""));
    
    for (Entry<Long, Athlete> ath : athletes.entrySet()) {
      items.add(new SelectItem(ath.getKey(), ath.getValue().getFullName()));
    }

    return items;
  }
  
  public Long getClub() {
    return (club != null) ? club.getId() : null;
  }

  public void setClub(Long id) {
    if (id == null || !clubs.containsKey(id))
      return;
      
    club = clubs.get(id);

    initMaps();
    
    entity.setAthlete(null);
    entity.setAttache(null);
  }

  private void initMaps() {
    attaches.clear();
    for (Attache attache : club.getAttaches()) {
      attaches.put(attache.getId(), attache);
    }
    
    athletes.clear();
    for (Athlete athlete : club.getAthletes()) {
      athletes.put(athlete.getId(), athlete);
    }
  }
  
  public Long getChamp() {
    return (entity.getChampionship() != null) ? entity.getChampionship().getId() : null;
  }

  public void setChamp(Long id) {
    if (id == null || !champs.containsKey(id))
      return;
    
    entity.setChampionship(champs.get(id));
  }
  
  public Long getAttache() {
    return (entity.getAttache() != null) ? entity.getAttache().getId() : null;
  }

  public void setAttache(Long id) {
    if (id == null || !attaches.containsKey(id))
      return;
    
    entity.setAttache(attaches.get(id));
  }

  public Long getAthlete() {
    return (entity.getAthlete() != null) ? entity.getAthlete().getId() : null;
  }

  public void setAthlete(Long id) {
    if (id == null || !athletes.containsKey(id))
      return;
    
    entity.setAthlete(athletes.get(id));
  }

}
