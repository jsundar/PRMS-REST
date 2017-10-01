/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDaoImpl;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;

/**
 *
 * @author JOHN
 */
public class RoleService {
    
     private static final Logger logger = Logger.getLogger(RoleService.class.getName());
     
     RoleDao roleDao = null;
     
     public RoleService() {
         this.roleDao = new RoleDaoImpl();
     }
     
     /***
      * get all role list from DB.
      * @return
      * @throws SQLException 
      */
     public List<Role> getRoleList() throws SQLException {
         return roleDao.getAllRoles();
     }

}
