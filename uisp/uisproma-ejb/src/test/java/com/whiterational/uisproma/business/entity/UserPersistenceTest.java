package com.whiterational.uisproma.business.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.entity.User_;

@RunWith(Arquillian.class)
public class UserPersistenceTest {

	private static final String[]	USER_NAMES	= { "Super Mario Brothers", "Mario Kart", "F-Zero" };
	private static final Logger		LOGGER			= LoggerFactory.getLogger(UserPersistenceTest.class);

	@PersistenceContext
	private EntityManager					em;

	@Inject
	private UserTransaction				utx;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(User.class.getPackage())
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

	@Test
	public void shouldFindAllUsersUsingJpqlQuery() throws Exception {
		// given
		String fetchingAllUsersInJpql = "select u from User u order by u.username";

		// when
		LOGGER.info("Selecting (using JPQL)...");
		List<User> users = em.createQuery(fetchingAllUsersInJpql, User.class).getResultList();

		// then
		LOGGER.info("Found " + users.size() + " users (using JPQL):");
		assertContainsAllUsers(users);
	}

	@Test
	public void shouldFindAllUsersUsignCriteria() throws Exception {
		// given
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> user = criteria.from(User.class);
		criteria.select(user);
		criteria.orderBy(builder.asc(user.get(User_.username)));

		// when
		LOGGER.info("Selecting (using Criteria)...");
		List<User> users = em.createQuery(criteria).getResultList();

		// then
		LOGGER.info("Found " + users.size() + " users (using Criteria):");
		assertContainsAllUsers(users);
	}

	private static void assertContainsAllUsers(Collection<User> retrievedUsers) {
		Assert.assertEquals(USER_NAMES.length, retrievedUsers.size());
		final Set<String> retrivedUserNames = new HashSet<String>();

		for (User user : retrievedUsers) {
			LOGGER.info("* " + user);
			retrivedUserNames.add(user.getUsername());
		}

		Assert.assertTrue(retrivedUserNames.containsAll(Arrays.asList(USER_NAMES)));
	}

}
