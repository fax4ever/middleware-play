package com.whiterational.uisproma.business.store;

import java.util.List;

public interface Store<K, E> {

	void create(E entity);
	
	void update(E entity);
	
	void refresh(E entity);

	void remove(E entity);
	
	E findById(K id);
	
	List<E> findAll();
	
	Long count();
	
}
