package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.StartHereApplicationTest;
import com.lambdaschool.starthere.models.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes= StartHereApplicationTest.class)
public class PostcommentControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    public static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() throws Exception
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @WithUserDetails("admin")
    @Test
    public void D_getPostComment() throws Exception
    {
        this.mockMvc.perform(get("/comments/comment/8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @WithUserDetails("admin")
    @Test
    public void givenUpdateAPostcomment() throws Exception
    {

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
        User u1 = new User("admin", "password", admins, "Miami");

        u1.setUserid(101);

        Userpost up1 = new Userpost(u1,
                "TEST We have too many potholes!", "Miami", "TEST Someone please help fix these potholes");
        up1.setUserpostid(808);
        Postcomment pc1 = new Postcomment(u1, up1, "Hello", "desc", "url");

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String stringR1 = mapper.writeValueAsString(pc1);

        given().contentType("application/json").body(stringR1).when().put("/comments/comment/8").then().statusCode(200);
    }

    @WithUserDetails("admin")
    @Test
    public void D_deletePostcommentById() throws Exception
    {
        this.mockMvc.perform(delete("/comments/comment/7"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }


    @WithUserDetails("admin")
    @Test
    public void givenAddAComment() throws Exception
    {


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
        User u1 = new User("admin", "password", admins, "Miami");

        u1.setUserid(101);

        Userpost up1 = new Userpost(u1,
                "TEST We have too many potholes!", "Miami", "TEST Someone please help fix these potholes");
        up1.setUserpostid(808);
        Postcomment pc1 = new Postcomment(u1, up1, "Hello", "desc", "url");

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String stringR3 = mapper.writeValueAsString(pc1);

        given().contentType("application/json").body(stringR3).when().post("/comments/comment/6").then().statusCode(201);
    }





}
