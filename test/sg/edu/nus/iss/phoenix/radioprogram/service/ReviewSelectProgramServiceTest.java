/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.service;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;

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
    
    
}
