package com.whiterational.uisproma.business.stateless;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.exception.ConfirmPasswordNotMatch;
import com.whiterational.uisproma.business.exception.OldPasswordNotMatch;
import com.whiterational.uisproma.business.remote.RemoteUserService;
import com.whiterational.uisproma.business.security.Md5Helper;
import com.whiterational.uisproma.business.service.UispUserService;
import com.whiterational.uisproma.business.store.UserStore;

@Stateless
public class UserService implements UispUserService, RemoteUserService {

	@Inject
	private UserStore store;

	@Override
	public void create(User user) {
		store.create(user);
	}

	@Override
	public User read(String username) {
		return store.findById(username);
	}

	@Override
	public void update(User user) {
		store.update(user);
	}

	@Override
	public void delete(User user) {
		store.remove(user);
	}

	@Override
	public List<User> findAll() {
		return store.findAll();
	}

	@Override
	public Long count() {
		return store.count();
	}

	@Override
	public boolean isValid(User user) {
		return store.isValid(user.getUsername(), user.getPassword());
	}

	@Override
	public boolean alredyInUse(String username) {
		return (store.findById(username) != null);
	}

	@Override
	public boolean changePassword(User user, String oldPassword, String newPassword, String confirmPassword) throws OldPasswordNotMatch,
			ConfirmPasswordNotMatch {
		
		if (!newPassword.equals(confirmPassword)) {
			throw new ConfirmPasswordNotMatch();
		}
		
		String old = Md5Helper.hashPassword(oldPassword);
		if (!old.equals(user.getPassword())) {
			throw new OldPasswordNotMatch();
		}
		
		user.setPassword(Md5Helper.hashPassword(newPassword));
		store.update(user);
		
		return true;
	}

}
