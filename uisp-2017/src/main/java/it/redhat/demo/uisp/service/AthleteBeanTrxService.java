/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

/**
 * Created by fabio on 18/08/2017.
 */
package it.redhat.demo.uisp.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import it.redhat.demo.uisp.entity.Athlete;
import it.redhat.demo.uisp.entity.SportClub;
import it.redhat.demo.uisp.service.exception.UispNotFoundException;
import org.slf4j.Logger;

import static java.util.stream.Collectors.groupingBy;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AthleteBeanTrxService {

    public static final int CHUNK_SIZE = 50;

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    public void deleteAthletesChunked(List<Athlete> athletes) {

        log.info("deleting {} athletes", athletes.size());
        AtomicInteger counter = new AtomicInteger();

        athletes.stream()
                .collect(groupingBy(x->counter.getAndIncrement()/ CHUNK_SIZE))
                .values().forEach(group -> this.deleteAthletes(group));

    }

    public void insertAthletesChunked(List<Athlete> athletes) {

        AtomicInteger counter = new AtomicInteger();

        athletes.stream()
            .collect(groupingBy(x->counter.getAndIncrement()/ CHUNK_SIZE))
            .values().forEach(group -> this.insertAthletes(group));

    }

    public void insertAthletes(List<Athlete> athletes) {

        try {

            ut.begin();
            em.joinTransaction();

            for (Athlete athlete : athletes) {
                em.persist(athlete);
            }

            ut.commit();

            // clear caches
            em.clear();

        } catch (Exception ex) {

            try {
                ut.rollback();
            } catch (SystemException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteAthletes(List<Athlete> athletes) {

        log.info("deleting (on single transactional chunk) {} athletes", athletes.size());

        try {

            ut.begin();
            em.joinTransaction();

            for (Athlete athlete : athletes) {
                athlete = em.merge(athlete);
                em.remove(athlete);
            }

            ut.commit();

            // clear caches
            em.clear();

        } catch (Exception ex) {

            try {
                ut.rollback();
            } catch (SystemException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void associate(String athleteUispCode, String clubCode) throws UispNotFoundException {

        try {

            ut.begin();
            em.joinTransaction();

            List<Athlete> athleteList = em.createQuery("select a from Athlete a where uispCode = :uispCode", Athlete.class)
                    .setParameter("uispCode", athleteUispCode).getResultList();
            if (athleteList.isEmpty()) {
                throw new UispNotFoundException("Athlete " + athleteUispCode + " not found");
            }

            List<SportClub> sportClubs = em.createQuery("select a from SportClub a where code = :code", SportClub.class)
                    .setParameter("code", clubCode)
                    .getResultList();

            if (sportClubs.isEmpty()) {
                throw new UispNotFoundException("Club " + clubCode + " not found");
            }

            Athlete athlete = athleteList.get(0);
            SportClub sportClub = sportClubs.get(0);

            athlete.setClub(sportClub);
            sportClub.add(athlete);

            ut.commit();

            // clear caches
            em.clear();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {

            try {
                ut.rollback();
            } catch (SystemException e1) {
                throw new RuntimeException(e);
            }
        }
    }

}
