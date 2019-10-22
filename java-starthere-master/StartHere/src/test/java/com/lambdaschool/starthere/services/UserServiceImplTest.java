package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.StartHereApplicationTest;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartHereApplicationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    PostcommentService postcommentService;

    @Autowired
    UserService userService;

    @Autowired
    UserpostService userpostService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loadUserByUsername() {
        //Premade functions I'm assuming are already tested
        UserDetails user = userService.loadUserByUsername("admin");
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void findUserById() {
        User user = userService.findUserById(4);
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void findByNameContaining() {
        List<User> mylist = userService.findByNameContaining("adm", Pageable.unpaged());
        assertTrue(mylist.size() > 0);
    }

    @Test
    public void findAll() {
        List<User> mylist = userService.findAll(Pageable.unpaged());
        assertEquals(15, mylist.size());
    }

    @Test
    public void delete() {
    }

    @Test
    public void findByName() {
        User user = userService.findByName("admin");
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void save() {
        User user = userService.findUserById(4);
        user.setUsername("admin2");
        List<Userpost> up = new ArrayList<>();
        user.setUserposts(up);
        User u2 = userService.save(user);
        List<User> mylist = userService.findAll(Pageable.unpaged());
        assertEquals(16, mylist.size());
        userService.delete(u2.getUserid());
    }

    @Test
    public void update() {
        User user = userService.findUserById(4);

        User newUser = new User();
        newUser.setDescription("cheese");

        User u3 = userService.update(newUser, user.getUserid(), true);


        assertEquals("cheese", u3.getDescription());

    }

    @Test
    public void deleteUserRole() {
    }

    @Test
    public void addUserRole() {
    }
}