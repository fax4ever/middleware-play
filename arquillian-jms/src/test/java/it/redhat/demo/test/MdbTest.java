package it.redhat.demo.test;

import it.redhat.demo.exception.WrongMessageType;
import it.redhat.demo.mdb.MessageMdb;
import it.redhat.demo.producer.LogProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;

import static org.junit.Assert.*;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@RunWith(Arquillian.class)
public class MdbTest {

    public static final int TIMEOUT = 10000;

    @Deployment
    public static WebArchive create() {

        return ShrinkWrap.create(WebArchive.class)
           .addClass(LogProducer.class)
           .addClass(MessageMdb.class)
           .addClass(WrongMessageType.class)
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

    @Resource(mappedName = "java:/jms/queue/DLQ")
    private Queue dlq;

    @Test
    public void test_good() throws Exception {

        jms.createProducer().send(requestQueue, "Fabio");

        Message receive = jms.createConsumer(responseQueue).receive(TIMEOUT);
        assertNotNull(receive);

        if (!(receive instanceof TextMessage)) {
            fail("wrong message type");
        }

        TextMessage textMessage = (TextMessage)receive;
        String text = textMessage.getText();
        log.info(text);

        assertEquals("Ciao Fabio", text);

    }

    @Test
    public void test_wrong_message_type() throws Exception {

        jms.createProducer().send(requestQueue, 739);

        Message receive = jms.createConsumer(dlq).receive(TIMEOUT);
        assertNotNull(receive);

        log.info("message type {}", receive.getClass());

        if (!(receive instanceof ObjectMessage)) {
            fail("wrong message type");
        }

        ObjectMessage textMessage = (ObjectMessage)receive;
        Object value = textMessage.getObject();
        log.info("dlq content message {}", value);

        assertEquals(739, value);

    }


}
