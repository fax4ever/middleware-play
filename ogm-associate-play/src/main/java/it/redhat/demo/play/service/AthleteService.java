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

import it.redhat.demo.play.entity.Athlete;
import it.redhat.demo.play.entity.Club;
import it.redhat.demo.play.repo.AthleteRepo;
import it.redhat.demo.play.repo.ClubRepo;
import it.redhat.demo.play.service.exception.UispNotFoundException;
import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@Stateless
public class AthleteService {

    @Inject
    private AthleteRepo athleteRepo;

    @Inject
    private ClubRepo clubRepo;

    @Inject
    private EntityManager em;

    @Inject
    private Logger log;

    public Athlete joinClub(String uispCode, String clubCode) throws UispNotFoundException {

        List<Athlete> athleteList = athleteRepo.findByUispCode(uispCode);
        if (athleteList.isEmpty()) {
            throw new UispNotFoundException("Athlete not found. Uisp code " + uispCode);
        }

        List<Club> clubList = clubRepo.findByCode(clubCode);
        if (clubList.isEmpty()) {
            throw new UispNotFoundException("Club not found. Club code " + clubCode);
        }

        Athlete athlete = athleteList.get(0);
        Club club = clubList.get(0);

        athlete = em.merge(athlete);
        club = em.merge(club);

        athlete.setClub(club);
        club.getAthletes().add(athlete);

        return athlete;

    }

    public Athlete create(Athlete athlete, String clubCode) throws UispNotFoundException {

        List<Club> clubList = clubRepo.findByCode(clubCode);
        if (clubList.isEmpty()) {
            throw new UispNotFoundException("Club not found. Club code " + clubCode);
        }

        Club club = clubList.get(0);
        club = em.merge(club);

        athlete.setClub(club);
        club.getAthletes().add(athlete);

        em.persist(athlete);

        return athlete;

    }

}
