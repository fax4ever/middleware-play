/*
 * @(#)ExternalRestService.java        1.00	16/feb/2014
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

package com.whiterational.uisproma.presentation.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.filter.AthleteFilter;
import com.whiterational.uisproma.business.filter.AthletePage;
import com.whiterational.uisproma.business.service.UispAthleteService;
import com.whiterational.uisproma.presentation.rest.client.PaymentClientService;

/**
 * La classe <code>ExternalRestService.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	16/feb/2014
 *
 */

@Named
@RequestScoped
@Path("free")
public class ExternalRestService {
	
	private static final String COMPLETED = "Completed";
	private static final Logger LOG = LoggerFactory.getLogger(ExternalRestService.class);
	
	@Inject
	private UispAthleteService service;
	
	@Inject
	private PaymentClientService payClient;
	
	@Inject
	private Properties prop;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("athletes")
	public List<Athlete> findAllAthletes() {
		AthletePage page = service.getPage(0, 27, new AthleteFilter());
		
		return page.getAthletes();
	}
	
	@POST
	@Path("payment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void payment(MultivaluedMap<String, String> form) throws UnsupportedEncodingException {
		
		if (!COMPLETED.equals(form.getFirst("payment_status"))) {
			return;
		}
		
		String receiverEmail = form.getFirst("receiver_email");
		String receiverEmailDecode = URLDecoder.decode(receiverEmail, "utf-8");
		if (!receiverEmailDecode.equals(prop.get("paypal.business"))) {
			return;
		}
		
		// verify ipn message resending to paypal
		ClientResponse response = payClient.getResponseAlt(form);
		LOG.info(response + "");
		
	}

}
