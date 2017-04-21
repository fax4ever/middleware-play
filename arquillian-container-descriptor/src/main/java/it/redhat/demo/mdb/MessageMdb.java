package it.redhat.demo.mdb;

import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@MessageDriven(name = "EntrypointMdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/message"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class MessageMdb implements MessageListener {

    @Inject
    private Logger log;

    @Override
    public void onMessage(Message message) {

        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                log.trace("Received Message from queue: {}", msg.getText());
            } else {
                throw new RuntimeException("Message of wrong type " + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

}
