package sg.edu.nus.iss.phoenix.User.RESTful;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.edu.nus.iss.phoenix.User.service.RoleService;

/*
 * 
 * Provide User Role related service
 * 
 */
/**
 *
 * @author JOHN
 */
@Path("/roles")
@RequestScoped
public class RolesRestService {

    private static final Logger logger
            = Logger.getLogger(RolesRestService.class.getName());

    private RoleService roleService;

    public RolesRestService() {
        roleService = new RoleService();
    }

    @GET
    @Path("selectall")
    public Response selectAllUser() {
        Roles roles = new Roles();
        try {
            roles.setRoles(roleService.getRoleList());
            if (roles.getRoles() == null || roles.getRoles().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No Record found..").build();
            } else {
                return Response.ok(roles, MediaType.APPLICATION_JSON).build();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

}
