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
    
    /**
    * List<User> getUserList().
    * Retrieving all users stored in database
    * @return List<User>
    * @throws java.sql.SQLException
    */
    public List<User> getUserList() throws SQLException {  
        return udao.loadAll();
    }
    
    /**
    * List<User> getProducerList().
    * Retrieving all users with role of Producer stored in database
    * @return List<User>
    * @throws java.sql.SQLException
    */
    public List<User> getProducerList() throws SQLException {  
        return udao.loadAllProducer();
    }
    
    /**
    * List<User> getPresenterList().
    * Retrieving all users with role of Presenter stored in database
    * @return List<User>
    * @throws java.sql.SQLException
    */
    public List<User> getPresenterList() throws SQLException {  
        return udao.loadAllPresenter();
    }
    
    /**
    * createUserInfo(User user)
    * creating user profile entry in database
    * @Param User
    * @throws java.sql.SQLException
    */
    public void createUserInfo(User user) throws SQLException{  
        udao.create(user);
    }
    
    /**
    * loadUser(User user)
    * loading user profile entry in database
    * @Param User
    * @throws java.sql.SQLException
    */
    public void loadUser(User user) throws SQLException, NotFoundException {  
        udao.load(user);
    }
    
    /**
    * updateUser(User user)
    * updating user profile entry in database
    * @Param User
    * @throws java.sql.SQLException
    */
    public void updateUser(User user) throws SQLException, NotFoundException {  
        udao.save(user);
    }
    
    /**
    * deleteUser(User user)
    * delete user profile entry in database
    * @Param User
    * @throws java.sql.SQLException
    */
    public boolean deleteUser(User user) throws SQLException, NotFoundException {  
        boolean isDeleted = true;
        if (programSlotService.getAssignedUserToProgramSlot(user.getId()) == 0) {
            udao.delete(user);
        } else {
            isDeleted = false;
        }
        return isDeleted;
    }
    
}
