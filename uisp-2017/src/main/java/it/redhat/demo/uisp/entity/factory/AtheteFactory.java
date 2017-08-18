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
package it.redhat.demo.uisp.entity.factory;

import java.util.ArrayList;
import java.util.Calendar;

import it.redhat.demo.uisp.entity.Address;
import it.redhat.demo.uisp.entity.Athlete;

public class AtheteFactory {

    public ArrayList<Athlete> buildAthletes(Integer size) {

        ArrayList<Athlete> athletes = new ArrayList<>();
        for (int i=0; i<size; i++) {
            athletes.add(buildAthlete(i+1));
        }

        return athletes;

    }

    public Athlete buildAthlete(int index) {

        String code = String.format("%09d", index);

        Athlete athlete = new Athlete();

        athlete.setUispCode(code);
        athlete.setName("Name " + index);
        athlete.setSurname("Surname " + index);
        athlete.setHometown("B City " + index);
        athlete.setTaxCode(code);
        athlete.setBirthDate(Calendar.getInstance());
        athlete.setEmail("ciao@gmail.com");

        Address address = new Address();

        address.setCity("A City " + index);
        address.setState("State " + index);
        address.setStreet("Street " + index);
        address.setZip("12345");

        athlete.setAddress(address);

        return athlete;

    }

}
