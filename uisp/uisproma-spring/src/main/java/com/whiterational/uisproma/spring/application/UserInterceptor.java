/*
 * @(#)UserInterceptor.java        1.00	07/nov/2013
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

package com.whiterational.uisproma.spring.application;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.whiterational.uisproma.business.remote.RemoteAttacheService;
import com.whiterational.uisproma.business.remote.RemoteSportService;

/**
 * La classe <code>UserInterceptor.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	07/nov/2013
 *
 */

@Component
public class UserInterceptor implements HandlerInterceptor {
	
	public final static String USER = "user";
	public final static String SPORTS = "sports";
	
	@Inject
	private RemoteAttacheService attServ;

	@Inject
	private RemoteSportService sportServ;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String name = request.getRemoteUser();
		if (name == null)
			return true;
		
		HttpSession session = request.getSession();
		Object user = session.getAttribute(USER);
		if (user == null) {
			user = attServ.findByUsername(name);
			session.setAttribute(USER, user);
		}
		
		Object sports = session.getAttribute(SPORTS);
		if (sports == null) {
			sports = sportServ.findAll();
			session.setAttribute(SPORTS, sports);
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception { 
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
