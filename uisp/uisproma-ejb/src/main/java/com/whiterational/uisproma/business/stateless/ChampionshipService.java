package com.whiterational.uisproma.business.stateless;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Address;
import com.whiterational.uisproma.business.entity.Curator;
import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.remote.RemoteChampionshipService;
import com.whiterational.uisproma.business.service.UispChampionshipService;
import com.whiterational.uisproma.business.store.ChampionshipStore;
import com.whiterational.uisproma.business.store.CuratorStore;
import com.whiterational.uisproma.business.valueobject.ChampBook;

@Stateless
public class ChampionshipService implements UispChampionshipService, RemoteChampionshipService {

	@Inject
	private ChampionshipStore store;

	@Inject
	private CuratorStore curator;

	@Override
	public void create(Championship champ, String user) {
		setCurator(champ, user);

		store.create(champ);
	}

	private void setCurator(Championship champ, String user) {
		if (champ.getCurator() == null || champ.getCurator().getUser() == null || champ.getCurator().getUser().getUsername() == null
				|| !champ.getCurator().getUser().getUsername().equals(user)) {
			Curator finded = curator.findByUsername(user);
			if (finded != null)
				champ.setCurator(finded);
		}
	}

	@Override
	public Championship read(Long id) {
		final Championship champ = store.findById(id);
		
		if (champ.getAddress() == null)
			champ.setAddress(new Address());
		
		return champ;
	}

	@Override
	public boolean update(Championship champ, String user) {
		if (!champ.canUpdate())
			return false;

		champ.alignAttendance();
		setCurator(champ, user);
		store.update(champ);
		return true;
	}

	@Override
	public boolean delete(Championship champ) {
		if (champ.canRemove())
			return false;

		store.remove(champ);
		return true;
	}

	@Override
	public List<Championship> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	@Produces
	public Map<Long, Championship> getChoises() {
		HashMap<Long, Championship> choises = new HashMap<Long, Championship>();
		for (Championship champ : findAll()) {
			choises.put(champ.getId(), champ);
		}
		return choises;
	}
	
	@Override
	public void insertBook(Championship champ, ChampBook book) {
		champ = store.findById(champ.getId());
		champ.insertBook(book);
		store.update(champ);
	}
	
	@Override
	public void removeBook(Championship champ, ChampBook book) {
		champ = store.findById(champ.getId());
		champ.removeBook(book);
		store.update(champ);
	}

}
