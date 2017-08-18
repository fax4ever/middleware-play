package com.whiterational.uisproma.business.service;

import java.util.List;

import javax.ejb.Local;

import com.whiterational.uisproma.business.entity.Role;

@Local
public interface UispRoleService {

	void create(Role role);

	Role read(Long id);

	void update(Role role);

	void delete(Role role);

	List<Role> findAll();

}