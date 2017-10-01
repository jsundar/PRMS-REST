/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.RESTful;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.edu.nus.iss.phoenix.User.service.UserService;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author JOHN
 */

@Path("/user")
@RequestScoped
public class UserInfoRESTService {
    
    private UserService service;
    
    
     private static final Logger logger = 
			Logger.getLogger(UserService.class.getName());
    
    public UserInfoRESTService() {
        service = new UserService();
    }
    
    @GET
    @Path("selectall")
    public Response selectAllUser() {
        Users users = new Users();
        try {
            users.setUsers(service.getUserList());
            if(users.getUsers() == null || users.getUsers().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
            } else {
                return Response.ok(users, MediaType.APPLICATION_JSON).build();
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
       
    }
    
    @GET
    @Path("presenter")
    public Response selectAllPresenter() {
        Users users = new Users();
        try {
            users.setUsers(service.getPresenterList());
            if(users.getUsers() == null || users.getUsers().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
            } else {
                return Response.ok(users, MediaType.APPLICATION_JSON).build();
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
       
    }
    
    @GET
    @Path("producer")
    public Response selectAllProducer() {
        Users users = new Users();
        try {
            users.setUsers(service.getProducerList());
            if(users.getUsers() == null || users.getUsers().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
            } else {
                return Response.ok(users, MediaType.APPLICATION_JSON).build();
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
       
    }
    
    @PUT
    @Path("create")
    public Response createAUser(User user) {
        try {
            service.createAUser(user);
            return Response.status(Response.Status.OK).entity("No Record found..").build();
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } 
    }
    
    @GET
    @Path("select/{userid}")
    public Response selectUser(@PathParam("userid") String userid) {
        
        User user = new User();
        user.setId(userid);
        try {
            service.loadUser(user);
            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch(NotFoundException e ) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
        }
    }
    
    @POST
    @Path("update")
    public Response updateUser(User user) {
        try {
            service.updateUser(user);
            return Response.ok(user, MediaType.TEXT_PLAIN).build();
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch(NotFoundException e ) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            
            return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
        }
    }
    
   @POST
    @Path("delete/{userid}")
    public Response deleteUser(@PathParam("userid") String userid) {
        try {
            User user = new User();
            user.setId(userid);
            if(service.deleteUser(user)) {
                return Response.ok(user, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(userid + " is already assigned with programslot.").build();
            }
            
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch(NotFoundException e ) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
        }
    }
    
}