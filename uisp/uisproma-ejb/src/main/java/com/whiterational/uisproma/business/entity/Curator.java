package com.whiterational.uisproma.business.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Curator extends Person {
	
	/**
   * 
   */
  private static final long serialVersionUID = 6417331225470612857L;
  
  @OneToOne(cascade = CascadeType.ALL)
	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
