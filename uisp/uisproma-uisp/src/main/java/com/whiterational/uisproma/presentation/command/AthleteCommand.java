package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.service.UispAthleteService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name = "athlete")
@ViewScoped
public class AthleteCommand implements Serializable {

  /**
   * 
   */
  private static final long        serialVersionUID = 1793199748641855317L;

  @Inject
  private UispAthleteService           service;
  
  @Inject
  private Map<Long, SportsClub>    clubs;

  private Athlete                  entity           = new Athlete();

  @Inject
  private transient FacesContext   ctx;

  @Inject
  private transient ResourceBundle bundle;

  private static final Logger      LOGGER           = LoggerFactory.getLogger(AthleteCommand.class);

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());

    if (ctx.isPostback() || entity.getId() == null)
      return "";

    entity = service.read(entity.getId());
    return "";
  }

  public String save() {
    LOGGER.debug("save");
    
    SportsClub club = entity.getClub();
    if (club == null)
    	return "athletehome?faces-redirect=true";
    
    club.addAthlete(entity);

    if (ViewMode.CREATE.equals(getMode()))
      service.create(entity);
    else {
      service.update(entity);
    }

    return "athletehome?faces-redirect=true";
  }

  public String delete() {
    LOGGER.debug("delete");

    if (ViewMode.UPDATE.equals(getMode()))
      service.delete(entity);

    return "athletehome?faces-redirect=true";
  }

  public ViewMode getMode() {
    return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }

  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
  }

  public String getTitle() {
    return bundle.getString("athleteHome." + getMode().getText() + ".title");
  }

  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }

  public Athlete getEntity() {
    return entity;
  }

  public void setEntity(Athlete entity) {
    this.entity = entity;
  }
  
  public void alredyInUse(FacesContext context, UIComponent toValidate, Object value) {
    String uispcode = (String) value;
    
    if (service.exist(uispcode)) {
      ((UIInput) toValidate).setValid(false);
      FacesMessage message = new FacesMessage("Tessera già presente");
      context.addMessage(toValidate.getClientId(context), message);
    }
  }
  
  public Long getClub() {
    return (entity.getClub() != null && entity.getClub().getId() != null) ? entity.getClub().getId() : null;
  }

  public void setClub(Long id) {
    if (clubs.containsKey(id))
      entity.setClub(clubs.get(id));
  }

  public List<SelectItem> getClubs() {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for (Entry<Long, SportsClub> entry : clubs.entrySet()) {
      items.add(new SelectItem(entry.getKey(), entry.getValue().getName()));
    }

    return items;
  }

}
