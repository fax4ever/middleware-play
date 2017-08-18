/*
 * @(#)AttendanceViews.java        1.00	10/nov/2013
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.entity.competition.Solution;
import com.whiterational.uisproma.business.valueobject.CompBook;

/**
 * La classe <code>AttendanceViews.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	10/nov/2013
 *
 */

public class RegistrationView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationView.class);
	
	private CompBook book;
	private List<AttendanceView> views = new ArrayList<AttendanceView>();
	private List<AttendanceView> selViews = new ArrayList<AttendanceView>();
	private Competition comp;
	private String code;
	
	public RegistrationView() { };
	
	public RegistrationView(Competition comp, Attache attache) {
		generateClient();
		this.book = comp.getBook(attache);
		for (CompetitionAttendance att : book.getChangeList()) {
			views.add(new AttendanceView(att));
		}
		
		this.comp = comp;
		for (AttendanceView view : views) {
			view.setSolutions(comp.getSolutions());
		}
	}

	public List<AttendanceView> getViews() {
		return views;
	}

	public void setViews(List<AttendanceView> views) {
		this.views = views;
	}

	public void initSelectedViews() {
		selViews.clear();
		
		for (AttendanceView view : views) {
			Boolean selected = view.getSelected();
			if (selected != null && selected)
				selViews.add(view);
		}
	}

	public Competition getComp() {
		return comp;
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for (AttendanceView view : views) {
			CompetitionAttendance entity = view.getEntity();
			if (view.getSelected() == null || !view.getSelected())
				continue;
			
			Solution solution = entity.getSolution();
			if (solution == null)
				continue;
			
			total = total.add(solution.getPrice());
		}
		
		return total;
	}	

	public CompBook getBook() {
		CompBook compBook = new CompBook();
		
		LinkedHashSet<CompetitionAttendance> notBooked = new LinkedHashSet<CompetitionAttendance>();
		LinkedHashSet<CompetitionAttendance> notPaid = new LinkedHashSet<CompetitionAttendance>();
		
		for (CompetitionAttendance att: book.getNotBooked()) {
			if (isSelected(att))
				notBooked.add(att);
		}
		
		for (CompetitionAttendance att: book.getNotPaid()) {
			if (isSelected(att))
				notPaid.add(att);
		}
		
		compBook.setNotBooked(notBooked);
		compBook.setNotPaid(notPaid);
		compBook.setNotPaidOld(book.getNotPaidOld());
		
		return compBook;
	}
	
	private boolean isSelected(CompetitionAttendance att) {
		for (AttendanceView view : selViews) {
			if (att.equals(view.getEntity()))
				return true;		
		}
		
		return false;
	}

	public void setBook(CompBook book) {
		this.book = book;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private void generateClient() {
		Date date = new Date();
		long time = date.getTime();
		double random = Math.random();
		
		this.code = time + ":" + random;
		LOG.info("Generated CODE: " + this.code);
	}

	public List<AttendanceView> getSelViews() {
		return selViews;
	}
	
}
