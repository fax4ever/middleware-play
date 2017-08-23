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

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import javax.inject.Inject;

import it.redhat.demo.play.entity.Hike;
import it.redhat.demo.play.entity.Trip;
import it.redhat.demo.play.repository.HikeRepository;
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
public class HikeIT {

    private static final Logger LOG = LoggerFactory.getLogger(HikeIT.class);

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
    private HikeRepository repository;

    @Test
    public void test_createTrip_createLinkedHike() {

        LocalDate localDateStart = LocalDate.of(2017, Month.AUGUST, 1);
        LocalDate localDateEnd = LocalDate.of(2017, Month.AUGUST, 31);

        Date startTrip = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endTrip = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Trip trip = new Trip();
        trip.name = "Alta via delle dolomiti";
        trip.startDate = startTrip;
        trip.endDate = endTrip;
        trip.price = 50.5;

        repository.createTrip(trip);

        Hike hike = new Hike("Selva di Val Gardena", "Sassolungo");

        repository.createHike(hike, trip);

        // reload entities
        long tripId = trip.id;
        long hikeId = hike.id;

        // verify loaded trip
        trip = repository.getTripByIdWithHikes(tripId);
        LOG.info("Loaded TRIP {}", trip);

        assertNotNull(trip);
        assertEquals(tripId, trip.id);
        assertEquals("Alta via delle dolomiti", trip.name);
        assertEquals(startTrip, trip.startDate);
        assertEquals(endTrip, trip.endDate);
        assertEquals(50.5, trip.price, 1);

        Set<Hike> availableHikes = trip.availableHikes;
        assertEquals(1, availableHikes.size());

        Hike linkedHike = availableHikes.iterator().next();
        assertEquals(hikeId, linkedHike.id);
        assertEquals("Selva di Val Gardena", linkedHike.start);
        assertEquals("Sassolungo", linkedHike.destination);

        // verify loaded hike
        hike = repository.getHikeById(hikeId);
        LOG.info("Loaded HIKE {}", hike);

        assertNotNull(hike);
        assertEquals(hikeId, hike.id);
        assertEquals("Selva di Val Gardena", hike.start);
        assertEquals("Sassolungo", hike.destination);

        Trip linkedTrip = hike.recommendedTrip;

        assertNotNull(linkedTrip);
        assertEquals(tripId, linkedTrip.id);
        assertEquals("Alta via delle dolomiti", linkedTrip.name);
        assertEquals(startTrip, linkedTrip.startDate);
        assertEquals(endTrip, linkedTrip.endDate);
        assertEquals(50.5, linkedTrip.price, 1);

    }

    @Test
    public void test_createTrip_createHike_link() {

        LocalDate localDateStart = LocalDate.of(2017, Month.AUGUST, 1);
        LocalDate localDateEnd = LocalDate.of(2017, Month.AUGUST, 31);

        Date startTrip = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endTrip = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Trip trip = new Trip();
        trip.name = "Alta via delle dolomiti";
        trip.startDate = startTrip;
        trip.endDate = endTrip;
        trip.price = 50.5;

        repository.createTrip(trip);

        Hike hike = new Hike("Selva di Val Gardena", "Sassolungo");

        repository.saveHike(hike);

        repository.addHikeToTrip(hike, trip);

        // reload entities
        long tripId = trip.id;
        long hikeId = hike.id;

        // verify loaded trip
        trip = repository.getTripByIdWithHikes(tripId);
        LOG.info("Loaded TRIP {}", trip);

        assertNotNull(trip);
        assertEquals(tripId, trip.id);
        assertEquals("Alta via delle dolomiti", trip.name);
        assertEquals(startTrip, trip.startDate);
        assertEquals(endTrip, trip.endDate);
        assertEquals(50.5, trip.price, 1);

        Set<Hike> availableHikes = trip.availableHikes;
        assertEquals(1, availableHikes.size());

        Hike linkedHike = availableHikes.iterator().next();
        assertEquals(hikeId, linkedHike.id);
        assertEquals("Selva di Val Gardena", linkedHike.start);
        assertEquals("Sassolungo", linkedHike.destination);

        // verify loaded hike
        hike = repository.getHikeById(hikeId);
        LOG.info("Loaded HIKE {}", hike);

        assertNotNull(hike);
        assertEquals(hikeId, hike.id);
        assertEquals("Selva di Val Gardena", hike.start);
        assertEquals("Sassolungo", hike.destination);

        Trip linkedTrip = hike.recommendedTrip;

        assertNotNull(linkedTrip);
        assertEquals(tripId, linkedTrip.id);
        assertEquals("Alta via delle dolomiti", linkedTrip.name);
        assertEquals(startTrip, linkedTrip.startDate);
        assertEquals(endTrip, linkedTrip.endDate);
        assertEquals(50.5, linkedTrip.price, 1);

    }

}
