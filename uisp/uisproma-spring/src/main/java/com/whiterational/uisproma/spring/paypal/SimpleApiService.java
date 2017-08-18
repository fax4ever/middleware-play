/*
 * @(#)SimpleApiService.java        1.00	09/feb/2014
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

package com.whiterational.uisproma.spring.paypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.RequestContext;

import com.whiterational.uisproma.spring.command.AttendanceView;
import com.whiterational.uisproma.spring.command.ChampBookView;
import com.whiterational.uisproma.spring.command.RegistrationView;

/**
 * La classe <code>SimpleApiService.java</code> &egrave;
 * 
 * @author Fabio Massimo Ercoli fabiomassimo.ercoli@gmail.com
 * @version 1.00 09/feb/2014
 * 
 */

@Service("paypalService")
public class SimpleApiService {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleApiService.class);

	@Autowired
	private Provider<PaypalInfo> paypalInfoFactory;

	@Value("#{paypalProperties['paypal.actionUrl']}")
	private String actionUrl;
	
	@Value("#{paypalProperties['paypal.business']}")
	private String business;

	public PaypalInfo getPaypalInfo(RequestContext ctx) {
		PaypalInfo paypal = paypalInfoFactory.get();

		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getNativeRequest();
		RegistrationView reg = (RegistrationView) ctx.getFlowScope().get("registration");

		this.setUrls(paypal, request);
		this.setValues(paypal, reg);

		return paypal;
	}
	
	public PaypalInfo getChampInfo(RequestContext ctx) {
		PaypalInfo paypal = paypalInfoFactory.get();
		
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getNativeRequest();
		ChampBookView reg = (ChampBookView) ctx.getFlowScope().get("registration");
		
		this.setUrls(paypal, request);
		this.setValues(paypal, reg);
		
		return paypal;
	}

	private void setValues(PaypalInfo paypal, ChampBookView reg) {
		BigDecimal total = reg.getTotal();
		if (total != null)
			paypal.setAmount(total + "");
		
		paypal.setItemNumber(reg.getCode());
		paypal.setCustom(reg.getCode());
	}

	private void setValues(PaypalInfo paypal, RegistrationView reg) {
		BigDecimal total = reg.getTotal();

		if (total != null)
			paypal.setAmount(total + "");

		List<AttendanceView> selViews = reg.getSelViews();
		if (selViews == null)
			return;

		paypal.setItemNumber(reg.getCode());
		paypal.setCustom(reg.getCode());
	}

	private void setUrls(PaypalInfo paypal, HttpServletRequest request) {
		String url = request.getRequestURL() + ";jsessionid=" + request.getSession().getId() + "?"
				+ request.getQueryString();
		String paypalCancelUrl = url + "&_eventId=cancel";
		String paypalApprovedUrl = url + "&_eventId=approved";

		LOG.info(paypalApprovedUrl);
		LOG.info(paypalCancelUrl);

		paypal.setCancelReturn(paypalCancelUrl);
		paypal.setReturnVal(paypalApprovedUrl);
	}

	private boolean paypalIPNVerification(HttpServletRequest request) throws Exception {
		@SuppressWarnings("rawtypes")
		Enumeration en = request.getParameterNames();
		String str = "cmd=_notify-validate";
		while (en.hasMoreElements()) {
			String paramName = (String) en.nextElement();
			String paramValue = request.getParameter(paramName);
			paramValue = new String(paramValue.getBytes("iso-8859-1"), "utf-8");
			str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "utf-8");
		}
		
		LOG.info(str);
		
		URL u = new URL(actionUrl);
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(str);
		pw.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String res = in.readLine();
		
		LOG.info("Response from PayPal: " + res);
		
		in.close();
		return res.equals("VERIFIED");
	}

	public boolean verifyPayPalPayment(HttpServletRequest request) throws Exception {
		try {
			String payment_status = request.getParameter("payment_status");
			String receiver_email = request.getParameter("receiver_email");

			if (!paypalIPNVerification(request)) {
				
				LOG.info("Not verified by PayPal --> IPN verify fail");
				return false;
			}

			if (!payment_status.equals("Completed")) {
				
				LOG.info("Payment not completed --> IPN verify fail");
				return false;
			}

			if (!URLDecoder.decode(receiver_email, "utf-8").equals(business)) {
				
				LOG.info("Merchant id/email invalid --> IPN verify fail");
				return false;
			}

			LOG.info("IPN verify OK!");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
