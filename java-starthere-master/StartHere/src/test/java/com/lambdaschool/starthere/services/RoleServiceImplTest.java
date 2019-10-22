package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.StartHereApplicationTest;
import com.lambdaschool.starthere.models.Role;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartHereApplicationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceImplTest {

    @Autowired
    PostcommentService postcommentService;

    @Autowired
    UserService userService;

    @Autowired
    UserpostService userpostService;

    @Autowired
    RoleService roleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        assertEquals(3, roleService.findAll().size());
    }

    @Test
    public void findRoleById() {
//        System.out.println(roleService.findAll().get(0).getRoleid());
        assertEquals("ADMIN", roleService.findRoleById(1).getName());
    }

    @Test
    public void findByName() {
        assertEquals("ADMIN", roleService.findByName("admin").getName());
    }

//    @Test
//    public void delete() {
//        roleService.delete(1);
//        assertEquals(2, roleService.findAll().size());
//    }
//
//    @Test
//    public void update() {
//    }

    @Test
    public void save() {
        Role r4 = new Role("FOUR");
        roleService.save(r4);
        assertEquals(4, roleService.findAll().size());
    }
}