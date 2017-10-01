/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
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
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 *
 * @author William
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewSelectProgramServiceTest extends TestCase {
    
    private ReviewSelectProgramService reviewSelectProgramService;
    
    @Mock
    private DAOFactoryImpl factoryMock;
    
    @Mock
    private ProgramDAO programDAOMock;
    
    @Before 
    public void setup() throws Exception {
        super.setUp();
        
        reviewSelectProgramService = new ReviewSelectProgramService();
        factoryMock = Mockito.mock(DAOFactoryImpl.class);
        programDAOMock = Mockito.mock(ProgramDAO.class);
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testReviewSelectRadioProgramWithResult() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(reviewSelectProgramService, "factory", factoryMock);
        Whitebox.setInternalState(reviewSelectProgramService, "rpdao", programDAOMock);
        
        List<RadioProgram> data = new ArrayList<>();
        RadioProgram radioProgram = new RadioProgram("name", "description", new Time(832732L));
        data.add(radioProgram);
        
        Mockito.when(programDAOMock.loadAll()).thenReturn(data);
        
        List<RadioProgram> radioProgramsResult = reviewSelectProgramService.reviewSelectRadioProgram();
        RadioProgram radioProgramResult = radioProgramsResult.get(0);
        
        Assert.assertEquals(1, radioProgramsResult.size());
        Assert.assertTrue(radioProgramResult.getName().equals("name"));
        Assert.assertTrue(radioProgramResult.getDescription().equals("description"));  
    }
    
    @Test
    public void testReviewSelectRadioProgramWithoutResult() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(reviewSelectProgramService, "factory", factoryMock);
        Whitebox.setInternalState(reviewSelectProgramService, "rpdao", programDAOMock);
        
        List<RadioProgram> data = new ArrayList<>();
        
        Mockito.when(programDAOMock.loadAll()).thenReturn(data);
        
        List<RadioProgram> radioProgramsResult = reviewSelectProgramService.reviewSelectRadioProgram();
        
        Assert.assertEquals(0, radioProgramsResult.size());
    }

}
