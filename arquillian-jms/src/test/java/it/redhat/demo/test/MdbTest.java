package it.redhat.demo.test;

import it.redhat.demo.mdb.MessageMdb;
import it.redhat.demo.producer.LogProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@RunWith(Arquillian.class)
public class MdbTest {

    @Deployment
    public static WebArchive create() {

        return ShrinkWrap.create(WebArchive.class)
           .addClass(LogProducer.class)
           .addClass(MessageMdb.class)
           .addAsWebInfResource("test-jms.xml")
           .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    private Logger log;

    @Inject
    private JMSContext jms;

    @Resource(mappedName = "java:/jms/queue/REQ")
    private Queue requestQueue;

    @Resource(mappedName = "java:/jms/queue/RES")
    private Queue responseQueue;

    @Test
    @InSequence(1)
    public void test_sendMessage() throws Exception {

        jms.createProducer().send(requestQueue, "Fabio");

        Message receive = jms.createConsumer(responseQueue).receive(10000);
        assertNotNull(receive);

        if (!(receive instanceof TextMessage)) {
            fail();
        }

        TextMessage textMessage = (TextMessage)receive;
        String text = textMessage.getText();
        log.info(text);

        assertEquals("Ciao Fabio", text);

    }

}
