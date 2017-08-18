package com.whiterational.uisproma.spring.controller;

import java.math.BigDecimal;
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
import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.entity.championship.ChampionshipAttendance;
import com.whiterational.uisproma.business.entity.championship.Step;
import com.whiterational.uisproma.business.remote.RemoteChampionshipService;
import com.whiterational.uisproma.business.valueobject.ChampBook;
import com.whiterational.uisproma.spring.application.UserInterceptor;
import com.whiterational.uisproma.spring.command.ChampBookView;

@Controller
@RequestMapping("champs")
public class ChampionshipController {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(ChampionshipController.class);

	@Inject
	private HttpSession session;

	@Inject
	private RemoteChampionshipService champServ;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String champs(@PathVariable("id") Long id, Model model) {
		Attache attache = (Attache) session.getAttribute(UserInterceptor.USER);

		Championship champ = champServ.read(id);
		model.addAttribute("champ", champ);

		List<ChampionshipAttendance> atts = champ.findByClub(attache.getClub().getId());
		model.addAttribute("atts", atts);

		return "champ";
	}

	public ChampBookView loadRegistration(Long id, Integer stepNumber) {
		Attache attache = (Attache) session.getAttribute(UserInterceptor.USER);
		Championship champ = champServ.read(id);
		List<Step> steps = champ.getSteps();
		
		if (stepNumber >= steps.size())
			return null;
		Step step = steps.get(stepNumber);
		BigDecimal price = step.getPrice();

		ChampBook book = new ChampBook(attache, stepNumber);
		champ.load(book);

		ChampBookView reg = new ChampBookView(book, champ, price);
		return reg;
	}

	public void saveRegistration(ChampBookView view) {
		champServ.insertBook(view.getChamp(), view.getBook());
	}

	public void undoRegistration(ChampBookView view) {
		champServ.removeBook(view.getChamp(), view.getBook());
	}

}
