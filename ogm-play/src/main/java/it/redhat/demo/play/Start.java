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

package it.redhat.demo.play;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import it.redhat.demo.entity.Car;
import it.redhat.demo.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class Start {

    private static final Logger LOG = LoggerFactory.getLogger(Start.class);

    @Inject
    private CarService service;

    @PostConstruct
    public void init() {
        LOG.info("Start");

        Car car = new Car();
        String id = UUID.randomUUID().toString();
        car.setId(id);
        car.setFrameNumber("2989398h3i3");
        car.setLicencePlate("DX339XC");

        service.saveCar(car);

        Car loadCar = service.getCar(id);

        LOG.info("car load: {}", loadCar);

    }

}
