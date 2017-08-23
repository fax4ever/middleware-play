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
package it.redhat.demo.play.it;

import java.util.List;
import javax.inject.Inject;

import it.redhat.demo.play.entity.Athlete;
import it.redhat.demo.play.entity.Club;
import it.redhat.demo.play.repository.AthleteRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@RunWith(Arquillian.class)
public class AthleteIT {

    private static final Logger LOG = LoggerFactory.getLogger(AthleteIT.class);

    @Deployment
    public static WebArchive create() {

        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, "it.redhat.demo.play")
            .addAsResource("META-INF/persistence.xml")
            .addAsResource("hotrodclient.properties")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource("jboss-deployment-structure.xml");

    }

    @Inject
    private AthleteRepository repository;

    @Test
    public void test_createClub_createLinkedAthlete() {

        Club club = new Club();
        club.setCode("RM739");

        repository.createClub(club);

        Athlete athlete = new Athlete();
        athlete.setUispCode("123456789");

        repository.createAthlete(athlete, club);

        // reload entities
        long clubId = club.getId();
        long athleteId = athlete.getId();

        // verify loaded club
        club = repository.getClubByIdWithAthletes(clubId);
        LOG.info("Loaded CLUB {}", club);

        assertNotNull(club);
        assertEquals(clubId, club.getId());
        assertEquals("RM739", club.getCode());

        List<Athlete> athletes = club.getAthletes();
        assertEquals(1, athletes.size());

        Athlete linkedAthlete = athletes.iterator().next();
        assertEquals(athleteId, linkedAthlete.getId());
        assertEquals("123456789", linkedAthlete.getUispCode());

        // verify loaded athlete
        athlete = repository.getAthleteById(athleteId);
        LOG.info("Loaded ATHLETE {}", athlete);

        assertNotNull(athlete);
        assertEquals(athleteId, athlete.getId());
        assertEquals("123456789", athlete.getUispCode());

        Club linkedClub = athlete.getClub();

        assertNotNull(linkedClub);
        assertEquals(clubId, linkedClub.getId());
        assertEquals("RM739", linkedClub.getCode());

    }

    @Test
    public void test_createClub_createAthlete_associate() {

        Club club = new Club();
        club.setCode("RM739");

        repository.createClub(club);

        Athlete athlete = new Athlete();
        athlete.setUispCode("123456789");

        repository.saveAthlete(athlete);

        repository.addAthleteToClub(athlete, club);

        // reload entities
        // reload entities
        long clubId = club.getId();
        long athleteId = athlete.getId();

        // verify loaded club
        club = repository.getClubByIdWithAthletes(clubId);
        LOG.info("Loaded CLUB {}", club);

        assertNotNull(club);
        assertEquals(clubId, club.getId());
        assertEquals("RM739", club.getCode());

        List<Athlete> athletes = club.getAthletes();
        assertEquals(1, athletes.size());

        Athlete linkedAthlete = athletes.iterator().next();
        assertEquals(athleteId, linkedAthlete.getId());
        assertEquals("123456789", linkedAthlete.getUispCode());

        // verify loaded athlete
        athlete = repository.getAthleteById(athleteId);
        LOG.info("Loaded ATHLETE {}", athlete);

        assertNotNull(athlete);
        assertEquals(athleteId, athlete.getId());
        assertEquals("123456789", athlete.getUispCode());

        Club linkedClub = athlete.getClub();

        assertNotNull(linkedClub);
        assertEquals(clubId, linkedClub.getId());
        assertEquals("RM739", linkedClub.getCode());

    }

}
