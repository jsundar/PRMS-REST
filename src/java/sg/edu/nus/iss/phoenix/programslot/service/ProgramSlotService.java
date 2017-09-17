/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.programslot.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.programslot.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.programslot.dao.ProgramSlotDAO;

/**
 *
 * @author JOHN
 */
public class ProgramSlotService {
    DAOFactoryImpl factory;
    ProgramSlotDAO programSlotDAO;
    
    public ProgramSlotService() {
        super();
	// Sorry. This implementation is wrong. To be fixed.
	factory = new DAOFactoryImpl();
	//userInfoDAO = factory.getUserInfoDAO();
    }
    
    
    public List<ProgramSlot> getProgramSlotList() throws SQLException, NotFoundException {
        return programSlotDAO.loadAll();
    }
    
    /**
     * 
     * @param userInfo
     * @return 
     */
    public void searchUser(ProgramSlot programSlot) {
        try {
            programSlotDAO.load(programSlot);
	} catch (SQLException | NotFoundException e) {
            // TODO Auto-generated catch block
           Logger.getLogger(ProgramSlotService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void createNewUserInfo(ProgramSlot user) {
        try {
            programSlotDAO.create(user);
	} catch (SQLException e) {
            // TODO Auto-generated catch block
           Logger.getLogger(ProgramSlotService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * 
     * @param user 
     */
    public void updateUser(ProgramSlot user) {
        try {
            programSlotDAO.save(user);
	} catch (SQLException e) {
            // TODO Auto-generated catch block
           Logger.getLogger(ProgramSlotService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteUser(ProgramSlot user) {
        try {
            programSlotDAO.delete(user);
	} catch (SQLException | NotFoundException e) {
            // TODO Auto-generated catch block
           Logger.getLogger(ProgramSlotService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
