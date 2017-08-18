package it.redhat.demo.uisp.rest;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.redhat.demo.uisp.entity.Athlete;
import it.redhat.demo.uisp.entity.factory.AtheteFactory;
import it.redhat.demo.uisp.service.AthleteService;
import it.redhat.demo.uisp.service.RegisterService;
import org.slf4j.Logger;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */
@Path("register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterRestService {

    public static final int SIZE_DEF_VALUE = 10000;
    @Inject
    private Logger log;

    @Inject
    private RegisterService registerService;

    @Inject
    private AthleteService athleteService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Athlete> findAll() {
        return athleteService.findAll();
    }

    @Path("batch/{size}")
    @POST
    public void batchInsert(@PathParam("size") Integer size) {

        if (size == null) {
            size = SIZE_DEF_VALUE;
        }

        registerService.insertAthletesChunked(new AtheteFactory().buildAthletes(size));

    }

    @DELETE
    public void deleteAll() {

        athleteService.deleteAll();

    }


}
