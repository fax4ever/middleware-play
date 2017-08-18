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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Role;
import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.security.Md5Helper;
import com.whiterational.uisproma.business.service.UispAttacheService;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name = "attache")
@ViewScoped
public class AttacheCommand implements Serializable {

  /**
   * 
   */
  private static final long        serialVersionUID = 2795199748641855317L;

  @Inject
  private UispAttacheService           service;

  @Inject
  private UispClubService             club;

  @Inject
  private Map<Long, SportsClub>    clubs;

  private Attache                  entity           = new Attache();
  private SportsClub               former           = null;
  private String                   confirmPassword;

  @Inject
  private transient FacesContext   ctx;

  @Inject
  private transient ResourceBundle bundle;

  private static final Logger      LOGGER           = LoggerFactory.getLogger(AttacheCommand.class);

  public AttacheCommand() {
    Role role = new Role();
    role.setUser(entity.getUser());
    role.setGroupName("ATTACHE");
    entity.getUser().getRoles().add(role);
  }

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());

    if (ctx.isPostback() || entity.getId() == null)
      return "";

    entity = service.read(entity.getId());
    former = entity.getClub();
    return "";
  }

  public String save() {
    if (!valid()) {
      ctx.addMessage(null, new FacesMessage("Le password non coincidono"));
      return "";
    }

    entity.getUser().setPassword(Md5Helper.hashPassword(entity.getUser().getPassword()));
    LOGGER.debug("save");

    if (ViewMode.CREATE.equals(getMode()))
      service.create(entity);
    else {
      
      updateClubs();
      service.update(entity);
    }

    return "attachehome";
  }

  private void updateClubs() {
    if (former != null && !former.equals(entity.getClub()) && former.getAttaches().contains(entity)) {
      former.getAttaches().remove(entity);
      club.update(former);
    }  

    if (entity.getClub() != null && !entity.getClub().getAttaches().contains(entity)) {
      entity.getClub().getAttaches().add(entity);
      club.update(entity.getClub());
    }
  }

  public String delete() {
    LOGGER.debug("delete");

    if (ViewMode.UPDATE.equals(getMode()))
      service.delete(entity);

    return "attachehome?faces-redirect=true";
  }

  public boolean valid() {
    return (entity.getUser().getPassword() != null && confirmPassword != null && entity.getUser().getPassword()
        .equals(confirmPassword));
  }

  public ViewMode getMode() {
    return (entity.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }

  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : entity.getId() + "";
  }

  public String getTitle() {
    return bundle.getString("attacheHome." + getMode().getText() + ".title");
  }

  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }

  public Attache getEntity() {
    return entity;
  }

  public void setEntity(Attache entity) {
    this.entity = entity;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
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
