/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;

/**
 *
 * @author JOHN
 */
public class ScheduleService {
    DAOFactoryImpl factory;
    ProgramSlotDAO programSlotDAO;
    
    public ScheduleService() {
        super();
	
	factory = new DAOFactoryImpl();
	programSlotDAO = factory.getProgramSlotDAO();
    }
    
    
    public List<ProgramSlot> getProgramSlotList() throws SQLException, NotFoundException {
        return programSlotDAO.loadAll();
    }
    
}
