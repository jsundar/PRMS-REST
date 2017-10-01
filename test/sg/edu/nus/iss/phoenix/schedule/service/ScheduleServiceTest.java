/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import sg.edu.nus.iss.phoenix.User.service.UserService;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDAO;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;

/**
 *
 * @author William
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest extends TestCase {
    
    private ScheduleService scheduleService;
    
    @Mock
    DAOFactoryImpl factoryMock;
    
    @Mock
    ScheduleDAO scheduleDAOMock;
    
    ProgramSlot programSlot;
    
    @Before 
    public void setup() throws Exception {
        super.setUp();
        
        programSlot = new ProgramSlot();
        RadioProgram radioProgram = new RadioProgram();
        radioProgram.setAll("name", "description", new Time(832732L));
        
        User presenter = new User();
        presenter.setAll("1", "password", "name", "presenter");
        
        User producer = new User();
        producer.setAll("2", "password", "name", "prodcuer");
        
        programSlot.setDateOfProgram("30/09/2017");
        programSlot.setDuration("100");
        programSlot.setPresenter(presenter);
        programSlot.setProducer(producer);
        programSlot.setProgram(radioProgram);
        programSlot.setStartTime("1130");
        
        scheduleService = new ScheduleService();
        factoryMock = Mockito.mock(DAOFactoryImpl.class);
        scheduleDAOMock = Mockito.mock(ScheduleDAO.class);
        
        
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testCreateScheduleValidateSuccess() throws Exception {
        
        this.setup();
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.TRUE);
        
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Schedule created successfully"));
    }
    
    @Test
    public void testCreateScheduleValidateEmptyProgramName() throws Exception {
        
        this.setup();
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.TRUE);
        
        programSlot.getProgram().setName("");
        
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Schedule created successfully"));
    }
    
    @Test
    public void testCreateScheduleValidateEmptySlotDate() throws Exception {
        
        this.setup();
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.TRUE);
        
        programSlot.setDateOfProgram("");
        
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Schedule created successfully"));
    }
    
    @Test
    public void testCreateScheduleValidateDAOCreateError() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.anyObject())).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.FALSE);
                
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Error occured while processing..."));
    }
    
    @Test
    public void testCreateScheduleValidateProgramSlotOverlap() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.TRUE);

        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.FALSE);
                
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("ProgramSlot overlapping"));
    }
    
    @Test
    public void testCreateScheduleValidatePresenterNotFound() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Presenter"))).thenReturn(Boolean.TRUE);

        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.FALSE);
                
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Presenter not available"));
    }
    
    @Test
    public void testCreateScheduleValidateProducerNotFound() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Presenter"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Producer"))).thenReturn(Boolean.TRUE);


        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.FALSE);
                
        // Execute
        String string = scheduleService.createSchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Producer not available"));
    }
    
    @Test
    public void testModifyScheduleValidateSomeError() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Presenter"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Producer"))).thenReturn(Boolean.TRUE);
                
        // Execute
        String string = scheduleService.modifySchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Producer not available"));
    }
    
    @Test
    public void testModifyScheduleModifyFail() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Presenter"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Producer"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.update(Mockito.anyObject())).thenReturn(Boolean.FALSE);
                
        // Execute
        String string = scheduleService.modifySchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Error occured while processing..."));
    }
    
    @Test
    public void testModifyScheduleModifySuccess() throws Exception {
        
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Presenter"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Producer"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.update(Mockito.anyObject())).thenReturn(Boolean.TRUE);
                
        // Execute
        String string = scheduleService.modifySchedule(programSlot);
        
        // Validate
        Assert.assertTrue(string.equals("Schedule updated successfully"));
    }
    
    @Test
    public void testGetProgramSlotList() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        List<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        programSlots.add(programSlot);
        
        Mockito.when(scheduleDAOMock.searchMatching(Mockito.anyObject())).thenReturn(programSlots);
        
        List<ProgramSlot> result = scheduleService.getProgramSlotList("DATE");
        ProgramSlot resultP = result.get(0);
        Assert.assertTrue(resultP.getPresenter().getName().equals("name"));
        Assert.assertTrue(resultP.getProducer().getName().equals("name"));
    }
    
    @Test
    public void testCopySchedule() throws Exception {
        this.setup();
        
        Whitebox.setInternalState(scheduleService, "factory", factoryMock);
        Whitebox.setInternalState(scheduleService, "scheduleDAO", scheduleDAOMock);
        
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("All"))).thenReturn(Boolean.TRUE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq(""))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Presenter"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.checkProgramSlotAvailabiltiy(Mockito.anyObject(), Mockito.eq("Producer"))).thenReturn(Boolean.FALSE);
        Mockito.when(scheduleDAOMock.create(Mockito.anyObject())).thenReturn(Boolean.TRUE);
        
        Boolean result = scheduleService.copySchedule("30/06/2017","30/07/2017");
        
        Assert.assertTrue(result);
    }
    
    
}
