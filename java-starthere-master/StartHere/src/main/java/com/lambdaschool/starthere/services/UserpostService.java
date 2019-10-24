package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserpostService {


    List<Userpost> findAll();


    Userpost save(Userpost userpost, User user);


    Userpost findUserpostById(long id);


    List<Userpost> findByUserName(String username);


    List<Userpost> findByCurrentLocation(String location);

    List<Userpost> findByNameContaining(String titlestring);

    List<Userpost> findByNotUserid(long id);


    Userpost increment(User user, Userpost userpost);


    Userpost decrement(User user, Userpost userpost);


    boolean checkMatch(User user, Userpost userpost);


    int getCount(Userpost userpost);

//    int getReputation(User user);


    void delete(long id);
//    User user, String title, long zip, String description, String imageurl

    Userpost update(Userpost userpost, long userpostid, boolean isAdmin);
}
