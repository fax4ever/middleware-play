/*
 * @(#)CompBookChange.java        1.00	23/feb/2014
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

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.Attache;

/**
 * La classe <code>CompBookChange.java</code> &egrave;
 * 
 * @author Fabio Massimo Ercoli fabiomassimo.ercoli@gmail.com
 * @version 1.00 23/feb/2014
 * 
 */

public class ChampBookChange {

	private Athlete athlete;
	private Attache attache;
	private String code;

	public Athlete getAthlete() {
		return athlete;
	}

	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	public Attache getAttache() {
		return attache;
	}

	public void setAttache(Attache attache) {
		this.attache = attache;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((athlete == null) ? 0 : athlete.hashCode());
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
		ChampBookChange other = (ChampBookChange) obj;
		if (athlete == null) {
			if (other.athlete != null)
				return false;
		} else if (!athlete.equals(other.athlete))
			return false;
		return true;
	}
	
}
