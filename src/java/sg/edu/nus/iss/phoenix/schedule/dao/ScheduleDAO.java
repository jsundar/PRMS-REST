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
 * @author WIN and PRABA
 */
public interface ScheduleDAO {

    /**
     * createValueObject-method. This method is used when the Dao class needs to
     * create new value object instance. The reason why this method exists is
     * that sometimes the programmer may want to extend also the valueObject and
     * then this method can be override to return extended valueObject. NOTE: If
     * you extend the valueObject class, make sure to override the clone()
     * method in it!
     *
     * @return ProgramSlot returning the ProgramSlot object
     */
    public ProgramSlot createValueObject();

    /**
     * getObject-method. This will create and load valueObject contents from
     * database using given Primary-Key as identifier. This method is just a
     * convenience method for the real load-method which accepts the valueObject
     * as a parameter. Returned valueObject will be created using the
     * createValueObject() method.
     *
     * @param id id of program slot
     * @return ProgramSlot ProgramSlot object
     * @throws SQLException throws exception when there is DB error
     * @throws NotFoundException throws exception when there is not found error
     */
    public ProgramSlot getObject(String id) throws NotFoundException, SQLException;

    /**
     * LoadAll-method. This will read all contents from database table and build
     * a List containing valueObjects. Please note, that this method will
     * consume huge amounts of resources if table has lot's of rows. This should
     * only be used when target tables have only small amounts of data.
     *
     * @return list of ProgramSlot
     * @throws SQLException throws exception when there is DB error
     * @throws NotFoundException throws exception when there is not found error
     */
    public List<ProgramSlot> loadAll() throws SQLException, NotFoundException;

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
     * @throws SQLException throws exception when there is DB error
     */
    public boolean create(ProgramSlot valueObject) throws SQLException;

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
     * @throws SQLException throws exception when there is DB error
     */
    public boolean update(ProgramSlot valueObject) throws SQLException;

    /**
     * delete-method. This method will remove the information from database as
     * identified by by primary-key in supplied valueObject. Once valueObject
     * has been deleted it can not be restored by calling save. Restoring can
     * only be done using create method but if database is using automatic
     * surrogate-keys, the resulting object will have different primary-key than
     * what it was in the deleted object. If delete can not find matching row,
     * NotFoundException will be thrown.
     *
     * @param valueObject This parameter contains the class instance to be
     * deleted. Primary-key field must be set for this to work properly.
     * @throws SQLException throws exception when there is DB error
     * @throws NotFoundException throws exception when there is not found error
     */
    public void delete(ProgramSlot valueObject) throws NotFoundException, SQLException;

    /**
     * searchMatching-Method. This method provides searching capability to get
     * matching valueObjects from database. It works by searching all objects
     * that match permanent instance variables of given object. Upper layer
     * should use this by setting some parameters in valueObject and then call
     * searchMatching. The result will be 0-N objects in a List, all matching
     * those criteria you specified. Those instance-variables that have NULL
     * values are excluded in search-criteria.
     *
     * @param startDate This parameter contains the class instance where
     * search will be based. Primary-key field should not be set.
     * @return list of ProgramSlot
     * @throws SQLException throws exception when there is DB error
     */
    public List<ProgramSlot> searchMatching(String startDate) throws SQLException;
    
    public abstract int getAssignedUserToProgramSlot(String userId) throws SQLException;
    

    /**
     * checkProgramSlot-Method. This method provides validation capability to get
     * matching valueObjects from database before create/modify schedule. 
     * It works by searching all objects that match permanent instance variables 
     * of given object. Upper layer should use this by setting some parameters 
     * in valueObject and then call searchMatching. 
     * The result will be 0-N objects in a List, all matching
     * those criteria you specified. Those instance-variables that have NULL
     * values are excluded in search-criteria.
     *
     * @param valueObject This parameter contains the class instance where search will be based. Primary-key field should not be set.
     * @param criteria condition of input
     * @return boolean to check schedule
     * @throws SQLException throws exception when there is DB error
     */
    public boolean checkProgramSlotAvailabiltiy(ProgramSlot valueObject, String criteria) throws SQLException;

    public void closeConnection();
}
