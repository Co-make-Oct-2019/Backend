package com.lambdaschool.starthere;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.RoleService;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UserpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Locale;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    UserpostService userpostService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                                 r1));
        admins.add(new UserRoles(new User(),
                                 r2));
        admins.add(new UserRoles(new User(),
                                 r3));
        User u1 = new User("admin",
                           "password",
//                           "admin@lambdaschool.local",
                           admins);
                u1.getUserposts()
          .add(new Userpost(u1,
                             "We have too many potholes!", 97222, "Someone please help fix these potholes", "http://example.come/image.jpg"));
        u1.getUserposts()
                .add(new Userpost(u1,
            "The park is too muddy", 97222, "If we planted grass, it would help with all the mud. Then it would be fun to play there.", "http://example.come/image.jpg"));
//        User user, String title, long zip, String line1, String imageurl



//        u1.getUseremails()
//          .add(new Useremail(u1,
//                             "admin@email.local"));
//        u1.getUseremails()
//          .add(new Useremail(u1,
//                             "admin@mymail.local"));

        userService.save(u1);

        Userpost up1 = new Userpost();
        User thisUser = new User();
        thisUser = userService.findByName("admin");

        up1 = userpostService.findUserpostById(29);
        up1.getPostcomments().add(new Postcomment(thisUser, up1, "This is a comment title", "Line 1 text", "http://image.com/image.jpg"));
//        userpostService.save(up1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                                r3));
        datas.add(new UserRoles(new User(),
                                r2));
        User u2 = new User("cinnamon",
                           "1234567",
//                           "cinnamon@lambdaschool.local",
                           datas);

        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", 97222, "I think he fell in a pothole"));
        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", 97222, "His name is Joe"));
//        u2.getUseremails()
//          .add(new Useremail(u2,
//                             "cinnamon@mymail.local"));
//        u2.getUseremails()
//          .add(new Useremail(u2,
//                             "hops@mymail.local"));
//        u2.getUseremails()
//          .add(new Useremail(u2,
//                             "bunny@email.local"));
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u3 = new User("barnbarn",
                           "ILuvM4th!",
//                           "barnbarn@lambdaschool.local",
                           users);

        u3.getUserposts()
                .add(new Userpost(u3,
                        "Town center mural", 97222, "The mural is old and faded. Let's get a group together to repaint it.", "http://example.come/image.jpg"));
//        u3.getUseremails()
//          .add(new Useremail(u3,
//                             "barnbarn@email.local"));
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u4 = new User("puttat",
                           "password",
//                           "puttat@school.lambda",
                           users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u5 = new User("misskitty",
                           "password",
//                           "misskitty@school.lambda",
                           users);
        userService.save(u5);

        // using JavaFaker create a bunch of regular users
        // https://www.baeldung.com/java-faker
        // https://www.baeldung.com/regular-expressions-java

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                                                                    new RandomService());
        Faker nameFaker = new Faker(new Locale("en-US"));

        for (int i = 0; i < 100; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                                    r2));
            fakeUser = new User(nameFaker.name()
                                         .username(),
                                "password",
//                                nameFaker.internet()
//                                         .emailAddress(),
                                users);
//            fakeUser.getUseremails()
//                    .add(new Useremail(fakeUser,
//                                       fakeValuesService.bothify("????##@gmail.com")));
            userService.save(fakeUser);
        }
    }
}