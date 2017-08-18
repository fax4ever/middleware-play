package com.whiterational.uisproma.business.store;

import com.whiterational.uisproma.business.entity.Attache;

public interface AttacheStore extends Store<Long, Attache> {
	
	Attache findByUsername(String username);

}
