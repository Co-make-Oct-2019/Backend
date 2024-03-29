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
                             "NEED MORE BAO", "Miami", "Someone please open up a bao shop. Those are delicious", nameFaker.internet().image(300,300,false, "co-make")));
        u1.getUserposts()
                .add(new Userpost(u1,
            "The park is too muddy", "Miami", "If we planted grass, it would help with all the mud. Then it would be fun to play there.", nameFaker.internet().avatar()));

        User firstUser = new User();
        u1.setImageurl(nameFaker.internet().avatar());
        firstUser = userService.save(u1);


        firstUser.getUserposts().get(0)
        .getPostcomments().add(new Postcomment(firstUser, firstUser.getUserposts().get(0), "Yea! Bao is soooo tasty!", "WHAT DO WE WANT?! BAO! WHEN DO WANT IT?! BAO!", "https://steamykitchen.com/wp-content/uploads/2010/03/xiao-long-bao.jpg"));

        firstUser.getUserposts().get(0)
                .getPostcomments().add(new Postcomment(firstUser, firstUser.getUserposts().get(0), "I'mma say it one more time...", "I want some bao right meow!", "https://steamykitchen.com/wp-content/uploads/2010/03/dumplings.jpg"));

        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                                r1));
//        datas.add(new UserRoles(new User(),
//                                r2));
        User u2 = new User("Bao",
                           "1234567",
                           datas, "Miami");

        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", "Miami", "I think he fell in a pothole", nameFaker.internet().image(300,300,false, "co-make")));
        u2.getUserposts()
                .add(new Userpost(u2,
                        "I lost my dog!", "Miami", "His name is Joe", nameFaker.internet().image(300,300,false, "co-make")));
        u2.setImageurl(nameFaker.internet().avatar());
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
                        "Town center mural", "Miami", "The mural is old and faded. Let's get a group together to repaint it.", nameFaker.internet().image(300,300,false, "co-make")));
        u3.setImageurl(nameFaker.internet().avatar());
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u4 = new User("puttat",
                           "password",
                           users, "Miami");
        u4.setImageurl(nameFaker.internet().avatar());
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));

        User u5 = new User("misskitty",
                           "password",
                           users, "New York");
        u5.setImageurl(nameFaker.internet().avatar());
        userService.save(u5);


        for (int i = 0; i < 5; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                                    r1));
            fakeUser = new User(nameFaker.gameOfThrones().character(),
                                "password",
                                users, "Miami");

            fakeUser.getUserposts()
                    .add(new Userpost(fakeUser,
                            nameFaker.chuckNorris().fact(), "Miami", nameFaker.gameOfThrones().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            User newUser = new User();
            fakeUser.setImageurl(nameFaker.internet().avatar());
            newUser = userService.save(fakeUser);
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
        }


        for (int i = 0; i < 5; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                    r1));
            fakeUser = new User(nameFaker.zelda().character(),
                    "password",
                    users, "Portland");

            fakeUser.getUserposts()
                    .add(new Userpost(fakeUser,
                            nameFaker.chuckNorris().fact(), "Portland", nameFaker.gameOfThrones().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            User newUser = new User();
            fakeUser.setImageurl(nameFaker.internet().avatar());
            newUser = userService.save(fakeUser);
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
        }

        for (int i = 0; i < 5; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                    r1));
            fakeUser = new User(nameFaker.internet().emailAddress(),
                    "password",
                    users, "Chicago");

            fakeUser.getUserposts()
                    .add(new Userpost(fakeUser,
                            nameFaker.chuckNorris().fact(), "Chicago", nameFaker.gameOfThrones().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            User newUser = new User();
            fakeUser.setImageurl(nameFaker.internet().avatar());
            newUser = userService.save(fakeUser);
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
        }

        for (int i = 0; i < 5; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                    r1));
            fakeUser = new User(nameFaker.artist().name(),
                    "password",
                    users, "Boston");

            fakeUser.getUserposts()
                    .add(new Userpost(fakeUser,
                            nameFaker.chuckNorris().fact(), "Boston", nameFaker.gameOfThrones().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            User newUser = new User();
            fakeUser.setImageurl(nameFaker.internet().avatar());
            newUser = userService.save(fakeUser);
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0), nameFaker.company().bs(), nameFaker.rickAndMorty().quote(), nameFaker.internet().image(300,300,false, "co-make")));
        }

    }
}