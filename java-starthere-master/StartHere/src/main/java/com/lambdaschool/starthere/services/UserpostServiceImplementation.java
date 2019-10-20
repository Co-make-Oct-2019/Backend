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
public class UserpostServiceImplementation implements UserpostService {

    @Autowired
    private UserpostRepository userpostrepos;

    @Override
    public Userpost save(Userpost userpost, User user) {

        Userpost newUserpost = new Userpost();
        newUserpost.setImageurl(userpost.getImageurl());
        newUserpost.setLine1(userpost.getLine1());
        newUserpost.setLocation(userpost.getLocation());
        newUserpost.setTitle(userpost.getTitle());
        newUserpost.setUser(user);

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
                .orElseThrow(() -> new ResourceNotFoundException("Userpost with id " + userpostid + " Not Found!"));
    }

    @Override
    public List<Userpost> findByCurrentLocation(String location) {
//        return userpostrepos.findAllByLocation(location);
        List<Userpost> list = new ArrayList<>();
        userpostrepos.findAllByLocation(location)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Userpost> findByUserName(String username) {

        return userpostrepos.findAllByUser_Username(username.toLowerCase());

    }

    @Override
    public List<Userpost> findByNotUserid(long id) {
        return userpostrepos.findByNotUserid(id);
    }

    @Override
    public void delete(long id) {
        if (userpostrepos.findById(id)
                .isPresent()) {
            userpostrepos.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Userpost with id " + id + " Not Found!");
        }
    }

    @Override
    public Userpost update(Userpost userpost, long userpostid, boolean isAdmin) {

        if (userpostrepos.findById(userpostid)
                .isPresent()) {

            Userpost userpostToEdit = findUserpostById(userpostid);

            if (userpost.getTitle() != null) {
                userpostToEdit.setTitle(userpost.getTitle());
            }
            if (userpost.getLocation() != null) {
                userpostToEdit.setLocation(userpost.getLocation());
            }
            if (userpost.getLine1() != null) {
                userpostToEdit.setLine1(userpost.getLine1());
            }
            if (userpost.getImageurl() != null) {
                userpostToEdit.setImageurl(userpost.getImageurl());
            }
            return userpostrepos.save(userpostToEdit);
        } else {
            throw new ResourceNotFoundException("Userpost with id " + userpostid + " Not Found!");
        }
    }

    @Override
    public boolean checkMatch(User user, Userpost userpost) {


        boolean matching =  userpostrepos.checkMatch(user.getUserid(), userpost.getUserpostid());
        System.out.println("MATCHING FROM CHECKMATCH: " + matching);
        return matching;
//        return false;
    }

    @Override
    public Userpost increment(User user, Userpost userpost)
    {
        boolean matching = checkMatch(user, userpost);
        if(matching = true)
        {
            System.out.println("IT MATCHES");
            userpost.setVoted(true);
        } else {
            System.out.println("IT DOES NOT MATCH");
            userpost.setVoted(false);
        }
        return userpost;
    }
}
