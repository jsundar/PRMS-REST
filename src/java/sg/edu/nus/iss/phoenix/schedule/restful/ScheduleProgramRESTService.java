/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.restful;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 * REST Web Service
 *
 * @author JOHN
 */
@Path("/schedules")
@RequestScoped
public class ScheduleProgramRESTService {

    @Context
    private UriInfo context;

  
    public ScheduleProgramRESTService() {
    }

    /**
     * Retrieves representation of an instance of sg.edu.nus.iss.phoenix.authenticate.RESTful.ProgramSlotService
     * @return an instance of java.lang.String
     */
    @GET
    public Response getWeeklySchedules() {
       
        Schedules schedules = new Schedules();

        ScheduleService service = new ScheduleService();
        schedules.setWeeklySchedules(service.getWeeklySchedules());
        
        return Response.ok(schedules, MediaType.APPLICATION_JSON).build();
     }
    
    @GET
    @Path("/{startDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProgramSlots(@PathParam("startDate") String startDate) {
            //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @POST
    @Path("/copy")
    @Produces(MediaType.APPLICATION_JSON)
    public Response copySchedule() {
        throw new UnsupportedOperationException();
    }
 
    /**
     * PUT method for updating or creating an instance of ProgramSlotService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
