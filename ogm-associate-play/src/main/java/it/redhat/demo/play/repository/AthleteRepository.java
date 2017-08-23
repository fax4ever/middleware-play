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
package it.redhat.demo.play.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.redhat.demo.play.entity.Athlete;
import it.redhat.demo.play.entity.Club;
import it.redhat.demo.play.entity.ClubEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@Stateless
public class AthleteRepository {

    private final static Logger LOG = LoggerFactory.getLogger(AthleteRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Athlete createAthlete(Athlete athlete, Club club) {

        if ( club != null ) {
            club = entityManager.merge( club );
            athlete.setClub(club);
            club.getAthletes().add( athlete );
        }

        entityManager.persist( athlete );
        return athlete;
    }

    public ClubEmployee createEmployee(ClubEmployee clubEmployee, Club club) {

        if ( club != null ) {
            club = entityManager.merge( club );
            clubEmployee.setCompany(club);
            club.getEmployees().add( clubEmployee );
        }

        entityManager.persist( clubEmployee );
        return clubEmployee;
    }

    public Athlete saveAthlete(Athlete athlete) {
        entityManager.persist( athlete );
        return athlete;
    }

    public Club createClub(Club club) {
        entityManager.persist( club );
        return club;
    }

    public Athlete getAthleteById(String athleteId) {
        return entityManager.find( Athlete.class, athleteId );
    }

    public Club getClubById(String clubId) {
        return entityManager.find( Club.class, clubId );
    }

    public Club getClubByIdWithAthletes(String clubId) {
        Club club = entityManager.find(Club.class, clubId);

        LOG.info("Club \"{}\" has {} athletes!", club.getCode(), club.getAthletes().size());
        return club;
    }

    public Athlete addAthleteToClub(Athlete athlete, Club club) {

        club = entityManager.merge(club);
        athlete = entityManager.merge(athlete);

        athlete.setClub(club);
        club.getAthletes().add( athlete );

        return athlete;

    }

}
