package com.whiterational.uisproma.spring.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.remote.RemoteCompetitionService;
import com.whiterational.uisproma.spring.application.UserInterceptor;
import com.whiterational.uisproma.spring.command.RegistrationView;

@Controller
@RequestMapping("comps")
public class CompetitionController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompetitionController.class);
	
	@Inject
	private HttpSession session;
	
	@Inject
	private RemoteCompetitionService compServ;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String comps(@PathVariable("id") Long id, Model model) {
		Attache attache = (Attache) session.getAttribute(UserInterceptor.USER);
		
		Competition comp = compServ.read(id);
		model.addAttribute("comp", comp);
		
		List<CompetitionAttendance> atts = comp.findByClub(attache.getClub().getId());
		model.addAttribute("atts", atts);
		
		return "comp";
	}
	
	public RegistrationView loadRegistration(Competition comp) {
		Attache attache = (Attache) session.getAttribute(UserInterceptor.USER);
		
		if (comp == null)
			return null;
		
		LOG.debug("load reminder for competition: "  + comp.getName());
		RegistrationView registrationView = new RegistrationView(comp, attache);
		
		return registrationView;
	}
	
	public void saveRegistration(RegistrationView view) {
		compServ.insertBook(view.getComp(), view.getBook(), view.getCode());
	}
	
	public void undoRegistration(RegistrationView view) {
		compServ.removeBook(view.getComp(), view.getBook());
	}

}
