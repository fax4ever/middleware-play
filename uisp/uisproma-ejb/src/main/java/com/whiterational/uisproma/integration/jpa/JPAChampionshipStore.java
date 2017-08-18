package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.store.ChampionshipStore;

public class JPAChampionshipStore extends JPAStore<Long, Championship> implements ChampionshipStore {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3447843622946576796L;

	@Override
	public List<Championship> findWithName(String name) {
	  
	  @SuppressWarnings("unchecked")
    List<Championship> result = (List<Championship>) entityManager.createNamedQuery("findAllChampionshipWithName").setParameter("name", name).getResultList();
	  
	  return result;
	}

}
