package com.whiterational.uisproma.presentation.view;

import java.io.Serializable;

import com.whiterational.uisproma.business.entity.User;

@View
public class UserView implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = -9016958537424848425L;

  private User user = new User();

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
