package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.Curator;

@Remote
public interface RemoteCuratorService {

	void create(Curator sport);

	Curator read(Long id);

	void update(Curator sport);

	void delete(Curator sport);

	List<Curator> findAll();

	Long count();

	Curator findByUsername(String username);

}