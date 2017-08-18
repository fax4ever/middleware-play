package com.whiterational.uisproma.business.stateless;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Curator;
import com.whiterational.uisproma.business.remote.RemoteCuratorService;
import com.whiterational.uisproma.business.service.UispCuratorService;
import com.whiterational.uisproma.business.store.CuratorStore;

@Stateless
public class CuratorService implements UispCuratorService, RemoteCuratorService {

	@Inject
	private CuratorStore store;

	@Override
	public void create(Curator sport) {
		store.create(sport);
	}

	@Override
	public Curator read(Long id) {
		return store.findById(id);
	}

	@Override
	public void update(Curator sport) {
		store.update(sport);
	}

	@Override
	public void delete(Curator sport) {
		store.remove(sport);
	}

	@Override
	public List<Curator> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	public Curator findByUsername(String username) {
		return store.findByUsername(username);
	}

}
