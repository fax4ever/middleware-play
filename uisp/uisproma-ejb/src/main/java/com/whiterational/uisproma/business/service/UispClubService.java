package com.whiterational.uisproma.business.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.filter.ClubFilter;
import com.whiterational.uisproma.business.filter.ClubPage;

@Local
public interface UispClubService {

	void create(SportsClub club);

	SportsClub read(Long id);

	void refresh(SportsClub club);

	void update(SportsClub club);

	void delete(SportsClub club);

	List<SportsClub> findAll();

	Long count();

	Map<Long, SportsClub> getChoises();

	boolean exist(String code);

	ClubPage getPage(int start, int size, ClubFilter filter);

	SportsClub findByCode(String code);
	
	public void save(Collection<SportsClub> clubs, Collection<String> novelties);

}