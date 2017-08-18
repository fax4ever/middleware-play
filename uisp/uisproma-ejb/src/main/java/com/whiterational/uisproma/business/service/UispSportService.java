package com.whiterational.uisproma.business.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.Sport;

@Local
public interface UispSportService {

	void create(Sport sport);

	Sport read(Long id);

	void update(Sport sport);

	void delete(Sport sport);

	List<Sport> findAll();

	Long count();

	Map<Long, Sport> getChoises();

}