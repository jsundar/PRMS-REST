/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;

/**
 *
 * @author WIN and PRABAKARAN
 * @version 1.0
 */
public class ScheduleService {

    DAOFactoryImpl factory;
    ScheduleDAO scheduleDAO;

    public ScheduleService() {

        factory = new DAOFactoryImpl();
        scheduleDAO = factory.getScheduleDAO();
    }

    /**
     * This method create a new schedule
     *
     * @param ps program slot to be created in the DB
     * @return result of create status 
     */
    public String createSchedule(ProgramSlot ps) {
        String statusMessage = "";
        try {
            // Adding second for the database operation
            ps.setStartTime(ps.getDateOfProgram() + " " + ps.getStartTime());
            
            statusMessage = validateScedule(ps);
            if (statusMessage.length() > 0) {
                return statusMessage;
            }
            if (scheduleDAO.create(ps)) {
                statusMessage = "Schedule created successfully";
            } else {
                statusMessage = "Error occured while processing...";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
             scheduleDAO.closeConnection();
        }
        return statusMessage;
        
    }

    /**
     * This method provide the interface to modify a schedule
     *
     * @param ps program slot that is to be modified
     * @return status of the modify operation
     */
    public String modifySchedule(ProgramSlot ps) {
        String statusMessage = "";
        try {
            // Adding second for the database operation
            ps.setStartTime(ps.getDateOfProgram() + " " + ps.getStartTime());
            
            statusMessage = validateScedule(ps);
            if (statusMessage.length() > 0) {
                return statusMessage;
            }
            if (scheduleDAO.update(ps)) {
                statusMessage = "Schedule updated successfully";
            } else {
                statusMessage = "Error occured while processing...";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
             scheduleDAO.closeConnection();
        }
        return statusMessage;
    }
    
    /**
     * This method provides the interface to delete a schedule
     * 
     * @param id start date of the program slot list
     * @return status of the delete operation
     */
    public boolean deleteSchdule(int id) {
        boolean result = false;
        
        try {
           ProgramSlot ps = new ProgramSlot();
           ps.setId(id);
           result = scheduleDAO.delete(ps);
        } catch (Exception e) {
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            scheduleDAO.closeConnection();
        }
        return result;
    }

    /**
     * This method provides the interface to get the list of weekly schedule
     * 
     * @param startDate start date of the program slot list
     * @return list of all WeeklySchedule
     * @throws SQLException throws exception when execute DB operation fails
     */
    public List<ProgramSlot> getProgramSlotList(String startDate) throws SQLException {
       
       List<ProgramSlot> programSlots = new ArrayList<>();
       try {
            programSlots =  scheduleDAO.searchMatching(startDate);
       } catch(Exception e) {
           Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
       } finally {
           scheduleDAO.closeConnection();
       }
       
       return programSlots;
    }
    
    /**
     * This method provides the interface to get the list of weekly schedule
     * 
     * @return list of all WeeklySchedule
     */
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

        for (int i = 1; i <= 52; i++) {
            WeeklySchedule weeklySchedule = new WeeklySchedule();
            weeklySchedule.setStartDate(sdf.format(startWeek.getTime()));

            weeklySchedule.setAssignedBy("Station Manager");
            weeklySchedules.add(weeklySchedule);
            
            startWeek.add(Calendar.DAY_OF_MONTH, 7);
        }

        return weeklySchedules;
    }

    /**
     * This method will copy the schedule with a defined start date and end date
     *
     * @param fromDate Contains the start date to create weekly schedule.
     * @param toDate Contains the end date to create weekly schedule.
     * @return status of the copying operation
     */
    public boolean copySchedule(String fromDate, String toDate) {
        boolean result = false;

        try {
            List<ProgramSlot> fromProgramSlots = scheduleDAO.searchMatching(fromDate);

            List<ProgramSlot> toProgramSlots = scheduleDAO.searchMatching(toDate);
            
            // Checking Duplicate
            if (toProgramSlots.isEmpty()) {
                fromProgramSlots = prepareSchedulesCopy(fromProgramSlots, fromDate, toDate);
                
                for (ProgramSlot programSlot : fromProgramSlots) {
                    //createSchedule(programSlot);
                    result = scheduleDAO.create(programSlot);
                    
                    if (!result) {
                        break;
                    }
                }
                
                
            } else {
                result = false;
            }
        } catch (SQLException e) {
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, e);
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            scheduleDAO.closeConnection();
        }

        return result;
    }

    /**
     * This will prepare the program slot that is required to be copied
     *
     * @param fromProgramSlots list of program slot for the purpose of preparing schedule to copy
     * @param from date from
     * @param to date to
     * @return list of ProgramSlot object copied
     * @throws java.sql.SQLException throws exception when execute DB operation fails
     */
    private List<ProgramSlot> prepareSchedulesCopy(List<ProgramSlot> fromProgramSlots, String from, String to) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfToInsert = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fromDate = sdf.parse(from);
        Date toDate = sdf.parse(to);
        
        long diff = toDate.getTime() - fromDate.getTime();
        
        long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        
            
        for (ProgramSlot fromProgramSlot : fromProgramSlots) {
            Date sDate = sdf.parse(fromProgramSlot.getDateOfProgram());

            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            cal.add(Calendar.DAY_OF_MONTH, (int)dayDiff);

            Date eDate = cal.getTime();

            String strEndDate = sdfToInsert.format(eDate);

            fromProgramSlot.setDateOfProgram(strEndDate);
            fromProgramSlot.setStartTime(strEndDate + " " +fromProgramSlot.getStartTime() + ":00");

        }
        return fromProgramSlots;
    }

    /**
     * This method validate the program slot with required checking
     *
     * @param ps program slot to be validated
     * @return result of operation
     * @throws java.sql.SQLException throws exception when execute DB operation fails
     */
    private String validateScedule(ProgramSlot ps) throws SQLException {
        String validationStatus = "";

        if (ps.getProgram().getName().length() > 0 && ps.getDateOfProgram().length() > 0) {
            if (!scheduleDAO.checkProgramSlotAvailabiltiy(ps, "All")) {
                return validationStatus;
            } else if (scheduleDAO.checkProgramSlotAvailabiltiy(ps, "")) {
                return "ProgramSlot overlapping";
            } else if (ps.getPresenter().getId().length() > 0 && scheduleDAO.checkProgramSlotAvailabiltiy(ps, "Presenter")) {
                return "Presenter not available";
            } else if (ps.getProducer().getId().length() > 0 && scheduleDAO.checkProgramSlotAvailabiltiy(ps, "Producer")) {
                return "Producer not available";
            }
        }
        return validationStatus;
    }

   /**
     * This method assign a user to a program slot
     *
     * @param userId user id of program slot
     * @return result of operation
     * @throws SQLException throws exception when execute DB operation fails
     */
    public int getAssignedUserToProgramSlot(String userId) throws SQLException {
        return scheduleDAO.getAssignedUserToProgramSlot(userId);
    }
    
   
}
