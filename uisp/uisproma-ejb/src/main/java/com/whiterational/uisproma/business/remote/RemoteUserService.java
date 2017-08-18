package com.whiterational.uisproma.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.exception.ConfirmPasswordNotMatch;
import com.whiterational.uisproma.business.exception.OldPasswordNotMatch;

@Remote
public interface RemoteUserService {

	void create(User user);

	User read(String username);

	void update(User user);

	void delete(User user);

	List<User> findAll();

	Long count();

	boolean isValid(User user);

	boolean alredyInUse(String username);
	
	/**
	 * Change the password of user.
	 * 
	 * @param user							the owner of the password
	 * @param oldPassword					old password not encoded
	 * @param newPassword					new password not encoded
	 * @param confirmPassword				new password again not encoded
	 * 	
	 * @return								true if the save operation succeed
	 * 
	 * @throws OldPasswordNotMatch			if the old password is not equal to stored password
	 * @throws ConfirmPasswordNotMatch		if the new password do not match with confirm
	 */
	boolean changePassword(User user, String oldPassword, String newPassword, String confirmPassword) throws OldPasswordNotMatch, ConfirmPasswordNotMatch;

}