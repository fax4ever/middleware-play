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
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.redhat.demo.uisp.entity.SportClub;

@Stateless
public class ClubContTrxService {

    @Inject
    private EntityManager em;

    public List<SportClub> getBulk() {

        return em.createQuery("select a from SportClub a", SportClub.class).getResultList();

    }

    public List<SportClub> fingByCode(String code) {

        return em.createQuery("select a from SportClub a where code = :code", SportClub.class)
                .setParameter("code", code)
                .getResultList();

    }

}
