/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.programslot.dao;

import java.sql.SQLException;
import java.util.List;
import sg.edu.nus.iss.phoenix.programslot.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author JOHN
 */
public interface ProgramSlotDAO {
    
    public ProgramSlot createValueObject();
    
    public ProgramSlot getObject(String id) throws NotFoundException, SQLException;
    
    public void load(ProgramSlot valueObject) throws NotFoundException, SQLException;
    
    public List<ProgramSlot> loadAll() throws SQLException, NotFoundException;
    
    public void create(ProgramSlot valueObject) throws SQLException;
    
    public void save(ProgramSlot userInfo) throws SQLException;
    
    public void delete(ProgramSlot valueObject) throws NotFoundException, SQLException;
    
    public void deleteAll() throws SQLException;
    
    public int countAll() throws SQLException;
    
    public List<ProgramSlot> searchMatching(ProgramSlot valueObject) throws SQLException;

    public ProgramSlot searchMatching(String uid) throws SQLException;
}
