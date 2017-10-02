/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @author William
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleDAOTest extends TestCase {
   
    private ScheduleDAOImpl scheduleDAOImpl;
    
    @Mock
    DataSource mockDataSource;
    
    @Mock
    Connection mockConn;
    
    @Mock
    PreparedStatement mockPreparedStmnt;
    
    @Mock
    ResultSet mockResultSet;
    
    int scheduleId = 100;
    
    @Before 
    public void setup() throws Exception {
        super.setUp();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testCreateWithNoExceptions() throws SQLException {
        
        mockDataSource = Mockito.mock(DataSource.class);
        mockConn = Mockito.mock(Connection.class);
        mockPreparedStmnt = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
        
        Mockito.when(mockDataSource.getConnection()).thenReturn(mockConn);
        Mockito.when(mockDataSource.getConnection(Mockito.anyString(), Mockito.anyString())).thenReturn(mockConn);
        Mockito.doNothing().when(mockConn).commit();
        Mockito.when(mockConn.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStmnt);
        Mockito.doNothing().when(mockPreparedStmnt).setString(Mockito.anyInt(), Mockito.anyString());
        Mockito.when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        Mockito.when(mockPreparedStmnt.executeUpdate()).thenReturn(1);
        Mockito.when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        
        scheduleDAOImpl = new ScheduleDAOImpl();
        Whitebox.setInternalState(scheduleDAOImpl, "connection", mockConn);
        
        Boolean result = scheduleDAOImpl.create(new ProgramSlot());

        //verify and assert
        Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.anyString());
        Mockito.verify(mockPreparedStmnt, Mockito.times(6)).setString(Mockito.anyInt(), Mockito.anyString());
        Mockito.verify(mockPreparedStmnt, Mockito.times(1)).executeUpdate();
        
        Assert.assertTrue(result);
    } 
}
