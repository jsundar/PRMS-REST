/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.RESTful;

import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;

/**
 *
 * @author JOHN
 */
public class Roles {

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
