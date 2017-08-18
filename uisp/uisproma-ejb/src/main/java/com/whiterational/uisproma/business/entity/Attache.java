package com.whiterational.uisproma.business.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

@Entity
@Cache(isolation=CacheIsolationType.ISOLATED)
public class Attache extends Person {

	/**
   * 
   */
	private static final long serialVersionUID = 1172660619425136465L;

	@ManyToOne
	private SportsClub club;

	@OneToOne(cascade = CascadeType.ALL)
	private User user = new User();

	public SportsClub getClub() {
		return club;
	}

	public void setClub(SportsClub club) {
		this.club = club;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
