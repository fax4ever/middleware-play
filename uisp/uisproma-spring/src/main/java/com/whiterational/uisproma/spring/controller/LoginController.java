package com.whiterational.uisproma.spring.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.spring.application.UserInterceptor;

@Controller
public class LoginController {
	
	@Inject
	private HttpServletRequest req;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
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
			HttpSession session = req.getSession();
			Object user = session.getAttribute(UserInterceptor.USER);
			if (user != null) {
				session.removeAttribute(UserInterceptor.USER);
			}
			
			Object sports = session.getAttribute(UserInterceptor.SPORTS);
			if (sports != null) {
				session.removeAttribute(UserInterceptor.SPORTS);
			}
			
			req.logout();
			
		} catch (Exception ex) {
			model.addAttribute("logouterror", "true");
			return "";
		}
		
		LOGGER.info("user " + name + " logged out");
		
		return "login";
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String home(Model uiModel) {

		return "home";
	}

}
