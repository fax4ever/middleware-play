package com.whiterational.uisproma.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.web.spring.CalendarEditor;

@Controller
public class LoginController {
	
	@Inject
	private HttpServletRequest req;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		
		binder.registerCustomEditor(GregorianCalendar.class, new CalendarEditor(dateFormat));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(ModelMap model) {
		
		model.addAttribute("error", "true");
		return "login";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.POST)
	public String post(ModelMap model) {
		
		model.addAttribute("error", "true");
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		
		String name = req.getRemoteUser();
		
		try {
			req.logout();
		} catch (Exception ex) {
			model.addAttribute("logouterror", "true");
			return "";
		}
		
		LOGGER.info("user " + name + " logged out");
		
		return "login";
	}

}
