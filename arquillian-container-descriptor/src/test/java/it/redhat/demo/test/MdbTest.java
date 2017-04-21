package it.redhat.demo.test;

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
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@RunWith(Arquillian.class)
public class MdbTest {

    @Deployment
    public static WebArchive create() {

        return ShrinkWrap.create(WebArchive.class)
               .addClass(LogProducer.class)
               .addAsWebInfResource("test-ds.xml")
               .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    private Logger log;

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext jms;

    @Resource(mappedName = "java:/jms/queue/DLQ")
    private Queue dlq;

    @Test
    @InSequence(1)
    public void test_sendMessage() {

        jms.createProducer().send(dlq, "CIAO");
        log.info("message sent");

    }

}
