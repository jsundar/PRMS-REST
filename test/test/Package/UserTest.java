/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Package;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.core.Response;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.User.service.UserService;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author WongCheeVui
 */
public class UserTest {
    
    @Before 
    public void PreUsertest()
    {

    }
    
    @Test
    public void Usertest_CreateMock()
    {
        User user = new User();
        user.setId("123");
        user.setName("wong");
        UserService service=mock(UserService.class);

    }
    
    @Test
    public void Usertest_Create()
    {
        User user = new User();
        user.setId("1234");
        user.setName("wongPresenter");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role("Presenter"));
        user.setRoles(roles);
        user.setPassword("12345678");
        
        UserService service = new UserService();
        
        try {
        service.createUserInfo(user);
        } catch (SQLException e) {
            fail();
            System.out.println(e);
        }
    }
    
    @Test
    public void Usertest_Retrieve()
    {
        UserService service = new UserService(); 
        List<User> users = null;
        try {
        users = service.getUserList();
        } catch (SQLException e) {
            fail();
            System.out.println(e);
        }        
    }    
    
    @Test
    public void Usertest_RetrievePresenter()
    {
        UserService service = new UserService(); 
        List<User> users = null;
        try {
        users = service.getPresenterList();
        } catch (SQLException e) {
            fail();
            System.out.println(e);
        }        
    }
 
    @Test
    public void Usertest_RetrieveProducer()
    {
        UserService service = new UserService(); 
        List<User> users = null;
        try {
        users = service.getProducerList();
        } catch (SQLException e) {
            fail();
            System.out.println(e);
        }        
    }
        
    @Test
    public void Usertest_Edit()
    {
        User user = new User();
        user.setId("123");
        user.setName("wongcheevui");
        user.setPassword("123456");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role("statio manager"));
        user.setRoles(roles);
        
        UserService service = new UserService();        
        try {
        service.updateUser(user);
        } catch (SQLException e) {
            fail();
            System.out.println(e);
        }catch (NotFoundException e) {
            fail();
            System.out.println(e);
        }
    }
    
    @Test
    public void Usertest_Delete()
    {
        User user = new User();
        user.setId("123");
        user.setName("wong");
        UserService service = new UserService();        
        try {
        service.deleteUser(user);
        } catch (SQLException e) {
            fail();
            System.out.println(e);
        }catch (NotFoundException e) {
            fail();
            System.out.println(e);
        }
    }  
}
