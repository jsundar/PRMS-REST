/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.dao;

import java.sql.Connection;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;

/**
 *
 * @author projects
 */
public interface DAOFactory {

    ProgramDAO getProgramDAO();
    
    ProgramDAO getProgramDAO(Connection conn);

    RoleDao getRoleDAO();

    UserDao getUserDAO();
    
    UserDao getUserDAO(Connection conn);

    ScheduleDAO getScheduleDAO();

}
