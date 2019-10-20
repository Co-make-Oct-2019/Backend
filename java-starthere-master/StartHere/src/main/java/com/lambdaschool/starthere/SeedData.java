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
                           admins, "Miami");
                u1.getUserposts()
          .add(new Userpost(u1,
                             "We have too many potholes!", "Miami", "Someone please help fix these potholes", nameFaker.internet().avatar()));
        u1.getUserposts()
                .add(new Userpost(u1,
            "The park is too muddy", "Miami", "If we planted grass, it would help with all the mud. Then it would be fun to play there.", nameFaker.internet().avatar()));

        User firstUser = new User();
        firstUser = userService.save(u1);


        firstUser.getUserposts().get(0)
        .getPostcomments().add(new Postcomment(firstUser, firstUser.getUserposts().get(0), "There's more in front of my house too!", "You're right. This is a big problem! Let's talk to the mayor.", nameFaker.internet().avatar()));



        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                                r3));
        datas.add(new UserRoles(new User(),
                                r2));
        User u2 = new User("cinnamon",
                           "1234567",
                           datas, "Miami");

        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", "Miami", "I think he fell in a pothole"));
        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", "Miami", "His name is Joe"));

        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u3 = new User("barnbarn",
                           "ILuvM4th!",
                           users, "Miami");

        u3.getUserposts()
                .add(new Userpost(u3,
                        "Town center mural", "Miami", "The mural is old and faded. Let's get a group together to repaint it.", nameFaker.internet().avatar()));
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u4 = new User("puttat",
                           "password",
                           users, "Miami");
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u5 = new User("misskitty",
                           "password",
                           users, "New York");
        userService.save(u5);


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
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().avatar()));
        }
    }
}