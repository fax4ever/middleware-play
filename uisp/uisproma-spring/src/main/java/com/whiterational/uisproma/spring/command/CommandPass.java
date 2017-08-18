/*
 * @(#)CommandPass.java        1.00	28/nov/2013
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
import java.util.ArrayList;
import java.util.List;

/**
 * La classe <code>CommandPass.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	28/nov/2013
 *
 */

public class CommandPass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5508573246665032243L;
	
	private String name;
	private Boolean fixed = false;
	private List<MiniCommandPass> passes = new ArrayList<MiniCommandPass>();
	
	public CommandPass() {}

	public CommandPass(String name) {
		this.name = name;
	}

	public CommandPass(String string, String[] passesNames) {
		this(string);
		
		for (String pass: passesNames) {
			this.addPass(pass);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getFixed() {
		return fixed;
	}

	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	public List<MiniCommandPass> getPasses() {
		return passes;
	}

	public void setPasses(List<MiniCommandPass> passes) {
		this.passes = passes;
	}
	
	private void addPass(String value) {
		MiniCommandPass pass = new MiniCommandPass(value);
		this.passes.add(pass);
	}
	
}
