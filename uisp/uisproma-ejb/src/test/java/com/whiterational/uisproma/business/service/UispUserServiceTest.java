package com.whiterational.uisproma.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.exception.ConfirmPasswordNotMatch;
import com.whiterational.uisproma.business.exception.OldPasswordNotMatch;
import com.whiterational.uisproma.business.exception.PasswordException;
import com.whiterational.uisproma.business.security.Md5Helper;

public class UispUserServiceTest extends UispServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UispUserServiceTest.class);
	
	@Inject
	private UispUserService target;
	
	@Test
	public void test() {
		User read = target.read("mario");

		assertNotNull(read);

		LOGGER.info("password: " + read.getPassword());
	}
	
	@Test(expected=OldPasswordNotMatch.class)
	public void changePassEx1() throws PasswordException {
		User user = target.read("mario");
		
		target.changePassword(user, "marco", "luigi", "luigi");
	}
	
	@Test(expected=ConfirmPasswordNotMatch.class)
	public void changePassEx2() throws PasswordException {
		User user = target.read("mario");
		
		target.changePassword(user, "mario", "luigi", "pirandello");
	}
	
	@Test
	public void changePassOk() throws PasswordException {
		User user = target.read("mario");
		
		LOGGER.info(user.getPassword());
		
		target.changePassword(user, "mario", "luigi", "luigi");
		
		LOGGER.info(user.getPassword());
		
		assertEquals(user.getPassword(), Md5Helper.hashPassword("luigi"));
	}
 
}
