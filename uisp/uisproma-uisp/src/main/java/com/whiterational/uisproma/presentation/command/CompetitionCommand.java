package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.whiterational.uisproma.business.entity.Sport;
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.service.UispCompetitionService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name="comp")
@ViewScoped
public class CompetitionCommand implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -51816680712969841L;

  @Inject
  private UispCompetitionService service;
  
  @Inject
  private Map<Long, Sport> sportChoises;
  
  @Inject
  private transient FacesContext ctx;
  
  @Inject
  private transient ResourceBundle bundle;
  
  private Competition entity = new Competition();
  
  private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionCommand.class);
  
  public Competition getEntity() {
    return entity;
  }

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());
    if (ctx.isPostback())
      return "";
    
    if (entity.getId() == null)
      return "";
    
    entity = service.read(entity.getId());
    return "";
  }
  
  private void persist() {
    String user = ctx.getExternalContext().getRemoteUser();
    LOGGER.debug("save by: " + user);
    
    if (ViewMode.CREATE.equals(this.getMode()))
      service.create(entity, user);
    else
      service.update(entity, user);
  }
  
  public String save() {
    persist();
    
    return "comphome?faces-redirect=true";
  }
  
  public String apply() {
    persist();
    
    return "";
  }
  
  public String delete() {
    LOGGER.debug("delete");
    
    if (ViewMode.UPDATE.equals(this.getMode()))
      service.delete(entity);
    
    return "comphome?faces-redirect=true";
  }
  
  public ViewMode getMode() {
    return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }
  
  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
  }
  
  public String getTitle() {
    return bundle.getString("compHome." + getMode().getText() + ".title");
  }
  
  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }
  
  public Long getSport() {
    return (entity.getSport() == null) ? null : entity.getSport().getId();
  }
  
  public void setSport(Long id) {
    if (sportChoises.containsKey(id))
      entity.setSport(sportChoises.get(id));
  }

  public List<SelectItem> getSports() {
    ArrayList<SelectItem> items = new ArrayList<SelectItem>();
    for (Entry<Long,Sport> entry : sportChoises.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }
    
    return items;
  }
  
  public void applyMax() {
    entity.applyMax();
  }
  
}
