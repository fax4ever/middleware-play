/*
 * @(#)PaymentController.java        1.00	13/feb/2014
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

package com.whiterational.uisproma.spring.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.business.remote.RemotePaymentService;
import com.whiterational.uisproma.spring.paypal.SimpleApiService;

/**
 * La classe <code>PaymentController.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	13/feb/2014
 *
 */

@Controller
@RequestMapping("free")
public class ExternalController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExternalController.class);
	
	@Inject
	private SimpleApiService paypalService;
	
	@Inject
	private RemotePaymentService payService;
	
	@RequestMapping(value = "pay", method = RequestMethod.POST)
	public void notifyPayment(HttpServletRequest req) throws Exception {
		
		boolean verifyPayPalPayment = paypalService.verifyPayPalPayment(req);
		if (!verifyPayPalPayment)
			return;
		
		String code = req.getParameter("item_number");
		LOG.info("item_number: " + code);
		
		payService.pay(code);
	}

}
