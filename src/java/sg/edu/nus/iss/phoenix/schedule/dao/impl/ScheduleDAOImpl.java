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
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;

/**
 *
 * @author JOHN
 */
public class ScheduleDAOImpl implements ScheduleDAO {

    private static final Logger logger = Logger.getLogger(ScheduleDAOImpl.class.getName());

    Connection connection;

    public ScheduleDAOImpl(Connection connection) {
        super();
        this.connection = openConnection();
    }

    public ScheduleDAOImpl() {
        
    }

    @Override
    public ProgramSlot createValueObject() {
        return new ProgramSlot();
    }

    @Override
    public ProgramSlot getObject(String dateOfProgram) throws NotFoundException, SQLException {

        return new ProgramSlot();

    }

    @Override
    public boolean create(ProgramSlot valueObject) throws SQLException {

        String sql = null;
        boolean statusFlag = false;
        PreparedStatement stmt = null;
        try {
            sql = "INSERT INTO program-slot (id, duration, dateOfProgram, startTime, program-name, presenter, producer) VALUES (?, ?, ?, ?, ?,?,?) ";
            stmt = this.connection.prepareStatement(sql);

            stmt.setInt(1, valueObject.getId());
            stmt.setString(2, valueObject.getDuration());
            stmt.setString(3, valueObject.getDateOfProgram());
            stmt.setString(4, valueObject.getStartTime());
            stmt.setString(5, valueObject.getProgramName());
            stmt.setString(6, valueObject.getPresenter());
            stmt.setString(7, valueObject.getProducer());

            int rowcount = databaseUpdate(stmt);
            if (rowcount != 1) {
                System.out.println("Error while updating DB!");
                throw new SQLException("Error while updating DB!");
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
        return !statusFlag;
    }

    @Override
    public boolean update(ProgramSlot valueObject) throws SQLException {
        String sql = "UPDATE program-slot SET duration = ?, dateOfProgram = ?, startTime = ?, program-name = ?, presenter = ?, producer = ? WHERE (id = ? ) ";
        PreparedStatement stmt = null;
        boolean statusFlag = false;
        try {
            stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, valueObject.getDuration());
            stmt.setString(2, valueObject.getDateOfProgram());
            stmt.setString(3, valueObject.getStartTime());
            stmt.setString(4, valueObject.getProgramName());
            stmt.setString(5, valueObject.getPresenter());
            stmt.setString(6, valueObject.getProducer());

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
            closeConnection();
        }
        return !statusFlag;
    }

    /**
     *
     * @return @throws SQLException
     * @throws NotFoundException
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
                programSlot.setId(result.getInt("id"));
                programSlot.setDateOfProgram(result.getString("dateOfProgram"));
                programSlot.setDuration(result.getString("duration"));
                programSlot.setStartTime(result.getString("startTime"));
                programSlot.setProgramName(result.getString("program-name"));
                programSlot.setPresenter(result.getString("presenter"));
                programSlot.setProducer(result.getString("producer"));

                
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

    @Override
    public void delete(ProgramSlot valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProgramSlot> searchMatching(String startDate) throws SQLException {
        List<ProgramSlot> ret = new ArrayList<>();
        
        String query = "SELECT * FROM `program-slot` WHERE dateOfProgram >= ? AND dateOfProgram <= ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = openConnection();
            pstmt = connection.prepareStatement(query);
            
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

        valueObject.setId(result.getInt("id"));
        valueObject.setDateOfProgram(result.getString("dateOfProgram"));
        valueObject.setDuration(result.getString("duration"));
        valueObject.setStartTime(result.getString("startTime"));
        valueObject.setProgramName(result.getString("program-name"));
        valueObject.setProgramName(result.getString("presenter"));
        valueObject.setProgramName(result.getString("producer"));
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

    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
     * @return
     * @throws java.sql.SQLException
     */
    protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

        int result = stmt.executeUpdate();

        return result;
    }

}
