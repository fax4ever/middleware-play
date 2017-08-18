package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.Athlete;
import com.whiterational.uisproma.business.filter.AthleteFilter;
import com.whiterational.uisproma.business.filter.AthletePage;

@Remote
public interface RemoteAthleteService {
	
	void create(Athlete athlete);

	Athlete read(Long id);

	void refresh(Athlete athlete);

	void update(Athlete athlete);

	void delete(Athlete athlete);

	List<Athlete> findAll();

	Long count();

	boolean exist(String code);

	AthletePage getPage(int start, int size, AthleteFilter filter);

}
