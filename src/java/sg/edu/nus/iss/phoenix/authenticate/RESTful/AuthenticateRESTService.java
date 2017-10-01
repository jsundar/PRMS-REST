/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.authenticate.RESTful;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import sg.edu.nus.iss.phoenix.User.service.UserService;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.service.AuthenticateService;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 * REST Web Service
 *
 * @author User
 */
@Path("/Login")
@RequestScoped
public class AuthenticateRESTService {

    @Context
    private UriInfo context;

    private AuthenticateService service;

    public AuthenticateRESTService() {
        service = new AuthenticateService();
    }

    /**
     * Retrieves representation of an instance of
     * sg.edu.nus.iss.phoenix.authenticate.RESTful.GenericResource
     * 
     * @param uname name of user
     * @param pwd password of user
     * @return an instance of java.lang.String
     */
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/doLogin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
    public AuthInfo doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd) {

        AuthInfo response = new AuthInfo();
        response.setUsername(uname);
        if (checkCredentials(uname, pwd)) {
            response.setAuthStatus(true);
            try {
                User user = new User();
                user.setId(uname);
                new UserService().loadUser(user);
                response.setUser(user);
            } catch (SQLException ex) {
                Logger.getLogger(AuthenticateRESTService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotFoundException ex) {
                Logger.getLogger(AuthenticateRESTService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setAuthStatus(false);
        }
        return response;
    }

    /**
     * Method to check whether the entered credential is valid
     *
     * @param uname
     * @param pwd
     * @return
     */
    private boolean checkCredentials(String uname, String pwd) {
        System.out.println("Inside checkCredentials");
        User user = new User();
        user.setId(uname);
        user.setPassword(pwd);
        user = service.validateUserIdPassword(user);
        if (null != user) {
            System.out.println("Login Sucess!");
            return true;
        } else {
            System.out.println("Login Failed - Wrong username/password!");
            return false;
        }
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    /*    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    } */
}
