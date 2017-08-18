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

package it.redhat.demo.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import it.redhat.demo.entity.Car;

@Stateless
@TransactionManagement( TransactionManagementType.BEAN )
public class CarService {

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    public String saveCar(String frame, String plate, String description) {

        try {

            ut.begin();
            em.joinTransaction();

            Car car = new Car();
            car.setFrameNumber(frame);
            car.setLicencePlate(plate);
            car.setDescription(description);

            em.persist(car);

            ut.commit();

            return car.getId();

        } catch (Exception ex) {

            try {
                ut.rollback();
            } catch (SystemException e) {
                throw new RuntimeException(e);
            }

            return null;

        }

    }

    public Car getCar(String id) {

        return em.find(Car.class, id);

    }

    public List<Car> getCars() {

        return em.createQuery("select c from Car c", Car.class).getResultList();

    }

}
