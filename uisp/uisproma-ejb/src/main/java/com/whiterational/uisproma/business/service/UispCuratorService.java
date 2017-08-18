package com.whiterational.uisproma.business.service;

import java.util.List;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.Curator;

@Local
public interface UispCuratorService {

	void create(Curator sport);

	Curator read(Long id);

	void update(Curator sport);

	void delete(Curator sport);

	List<Curator> findAll();

	Long count();

	Curator findByUsername(String username);

}