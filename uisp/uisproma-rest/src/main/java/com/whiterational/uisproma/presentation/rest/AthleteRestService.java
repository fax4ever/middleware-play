package com.whiterational.uisproma.presentation.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.filter.AthleteFilter;
import com.whiterational.uisproma.business.filter.AthletePage;
import com.whiterational.uisproma.business.service.UispAthleteService;

@Named
@RequestScoped
@Path("athletes")
public class AthleteRestService {
	
	@Inject
	private UispAthleteService service;
	
	@GET
	@RolesAllowed({"ADMINISTRATOR"})
	@Produces(MediaType.APPLICATION_JSON)
	public List<Athlete> findAll() {
		AthletePage page = service.getPage(0, 27, new AthleteFilter());
		
		return page.getAthletes();
	}

}
