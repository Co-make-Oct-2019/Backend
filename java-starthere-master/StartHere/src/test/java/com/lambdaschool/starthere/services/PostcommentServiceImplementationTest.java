package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.StartHereApplication;
import com.lambdaschool.starthere.StartHereApplicationTest;
import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.UserRoles;
import com.lambdaschool.starthere.models.Userpost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartHereApplicationTest.class)
public class PostcommentServiceImplementationTest {


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
    public void findAll() {
        assertEquals(11, postcommentService.findAll().size());
    }

    @Test
    public void findPostcommentById() {
        assertEquals("TEST There's more in front of my house too!", postcommentService.findPostcommentById(7).getTitle());
    }

//    @Test
//    public void findByUserName() {
//        List<Postcomment> pcArray = postcommentService.findByUserName("admin", true);
//        assertEquals(2, pcArray.size());
//    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
//        User newUser = new User(null,null,null, null, 3);
//        User user = userService.findUserById(4);
//        Postcomment pc1 = new Postcomment(user, user.getUserposts().get(0), "Meow", null,null);
//        PostcommentService updatedPc1 = postcommentService.update(pc1, 7)
        Postcomment newPc1 = new Postcomment();
        newPc1.setDescription("Meow");
        Postcomment pc = postcommentService.findPostcommentById(7);
//        pc.setTitle("Meow");
        postcommentService.update(newPc1, 7);
        assertEquals("Meow", postcommentService.findPostcommentById(7).getDescription());
    }

    @Test
    public void save() {
        ArrayList<UserRoles> datas = new ArrayList<>();
        User user = new User("guy","password",datas, "location");
        user.setUserid(100);
        User saveU2 = userService.save(user);
        Userpost saveup = userpostService.save(new Userpost(saveU2, "title", "location", "descritption", "url"), saveU2);
        Postcomment pc = new Postcomment(saveU2, saveup, "TITLE", "DESC", "IMAGE");
        Postcomment pc2 = postcommentService.save(pc, saveU2, saveup);
        assertNotNull(pc2);
        assertEquals("guy", pc2.getUser().getUsername());
    }
}