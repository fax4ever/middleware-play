package it.redhat.demo.exception;

import javax.jms.Message;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */
public class WrongMessageType extends RuntimeException {

    public WrongMessageType(Message message) {
        super("Message of wrong type " + message.getClass().getName());
    }

}
