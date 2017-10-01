/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.User.service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
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
    
     /**
     * Usertest_Create-method. 
     * This is Junit test to Create User to database
     *
     */
    @Test
    public void Usertest_Create()
    {
        User user = new User();
        user.setId("1234");
        user.setName("ABCDEFG");
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
        Usertest_Edit();
        Usertest_Delete();
    }
    
     /**
     * Usertest_Retrieve-method. 
     * This is Junit test to retrieve all Users from database
     *
     */
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
    
     /**
     * Usertest_RetrievePresenter-method. 
     * This is Junit test to retrieve all Users with Presenter Role from database
     *
     */
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
 
     /**
     * Usertest_RetrieveProducer-method. 
     * This is Junit test to retrieve all Users with Producer Role from database
     *
     */
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
        
     /**
     * Usertest_Edit-method. 
     * This is Junit test to test functionality to edit user profile in database
     *
     */
    public void Usertest_Edit()
    {
        User user = new User();
        user.setId("1234");
        user.setName("ABCEdited");
        user.setPassword("123edited");
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
    
     /**
     * Usertest_Delete-method. 
     * This is Junit test to delete user profile in database
     *
     */
    public void Usertest_Delete()
    {
        User user = new User();
        user.setId("1234");
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
