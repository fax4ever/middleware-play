/*
 * @(#)ChampRegistrationView.java        1.00	23/feb/2014
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
import java.util.LinkedHashSet;
import java.util.List;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.valueobject.ChampBook;

/**
 * La classe <code>ChampRegistrationView.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	23/feb/2014
 *
 */

public class ChampBookView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182561901951043671L;
	
	private List<AthleteView> views = new ArrayList<AthleteView>();
	private ChampBook book;
	private Championship champ;
	private BigDecimal price;
	
	public ChampBookView() {}

	public ChampBookView(ChampBook book, Championship champ, BigDecimal price) {
		this.book = book;
		this.champ = champ;
		this.price = price;
		for (Athlete athlete : book.getAthletes()) {
			views.add(new AthleteView(athlete));
		}
	}

	public List<AthleteView> getViews() {
		return views;
	}
	
	public ChampBook getBook() {
		LinkedHashSet<Athlete> athletes = getSelected();
		
		ChampBook champBook = new ChampBook(book.getAttache(), book.getLevel());
		champBook.setAthletes(athletes);
		champBook.setCode(book.getCode());
		champBook.setChanges(book.getChanges());
		
		return champBook;
	}

	public LinkedHashSet<Athlete> getSelected() {
		LinkedHashSet<Athlete> athletes = new LinkedHashSet<Athlete>();
		for (AthleteView view : views) {
			if (view.getSelected())
				athletes.add(view.getAthlete());
		}
		return athletes;
	}

	public Championship getChamp() {
		return champ;
	}
	
	public BigDecimal getTotal() {
		return price.multiply(new BigDecimal(getSelected().size()));
	}
	
	public String getCode() {
		return book.getCode();
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public Integer getStepNum() {
		return book.getLevel() + 1;
	}
	
	public String getName() {
		return champ.getName();
	}

}
