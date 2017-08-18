/*
 * @(#)AthleteView.java        1.00	23/feb/2014
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

package com.whiterational.uisproma.spring.command;

import java.io.Serializable;

import com.whiterational.uisproma.business.entity.Athlete;

/**
 * La classe <code>AthleteView.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	23/feb/2014
 *
 */

public class AthleteView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7029986028880429537L;
	
	private Boolean selected = false;
	private Athlete athlete;
	
	public AthleteView() {};
	
	public AthleteView(Athlete athlete) {
		this.athlete = athlete;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Athlete getAthlete() {
		return athlete;
	};
	
}
