package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.repository.UseremailRepository;
import com.lambdaschool.starthere.repository.UserpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "userpostService")
public class UserpostServiceImplementation implements UserpostService{

    @Autowired
    private UserpostRepository userpostrepos;


    @Override
    public Userpost save(Userpost userpost) {
        Userpost newUserpost = new Userpost();
        newUserpost.setImageurl(userpost.getImageurl());
        newUserpost.setLine1(userpost.getLine1());
        newUserpost.setZip(userpost.getZip());
        newUserpost.setTitle(userpost.getTitle());
//        newUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());

//        ArrayList<UserRoles> newRoles = new ArrayList<>();
//        for (UserRoles ur : user.getUserroles()) {
//            long id = ur.getRole()
//                    .getRoleid();
//            Role role = rolerepos.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
//            newRoles.add(new UserRoles(newUser,
//                    ur.getRole()));
//        }
//        newUser.setUserroles(newRoles);

//        for (Userpost up : user.getUserposts()) {
//
//            newUser.getUserposts()
//                    .add(new Userpost(newUser,
//                            up.getTitle(), up.getZip(), up.getLine1(), up.getImageurl()));
//        }

//        for (Useremail ue : user.getUseremails())
//        {
//            newUser.getUseremails()
//                   .add(new Useremail(newUser,
//                                      ue.getUseremail()));
//        }
        System.out.println("MEOW MOEW *********************************************************");
        System.out.println("MEOW MOEW *********************************************************");
        System.out.println("MEOW MOEW *********************************************************");
        System.out.println("MEOW MOEW *********************************************************");
        return userpostrepos.save(newUserpost);
    }

    @Override
    public List<Userpost> findAll() {
        List<Userpost> list = new ArrayList<>();
        userpostrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Userpost findUserpostById(long userpostid) {
        return userpostrepos.findById(userpostid)
//        return userpostrepos.findByUserpostid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Userpost with id " + userpostid + " Not Found!"));
    }

    @Override
    public List<Userpost> findByUserName(String username, boolean isAdmin) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName().toLowerCase()) || isAdmin)
        {
            return userpostrepos.findAllByUser_Username(username.toLowerCase());
        } else
        {
            throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
        }
    }

    @Override
    public List<Userpost> findByNotUserid(long id) {
        System.out.println("*****************************************************");
        System.out.println("*****************************************************");
        System.out.println("*****************************************************");
        System.out.println(id);
        return userpostrepos.findByNotUserid(id);
    }

    @Override
    public void delete(long id, boolean isAdmin) {
        if (userpostrepos.findById(id)
                .isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (userpostrepos.findById(id)
                    .get()
                    .getUser()
                    .getUsername()
                    .equalsIgnoreCase(authentication.getName()) || isAdmin)
            {
                userpostrepos.deleteById(id);
            } else
            {
                throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
            }
        } else
        {
            throw new ResourceNotFoundException("Useremail with id " + id + " Not Found!");
        }
    }

    @Override
    public Userpost update(long userpostid, String title, long zip, String line1, String imageurl, boolean isAdmin) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (userpostrepos.findById(userpostid)
                .isPresent())
        {
            if (userpostrepos.findById(userpostid)
                    .get()
                    .getUser()
                    .getUsername()
                    .equalsIgnoreCase(authentication.getName()) || isAdmin)
            {
                Userpost userpost = findUserpostById(userpostid);
                userpost.setTitle(title);
                userpost.setZip(zip);
                userpost.setLine1(line1);
                userpost.setImageurl(imageurl);
                return userpostrepos.save(userpost);
            } else
            {
                throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
            }
        } else
        {
            throw new ResourceNotFoundException("Useremail with id " + userpostid + " Not Found!");
        }
    }
}
