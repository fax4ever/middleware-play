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
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.redhat.demo.uisp.entity.Athlete;
import it.redhat.demo.uisp.entity.SportClub;
import it.redhat.demo.uisp.factory.ObjectHelper;
import it.redhat.demo.uisp.service.exception.UispNotFoundException;

@Stateless
public class AthleteContTrxService {

    @Inject
    private EntityManager em;

    @Inject
    private ClubContTrxService clubService;

    public List<Athlete> getBulk() {

        return em.createQuery("select a from Athlete a", Athlete.class).getResultList();

    }

    public List<Athlete> findBySurname(String surname) {

        return em.createQuery("select a from Athlete a where surname = :surname", Athlete.class)
                .setParameter("surname", surname).getResultList();
    }

    public List<Athlete> findByUispCode(String uispCode) {

        return em.createQuery("select a from Athlete a where uispCode = :uispCode", Athlete.class)
                .setParameter("uispCode", uispCode).getResultList();
    }

    public List<Athlete> findByUispcodeAndSurname(String uispCode, String surname) {

        return em.createQuery("select a from Athlete a where uispCode = :uispCode and surname = :surname",
                              Athlete.class)
                .setParameter("uispCode", uispCode)
                .setParameter("surname", surname)
                .getResultList();
    }

    public void deleteByUispCode(String uispCode) {

        findByUispCode(uispCode)
                .stream()
                .forEach(athlete -> em.remove(athlete));
    }

    public List<Athlete> findByParams(AthleteBeanParams params) {

        if (params.getSurname() != null && params.getUispCode() != null) {
            return findByUispcodeAndSurname(params.getUispCode(),
                                            params.getSurname());
        } else if (params.getSurname() != null) {
            return findBySurname(params.getSurname());
        } else if (params.getUispCode() != null) {
            return findByUispCode(params.getUispCode());
        } else {
            return getBulk();
        }
    }

    public void createOneAthleteOneClub() {

        // remove all entities
        List<Athlete> athleteList = getBulk();
        athleteList.stream().forEach(athlete -> em.remove(athlete));

        List<SportClub> sportClubs = em.createQuery("select a from SportClub a", SportClub.class)
                .getResultList();
        sportClubs.stream().forEach(sportClub -> em.remove(sportClub));

        Athlete athlete = ObjectHelper.buildAthlete(1);
        SportClub sportClub = ObjectHelper.buildClub(1);

        em.persist(sportClub);
        em.persist(athlete);

    }

    public void associate(String athleteUispCode, String clubCode) throws UispNotFoundException {

        List<Athlete> athleteList = findByUispCode(athleteUispCode);
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

    }

    public void createOneAthleteOneClubAndAssociate() {

        // remove all entities
        List<Athlete> athleteList = getBulk();
        athleteList.stream().forEach(athlete -> em.remove(athlete));

        List<SportClub> sportClubs = em.createQuery("select a from SportClub a", SportClub.class)
                .getResultList();
        sportClubs.stream().forEach(sportClub -> em.remove(sportClub));

        Athlete athlete = ObjectHelper.buildAthlete(1);
        SportClub sportClub = ObjectHelper.buildClub(1);

        athlete.setClub(sportClub);
        sportClub.add(athlete);

        em.persist(sportClub);
        em.persist(athlete);

    }

    public void update(Athlete athlete) {

        em.merge(athlete);

    }

    public void saveAll(List<Athlete> athletes) {

        athletes.stream().forEach(athlete -> em.persist(athlete));

    }

}
