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
import sun.java2d.pipe.SpanShapeRenderer;

/**
 *
 * @author 
 *      WIN - getProgramSlotList, getWeeklySchedules, copySchedule, prepareSchedulesCopy
 *      PRABAKARAN - createSchedule & modifySchedule
 * 
 */
public class ScheduleService {

    DAOFactoryImpl factory;
    ScheduleDAO scheduleDAO;

    public ScheduleService() {

        factory = new DAOFactoryImpl();
        scheduleDAO = factory.getScheduleDAO();
    }

    /**
     * create-method. This will create new program-slot in database according to
     * supplied valueObject contents. Make sure that values for all NOT NULL
     * columns are correctly specified. Also, if this table does not use
     * automatic surrogate-keys the primary-key must be specified. After INSERT
     * command this method will read the generated primary-key back to
     * valueObject if automatic surrogate-keys were used.
     *
     * @param valueObject This parameter contains the class instance to be
     * created. Automatic surrogate-keys are used the Primary-key field must be
     * set for this to work properly.
     * @return createStatus
     * @throws java.sql.SQLException
     */
    public String createSchedule(ProgramSlot ps) {
        String statusMessage = "";
        try {
            // Adding second for the database operation
            ps.setStartTime(ps.getStartTime() + ":00");
            
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
     * update-method. This method will save the current state of valueObject to
     * database. Save can not be used to create new instances in database, so
     * upper layer must make sure that the primary-key is correctly specified.
     * Primary-key will indicate which instance is going to be updated in
     * database. If save can not find matching row, NotFoundException will be
     * thrown.
     *
     * @param valueObject This parameter contains the class instance to be
     * updated. Primary-key field must be set for this to work properly.
     * @return updateStatus
     * @throws java.sql.SQLException
     */
    public String modifySchedule(ProgramSlot ps) {
        String statusMessage = "";
        try {
            // Adding second for the database operation
            ps.setStartTime(ps.getStartTime() + ":00");
            
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
     * getWeeklySchedules-Method. This method provides searching capability to get
     * matching valueObjects from database. It works by searching all objects
     * that match permanent instance variables of given object. Upper layer
     * should use this by setting some parameters in valueObject and then call
     * searchMatching. The result will be 0-N objects in a List, all matching
     * those criteria you specified. Those instance-variables that have NULL
     * values are excluded in search-criteria.
     *
     * @param valueObject This parameter contains the class instance where
     * search will be based. Primary-key field should not be set.
     * @return
     * @throws java.sql.SQLException
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
     * copySchedule-method. This will create new program-slot in database according to
     * supplied valueObject contents. Make sure that values for all NOT NULL
     * columns are correctly specified. Also, if this table does not use
     * automatic surrogate-keys the primary-key must be specified. After INSERT
     * command this method will read the generated primary-key back to
     * valueObject if automatic surrogate-keys were used.
     *
     * @param String This parameter contains the startedate and enddate to 
     * create weekly schedule. Automatic surrogate-keys are used the Primary-key field must be
     * set for this to work properly.
     * @return createStatus
     * @throws java.sql.SQLException
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
     * prepareSchedulesCopy-method. This will create new program-slot in database according to
     * supplied valueObject contents. Make sure that values for all NOT NULL
     * columns are correctly specified. Also, if this table does not use
     * automatic surrogate-keys the primary-key must be specified. After INSERT
     * command this method will read the generated primary-key back to
     * valueObject if automatic surrogate-keys were used.
     *
     * @param List<ProgramSlot> This parameter contains the list of ProgramSlots to 
     * create weekly schedule. Automatic surrogate-keys are used the Primary-key field must be
     * set for this to work properly.
     * @return List<ProgramSlot>
     *      returns all the 
     * @throws java.sql.SQLException
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


    public int getAssignedUserToProgramSlot(String userId) throws SQLException {
        return scheduleDAO.getAssignedUserToProgramSlot(userId);
    }
    
    public static void main(String[] args) {
        String fromDate = "20170101";
        String toDate = "20170122";
        
        ScheduleService service =  new ScheduleService();
        service.copySchedule(fromDate, toDate);
    }

}
