package com.whiterational.uisproma.business.remote;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.Attache;

@Remote
public interface RemoteAttacheService {

	void create(Attache sport);

	Attache read(Long id);

	void update(Attache sport);

	void delete(Attache sport);

	List<Attache> findAll();

	Long count();

	Map<Long, Attache> getChoises();

	Attache findByUsername(String username);

}