package com.lambdaschool.starthere;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.PostcommentService;
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

    @Autowired
    PostcommentService postcommentService;


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
                             "TEST We have too many potholes!", "Miami", "TEST Someone please help fix these potholes", nameFaker.internet().image()));
        u1.getUserposts()
                .add(new Userpost(u1,
            "TEST The park is too muddy", "Miami", "TEST If we planted grass, it would help with all the mud. Then it would be fun to play there.", nameFaker.internet().avatar()));

        User firstUser = new User();
        u1.setImageurl(nameFaker.internet().avatar());
        firstUser = userService.save(u1);



//        firstUser.getUserposts().get(0)
//        .getPostcomments().add(new Postcomment(firstUser, firstUser.getUserposts().get(0), "TEST There's more in front of my house too!", "TEST You're right. This is a big problem! Let's talk to the mayor.", nameFaker.internet().image()));

        Postcomment pc = postcommentService.save(new Postcomment(firstUser, firstUser.getUserposts().get(0), "TEST There's more in front of my house too!", "TEST You're right. This is a big problem! Let's talk to the mayor.", nameFaker.internet().image()), firstUser, firstUser.getUserposts().get(0));

//        Postcomment pc = firstUser.getUserposts().get(0).getPostcomments().get(0);
        System.out.println(firstUser.getUserid());
//        System.out.println(pc.getTitle());
        System.out.println("**************************");
        System.out.println("**************************");

        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                                r1));
//        datas.add(new UserRoles(new User(),
//                                r2));
        User u2 = new User("cinnamon",
                           "1234567",
                           datas, "Miami");

        u2.getUserposts()
                .add(new Userpost(u2,
                        "TEST I lost my dog!", "Miami", "TEST I think he fell in a pothole", nameFaker.internet().image()));
        u2.getUserposts()
                .add(new Userpost(u2,
                        "TEST I lost my dog!", "Miami", "TEST His name is Joe", nameFaker.internet().image()));
        u2.setImageurl(nameFaker.internet().avatar());
        User cin = userService.save(u2);
        System.out.println(cin.getUserid());
        System.out.println(cin.getUsername());

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                                r2));
        User u3 = new User("barnbarn",
                           "ILuvM4th!",
                           users, "Miami");

        u3.getUserposts()
                .add(new Userpost(u3,
                        "TEST Town center mural", "Miami", "TEST The mural is old and faded. Let's get a group together to repaint it.", nameFaker.internet().image()));
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


        for (int i = 0; i < 10; i++)
        {
            new User();
            User fakeUser;

            users = new ArrayList<>();
            users.add(new UserRoles(new User(),
                                    r1));
            fakeUser = new User("TEST "+nameFaker.zelda().character(),
                                "password",
                                users, "Miami");

            fakeUser.getUserposts()
                    .add(new Userpost(fakeUser,
                            "TEST " + nameFaker.chuckNorris().fact(), "Portland", "TEST" + nameFaker.gameOfThrones().quote(), nameFaker.internet().image()));
            User newUser = new User();
            fakeUser.setImageurl(nameFaker.internet().avatar());
            newUser = userService.save(fakeUser);
            newUser.getUserposts()
                    .get(0)
                    .getPostcomments()
                    .add(new Postcomment(newUser, newUser.getUserposts().get(0),"TEST "+ nameFaker.company().bs(),"TEST " + nameFaker.rickAndMorty().quote(), nameFaker.internet().image()));
        }
    }
}