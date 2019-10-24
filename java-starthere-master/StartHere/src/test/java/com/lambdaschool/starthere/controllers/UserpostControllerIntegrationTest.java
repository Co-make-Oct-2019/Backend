package com.lambdaschool.starthere.controllers;


import com.lambdaschool.starthere.StartHereApplicationTest;
import com.lambdaschool.starthere.models.Role;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.UserRoles;
import com.lambdaschool.starthere.models.Userpost;
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
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes= StartHereApplicationTest.class)
public class UserpostControllerIntegrationTest {

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
    public void givenUpdateAPost() throws Exception
    {

//        User user = new User();
//        user.setUserid(1);
//        Userpost r1 = new Userpost(user,"hello",null,null,null);
//        r1.setUserpostid(909);

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

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String stringR1 = mapper.writeValueAsString(up1);

        given().contentType("application/json").body(stringR1).when().put("/posts/post/6").then().statusCode(200);
    }

    @WithUserDetails("admin")
    @Test
    public void givenAddAPost() throws Exception
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

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String stringR3 = mapper.writeValueAsString(up1);

        given().contentType("application/json").body(stringR3).when().post("/posts/post").then().statusCode(201);
    }

    @WithUserDetails("admin")
    @Test
    public void givenIncrementAPost() throws Exception
    {

        given().contentType("application/json").body("").when().put("/posts/post/increment/6").then().statusCode(200);
    }

    @WithUserDetails("admin")
    @Test
    public void givenDecrementAPost() throws Exception
    {

        given().contentType("application/json").body("").when().put("/posts/post/decrement/6").then().statusCode(200);
    }

    @WithUserDetails("admin")
    @Test
    public void A_whenMeasuredResponseTime()
    {
        given().when()
                .get("/posts/allposts")
                .then()
                .time(lessThan(5000L));
    }

    @WithUserDetails("admin")
    @Test
    public void B_listAllPosts() throws Exception
    {
        this.mockMvc.perform(get("/posts/allposts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @WithUserDetails("admin")
    @Test
    public void B_listAllMyPosts() throws Exception
    {
        this.mockMvc.perform(get("/posts/myposts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @WithUserDetails("admin")
    @Test
    public void C_listOtherPosts() throws Exception
    {
        this.mockMvc.perform(get("/posts/otherposts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TEST")));
    }


    @WithUserDetails("admin")
    @Test
    public void D_listPostsInCurrentLocation() throws Exception
    {
        this.mockMvc.perform(get("/posts/currentlocation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Miami")));
    }

    @WithUserDetails("admin")
    @Test
    public void D_findUserPostByLocation() throws Exception
    {
        this.mockMvc.perform(get("/posts/location/Portland"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Portland")));
    }

    @WithUserDetails("admin")
    @Test
    public void D_findUserPostById() throws Exception
    {
        this.mockMvc.perform(get("/posts/post/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @WithUserDetails("admin")
    @Test
    public void D_deleteUserPostById() throws Exception
    {
        this.mockMvc.perform(delete("/posts/post/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")));
    }











}
