/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.restful;

import java.sql.SQLException;
import java.util.logging.Level;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
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

    private static final Logger logger = Logger.getLogger(ScheduleService.class.getName());

    private ScheduleService scheduleService = null;

    public ScheduleProgramRESTService() {
        scheduleService = new ScheduleService();
    }

    @GET
    public Response getWeeklySchedules() {

        Schedules schedules = new Schedules();

        ScheduleService service = new ScheduleService();
        schedules.setWeeklySchedules(service.getWeeklySchedules());

        return Response.ok(schedules, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{startDate}/programslots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProgramSlots(@PathParam("startDate") String startDate) {
        //TODO return proper representation object
        ProgramSlots programSlots = new ProgramSlots();
        ScheduleService scheduleService = new ScheduleService();

        try {
            programSlots.setProgramSlots(scheduleService.getProgramSlotList(startDate));
            return Response.ok(programSlots, MediaType.APPLICATION_JSON).build();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleProgramRESTService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST method for creating an instance of resource
     *
     * @param ps ProgramSlot
     * @return response http response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSchedule(ProgramSlot ps) {
        String statusMessage = scheduleService.createSchedule(ps);
        return Response.status(Response.Status.OK).entity(statusMessage).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSchedule(ProgramSlot ps) {
        String statusMessage = scheduleService.modifySchedule(ps);
        return Response.status(Response.Status.OK).entity(statusMessage).build();
    }
    
    
    @DELETE
    @Path("/{id}")
    public Response deleteSchdeule(@PathParam("id") String id) {
        boolean status = scheduleService.deleteSchdule(Integer.parseInt(id));
        if (status) {
            Logger.getLogger(ScheduleProgramRESTService.class.getName()).log(Level.INFO, "Deleted Successfully");
            return Response.status(Response.Status.OK).entity("Program Slot deleted successfully").build();
        } else {
            Logger.getLogger(ScheduleProgramRESTService.class.getName()).log(Level.INFO, "Deletipon Failed");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/copy")
    @Produces(MediaType.APPLICATION_JSON)
    public Response copySchedule(CopySchedule copySchedule) {
        boolean status = scheduleService.copySchedule(copySchedule.getFromDate(), copySchedule.getToDate());

        if (status) {
            return Response.status(Response.Status.OK).entity("Schedule copied successfully").build();
        } else {
            return Response.serverError().status(Response.Status.OK).entity("The destination schdule is duplicate.").build();
        }

    }

}
