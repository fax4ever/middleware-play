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
package it.redhat.demo.rest.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.redhat.demo.entity.Car;

public class CarView {

    private final String id;
    private final String licencePlate;
    private final String frameNumber;
    private final Date dateInsert;
    private final String description;

    public CarView(Car entity) {
        id = entity.getId();
        licencePlate = entity.getLicencePlate();
        frameNumber = entity.getFrameNumber();
        dateInsert = entity.getDateInsert();
        description = entity.getDescription();
    }

    public String getId() {
        return id;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public String getDescription() {
        return description;
    }

    public static List<CarView> toView(List<Car> entities) {
        return entities.stream().map(car -> new CarView(car)).collect(Collectors.toList());
    }

}
