package com.whiterational.uisproma.business.service;

import java.util.List;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.Event;

@Local
public interface UispEventService {
	
	void create(Event event);

	Event read(Long id);

	void update(Event event);

	void delete(Event event);

	List<Event> findAll();
	
	List<Event> findBySportId(Long sportId);

}
