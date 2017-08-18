package com.whiterational.uisproma.presentation.view;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Attache;

@View
public class AttacheView implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 5884046599527358482L;
  
  private Attache attache = new Attache();
  private String confirmPassword;
  
  @Inject
  private transient ResourceBundle bundle;

  public Attache getAttache() {
    return attache;
  }

  public void setAttache(Attache attache) {
    this.attache = attache;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
  
  public boolean valid() {
    return (attache.getUser().getPassword() != null && confirmPassword != null && attache.getUser().getPassword().equals(confirmPassword));
  }
  
  public ViewMode getMode() {
    return (attache.getId() == null) ? ViewMode.CREATE : ViewMode.UPDATE;
  }
  
  public String getCode() {
    return (ViewMode.CREATE.equals(getMode())) ? bundle.getString("new") : attache.getId() + "";
  }
  
  public String getTitle() {
    return bundle.getString("attacheHome." + getMode().getText() + ".title");
  }
  
  public String getActionName() {
    return bundle.getString("action." + getMode().getText());
  }

}
