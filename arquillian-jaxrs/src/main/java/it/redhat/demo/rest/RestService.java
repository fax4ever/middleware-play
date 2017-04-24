package it.redhat.demo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */

@Path("")
public class RestService {

    @GET
    public String ciao() {
        return "ciao";
    }

}
