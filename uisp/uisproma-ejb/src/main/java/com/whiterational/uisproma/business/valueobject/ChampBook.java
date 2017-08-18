/*
 * @(#)ChampBook.java        1.00	23/feb/2014
 *
 * Copyright (c) 2007-2013 Paybay Networks srl,
 * XX Settembre Road, Rome, Italy.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Paybay 
 * Networks srl, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Paybay Networks.
 */

package com.whiterational.uisproma.business.valueobject;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.Attache;

/**
 * La classe <code>ChampBook.java</code> &egrave;
 * 
 * @author Fabio Massimo Ercoli fabiomassimo.ercoli@gmail.com
 * @version 1.00 23/feb/2014
 * 
 */

public class ChampBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7816208090570224607L;

	private String code;
	private Attache attache;
	private Set<Athlete> athletes = new LinkedHashSet<Athlete>();
	private Integer level = 0;
	private Set<ChampBookChange> changes = new LinkedHashSet<ChampBookChange>();

	public ChampBook() {
		Date date = new Date();
		long time = date.getTime();
		double random = Math.random();
		
		this.code = time + ":" + random;
	}
	
	public ChampBook(Attache attache, Integer level) {
		this();
		this.attache = attache;
		this.athletes = new LinkedHashSet<Athlete>(attache.getClub().getAthletes());
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Athlete> getAthletes() {
		return athletes;
	}

	public void setAthletes(Set<Athlete> athletes) {
		this.athletes = athletes;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Attache getAttache() {
		return attache;
	}

	public void setAttache(Attache attache) {
		this.attache = attache;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChampBook other = (ChampBook) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public void clear() {
		this.changes.clear();
	}

	public void addChange(Athlete athlete) {
		ChampBookChange change = new ChampBookChange();
		change.setAthlete(athlete);
		change.setAttache(attache);
		change.setCode(code);
		this.changes.add(change);
	}

	public void addChange(Athlete athlete, Attache attache, String code) {
		ChampBookChange change = new ChampBookChange();
		change.setAthlete(athlete);
		change.setAttache(attache);
		change.setCode(code);
		this.changes.add(change);
	}

	public Set<ChampBookChange> getChanges() {
		return changes;
	}

	public void setChanges(Set<ChampBookChange> changes) {
		this.changes = changes;
	}

}
