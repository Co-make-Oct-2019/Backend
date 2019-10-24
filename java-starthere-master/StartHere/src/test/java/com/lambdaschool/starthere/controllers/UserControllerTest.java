package com.lambdaschool.starthere.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.starthere.StartHereApplicationTest;
import com.lambdaschool.starthere.models.Role;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.UserRoles;
//import com.lambdaschool.starthere.models.Useremail;
import com.lambdaschool.starthere.services.RoleService;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UserpostService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// mocking service to test controller
// Due to reliance on security, cannot test
//     getCurrentUserInfo
//     getCurrentUserName

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
@ContextConfiguration(classes= StartHereApplicationTest.class)
public class UserControllerTest
{
//    @Autowired
//    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenStore tokenStore;

    @MockBean
    private UserpostService userpostService;

    @MockBean
    private AuthenticationManager authManager;

    @MockBean
    private RoleService roleService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("admin", "ILuvM4th!", admins, "Miami");

        u1.setUserid(101);
        userList.add(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r3));
        datas.add(new UserRoles(new User(), r2));
        User u2 = new User("cinnamon", "1234567", datas, "Miami");

        u2.setUserid(102);
        userList.add(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r1));
        User u3 = new User("testingbarn", "ILuvM4th!", users, "Miami");

        u3.setUserid(103);
        userList.add(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("testingcat", "password", users, "Miami");
        u4.setUserid(104);
        userList.add(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("testingdog", "password", users, "Miami");
        u5.setUserid(105);
        userList.add(u5);

        System.out.println("\n*** Seed Data ***");
        for (User u : userList)
        {
            System.out.println(u);
        }
        System.out.println("*** Seed Data ***\n");
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        //Requires authentication
    }


    @Test
    public void getUserById() throws Exception
    {
        //Requires authentication
    }




    @Test
    public void getCurrentUserName() throws Exception
    {
        // requires security which we have turned off for unit test
        // refer to integration testing for test of this method
    }

    @Test
    public void addNewUser() throws Exception
    {
        String apiUrl = "/newuser/createnewuser";

        // build a user
        ArrayList<UserRoles> thisRole = new ArrayList<>();
//        ArrayList<Useremail> thisEmail = new ArrayList<>();
        User u1 = new User();
        u1.setUserid(100);
        u1.setUsername("tiger");
        u1.setPassword("ILuvM4th!");
//        u1.setPrimaryemail("tiger@home.local");
        u1.setUserroles(thisRole);
//        u1.setUseremails(thisEmail);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u1);

        Mockito.when(userService.save(any(User.class))).thenReturn(u1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception
    {
        //requires authentication
    }

}