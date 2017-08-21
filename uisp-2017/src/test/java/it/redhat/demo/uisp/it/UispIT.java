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
 * Created by fabio on 21/08/2017.
 */
package it.redhat.demo.uisp.it;

import java.util.List;
import javax.inject.Inject;

import it.redhat.demo.uisp.entity.Athlete;
import it.redhat.demo.uisp.entity.SportClub;
import it.redhat.demo.uisp.factory.ObjectHelper;
import it.redhat.demo.uisp.service.AthleteBeanTrxService;
import it.redhat.demo.uisp.service.AthleteContTrxService;
import it.redhat.demo.uisp.service.ClubBeanTrxService;
import it.redhat.demo.uisp.service.ClubContTrxService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

@RunWith(Arquillian.class)
public class UispIT {

    @Deployment
    public static WebArchive create() {

        return ShrinkWrap.create(WebArchive.class)
            .addPackages(true, "it.redhat.demo.uisp.cdi", "it.redhat.demo.uisp.entity", "it.redhat.demo.uisp.factory", "it.redhat.demo.uisp.service")
            .addAsResource("META-INF/persistence.xml")
            .addAsResource("hotrodclient.properties")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource("jboss-deployment-structure.xml");

    }

    @Inject
    protected Logger log;

    @Inject
    protected AthleteBeanTrxService athleteBeanTrxService;

    @Inject
    protected AthleteContTrxService athleteContTrxService;

    @Inject
    protected ClubBeanTrxService clubBeanTrxService;

    @Inject
    protected ClubContTrxService clubContTrxService;

    @Before
    @After
    public void clearAll() {

        // clear athletes
        List<Athlete> athletes = athleteContTrxService.getBulk();
        athleteBeanTrxService.deleteAthletesChunked(athletes);

        // clear clubs
        List<SportClub> clubs = clubContTrxService.getBulk();
        clubBeanTrxService.deleteClubsChunked(clubs);

        log.info("{} athletes deleted", athletes.size());
        log.info("{} clubs deleted", clubs.size());

    }

    @Test
    public void insertClubs() {

        List<Athlete> athletes = athleteContTrxService.getBulk();
        List<SportClub> clubs = clubContTrxService.getBulk();

        Assert.assertEquals(0, athletes.size());
        Assert.assertEquals(0, clubs.size());

        clubs = ObjectHelper.buildClubs(10);
        clubBeanTrxService.insertClubs(clubs);

        clubs = clubContTrxService.getBulk();
        Assert.assertEquals(10, clubs.size());

        // insertAthletes

        // create 100 athletes
        athletes = ObjectHelper.buildAthletes(100);

        // get all sport club (10)
        clubs = clubContTrxService.getBulk();

        // associate a club
        for (int i=0; i<athletes.size(); i++) {

            int clubIndex = i % 10;
            SportClub sportClub = clubs.get(clubIndex);
            Athlete athlete = athletes.get(i);

            athlete.setClub(sportClub);
            sportClub.add(athlete);

            athleteContTrxService.update(athlete);

        }

        //verify100athletesFor10club
        athletes = athleteContTrxService.getBulk();
        Assert.assertEquals(100, athletes.size());

        // reload clubs
        clubs = clubContTrxService.getBulk();

        // verify than each club has 10 athletes
        for (SportClub club : clubs) {

            Assert.assertEquals(10, club.getAthletes().size());

        }
    }

    @Test
    public void repeatAllInSingleTrx() {

        List<Athlete> athletes = athleteContTrxService.getBulk();
        List<SportClub> clubs = clubContTrxService.getBulk();

        Assert.assertEquals(0, athletes.size());
        Assert.assertEquals(0, clubs.size());

        athletes = ObjectHelper.buildAthletes(100);
        clubs = ObjectHelper.buildClubs(10);

        // associate a club
        for (int i = 0; i < athletes.size(); i++) {

            int clubIndex = i % 10;
            SportClub sportClub = clubs.get(clubIndex);
            Athlete athlete = athletes.get(i);

            athlete.setClub(sportClub);
            sportClub.add(athlete);
        }

        athleteContTrxService.saveAll(athletes);

        //verify100athletesFor10club
        athletes = athleteContTrxService.getBulk();
        Assert.assertEquals(100, athletes.size());

        // reload clubs
        clubs = clubContTrxService.getBulk();

        // verify than each club has 10 athletes
        for (SportClub club : clubs) {

            Assert.assertEquals(10, club.getAthletes().size());

        }
    }

}
