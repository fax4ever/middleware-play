package com.whiterational.uisproma.web.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Sport;
import com.whiterational.uisproma.business.service.UispAttacheService;
import com.whiterational.uisproma.business.service.UispSportService;
import com.whiterational.uisproma.web.command.ChangePassword;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Inject
	private HttpServletRequest req;

	@Inject
	private UispAttacheService attServ;

	@Inject
	private UispSportService sportServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public String changePassword(Model uiModel) {
		String name = req.getRemoteUser();
		
		if (name != null) {
			Attache attache = attServ.findByUsername(name);

			if (attache != null)
				uiModel.addAttribute("user", attache);

		}
		
		List<Sport> sports = sportServ.findAll();
		uiModel.addAttribute("sports", sports);
		uiModel.addAttribute("command", new ChangePassword());
		
		return "user";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String changePasswordAct(Model uiModel, @ModelAttribute("command") ChangePassword command) {
		String name = req.getRemoteUser();
		
		if (name != null) {
			Attache attache = attServ.findByUsername(name);

			if (attache != null)
				uiModel.addAttribute("user", attache);

		}
		
		List<Sport> sports = sportServ.findAll();
		uiModel.addAttribute("sports", sports);
		
		return "home";
	}

}
