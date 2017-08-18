/*
 * @(#)WebFlowConfig.java        1.00	03/nov/2013
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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

/**
 * La classe <code>WebFlowConfig.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	03/nov/2013
 *
 */

@Configuration
@ImportResource("classpath:webflow-config.xml")
public class WebFlowConfig {
	
	@Inject
	private FlowExecutor flowExecutor;
	
	@Inject
	private FlowDefinitionRegistry flowRegistry;
	
	@Inject
	private LocaleChangeInterceptor localeChangeInterceptor;
	
	@Inject
	private UserInterceptor userInterceptor;
	
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
		flowHandlerAdapter.setFlowExecutor(flowExecutor);
		return flowHandlerAdapter;
	}
	
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
		flowHandlerMapping.setInterceptors(new Object[] {localeChangeInterceptor, userInterceptor});
		flowHandlerMapping.setFlowRegistry(flowRegistry);
		return flowHandlerMapping; 
	}

}
