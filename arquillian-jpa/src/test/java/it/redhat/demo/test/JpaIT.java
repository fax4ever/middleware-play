package it.redhat.demo.test;

import it.redhat.demo.entity.PersonEntity;
import it.redhat.demo.producer.ResourcesProducer;
import it.redhat.demo.stateless.PersonStateless;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */
@RunWith(Arquillian.class)
public class JpaIT {

    private static final Logger LOG = LoggerFactory.getLogger(JpaIT.class);

    @Deployment
    public static WebArchive create() {

        return ShrinkWrap.create(WebArchive.class)
            .addClass(ResourcesProducer.class)
            .addClass(PersonEntity.class)
            .addClass(PersonStateless.class)
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource("test-ds.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    private PersonStateless service;

    @Inject
    private Logger log;

    private Long id;

    @Test
    @ApplyScriptBefore("test-data.sql")
    @InSequence(1)
    public void test_create() throws Exception {

        PersonEntity person = new PersonEntity();
        person.setName("Fabio Massimo");
        person.setSurname("Ercoli");
        person.setBirth(new Date());
        person.setCompany("Red Hat");
        person.setRole("Associate Middleware Consultant");

        service.save(person);
        log.info("create new person {}", person);

        id = person.getId();
        assertNotNull(id);

        person = service.load(id);
        log.info("person loaded {}", person);

        assertNotNull(person);
        assertEquals("Red Hat", person.getCompany());

        person.setSurname("Rossi");
        service.update(person);
        log.info("person updated {}", person);

        assertNotNull(person);
        assertEquals("Rossi", person.getSurname());

    }


}
