/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.service;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * @author William
 * @version 1.0 2017/10/01
 */
@RunWith(MockitoJUnitRunner.class)
public class ProgramServiceTest extends TestCase {
    
    private ProgramService programService;
    
    @Mock
    private DAOFactoryImpl factoryMock;
    
    @Mock
    private ProgramDAO programDAOMock;
    
    @Before 
    public void setup() throws Exception {
        super.setUp();
        
        programService = new ProgramService();
        factoryMock = Mockito.mock(DAOFactoryImpl.class);
        programDAOMock = Mockito.mock(ProgramDAO.class);
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testSearchProgram() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        List<RadioProgram> list = new ArrayList<>();
        RadioProgram radioProgram = new RadioProgram("name", "description", new Time(832732L));
        list.add(radioProgram);
        
        Mockito.when(programDAOMock.searchMatching(Mockito.any(RadioProgram.class))).thenReturn(list);
        
        ArrayList<RadioProgram> result = programService.searchPrograms(radioProgram);
        
        Assert.assertEquals(1, result.size());
    }
    
    @Test(expected = SQLException.class)
    public void testSearchProgramThrowsException() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        List<RadioProgram> list = new ArrayList<>();
        RadioProgram radioProgram = new RadioProgram("name", "description", new Time(832732L));
        list.add(radioProgram);
        
        Mockito.when(programDAOMock.searchMatching(Mockito.any(RadioProgram.class))).thenThrow(SQLException.class);
        
        ArrayList<RadioProgram> result = programService.searchPrograms(radioProgram);
        
        Assert.assertEquals(0, result.size());
    }
    
    @Test
    public void testFindRPByCriteria() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        List<RadioProgram> list = new ArrayList<>();
        RadioProgram radioProgram = new RadioProgram("name", "description", new Time(832732L));
        list.add(radioProgram);
        
        Mockito.when(programDAOMock.searchMatching(Mockito.any(RadioProgram.class))).thenReturn(list);
        
        ArrayList<RadioProgram> result = programService.findRPByCriteria(radioProgram);
        
        Assert.assertEquals(1, result.size());
    }
    
    @Test(expected = SQLException.class)
    public void testFindRPByCriteriaThrowsException() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        List<RadioProgram> list = new ArrayList<>();
        RadioProgram radioProgram = new RadioProgram("name", "description", new Time(832732L));
        list.add(radioProgram);
        
        Mockito.when(programDAOMock.searchMatching(Mockito.any(RadioProgram.class))).thenThrow(SQLException.class);
        
        ArrayList<RadioProgram> result = programService.findRPByCriteria(radioProgram);
        
        Assert.assertEquals(0, result.size());
    }
    
    @Test
    public void testFindRP() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        List<RadioProgram> list = new ArrayList<>();
        RadioProgram radioProgram = new RadioProgram("name", "description", new Time(832732L));
        list.add(radioProgram);
        
        Mockito.when(programDAOMock.searchMatching(Mockito.any(RadioProgram.class))).thenReturn(list);
        
        RadioProgram result = programService.findRP("name");
        
        Assert.assertTrue(result.getName().equals("name"));
        Assert.assertTrue(result.getDescription().equals("description"));
    }
    
    @Test(expected = SQLException.class)
    public void testFindRPThrowsException() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        Mockito.when(programDAOMock.searchMatching(Mockito.any(RadioProgram.class))).thenThrow(SQLException.class);
        
        RadioProgram result = programService.findRP("name");
    }
    
    @Test
    public void testFindAllRP() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        List<RadioProgram> list = new ArrayList<>();
        RadioProgram radioProgram1 = new RadioProgram("name1", "description", new Time(832732L));
        list.add(radioProgram1);
        RadioProgram radioProgram2 = new RadioProgram("name2", "description", new Time(832732L));
        list.add(radioProgram2);
        RadioProgram radioProgram3 = new RadioProgram("name3", "description", new Time(832732L));
        list.add(radioProgram3);
        
        Mockito.when(programDAOMock.loadAll()).thenReturn(list);
        
        List<RadioProgram> result = programService.findAllRP();
        
        Assert.assertEquals(result.size(), 3);
        Assert.assertTrue(result.get(0).getName().equals("name1"));
        Assert.assertTrue(result.get(1).getName().equals("name2"));
        Assert.assertTrue(result.get(2).getName().equals("name3"));
    }
    
    @Test(expected = SQLException.class)
    public void testProcessModifyThrowsSQLException() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        Mockito.doThrow(new SQLException()).doNothing().when(programDAOMock).save(Mockito.any(RadioProgram.class));
        
        programService.processModify(new RadioProgram());
    }
    
    @Test(expected = SQLException.class)
    public void testprocessDeleteThrowsSQLException() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(programService, "factory", factoryMock);
        Whitebox.setInternalState(programService, "rpdao", programDAOMock);
        
        Mockito.doThrow(new SQLException()).doNothing().when(programDAOMock).delete(Mockito.any(RadioProgram.class));
        
        programService.processDelete("test");
    }

    
}
