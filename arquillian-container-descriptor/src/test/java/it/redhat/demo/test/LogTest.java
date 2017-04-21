package it.redhat.demo.test;

import it.redhat.demo.producer.LogProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@RunWith(Arquillian.class)
public class LogTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(LogProducer.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private Logger log;

    @Test
    @InSequence(1)
    public void test_logIsProduced() {

        assertNotNull(log);
        log.info("I'm not null! - I'm a real logger instance");

    }

}
