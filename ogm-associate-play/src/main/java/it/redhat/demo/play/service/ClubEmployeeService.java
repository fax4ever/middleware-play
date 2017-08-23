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
package it.redhat.demo.play.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.redhat.demo.play.entity.Club;
import it.redhat.demo.play.entity.ClubEmployee;
import it.redhat.demo.play.repo.ClubRepo;
import it.redhat.demo.play.service.exception.UispNotFoundException;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@Stateless
public class ClubEmployeeService {

    @Inject
    private EntityManager em;

    @Inject
    private ClubRepo clubRepo;

    public ClubEmployee create(ClubEmployee employee, String clubCode) throws UispNotFoundException {

        List<Club> clubList = clubRepo.findByCode(clubCode);
        if (clubList.isEmpty()) {
            throw new UispNotFoundException("Club not found. Club code " + clubCode);
        }

        Club club = clubList.get(0);
        club = em.merge(club);

        employee.setCompany(club);
        club.getEmployees().add(employee);

        em.persist(employee);

        return employee;

    }

}
