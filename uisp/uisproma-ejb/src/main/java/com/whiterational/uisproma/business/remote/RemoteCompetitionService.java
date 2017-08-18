package com.whiterational.uisproma.business.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.competition.Competition;
import com.whiterational.uisproma.business.valueobject.CompBook;

@Remote
public interface RemoteCompetitionService {

	void create(Competition comp, String user);

	Competition read(Long id);

	void update(Competition comp, String user);

	boolean delete(Competition comp);

	List<Competition> findAll();
	
	List<Competition> findBySportId(Long sportId);

	Long count();

	Map<Long, Competition> getChoises();

	void update(Competition comp);

	void insertBook(Competition comp, CompBook book, String code);

	void removeBook(Competition comp, CompBook book);

}