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

import it.redhat.demo.play.entity.Hike;
import it.redhat.demo.play.entity.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@Stateless
public class HikeRepository {

    private final static Logger LOG = LoggerFactory.getLogger(HikeRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Hike createHike(Hike hike, Trip recommendedTrip) {

        if ( recommendedTrip != null ) {
            recommendedTrip = entityManager.merge( recommendedTrip );
            hike.recommendedTrip = recommendedTrip;
            recommendedTrip.availableHikes.add( hike );
        }

        entityManager.persist( hike );
        return hike;
    }

    public Hike saveHike(Hike hike) {
        entityManager.persist( hike );
        return hike;
    }

    public Trip createTrip(Trip trip) {
        entityManager.persist( trip );
        return trip;
    }

    public Hike getHikeById(long hikeId) {
        return entityManager.find( Hike.class, hikeId );
    }

    public Trip getTripById(long tripId) {
        return entityManager.find(Trip.class, tripId);
    }

    public Trip getTripByIdWithHikes(long tripId) {
        Trip trip = entityManager.find(Trip.class, tripId);

        LOG.info("Trip \"{}\" has {} available hikes!", trip.name, trip.availableHikes.size());
        return trip;
    }

    public Hike addHikeToTrip(Hike hike, Trip recommendedTrip) {

        recommendedTrip = entityManager.merge(recommendedTrip);
        hike = entityManager.merge(hike);

        hike.recommendedTrip = recommendedTrip;
        recommendedTrip.availableHikes.add( hike );

        return hike;

    }

}
