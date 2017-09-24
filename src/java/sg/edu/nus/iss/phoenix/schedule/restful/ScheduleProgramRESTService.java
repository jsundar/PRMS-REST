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
    @Path("/{startDate}")
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
     * @param ps
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSchedule(ProgramSlot ps) {
        boolean statusFlag = scheduleService.createSchedule(ps);
        if (statusFlag) {
            return Response.status(Response.Status.OK).entity("Schedule created successfully").build();
        } else {
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSchedule(ProgramSlot ps) {
        boolean statusFlag = scheduleService.modifySchedule(ps);
        if (statusFlag) {
            return Response.status(Response.Status.OK).entity("Schedule updated successfully").build();
        } else {
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT method for updating or creating an instance of ProgramSlotService
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
