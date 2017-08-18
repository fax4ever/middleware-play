package com.whiterational.uisproma.spring.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.exception.ConfirmPasswordNotMatch;
import com.whiterational.uisproma.business.exception.OldPasswordNotMatch;
import com.whiterational.uisproma.business.remote.RemoteUserService;
import com.whiterational.uisproma.spring.application.UserInterceptor;
import com.whiterational.uisproma.spring.command.ChangePassword;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Inject
	private HttpSession session;
	
	@Inject
	private RemoteUserService userServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public String changePassword(Model uiModel) {
		
		uiModel.addAttribute("command", new ChangePassword());
		return "user";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String changePasswordAct(Model uiModel, @ModelAttribute("command") ChangePassword command) {
		Attache attache = (Attache) session.getAttribute(UserInterceptor.USER);
			
		try {
			userServ.changePassword(attache.getUser(), command.getOld(), command.getValue(), command.getConfirm());
		} catch (OldPasswordNotMatch e) {
			uiModel.addAttribute("oldPasswordError", "true");
			
			return "user";
		} catch (ConfirmPasswordNotMatch e) {
			uiModel.addAttribute("confirmPasswordError", "true");
			
			return "user";
		}  
		
		return "redirect:home";
	}

}
