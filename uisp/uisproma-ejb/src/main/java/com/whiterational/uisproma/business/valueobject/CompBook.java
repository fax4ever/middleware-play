/*
 * @(#)CompBook.java        1.00	19/feb/2014
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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;

/**
 * La classe <code>CompBook.java</code> &egrave;
 * 
 * @author Fabio Massimo Ercoli fabiomassimo.ercoli@gmail.com
 * @version 1.00 19/feb/2014
 * 
 */

public class CompBook implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5898530760664591405L;

	private static final Logger LOG = LoggerFactory.getLogger(CompBook.class);

	private Set<CompetitionAttendance> notBooked = new LinkedHashSet<CompetitionAttendance>();
	private Set<CompetitionAttendance> notPaid = new LinkedHashSet<CompetitionAttendance>();
	private Set<CompetitionAttendance> notPaidOld = new LinkedHashSet<CompetitionAttendance>();

	public Set<CompetitionAttendance> getNotBooked() {
		return notBooked;
	}

	public void setNotBooked(Set<CompetitionAttendance> notBooked) {
		this.notBooked = notBooked;
	}

	public Set<CompetitionAttendance> getNotPaid() {
		return notPaid;
	}

	public void setNotPaid(Set<CompetitionAttendance> notPaid) {
		this.notPaid = notPaid;
	}

	public Set<CompetitionAttendance> getNotPaidOld() {
		return notPaidOld;
	}

	public void setNotPaidOld(Set<CompetitionAttendance> notPaidOld) {
		this.notPaidOld = notPaidOld;
	}

	public void addNotPaid(CompetitionAttendance att) {
		try {
			CompetitionAttendance clone = (CompetitionAttendance) att.clone();
			this.notPaid.add(clone);
		} catch (CloneNotSupportedException e) {
			LOG.debug(e.getMessage());
		}
		
		this.notPaidOld.add(att);
	}

	public void addNotBooked(CompetitionAttendance att) {
		this.notBooked.add(att);
	}
	
	public void applyCode(String code) {
		for (CompetitionAttendance att : notBooked) {
			att.createFee(code);
		}
		
		for (CompetitionAttendance att : notPaid) {
			att.createFee(code);
		}
	}
	
	public List<CompetitionAttendance> getChangeList() {
		ArrayList<CompetitionAttendance> changeList = new ArrayList<CompetitionAttendance>();
		
		changeList.addAll(notBooked);
		changeList.addAll(notPaid);
		
		return changeList;
	}

}
