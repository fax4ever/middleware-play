package it.redhat.demo.jms;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by fabio.ercoli@redhat.com on 21/04/17.
 */

@Singleton
@Startup
public class JmsProducer {

    @Inject
    private Logger log;

    @PostConstruct
    public void go() {
        log.info("ciao");
    }

}
