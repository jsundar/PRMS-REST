/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author JOHN
 */
public interface ScheduleDAO {
    
    public ProgramSlot createValueObject();
    
    public ProgramSlot getObject(String id) throws NotFoundException, SQLException;
    
    public List<ProgramSlot> loadAll() throws SQLException, NotFoundException;
    
    public boolean create(ProgramSlot valueObject) throws SQLException;
    
    public boolean update(ProgramSlot valueObject) throws SQLException;
    
    
    public void delete(ProgramSlot valueObject) throws NotFoundException, SQLException;
    
    public List<ProgramSlot> searchMatching(ProgramSlot valueObject) throws SQLException;
}
