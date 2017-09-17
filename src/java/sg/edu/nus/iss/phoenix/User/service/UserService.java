/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 *
 * @author JOHN
 */
public class UserService {
    
    private static final Logger logger = 
			Logger.getLogger(UserService.class.getName());

    DAOFactoryImpl factory;
    UserDao udao;
    ScheduleService programSlotService;

    public UserService() {
        super();
        // TODO Auto-generated constructor stub
        factory = new DAOFactoryImpl();
        udao = factory.getUserDAO();
        programSlotService = new ScheduleService();
    }
    
    public List<User> getUserList() throws SQLException {  
        return udao.loadAll();
    }
    
    public void createUserInfo(User user) throws SQLException{  
        udao.create(user);
    }
    
    public void loadUser(User user) throws SQLException, NotFoundException {  
        udao.load(user);
    }
    
    public void updateUser(User user) throws SQLException, NotFoundException {  
        udao.save(user);
    }
    
    public void deleteUser(User user) throws SQLException, NotFoundException {  
       
    }
    
}
