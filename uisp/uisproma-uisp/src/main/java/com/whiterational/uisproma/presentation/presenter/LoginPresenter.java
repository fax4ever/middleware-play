package com.whiterational.uisproma.presentation.presenter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.presentation.view.UserView;

@Presenter
public class LoginPresenter implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = -1206580896971667488L;

	@Inject
	private UserView view;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginPresenter.class);

	@Inject
	private transient FacesContext ctx;

	public String login() {
		if (ctx.getExternalContext().getRemoteUser() != null)
			logout();

		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		try {
			req.login(view.getUser().getUsername(), view.getUser().getPassword());
		} catch (ServletException e) {
			ctx.addMessage(null, new FacesMessage("Username o Password non valida"));
			return null;
		}

		LOGGER.info(view.getUser().getUsername() + " logged in");
		return "/menu.xhtml?faces-redirect=true";
	}

	public String logout() {
		String name = getLoggedUsername();

		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		try {
			req.logout();
		} catch (ServletException e) {
			ctx.addMessage(null, new FacesMessage("Logout non riuscito"));
			return null;
		}

		LOGGER.info(name + " logged out");
		return "/login.xhtml?faces-redirect=true";
	}

	public String getLoggedUsername() {
		return ctx.getExternalContext().getRemoteUser();
	}

}
