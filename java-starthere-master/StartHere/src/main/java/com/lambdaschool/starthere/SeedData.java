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
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                new RandomService());
        Faker nameFaker = new Faker(new Locale("en-US"));

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
                           admins, "Miami");
                u1.getUserposts()
          .add(new Userpost(u1,
                             "We have too many potholes!", "Miami", "Someone please help fix these potholes", nameFaker.internet().avatar()));
        u1.getUserposts()
                .add(new Userpost(u1,
            "The park is too muddy", "Miami", "If we planted grass, it would help with all the mud. Then it would be fun to play there.", nameFaker.internet().avatar()));
//        User user, String title, long zip, String line1, String imageurl



//        u1.getUseremails()
//          .add(new Useremail(u1,
//                             "admin@email.local"));
//        u1.getUseremails()
//          .add(new Useremail(u1,
//                             "admin@mymail.local"));
        User firstUser = new User();
        firstUser = userService.save(u1);

//        Userpost up1 = new Userpost();
//        User thisUser = new User();
//        thisUser = userService.findByName("admin");

//        up1 = userpostService.findUserpostById(29);
        firstUser.getUserposts().get(0)
        .getPostcomments().add(new Postcomment(firstUser, firstUser.getUserposts().get(0), "There's more in front of my house too!", "You're right. This is a big problem! Let's talk to the mayor.", nameFaker.internet().avatar()));
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
                           datas, "Miami");

        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", "Miami", "I think he fell in a pothole"));
        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", "Miami", "His name is Joe"));
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
                           users, "Miami");

        u3.getUserposts()
                .add(new Userpost(u3,
                        "Town center mural", "Miami", "The mural is old and faded. Let's get a group together to repaint it.", nameFaker.internet().avatar()));
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
                           users, "Miami");
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u5 = new User("misskitty",
                           "password",
//                           "misskitty@school.lambda",
                           users, "New York");
        userService.save(u5);

        // using JavaFaker create a bunch of regular users
        // https://www.baeldung.com/java-faker
        // https://www.baeldung.com/regular-expressions-java



        for (int i = 0; i < 10; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                                    r2));
            fakeUser = new User(nameFaker.zelda().character(),
                                "password",
                                users, "Miami");

            fakeUser.getUserposts()
                    .add(new Userpost(fakeUser,
                            nameFaker.chuckNorris().fact(), "Portland", nameFaker.gameOfThrones().quote(), nameFaker.internet().avatar()));
            User newUser = new User();

            newUser = userService.save(fakeUser);
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.backToTheFuture().quote(), nameFaker.rickAndMorty().quote(), nameFaker.internet().avatar()));
        }
    }
}