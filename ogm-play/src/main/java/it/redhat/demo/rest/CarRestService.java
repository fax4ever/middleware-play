package it.redhat.demo.rest;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.redhat.demo.entity.Car;
import it.redhat.demo.rest.dto.CarView;
import it.redhat.demo.service.CarService;
import org.slf4j.Logger;

/**
 * Created by fabio.ercoli@redhat.com on 24/04/17.
 */

@Path("car")
public class CarRestService {

    @Inject
    private Logger log;

    @Inject
    private CarService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarView> getAll() {

        List<Car> cars = service.getCars();
        return CarView.toView(cars);

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCar(@PathParam("id") String id) {

        return service.getCar(id);

    }

    @POST
    public String insertCar() {

        String frameNumber = "2989398h3i3";
        String plate = "DX339XC";
        String description = "General";

        return service.saveCar(frameNumber, plate, description);

    }

}
