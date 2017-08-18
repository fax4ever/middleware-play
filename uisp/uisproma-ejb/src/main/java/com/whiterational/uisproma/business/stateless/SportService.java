package com.whiterational.uisproma.business.stateless;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Sport;
import com.whiterational.uisproma.business.remote.RemoteSportService;
import com.whiterational.uisproma.business.service.UispSportService;
import com.whiterational.uisproma.business.store.SportStore;

@Stateless
public class SportService implements UispSportService, RemoteSportService {

	@Inject
	private SportStore store;

	@Override
	public void create(Sport sport) {
		store.create(sport);
	}

	@Override
	public Sport read(Long id) {
		return store.findById(id);
	}

	@Override
	public void update(Sport sport) {
		store.update(sport);
	}

	@Override
	public void delete(Sport sport) {
		store.remove(sport);
	}

	@Override
	public List<Sport> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	@Produces
	public Map<Long, Sport> getChoises() {
		HashMap<Long, Sport> choises = new HashMap<Long, Sport>();
		for (Sport sport : findAll()) {
			choises.put(sport.getId(), sport);
		}
		return choises;
	}

}
