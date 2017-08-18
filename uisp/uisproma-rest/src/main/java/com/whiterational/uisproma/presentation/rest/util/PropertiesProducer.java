/*
 * @(#)PropertiesProducer.java        1.00	16/feb/2014
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

package com.whiterational.uisproma.presentation.rest.util;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La classe <code>PropertiesProducer.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	16/feb/2014
 *
 */

@Singleton
public class PropertiesProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(PropertiesProducer.class);
	
	@Produces
	public Properties getProperties() {
		Properties prop = new Properties();

		try {
			prop.load(this.getClass().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			LOG.error(e + "", e);
		}

		return prop;
	}

}
