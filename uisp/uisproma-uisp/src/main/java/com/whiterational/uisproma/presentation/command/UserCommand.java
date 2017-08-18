package com.whiterational.uisproma.presentation.command;

import java.io.Serializable;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Role;
import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.security.Md5Helper;
import com.whiterational.uisproma.business.service.UispRoleService;
import com.whiterational.uisproma.business.service.UispUserService;
import com.whiterational.uisproma.presentation.presenter.LoginPresenter;
import com.whiterational.uisproma.presentation.view.ViewMode;

@ManagedBean(name = "user")
@ViewScoped
public class UserCommand implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2849604646448992479L;

  @Inject
  private UispUserService              service;

  @Inject
  private UispRoleService              role;

  private User                     entity = new User();
  private String                   confirmPassword;
  private String[]                 roles;

  @Inject
  private transient FacesContext   ctx;

  @Inject
  private transient ResourceBundle bundle;

  private static final Logger      LOGGER = LoggerFactory.getLogger(LoginPresenter.class);

  public String load() {
    LOGGER.debug("load postback: " + ctx.isPostback());
    if (ctx.isPostback())
      return "";

    if (entity.getUsername() == null) {
      roles = new String[0];
      return "";
    }

    entity = service.read(entity.getUsername());

    confirmPassword = entity.getPassword();
    roles = new String[entity.getRoles().size()];
    int i = 0;
    for (Role role : entity.getRoles()) {
      roles[i] = role.getGroupName();
      i++;
    }

    return "";
  }

  public String save() {
    if (!valid()) {
      ctx.addMessage(null, new FacesMessage("Le password non coincidono"));
      return "";
    }

    entity.setPassword(Md5Helper.hashPassword(entity.getPassword()));
    LOGGER.debug("save");

    Set<Role> formers = new HashSet<Role>();
    boolean find = false;

    for (Role role : entity.getRoles()) {
      find = false;

      for (String roleName : roles) {
        if (roleName.equals(role.getGroupName())) {
          find = true;
        }
      }

      if (!find)
        formers.add(role);
    }

    for (Role former : formers) {
      entity.getRoles().remove(former);
      role.delete(former);
    }

    for (String roleName : roles) {
      find = false;

      for (Role role : entity.getRoles()) {
        if (roleName.equals(role.getGroupName())) {
          find = true;
        }
      }

      if (!find) {
        Role role = new Role();
        role.setGroupName(roleName);
        role.setUser(entity);
        entity.getRoles().add(role);
      }
    }

    if (ViewMode.CREATE.equals(getMode()))
      service.create(entity);
    else
      service.update(entity);

    return "userhome?faces-redirect=true";
  }

  public String delete() {
    LOGGER.debug("delete");

    if (ViewMode.UPDATE.equals(getMode()))
      service.delete(entity);

    return "userhome?faces-redirect=true";
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public boolean valid() {
    return (entity.getPassword() != null && confirmPassword != null && entity.getPassword().equals(confirmPassword));
  }

  public ViewMode getMode() {
    return (entity.getUsername() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }

  public String getTitle() {
    return bundle.getString("userHome." + getMode().getText() + ".title");
  }

  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }

  public User getEntity() {
    return entity;
  }

  public void setEntity(User entity) {
    this.entity = entity;
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public void alredyInUse(FacesContext context, UIComponent toValidate, Object value) {
    String username = (String) value;
    
    if (service.alredyInUse(username)) {
      ((UIInput) toValidate).setValid(false);
      FacesMessage message = new FacesMessage("Username non disponibile");
      context.addMessage(toValidate.getClientId(context), message);
    }
  }

}
