package com.whiterational.uisproma.business.stateless;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Event;
import com.whiterational.uisproma.business.remote.RemoteEventService;
import com.whiterational.uisproma.business.service.UispEventService;
import com.whiterational.uisproma.business.store.EventStore;

@Stateless
public class EventService implements UispEventService, RemoteEventService {
	
	@Inject
	private EventStore store;

	@Override
	public void create(Event event) {
		store.create(event);
	}

	@Override
	public Event read(Long id) {
		return store.findById(id);
	}

	@Override
	public void update(Event event) {
		store.refresh(event);
	}

	@Override
	public void delete(Event event) {
		store.remove(event);
	}

	@Override
	public List<Event> findAll() {
		return store.findAll();
	}

	@Override
	public List<Event> findBySportId(Long sportId) {
		return store.findBySportId(sportId);
	}

}
