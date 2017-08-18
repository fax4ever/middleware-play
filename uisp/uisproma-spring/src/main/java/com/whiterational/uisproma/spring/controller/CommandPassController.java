/*
 * @(#)CommandPassController.java        1.00	28/nov/2013
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.spring.command.CommandPass;
import com.whiterational.uisproma.spring.command.MiniCommandPass;

/**
 * La classe <code>CommandPassController.java</code> &egrave;
 *
 * @author Fabio Massimo Ercoli			fabiomassimo.ercoli@gmail.com
 * @version 1.00	28/nov/2013
 *
 */

@Controller
@RequestMapping("/free/commandPass")
public class CommandPassController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommandPassController.class);
	private static final String[] PASSES_NAMES = { "Quando", "Un giorno", "Mirandola", "Verrà", "Giudicherò", "Con", "Giudizio" };
	
	@RequestMapping(method = RequestMethod.GET)
	public String load(ModelMap modelBinder) {
		modelBinder.addAttribute("commandPass", createPass());
		return "commandPass";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(@ModelAttribute("commandPass") CommandPass pass) {
		verifyPass(pass);
		return "redirect:commandPass";
	}
	
	public CommandPass createPass() {
		CommandPass commandPass = new CommandPass("Le Caravanne Passe", PASSES_NAMES);
		return commandPass;
	}
	
	public void verifyPass(CommandPass pass) {
		LOG.info("My name is:" + pass.getName());
		LOG.info("My fixed is" + pass.getFixed());
		
		for (MiniCommandPass passe : pass.getPasses()) {
			LOG.info("ITEM Mirell: " + passe.getShimmia());
		}
	}

}
