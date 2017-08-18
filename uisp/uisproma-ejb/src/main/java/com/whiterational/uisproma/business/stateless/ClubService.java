package com.whiterational.uisproma.business.stateless;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.filter.ClubFilter;
import com.whiterational.uisproma.business.filter.ClubPage;
import com.whiterational.uisproma.business.remote.RemoteClubService;
import com.whiterational.uisproma.business.service.UispClubService;
import com.whiterational.uisproma.business.store.SportClubStore;

@Stateless
public class ClubService implements UispClubService, RemoteClubService {

	@Inject
	private SportClubStore store;

	@Override
	public void create(SportsClub club) {
		store.create(club);
	}

	@Override
	public SportsClub read(Long id) {
		return store.findById(id);
	}

	@Override
	public void refresh(SportsClub club) {
		store.refresh(club);
	}

	@Override
	public void update(SportsClub club) {
		store.update(club);
	}

	@Override
	public void delete(SportsClub club) {
		store.remove(club);
	}

	@Override
	public List<SportsClub> findAll() {
		return store.getAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	@Produces
	public Map<Long, SportsClub> getChoises() {
		HashMap<Long, SportsClub> choises = new LinkedHashMap<Long, SportsClub>();
		for (SportsClub club : findAll()) {
			choises.put(club.getId(), club);
		}
		return choises;
	}

	@Override
	public boolean exist(String code) {
		return (store.findByCode(code) != null);
	}

	@Override
	public ClubPage getPage(int start, int size, ClubFilter filter) {
		List<SportsClub> athletes = store.findByFilter(start, size, filter);
		Long total = store.countByFilter(filter);
		return new ClubPage(athletes, total);
	}

	@Override
	public SportsClub findByCode(String code) {
		return store.findByCode(code);
	}
	
	public void save(Collection<SportsClub> clubs, Collection<String> novelties) {
		for (SportsClub club : clubs) {
	      if (novelties.contains(club.getFreshman()))
	        create(club);
	      else  
	        update(club);
	    }
	}

}
