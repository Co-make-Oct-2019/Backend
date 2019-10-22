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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartHereApplicationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserpostServiceImplementationTest {


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
    public void save() {

        User user = userService.findByName("admin");
        user.setUsername("admin233");
        List<Userpost> ups = new ArrayList<>();
        user.setUserposts(ups);
        User u2 = userService.save(user);
        Userpost newPost = userpostService.save(new Userpost(u2, "something else", "something else", "something else", "something else"), u2);

        assertNotNull(newPost);
    }

    @Test
    public void findAll() {
        assertEquals(16, userpostService.findAll().size());
    }

    @Test
    public void findUserpostById() {
        Userpost up = userpostService.findUserpostById(5);
        assertEquals("TEST We have too many potholes!", up.getTitle());
    }

    @Test
    public void findByCurrentLocation() {
        List<Userpost> mylist = userpostService.findByCurrentLocation("Miami");
        assertEquals(4, mylist.size());
    }

    @Test
    public void findByUserName() {
        List<Userpost> mylist = userpostService.findByUserName("admin");
        assertEquals(1, mylist.size());
    }

    @Test
    public void findByNotUserid() {
        List<Userpost> mylist = userpostService.findByNotUserid(4);
        assertEquals(15, mylist.size());
    }

    @Test
    public void delete() {
        userpostService.delete(6);
        assertEquals(16, userpostService.findAll().size());
    }

    @Test
    public void update() {
        User user = userService.findByName("admin");
        user.setUsername("admin2212");
        List<Userpost> ups = new ArrayList<>();
        user.setUserposts(ups);
        User u2 = userService.save(user);
        Userpost newPost = userpostService.save(new Userpost(u2, "something else", "something else", "something else", "something else"), u2);
        newPost.setTitle("Moo");
        Userpost u3 = userpostService.update(newPost, newPost.getUserpostid(), true);
        System.out.println(u3.getTitle());
    }

    @Test
    public void checkMatch() {
        User user = userService.findByName("admin");
        user.setUsername("admin2");
        List<Userpost> ups = new ArrayList<>();
        user.setUserposts(ups);
        User u2 = userService.save(user);
        Userpost newPost = userpostService.save(new Userpost(u2, "something else", "something else", "something else", "something else"), u2);
        boolean matching = userpostService.checkMatch(u2, newPost);
        assertEquals(false, matching);
    }

    @Test
    public void increment() {
        User user = userService.findByName("admin");
        user.setUsername("admin244");
        List<Userpost> ups = new ArrayList<>();
        user.setUserposts(ups);
        User u2 = userService.save(user);
        Userpost newPost = userpostService.save(new Userpost(u2, "something else", "something else", "something else", "something else"), u2);

        Userpost u3 = userpostService.increment(user, newPost);
//        System.out.println(u3.getCount());
        assertTrue(1 == u3.getCount());
//        assertEquals(1, u3.getCount());

//        boolean matching = userpostService.checkMatch(u2, newPost);
//        assertEquals(false, matching);

    }

    @Test
    public void decrement() {

        User user = userService.findByName("admin");
        user.setUsername("admin23");
        List<Userpost> ups = new ArrayList<>();
        user.setUserposts(ups);
        User u2 = userService.save(user);
        Userpost newPost = userpostService.save(new Userpost(u2, "something else", "something else", "something else", "something else"), u2);

        Userpost u3 = userpostService.increment(user, newPost);
//        System.out.println(u3.getCount());
        assertTrue(1 == u3.getCount());
        Userpost u4 = userpostService.decrement(user, newPost);
        assertTrue(0 == u4.getCount());
    }

    @Test
    public void getCount() {

        User user = userService.findByName("admin");
        user.setUsername("admin255");
        List<Userpost> ups = new ArrayList<>();
        user.setUserposts(ups);
        User u2 = userService.save(user);
        Userpost newPost = userpostService.save(new Userpost(u2, "something else", "something else", "something else", "something else"), u2);

        Userpost u3 = userpostService.increment(user, newPost);

        assertTrue(userpostService.getCount(u3) == 1);
//        System.out.println(u3.getCount());
//        assertTrue(1 == u3.getCount());


    }

    @Test
    public void findByNameContaining() {

        List<Userpost> mylist = userpostService.findByNameContaining("TEST");
        assertTrue(mylist.size()> 1);
    }
}