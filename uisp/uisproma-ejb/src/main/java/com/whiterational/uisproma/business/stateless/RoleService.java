package com.whiterational.uisproma.business.stateless;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.Role;
import com.whiterational.uisproma.business.remote.RemoteRoleService;
import com.whiterational.uisproma.business.service.UispRoleService;
import com.whiterational.uisproma.business.store.RoleStore;

@Stateless
public class RoleService implements UispRoleService, RemoteRoleService {

	@Inject
	private RoleStore store;

	@Override
	public void create(Role role) {
		store.create(role);
	}

	@Override
	public Role read(Long id) {
		return store.findById(id);
	}

	@Override
	public void update(Role role) {
		store.update(role);
	}

	@Override
	public void delete(Role role) {
		store.remove(role);
	}

	@Override
	public List<Role> findAll() {
		return store.findAll();
	}

}
