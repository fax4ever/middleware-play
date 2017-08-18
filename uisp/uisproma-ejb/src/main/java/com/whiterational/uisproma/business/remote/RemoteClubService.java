package com.whiterational.uisproma.business.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.SportsClub;
import com.whiterational.uisproma.business.filter.ClubFilter;
import com.whiterational.uisproma.business.filter.ClubPage;

@Remote
public interface RemoteClubService {

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

}