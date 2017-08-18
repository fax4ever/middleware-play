/*
 * @(#)AttendanceView.java        1.00	10/nov/2013
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.entity.competition.Solution;

/**
 * La classe <code>AttendanceView.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	10/nov/2013
 *
 */

public class AttendanceView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -228683679172195681L;
	private static final Logger LOG = LoggerFactory.getLogger(AttendanceView.class);
	
	private CompetitionAttendance entity;
	private Boolean selected;
	private Map<Long, Solution> solutions = new HashMap<Long, Solution>();
	
	public AttendanceView() { }

	public AttendanceView(CompetitionAttendance entity) {
		this.entity = entity;
	}

	public CompetitionAttendance getEntity() {
		return entity;
	}

	public void setEntity(CompetitionAttendance entity) {
		this.entity = entity;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
		
		LOG.info(this.selected + "");
	}

	public Long getSolutionId() {
		Solution solution = entity.getSolution();
		if (solution == null)
			return null;
		
		return solution.getId();
	}

	public void setSolutionId(Long solutionId) {
		Solution solution = solutions.get(solutionId);
		if (solution == null)
			return;
		
		entity.setSolution(solution);
	}

	public void setSolutions(List<Solution> solutions) {
		if (solutions.isEmpty())
			return;
		
		for (Solution solution : solutions) {
			this.solutions.put(solution.getId(), solution);
		}
		
		if (entity.getSolution() == null)
			entity.setSolution(solutions.get(0));
	}
	
}
