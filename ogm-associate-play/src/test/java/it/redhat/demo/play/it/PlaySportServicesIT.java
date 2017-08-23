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
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
package it.redhat.demo.play.it;

import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

import it.redhat.demo.play.entity.Address;
import it.redhat.demo.play.entity.Athlete;
import it.redhat.demo.play.entity.Club;
import it.redhat.demo.play.entity.ClubEmployee;
import it.redhat.demo.play.repo.AthleteRepo;
import it.redhat.demo.play.repo.ClubRepo;
import it.redhat.demo.play.repository.AthleteRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PlaySportServicesIT {

    @Deployment
    public static WebArchive create() {
        return ShrinkWrap
            .create(WebArchive.class, "playsport-demo.war")
            .addPackages(true, "it.redhat.demo.play")
            .addAsResource("META-INF/persistence.xml")
            .addAsResource("hotrodclient.properties")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource("jboss-deployment-structure.xml");
    }

    @Inject
    private Logger log;

    @Inject
    private ClubRepo clubRepo;

    @Inject
    private AthleteRepo athleteRepo;

    @Inject
    private AthleteRepository athleteRepository;

    @Test
    @InSequence(1)
    public void insertClubs() throws Exception {

        Club club = new Club("Roma", "RM731", "RM21M2222", "CSSDSD333S");
        clubRepo.add(club);

        String  id = club.getId();
        assertNotNull(id);
        log.info("*** Created sport club with id {}", id);

        // verify load by id
        club = clubRepo.findById(id);
        assertNotNull(club);

        // verify find by name
        List<Club> clubs = clubRepo.findByName("Roma");
        assertFalse(clubs.isEmpty());

        // verify find by code
        clubs = clubRepo.findByCode("RM731");
        assertFalse(clubs.isEmpty());

        clubRepo.add(new Club("Milan", "MI731", "MI21M2222", "CSSDSD333D"));
        clubRepo.add(new Club("Juve", "TO733", "TO21M2222", "CSSDSD323D"));

        // verify get all (bulk)
        clubs = clubRepo.findAll();
        assertEquals(3, clubs.size());

    }

    @Test
    @InSequence(2)
    public void insertAthletes() throws Exception {

        Athlete athlete = new Athlete();
        athlete.setUispCode("123456789");
        athlete.setName("Fabio Massimo");
        athlete.setSurname("Ercoli");
        athlete.setTaxCode("J39d39JD3");
        athlete.setEmail("f.ciao@gmail.com");
        athlete.setHometown("Rome");

        Calendar cal = Calendar.getInstance();
        cal.set(1979, Calendar.NOVEMBER, 21);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via mario iv");
        address.setZip("01921");
        athlete.setAddress(address);

        athleteRepo.add(athlete);
        String id = athlete.getId();

        // verify load by id
        athlete = athleteRepo.findById(id);
        assertNotNull(athlete);

        // verify find by uisp code
        List<Athlete> athletes = athleteRepo.findByUispCode("123456789");
        assertFalse(athletes.isEmpty());

        athlete = new Athlete();
        athlete.setUispCode("987654321");
        athlete.setName("Stefano");
        athlete.setSurname("Linguerri");
        athlete.setTaxCode("J39dW9JD3");
        athlete.setEmail("s.ciao@gmail.com");
        athlete.setHometown("Rome");

        cal = Calendar.getInstance();
        cal.set(1977, Calendar.MARCH, 10);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via dario vii");
        address.setZip("01922");
        athlete.setAddress(address);

        athleteRepo.add(athlete);

        athlete = new Athlete();
        athlete.setUispCode("333111333");
        athlete.setName("Giovanni");
        athlete.setSurname("Marigi");
        athlete.setTaxCode("J19dW9JD3");
        athlete.setEmail("g.ciao@gmail.com");
        athlete.setHometown("Rome");

        cal = Calendar.getInstance();
        cal.set(1981, Calendar.MARCH, 24);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        address = new Address();
        address.setState("Italy");
        address.setCity("Rome");
        address.setStreet("via viola ix");
        address.setZip("01911");
        athlete.setAddress(address);

        athleteRepo.add(athlete);

        // verify get all (bulk)
        athletes = athleteRepo.findAll();
        assertEquals(3, athletes.size());

    }

    @Test
    @InSequence(3)
    public void associateClubToAthletes() throws Exception {

        List<Athlete> athleteList = athleteRepo.findByUispCode("123456789");
        List<Club> clubList = clubRepo.findByCode("RM731");

        assertEquals(1, athleteList.size());
        assertEquals(1, clubList.size());

        athleteRepo.addAthleteToClub(athleteList.get(0), clubList.get(0));

    }

    @Test
    @InSequence(4)
    public void createClubEmployee() throws Exception {

        ClubEmployee clubEmployee = new ClubEmployee();
        clubEmployee.setName("Michele");
        clubEmployee.setSurname("Diodati");
        clubEmployee.setTaxCode("J31d39JD3");
        clubEmployee.setEmail("gg.ciao@gmail.com");
        clubEmployee.setHometown("Milan");

        Calendar cal = Calendar.getInstance();
        cal.set(1979, Calendar.NOVEMBER, 21);
        clubEmployee.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Milan");
        address.setStreet("via tony 2");
        address.setZip("01911");
        clubEmployee.setAddress(address);

        List<Club> clubList = clubRepo.findByCode("TO733");
        assertEquals(1, clubList.size());

        athleteRepository.createEmployee(clubEmployee, clubList.get(0));

    }

    @Test
    @InSequence(5)
    public void createAthleteWithClub() throws Exception {

        Athlete athlete = new Athlete();
        athlete.setUispCode("111222333");
        athlete.setName("Lorenzo");
        athlete.setSurname("Rossi");
        athlete.setTaxCode("J31d39JD3");
        athlete.setEmail("lr.ciao@gmail.com");
        athlete.setHometown("Milan");

        Calendar cal = Calendar.getInstance();
        cal.set(1979, Calendar.NOVEMBER, 21);
        athlete.setBirthDate(cal);

        log.info("BirthDate long value is {}", cal.getTimeInMillis());

        Address address = new Address();
        address.setState("Italy");
        address.setCity("Milan");
        address.setStreet("via tony 2");
        address.setZip("01911");
        athlete.setAddress(address);

        List<Club> clubList = clubRepo.findByCode("RM731");
        assertEquals(1, clubList.size());

        athleteRepo.createAthlete(athlete, clubList.get(0));

    }

}
