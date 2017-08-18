package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name="club")
@ViewScoped
public class ClubCommand implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1138247064419993423L;
  
  @Inject
  private UispClubService service;
  
  private SportsClub entity = new SportsClub();
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipCommand.class);
  
  @Inject
  private transient FacesContext ctx;
  
  @Inject
  private transient ResourceBundle bundle;
  
  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());
    if (ctx.isPostback())
      return "";
    
    if (entity.getId() == null)
      return "";
    
    entity = service.read(entity.getId());
    return "";
  }
  
  public String save() {
    LOGGER.debug("save");
    
    if (ViewMode.CREATE.equals(this.getMode()))
      service.create(entity);
    else
      service.update(entity);
    
    return "clubhome?faces-redirect=true";
  }
  
  public String delete() {
    LOGGER.debug("delete");
    
    if (ViewMode.UPDATE.equals(this.getMode()))
      service.delete(entity);
    
    return "clubhome?faces-redirect=true";
  }
  
  public ViewMode getMode() {
    return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }
  
  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
  }
  
  public String getTitle() {
    return bundle.getString("clubHome." + getMode().getText() + ".title");
  }
  
  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }

  public SportsClub getEntity() {
    return entity;
  }

  public void setEntity(SportsClub entity) {
    this.entity = entity;
  }
  
  public void alredyInUse(FacesContext context, UIComponent toValidate, Object value) {
    String uispcode = (String) value;
    
    if (service.exist(uispcode)) {
      ((UIInput) toValidate).setValid(false);
      FacesMessage message = new FacesMessage("Codice UISP già presente");
      context.addMessage(toValidate.getClientId(context), message);
    }
  }
  
}
