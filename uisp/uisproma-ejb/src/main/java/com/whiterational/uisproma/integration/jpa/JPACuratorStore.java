package com.whiterational.uisproma.integration.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.whiterational.uisproma.business.entity.Curator;
import com.whiterational.uisproma.business.entity.Curator_;
import com.whiterational.uisproma.business.entity.User_;
import com.whiterational.uisproma.business.store.CuratorStore;

public class JPACuratorStore extends JPAStore<Long, Curator> implements CuratorStore {

  /**
   * 
   */
  private static final long serialVersionUID = 2663811396893191975L;

  @Override
  public Curator findByUsername(String username) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Curator> criteria = cb.createQuery(Curator.class);
    Root<Curator> curator = criteria.from(Curator.class);
    criteria.where(cb.equal(curator.get(Curator_.user).get(User_.username), username));
    TypedQuery<Curator> query = entityManager.createQuery(criteria);
    List<Curator> resultList = query.getResultList();
    
    return (resultList.size() == 0) ? null : resultList.get(0);
  }

}
