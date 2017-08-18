/*
 * @(#)MiniCommandPass.java        1.00	28/nov/2013
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

/**
 * La classe <code>MiniCommandPass.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	28/nov/2013
 *
 */

public class MiniCommandPass implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3395596796541383856L;
	private String value;
	private Boolean shimmia;
	
	public MiniCommandPass() { }
	
	public MiniCommandPass(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getShimmia() {
		return shimmia;
	}

	public void setShimmia(Boolean shimmia) {
		this.shimmia = shimmia;
	}
	
}
