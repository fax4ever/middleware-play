package it.redhat.demo.ogm;

import it.redhat.demo.ogm.entity.Dog;
import it.redhat.demo.ogm.service.JavaSEService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@RunWith(JUnit4.class)
public class TestCache {

    @Test
    public void run() throws Exception {

        JavaSEService javaSEService = new JavaSEService();

        javaSEService.open();

        try {
            Long dinaId = javaSEService.createDina();
            Dog dina = javaSEService.loadDina(dinaId);

            assertNotNull(dina);

        } finally {
            javaSEService.close();
        }


    }

}
