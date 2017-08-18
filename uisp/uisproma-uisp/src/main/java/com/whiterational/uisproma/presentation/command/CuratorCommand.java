package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Curator;
import com.whiterational.uisproma.business.entity.Role;
import com.whiterational.uisproma.business.security.Md5Helper;
import com.whiterational.uisproma.business.service.UispCuratorService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name="curator")
@ViewScoped
public class CuratorCommand implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8750033640638572476L;
  
  @Inject
  private UispCuratorService service;
  
  private Curator entity = new Curator();
  private String confirmPassword;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipCommand.class);
  
  @Inject
  private transient FacesContext ctx;
  
  @Inject
  private transient ResourceBundle bundle;
  
  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());
    if (ctx.isPostback() || entity.getId() == null) {
      Role role = new Role();
      role.setUser(entity.getUser());
      role.setGroupName("CURATOR");
      entity.getUser().getRoles().add(role);
      return "";
    }
      
    entity = service.read(entity.getId());
    return "";
  }
  
  public String save() {
    if (!valid()) {
      ctx.addMessage(null, new FacesMessage("Le password non coincidono"));
      return "";
    }

    entity.getUser().setPassword(Md5Helper.hashPassword(entity.getUser().getPassword()));
    LOGGER.debug("save");
    
    if (ViewMode.CREATE.equals(this.getMode()))
      service.create(entity);
    else
      service.update(entity);
    
    return "curatorhome?faces-redirect=true";
  }
  
  public String delete() {
    LOGGER.debug("delete");
    
    if (ViewMode.UPDATE.equals(this.getMode()))
      service.delete(entity);
    
    return "curatorhome?faces-redirect=true";
  }
  
  public ViewMode getMode() {
    return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }
  
  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
  }
  
  public String getTitle() {
    return bundle.getString("curatorHome." + getMode().getText() + ".title");
  }
  
  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }

  public Curator getEntity() {
    return entity;
  }

  public void setEntity(Curator entity) {
    this.entity = entity;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
  
  public boolean valid() {
    return (entity.getUser().getPassword() != null && confirmPassword != null && entity.getUser().getPassword().equals(confirmPassword));
  }

}
