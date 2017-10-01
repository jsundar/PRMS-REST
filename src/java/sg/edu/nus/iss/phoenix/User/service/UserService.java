/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
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
    * Retrieving all users stored in database
    * @return list of users
    * @throws SQLException throws exception when there is DB error
    */
    public List<User> getUserList() throws SQLException {  
        return udao.loadAll();
    }
    
    /**
    * Retrieving all users with role of Producer stored in database
    * 
    * @return list of user
    * @throws java.sql.SQLException throws exception when there is DB error
    */
    public List<User> getProducerList() throws SQLException {  
        return udao.loadAllProducer();
    }
    
    /**
    * Retrieving all users with role of Presenter stored in database
    * 
    * @return list of user
    * @throws java.sql.SQLException throws exception when there is DB error
    */
    public List<User> getPresenterList() throws SQLException {  
        return udao.loadAllPresenter();
    }
    
    /**
    * creating user profile entry in database
    * 
    * @param user to be created
    * @throws java.sql.SQLException throws exception when there is DB error
    */
    public void createAUser(User user) throws SQLException{  
        udao.create(user);
    }
    
    /**
    * loading user profile entry in database
    * 
    * @param user user to be loaded
    * @throws SQLException throws exception when there is DB error
    * @throws NotFoundException throws exception when there is not found error
    */
    public void loadUser(User user) throws SQLException, NotFoundException {  
        udao.load(user);
    }
    
    /**
    * updating user profile entry in database
    * 
    * @param user user for updating
    * @throws SQLException throws exception when there is DB error
    * @throws NotFoundException throws exception when there is not found error
    */
    public void updateUser(User user) throws SQLException, NotFoundException {  
        udao.save(user);
    }
    
    /**
    * deleteUser(User user)
    * delete user profile entry in database
    * @param user user for deleting
    * @return boolean true if user is deleted
    * @throws SQLException throws exception when there is DB error
    * @throws NotFoundException throws exception when there is not found error
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
