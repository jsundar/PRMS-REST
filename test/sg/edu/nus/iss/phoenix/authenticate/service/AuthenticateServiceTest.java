package sg.edu.nus.iss.phoenix.authenticate.service;


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
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.authenticate.service.AuthenticateService;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 * @author William
 * @version 1.0 2017/10/01
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticateServiceTest extends TestCase {
    
    private AuthenticateService authenticateService;
            
    @Mock
    private DAOFactoryImpl factoryMock;
    
    @Mock
    private UserDao udaoMock;
	
    @Mock
    private RoleDao rdaoMock;
    
    @Before 
    public void setup() throws Exception {
        super.setUp();
        
        authenticateService = new AuthenticateService();
        factoryMock = Mockito.mock(DAOFactoryImpl.class);
        udaoMock = Mockito.mock(UserDao.class);
        rdaoMock = Mockito.mock(RoleDao.class); 
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testValidateUserIdPasswordUserNotFound() throws Exception {
        this.setup();
        Whitebox.setInternalState(authenticateService, "factory", factoryMock);
        Whitebox.setInternalState(authenticateService, "udao", udaoMock);
        Whitebox.setInternalState(authenticateService, "rdao", rdaoMock);
        
        Mockito.when(udaoMock.searchMatching(Mockito.any(User.class))).thenReturn(null);
        
        User user = authenticateService.validateUserIdPassword(new User());
        
        Assert.assertNull(user);
    }
    
    @Test
    public void testValidateUserIdPasswordRoleFound() throws Exception {
        this.setup();
        Whitebox.setInternalState(authenticateService, "factory", factoryMock);
        Whitebox.setInternalState(authenticateService, "udao", udaoMock);
        Whitebox.setInternalState(authenticateService, "rdao", rdaoMock);
        
        
        User user = new User();
        user.setAll("id", "password", "name", "presenter");
        List<User> users = new ArrayList<>();
        users.add(user);
        
        List<Role> roles = new ArrayList<>();
        Role role = new Role("presenter");
        Role role2 = new Role("presenter2");
        roles.add(role);
        roles.add(role2);
        
        Mockito.when(udaoMock.searchMatching(Mockito.anyString())).thenReturn(user);
        Mockito.when(rdaoMock.searchMatching(Mockito.any(Role.class))).thenReturn(roles);

        
        User userResult = authenticateService.validateUserIdPassword(new User());
        List <Role> roleResult = userResult.getRoles();
        
        Assert.assertNotNull(userResult);
        Assert.assertEquals("name", userResult.getName());
        Assert.assertEquals(2, roles.size());
        Assert.assertEquals("presenter", roleResult.get(0).getRole());
    }
}
