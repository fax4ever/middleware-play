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
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.entity.competition.Solution;
import com.whiterational.uisproma.business.service.UispCompetitionAttendanceService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name = "compattendance")
@ViewScoped
public class CompetitionAttendanceCommand implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5261730739138229961L;
  
  private static final String HOME_PAGE = "compattendancehome?faces-redirect=true";
  private static final String MSG_PREFIX = "compAttendanceHome";
  
  @Inject
  private UispCompetitionAttendanceService service;
  
  @Inject
  private Map<Long, SportsClub>  clubs;
  
  @Inject
  private Map<Long, Competition> competitions;
  
  private Map<Long, Attache> attaches = new LinkedHashMap<Long, Attache>();
  private Map<Long, Athlete> athletes = new LinkedHashMap<Long, Athlete>();
  private Map<Long, Solution> solutions = new LinkedHashMap<Long, Solution>();
  
  private CompetitionAttendance entity = new CompetitionAttendance();
  private SportsClub club;
  
  @Inject
  private transient FacesContext ctx;
  
  @Inject
  private transient ResourceBundle bundle;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionAttendanceCommand.class);
  
  public CompetitionAttendance getEntity() {
    return entity;
  }

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());
    if (ctx.isPostback())
      return "";
    
    if (entity.getId() == null) {
      club = clubs.values().iterator().next();
      entity.setCompetition(competitions.values().iterator().next());
      
      initSolutionMap();
      initAttachesAndAthletes();
      
      return "";
    }
    
    entity = service.read(entity.getId());
    if (entity.getAthlete() == null || entity.getCompetition() == null)
      return "";
    
    initSolutionMap();
    
    club = entity.getAthlete().getClub();
    if (club == null)
      return "";
    
    initAttachesAndAthletes(); 
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
  
  public List<SelectItem> getCompetitions() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    //items.add(new SelectItem(0, ""));
    
    for (Entry<Long, Competition> comp : competitions.entrySet()) {
      items.add(new SelectItem(comp.getKey(), comp.getValue().getName()));
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
  
  public List<SelectItem> getSolutions() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    //items.add(new SelectItem(0, ""));
    
    for (Entry<Long, Solution> sol : solutions.entrySet()) {
      items.add(new SelectItem(sol.getKey(), sol.getValue().getName()));
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

    initAttachesAndAthletes();
    
    entity.setAthlete(null);
    entity.setAttache(null);
  }

  private void initAttachesAndAthletes() {
    attaches.clear();
    for (Attache attache : club.getAttaches()) {
      attaches.put(attache.getId(), attache);
    }
    
    athletes.clear();
    for (Athlete athlete : club.getAthletes()) {
      athletes.put(athlete.getId(), athlete);
    }
  }
  
  public Long getCompetition() {
    return (entity.getCompetition() != null) ? entity.getCompetition().getId() : null;
  }

  public void setCompetition(Long id) {
    if (id == null || !competitions.containsKey(id))
      return;
    
    entity.setCompetition(competitions.get(id));
    
    initSolutionMap();
    
    entity.setSolution(null);
  }

  private void initSolutionMap() {
    solutions.clear();
    for (Solution sol : entity.getCompetition().getSolutions()) {
      solutions.put(sol.getId(), sol);
    }
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

  public Long getSolution() {
    return (entity.getSolution() != null) ? entity.getSolution().getId() : null;
  }

  public void setSolution(Long id) {
    if (id == null || !solutions.containsKey(id))
      return;
    
    entity.setSolution(solutions.get(id));
  }

}
