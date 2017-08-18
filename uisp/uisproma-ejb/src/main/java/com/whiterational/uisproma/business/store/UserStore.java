package com.whiterational.uisproma.business.store;

import java.util.List;

import com.whiterational.uisproma.business.entity.User;

public interface UserStore extends Store<String, User> {
	
	public boolean isValid(String username, String password);
	public List<User> findWithName(String name);

}
