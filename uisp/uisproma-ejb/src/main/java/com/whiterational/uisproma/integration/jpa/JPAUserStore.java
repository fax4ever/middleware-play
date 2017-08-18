package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import com.whiterational.uisproma.business.entity.User;
import com.whiterational.uisproma.business.store.UserStore;

public class JPAUserStore extends JPAStore<String, User> implements UserStore {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8785585470001057045L;

  @Override
  public boolean isValid(String username, String password) {
    List<?> results = entityManager.createQuery("select u from User u where u.username=:username and u.password=:password")
        .setParameter("username", username).setParameter("password", password).getResultList();

    return !results.isEmpty();
  }

  @Override
  public List<User> findWithName(String name) {
    List<User> result = (List<User>) entityManager.createQuery("select u from User u where u.username LIKE :username", User.class)
        .setParameter("username", name).getResultList();

    return result;
  }

}
