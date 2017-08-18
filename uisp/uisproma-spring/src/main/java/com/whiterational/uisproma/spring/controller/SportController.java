package com.whiterational.uisproma.spring.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whiterational.uisproma.business.entity.Attache;
import com.whiterational.uisproma.business.entity.Event;
import com.whiterational.uisproma.business.entity.Sport;
import com.whiterational.uisproma.business.remote.RemoteAttacheService;
import com.whiterational.uisproma.business.remote.RemoteEventService;
import com.whiterational.uisproma.business.remote.RemoteSportService;

@Controller
@RequestMapping("sports")
public class SportController {
	
	@Inject
	private HttpServletRequest req;
	
	@Inject
	private RemoteAttacheService attServ;

	@Inject
	private RemoteSportService sportServ;
	
	@Inject
	private RemoteEventService eventServ;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String sport(@PathVariable("id") Long id, Model model) {
		String name = req.getRemoteUser();

		if (name != null) {
			Attache attache = attServ.findByUsername(name);

			if (attache != null)
				model.addAttribute("user", attache);

		}
		
		List<Sport> sports = sportServ.findAll();
		model.addAttribute("sports", sports);
		
		Sport read = sportServ.read(id);
		model.addAttribute("sport", read);
		model.addAttribute("five", read.getId());
		
		List<Event> events = eventServ.findBySportId(read.getId());
		model.addAttribute("events", events);
		
		return "sport";
	}

}
