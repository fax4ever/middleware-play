/*
 * @(#)PaymentClientService.java        1.00	16/feb/2014
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

package com.whiterational.uisproma.presentation.rest.client;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * La classe <code>PaymentClientService.java</code> &egrave;
 * 
 * @author Fabio Massimo Ercoli fabiomassimo.ercoli@gmail.com
 * @version 1.00 16/feb/2014
 * 
 */

@Singleton
public class PaymentClientService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PaymentClientService.class);

	@Inject
	private Properties prop;
	
	private Client client = Client.create();

	public ClientResponse getResponse(MultivaluedMap<String, String> form) {
		WebResource webResource = client.resource(prop.getProperty("paypal.actionUrl"));
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.TEXT_PLAIN)
				.post(ClientResponse.class, form);
		String entity = response.getEntity(String.class);
		LOG.info(entity);

		return response;
	}
	
	public ClientResponse getResponseAlt(MultivaluedMap<String, String> form) {
		
		try {
			 
			Client client = Client.create();
	 
			WebResource webResource = client
			   .resource(prop.getProperty("paypal.actionUrl"));
	 
			String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
	 
			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, input);
	 
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}
	 
			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			return response;
	 
		  } catch (Exception e) {
	 
			LOG.error(e.getMessage());
			return null;
	 
		  }
	 
	}

}
