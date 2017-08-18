package com.whiterational.uisproma.business.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.security.Md5Helper;

@RunWith(Arquillian.class)
public abstract class UispServiceTest {
	
	private static final String[]	USER_NAMES	= { "mario", "pellardi", "coppe" };
	private static final Logger LOGGER = LoggerFactory.getLogger(UispServiceTest.class);

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
			.addPackages(true, "com.whiterational.uisproma.business", "com.whiterational.uisproma.integration")
			.addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();

		LOGGER.info("Dumping old records...");
		em.createQuery("delete from User").executeUpdate();
		utx.commit();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();

		LOGGER.info("Inserting records...");
		for (String name : USER_NAMES) {
			User user = new User();
			user.setUsername(name);
			user.setPassword(Md5Helper.hashPassword(name));
			em.persist(user);
		}

		utx.commit();
		em.clear();
	}
	
	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		insertData();
		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

}
