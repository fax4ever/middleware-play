package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.Event;

public interface EventStore extends Store<Long, Event> {

	List<Event> findBySportId(Long sportId);

}
