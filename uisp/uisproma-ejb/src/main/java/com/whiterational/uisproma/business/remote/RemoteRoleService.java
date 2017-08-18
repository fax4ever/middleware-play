package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.Role;

@Remote
public interface RemoteRoleService {

	void create(Role role);

	Role read(Long id);

	void update(Role role);

	void delete(Role role);

	List<Role> findAll();

}