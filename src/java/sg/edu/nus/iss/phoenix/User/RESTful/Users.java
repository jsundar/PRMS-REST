/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.RESTful;

import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;

/**
 *
 * @author JOHN
 */
public class Users {
    
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    
}
