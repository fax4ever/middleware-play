package it.redhat.demo.mdb;

import it.redhat.demo.exception.WrongMessageType;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@MessageDriven(name = "MessageMdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/REQ"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class MessageMdb implements MessageListener {

    @Inject
    private Logger log;

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext jms;

    @Resource(mappedName = "java:/jms/queue/RES")
    private Queue responseQueue;

    @Override
    public void onMessage(Message message) {

        String text;

        try {
            if (!(message instanceof TextMessage)) {
                throw new WrongMessageType(message);
            }

            TextMessage msg = (TextMessage) message;
            text = msg.getText();

            log.trace("Received Message from queue: {}", msg.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        jms.createProducer().send(responseQueue, "Ciao " + text);

    }

}
