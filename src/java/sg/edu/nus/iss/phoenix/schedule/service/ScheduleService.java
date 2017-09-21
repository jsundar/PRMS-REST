/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import java.time.temporal.TemporalAmount;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
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
    
    public List<WeeklySchedule> getWeeklySchedules() {
        List<WeeklySchedule> weeklySchedules = new ArrayList<>();
        
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        int year = prevYear.get(Calendar.YEAR);
        LocalDate firstSundayOfNextMonth = LocalDate
              .ofYearDay(year, 365)
              .with(firstDayOfNextMonth())
              .with(nextOrSame(DayOfWeek.SUNDAY));
        
        Calendar startWeek = Calendar.getInstance();
                        startWeek.set(firstSundayOfNextMonth.getYear(), 
                        firstSundayOfNextMonth.getMonthValue() - 1, 
                        firstSundayOfNextMonth.getDayOfMonth());
        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        System.out.println(sdf.format(startWeek.getTime()));
        
        for (int i=1; i<= 52; i++) {
            startWeek.add(Calendar.DAY_OF_MONTH, 7);
            
            WeeklySchedule weeklySchedule = new WeeklySchedule();
            weeklySchedule.setStartDate(sdf.format(startWeek.getTime()));
            System.out.println("Week " + i + " " + sdf.format(startWeek.getTime()));
            weeklySchedule.setAssignedBy("Station Manager");
            weeklySchedules.add(weeklySchedule);
        }
        
        return weeklySchedules;
    }

}
