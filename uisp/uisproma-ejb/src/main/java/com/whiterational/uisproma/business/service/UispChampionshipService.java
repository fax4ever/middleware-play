package com.whiterational.uisproma.business.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.championship.Championship;
import com.whiterational.uisproma.business.valueobject.ChampBook;

@Local
public interface UispChampionshipService {

	void create(Championship champ, String user);

	Championship read(Long id);

	boolean update(Championship champ, String user);

	boolean delete(Championship champ);

	List<Championship> findAll();

	Long count();

	Map<Long, Championship> getChoises();
	
	void insertBook(Championship champ, ChampBook book);
	
	void removeBook(Championship champ, ChampBook book);

}