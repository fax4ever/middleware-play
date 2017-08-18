package com.whiterational.uisproma.business.stateless;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Address;
import com.whiterational.uisproma.business.entity.Curator;
import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.entity.competition.CompetitionAttendance;
import com.whiterational.uisproma.business.remote.RemoteCompetitionService;
import com.whiterational.uisproma.business.service.UispCompetitionService;
import com.whiterational.uisproma.business.store.CompetitionStore;
import com.whiterational.uisproma.business.store.CuratorStore;
import com.whiterational.uisproma.business.valueobject.CompBook;

@Stateless
public class CompetitionService implements UispCompetitionService, RemoteCompetitionService {

	@Inject
	private CompetitionStore store;

	@Inject
	private CuratorStore curator;

	@Override
	public void create(Competition comp, String user) {
		setCurator(comp, user);

		store.create(comp);
	}

	private void setCurator(Competition comp, String user) {
		if (comp.getCurator() == null || comp.getCurator().getUser() == null || comp.getCurator().getUser().getUsername() == null
				|| !comp.getCurator().getUser().getUsername().equals(user)) {
			Curator finded = curator.findByUsername(user);
			if (finded != null)
				comp.setCurator(finded);
		}
	}

	@Override
	public Competition read(Long id) {
		final Competition comp = store.findById(id);
		
		if (comp.getAddress() == null)
			comp.setAddress(new Address());
		
		return comp;
	}

	@Override
	public void update(Competition comp, String user) {
		setCurator(comp, user);

		store.update(comp);
	}
	
	@Override
	public void update(Competition comp) {
		store.update(comp);
	}

	@Override
	public boolean delete(Competition comp) {
		if (!comp.canRemove())
			return false;

		store.remove(comp);
		return true;
	}

	@Override
	@Produces
	public List<Competition> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	@Produces
	public Map<Long, Competition> getChoises() {
		HashMap<Long, Competition> choises = new HashMap<Long, Competition>();
		for (Competition comp : findAll()) {
			choises.put(comp.getId(), comp);
		}
		return choises;
	}

	@Override
	public List<Competition> findBySportId(Long sportId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insertBook(Competition comp, CompBook book, String code) {
		Competition competition = store.findById(comp.getId());
		book.applyCode(code);
		
		for (CompetitionAttendance att : book.getNotBooked()) {
			competition.insert(att);
		}
		
		for (CompetitionAttendance att : book.getNotPaid()) {
			competition.update(att);
		}
		
		this.update(competition);
	}
	
	@Override
	public void removeBook(Competition comp, CompBook book) {
		Competition competition = store.findById(comp.getId());
		
		for (CompetitionAttendance att : book.getNotBooked()) {
			competition.remove(att);
		}
		
		for (CompetitionAttendance att : book.getNotPaidOld()) {
			competition.update(att);
		}
		
		this.update(competition);
	}

}
