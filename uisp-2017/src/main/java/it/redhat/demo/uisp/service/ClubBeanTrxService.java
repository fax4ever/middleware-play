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
 * Created by fabio on 20/08/2017.
 */
package it.redhat.demo.uisp.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import it.redhat.demo.uisp.entity.SportClub;
import org.slf4j.Logger;

import static java.util.stream.Collectors.groupingBy;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ClubBeanTrxService {

    public static final int CHUNK_SIZE = 50;

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    public void deleteClubsChunked(List<SportClub> clubs) {

        log.info("deleting {} clubs", clubs.size());
        AtomicInteger counter = new AtomicInteger();

        clubs.stream()
                .collect(groupingBy(x->counter.getAndIncrement()/ CHUNK_SIZE))
                .values().forEach(group -> this.deleteClubs(group));

    }

    public void insertClubsChunked(List<SportClub> clubs) {

        AtomicInteger counter = new AtomicInteger();

        clubs.stream()
                .collect(groupingBy(x->counter.getAndIncrement()/ CHUNK_SIZE))
                .values().forEach(group -> this.insertClubs(group));

    }

    public void insertClubs(List<SportClub> clubs) {

        try {

            ut.begin();
            em.joinTransaction();

            for (SportClub club : clubs) {
                em.persist(club);
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

    public void deleteClubs(List<SportClub> clubs) {

        log.info("deleting (on single transactional chunk) {} clubs", clubs.size());

        try {

            ut.begin();
            em.joinTransaction();

            for (SportClub club : clubs) {
                club = em.merge(club);
                em.remove(club);
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

}
