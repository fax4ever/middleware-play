package it.redhat.demo.stateless;

import it.redhat.demo.entity.PersonEntity;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */

@Stateless
public class PersonStateless {

    @Inject
    private Logger log;

    @Inject
    private EntityManager entityManager;

    public PersonEntity load(Long id) {

        return entityManager.find(PersonEntity.class, id);

    }

    public void save(PersonEntity entity) {

        entityManager.persist(entity);

    }

    public void update(PersonEntity entity) {

        entityManager.merge(entity);

    }

    public void delete(PersonEntity entity) {

        entityManager.remove(entity);

    }

}
