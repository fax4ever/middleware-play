package com.whiterational.uisproma.business.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.Sport;

@Remote
public interface RemoteSportService {

	void create(Sport sport);

	Sport read(Long id);

	void update(Sport sport);

	void delete(Sport sport);

	List<Sport> findAll();

	Long count();

	Map<Long, Sport> getChoises();

}