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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;

/**
 *
 * @author JOHN
 */
public class ProgramSlotDAOImpl implements ProgramSlotDAO {
    
    private static final Logger logger = Logger.getLogger(ProgramSlotDAOImpl.class.getName());

    Connection connection;

    public ProgramSlotDAOImpl(Connection connection) {
        super();
        this.connection = openConnection();
    }

    public ProgramSlotDAOImpl() {
        
    }
 
    @Override
    public ProgramSlot createValueObject() {
        return new ProgramSlot(); 
    }

    @Override
    public ProgramSlot getObject(String dateOfProgram) throws NotFoundException, SQLException {
        
      return new ProgramSlot();
        
    }

    /**
     *
     * @return
     * @throws SQLException
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
                programSlot.setDateOfProgram(result.getDate("dateOfProgram").toString());
                programSlot.setDuration(result.getTime("duration").toString());
                programSlot.setStartTime(result.getString("startTime"));
                programSlot.setProgramName(result.getString("program-name"));
                
                programSlotList.add(programSlot);
            } 
            if(programSlotList.isEmpty()) {
                 throw new NotFoundException("User Object Not Found!");
            }
        } finally {
                if (stmt != null)
                        stmt.close();
                
                if (stmt != null)
                    stmt.close();
        }
        
        return programSlotList;

    }

    @Override
    public boolean create(ProgramSlot valueObject) throws SQLException {
        throw 
                new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ProgramSlot valueObject) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
  
    @Override
    public List<ProgramSlot> searchMatching(ProgramSlot valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    private Connection openConnection() {
        Connection conn = null;
        try {
                Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ProgramSlotDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
                conn = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/phoenix", "root",
                                "Test@1234");
        } catch (SQLException e) {
             Logger.getLogger(ProgramSlotDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }

    
    
    /**
	 * databaseQuery-method. This method is a helper method for internal use. It
	 * will execute all database queries that will return only one row. The
	 * resultset will be converted to valueObject. If no rows were found,
	 * NotFoundException will be thrown.
	 * 
	 * @param stmt
	 *            This parameter contains the SQL statement to be excuted.
	 * @param valueObject
	 *            Class-instance where resulting data will be stored.
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
	 */
	protected void singleQuery(PreparedStatement stmt, ProgramSlot valueObject)
			throws NotFoundException, SQLException {

            try (ResultSet result = stmt.executeQuery()) {

                    if (result.next()) {
                        valueObject.setDateOfProgram(result.getDate("dateOfProgram").toString());
                        valueObject.setDuration(result.getTime("duration").toString());
                        valueObject.setStartTime(result.getString("startTime"));
                        valueObject.setProgramName(result.getString("program-name"));
                    } else {				
                        throw new NotFoundException("User Object Not Found!");
                    }
            } finally {
                    if (stmt != null)
                            stmt.close();
            }
	}

    @Override
    public boolean update(ProgramSlot valueObject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
