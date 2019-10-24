package com.lambdaschool.starthere.controller;

import com.lambdaschool.starthere.StartHereApplicationTest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for UserController so only looking at 100% coverage on UserController
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes= StartHereApplicationTest.class)
public class UserControllerIntegrationTests
{
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
    public void A_whenMeasuredResponseTime()
    {
        given().when()
                .get("/users/users")
                .then()
                .time(lessThan(5000L));
    }

    @WithUserDetails("admin")
    @Test
    public void B_getAllUsers() throws Exception
    {
        this.mockMvc.perform(get("/users/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }


    @WithUserDetails("admin")
    @Test
    public void BC_getUserInfo() throws Exception
    {
        this.mockMvc.perform(get("/users/getuserinfo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @WithUserDetails("admin")
    @Test
    public void BD_getUserLikeName() throws Exception
    {
        this.mockMvc.perform(get("/users/user/name/like/{userName}", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @WithUserDetails("admin")
    @Test
    public void C_getUserById() throws Exception
    {
        this.mockMvc.perform(get("/users/user/{userid}",
                28))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @WithUserDetails("admin")
    @Test
    public void CA_getUserByIdNotFound() throws Exception
    {
        this.mockMvc.perform(get("/users/user/{userid}",
                100))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("ResourceNotFoundException")));
    }

    @WithUserDetails("admin")
    @Test
    public void D_getUserByName() throws Exception
    {
        this.mockMvc.perform(get("/users/user/name/{userName}",
                "admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @WithUserDetails("admin")
    @Test
    public void DA_getUserByNameNotFound() throws Exception
    {
        this.mockMvc.perform(get("/users/user/name/{userName}",
                "rabbit"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("ResourceNotFoundException")));
    }









}