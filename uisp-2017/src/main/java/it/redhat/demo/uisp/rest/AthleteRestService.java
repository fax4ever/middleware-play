package it.redhat.demo.uisp.rest;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.redhat.demo.uisp.entity.Athlete;
import it.redhat.demo.uisp.rest.factory.EntityFactory;
import it.redhat.demo.uisp.service.AthleteBeanParams;
import it.redhat.demo.uisp.service.AthleteContTrxService;
import it.redhat.demo.uisp.service.AthleteBeanTrxService;
import org.slf4j.Logger;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */
@Path("athletes")
public class AthleteRestService {

    public static final int SIZE_DEF_VALUE = 10000;

    @Inject
    private Logger log;

    @Inject
    private AthleteBeanTrxService beanTrxService;

    @Inject
    private AthleteContTrxService contTrxService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Athlete> findByParams(@BeanParam AthleteBeanParams params) {
        return contTrxService.findByParams(params);
    }

    @Path("bulk/{size}")
    @POST
    public void bulkInsert(@PathParam("size") Integer size) {

        if (size == null) {
            size = SIZE_DEF_VALUE;
        }

        beanTrxService.insertAthletesChunked(EntityFactory.buildAthletes(size));

    }

    @Path("bulk")
    @DELETE
    public void bulkDelete() {

        List<Athlete> allDetachedAthletes = contTrxService.getBulk();
        beanTrxService.deleteAthletesChunked(allDetachedAthletes);


    }

    @Path("uispCode/{uispCode}")
    @DELETE
    public void deleteByUispCode(@PathParam("uispCode") String uispCode) {

        contTrxService.deleteByUispCode(uispCode);

    }


}
