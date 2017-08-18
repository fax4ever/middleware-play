package com.whiterational.uisproma.business.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.Attache;

@Local
public interface UispAttacheService {

	void create(Attache sport);

	Attache read(Long id);

	void update(Attache sport);

	void delete(Attache sport);

	List<Attache> findAll();

	Long count();

	Map<Long, Attache> getChoises();

	Attache findByUsername(String username);

}