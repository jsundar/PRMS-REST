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
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;

/**
 *
 * @author JOHN
 */
public class ScheduleService {

    DAOFactoryImpl factory;
    ScheduleDAO scheduleDAO;

    public ScheduleService() {

        factory = new DAOFactoryImpl();
        scheduleDAO = factory.getScheduleDAO();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public boolean createSchedule(ProgramSlot ps) {
        boolean statusFlag = false;
        try {
            statusFlag = scheduleDAO.create(ps);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
        }
        return statusFlag;
    }

    public boolean modifySchedule(ProgramSlot ps) {
        boolean statusFlag = false;
        try {
            statusFlag = scheduleDAO.update(ps);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
        }
        return statusFlag;

    }

    public List<ProgramSlot> getProgramSlotList() throws SQLException, NotFoundException {
        return scheduleDAO.loadAll();
    }

}
