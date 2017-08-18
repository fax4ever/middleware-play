package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.Event;

@Remote
public interface RemoteEventService {
	
	void create(Event event);

	Event read(Long id);

	void update(Event event);

	void delete(Event event);

	List<Event> findAll();
	
	List<Event> findBySportId(Long sportId);

}
