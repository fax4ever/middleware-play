package it.redhat.demo.ogm.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import it.redhat.demo.ogm.entity.Breed;
import it.redhat.demo.ogm.entity.Dog;
import org.jboss.logging.Logger;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class JavaSEService {

    private static final Logger LOG = Logger.getLogger(JavaSEService.class);

    private final TransactionManager tm;
    private EntityManagerFactory emf;

    public JavaSEService() {
        tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
    }

    public void open() {
        emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
    }

    public Long createDina() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        //Persist entities the way you are used to in plain JPA
        tm.begin();
        LOG.infof("About to store dog and breed");
        EntityManager em = emf.createEntityManager();
        Breed collie = new Breed();
        collie.setName("Collie");
        em.persist(collie);
        Dog dina = new Dog();
        dina.setName("Dina");
        dina.setBreed(collie);
        em.persist(dina);
        Long dinaId = dina.getId();
        em.flush();
        em.close();
        tm.commit();

        return dinaId;

    }

    public Dog loadDina(Long dinaId) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        tm.begin();
        LOG.infof("About to retrieve dog and breed");
        EntityManager em = emf.createEntityManager();
        Dog dina = em.find(Dog.class, dinaId);
        LOG.infof("Found dog %s of breed %s", dina.getName(), dina.getBreed().getName());
        em.flush();
        em.close();
        tm.commit();

        return dina;

    }

    public void close() {
        emf.close();
    }

}
