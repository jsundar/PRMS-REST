/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;

/**
 *
 * @author WIN and PRABAKARAN
 *
 */
public class ScheduleDAOImpl implements ScheduleDAO {

    private static final Logger logger = Logger.getLogger(ScheduleDAOImpl.class.getName());

    Connection connection;

    public ScheduleDAOImpl(Connection connection) {
        super();
        this.connection = connection;
    }

    public ScheduleDAOImpl() {
        this.connection = openConnection();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#createValueObject()
     */
    @Override
    public ProgramSlot createValueObject() {
        return new ProgramSlot();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#getObject(java.sql
	 * .Connection, String)
     */
    @Override
    public ProgramSlot getObject(String dateOfProgram) throws NotFoundException, SQLException {

        return new ProgramSlot();

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#create(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.ProgramSlot)
     */
    @Override
    public boolean create(ProgramSlot valueObject) throws SQLException {

        String sql = "";
        boolean statusFlag = false;
        PreparedStatement stmt = null;
        try {
            sql = " INSERT INTO `phoenix`.`program-slot` ( duration, dateOfProgram, startTime, `program-name`, presenter, producer) VALUES (?, ?, ?, ?, ?,?); ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, valueObject.getDuration());
            stmt.setString(2, valueObject.getDateOfProgram());
            stmt.setString(3, valueObject.getStartTime());
            stmt.setString(4, valueObject.getProgram().getName());
            stmt.setString(5, valueObject.getPresenter().getId());
            stmt.setString(6, valueObject.getProducer().getId());

            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                System.out.println("Error while updating DB!");
                throw new SQLException("Error while updating DB!");
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return !statusFlag;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#update(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.ProgramSlot)
     */
    @Override
    public boolean update(ProgramSlot valueObject) throws SQLException {
        String sql = "UPDATE `phoenix`.`program-slot` SET duration = ?, dateOfProgram = ?, startTime = ?, `program-name` = ?, presenter = ?, producer = ? WHERE ( id = ? ) ";
        PreparedStatement stmt = null;
        boolean statusFlag = false;
        try {
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, valueObject.getDuration());
            stmt.setString(2, valueObject.getDateOfProgram());
            stmt.setString(3, valueObject.getStartTime());
            stmt.setString(4, valueObject.getProgram().getName());
            stmt.setString(5, valueObject.getPresenter().getId());
            stmt.setString(6, valueObject.getProducer().getId());
            stmt.setInt(7, valueObject.getId());

            int rowcount = databaseUpdate(stmt);
            if (rowcount == 0) {
                System.out.println("ProgramSlot could not be saved! (ProgramSlot not found)");
                throw new SQLException("ProgramSlot could not be saved! (ProgramSlot not found)");
            }
            if (rowcount > 1) {
                System.out.println("PrimaryKey Error when updating DB! (Many ProgramSlots were affected!)");
                throw new SQLException(
                        "PrimaryKey Error when updating DB! (Many ProgramSlots were affected!)");
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return !statusFlag;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#loadAll(java.sql
	 * .Connection)
     */
    @Override
    public List<ProgramSlot> loadAll() throws SQLException, NotFoundException {
        String sql = "SELECT * FROM Program-Slot";
        PreparedStatement stmt = null;
        List<ProgramSlot> programSlotList = new ArrayList<>();

        try {
            stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                ProgramSlot programSlot = new ProgramSlot();

                readRecord(result, programSlot);
                programSlotList.add(programSlot);
            }
            if (programSlotList.isEmpty()) {
                throw new NotFoundException("ProgramSlot Not Found!");
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        }

        return programSlotList;

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#delete(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.ProgramSlot)
     */
    @Override
    public void delete(ProgramSlot valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.ScheduleDAO#searchMatching(java.sql.Connection
	 * , String)
     */
    @Override
    public List<ProgramSlot> searchMatching(String startDate) throws SQLException {
        List<ProgramSlot> ret = new ArrayList<>();

        String query = "SELECT id, duration, dateOfProgram, DATE_FORMAT(startTime,  '%H:%i') startTime, "
                + "`program-name`, presenter, producer FROM `program-slot` "
                + "WHERE dateOfProgram >= ? AND dateOfProgram <= ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = openConnection();
            pstmt = connection.prepareStatement(query);

            DAOFactory daoFactory = new DAOFactoryImpl();
            UserDao userDAO = daoFactory.getUserDAO();
            ProgramDAO programDAO = daoFactory.getProgramDAO();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date sDate = sdf.parse(startDate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            cal.add(Calendar.DAY_OF_MONTH, 7);

            Date eDate = cal.getTime();

            String strStartDate = sdf.format(sDate);
            String strEndDate = sdf.format(eDate);

            pstmt.setString(1, strStartDate);
            pstmt.setString(2, strEndDate);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProgramSlot currentObject = new ProgramSlot();
                readRecord(rs, currentObject);

                try {

                    currentObject.setPresenter(userDAO.getObject(currentObject.getPresenter().getId()));
                    currentObject.setProducer(userDAO.getObject(currentObject.getProducer().getId()));
                    currentObject.setProgram(programDAO.getObject(currentObject.getProgram().getName()));
                } catch (NotFoundException e) {
                    Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
                }
                ret.add(currentObject);
            }

        } catch (SQLException e) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }

            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }

            closeConnection();
        }

        return ret;
    }

    protected void readRecord(ResultSet result, ProgramSlot valueObject)
            throws SQLException {

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfStartDateTme = new SimpleDateFormat("HH:mm:ss");

        valueObject.setId(result.getInt("id"));

        Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, "Date of Program : " + result.getDate("dateOfProgram"));
        Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, "Date of Program : " + result.getDate("duration"));

        valueObject.setDateOfProgram(sdfDate.format(result.getDate("dateOfProgram")));

        valueObject.setDuration(sdfTime.format(result.getTime("duration")));
        valueObject.setStartTime(result.getString("startTime"));

        RadioProgram radioProgram = new RadioProgram();
        radioProgram.setName(result.getString("program-name"));
        valueObject.setProgram(radioProgram);

        User presenter = new User();
        presenter.setId(result.getString("presenter"));
        valueObject.setPresenter(presenter);

        User producer = new User();
        producer.setId(result.getString("producer"));
        valueObject.setProducer(producer);

//        valueObject.setProgramName(result.getString("program-name"));
//        valueObject.setProgramName(result.getString("presenter"));
//        valueObject.setProgramName(result.getString("producer"));
    }

    private Connection openConnection() {
        Connection conn = null;
        try {
            Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            conn = DriverManager.getConnection(DBConstants.dbUrl, DBConstants.dbUserName, DBConstants.dbPassword);
        } catch (SQLException e) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if(connection != null && !connection.isClosed())
            this.connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    /**
     * databaseUpdate-method. This method is a helper method for internal use.
     * It will execute all database handling that will change the information in
     * tables. SELECT queries will not be executed here however. The return
     * value indicates how many rows were affected. This method will also make
     * sure that if cache is used, it will reset when data changes.
     *
     * @param stmt This parameter contains the SQL statement to be excuted.
     * @return int success of fail
     * @throws SQLException throws exception when there is DB error
     */
    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;
    }

    private String getProgramSlotValidationScript(String criteria) {
        String query = "SELECT * "
                + "FROM `program-slot` WHERE dateOfProgram = ? and startTime = ? and "
                + "`program-name` = ? ";

        switch (criteria) {
            case "All":
                query += " and presenter = ? and producer = ? ";
                break;
            case "Presenter":
                query += " and presenter = ? ";
                break;
            case "Producer":
                query += " and producer = ? ";
                break;
            default:
                break;
        }

        return query;
    }

    @Override
    public boolean checkProgramSlotAvailabiltiy(ProgramSlot ps, String criteria) throws SQLException {
        boolean result = false;

        String query = getProgramSlotValidationScript(criteria);
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = this.connection.prepareStatement(query);

            pstmt.setString(1, ps.getDateOfProgram());
            pstmt.setString(2, ps.getStartTime());
            pstmt.setString(3, ps.getProgram().getName());

            switch (criteria) {
                case "All":
                    pstmt.setString(4, ps.getPresenter().getId());
                    pstmt.setString(5, ps.getProducer().getId());
                    break;
                case "Presenter":
                    pstmt.setString(4, ps.getPresenter().getId());
                    break;
                case "Producer":
                    pstmt.setString(4, ps.getProducer().getId());
                    break;
                default:
                    break;
            }

            rs = pstmt.executeQuery();

            if (rs != null && rs.next()) {
                ProgramSlot currentObject = new ProgramSlot();
                readRecord(rs, currentObject);
                result = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(ScheduleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

        return result;
    }
    
     public int getAssignedUserToProgramSlot(String userId) throws SQLException {
            
        String sqlString = "SELECT count(*) from `program-slot`  where presenter = ? or producer = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rowcount = 0;

        try {
            stmt = connection.prepareStatement(sqlString);
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            rs = stmt.executeQuery();
            if(rs.next()) {
                 rowcount = rs.getInt(1);
            }          
        } catch(SQLException e) {
            throw e;
        }
        return rowcount;
    }

}
