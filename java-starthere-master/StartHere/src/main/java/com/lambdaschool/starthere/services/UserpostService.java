package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;

import java.util.List;

public interface UserpostService {

    List<Userpost> findAll();

    Userpost save(Userpost userpost, User user);

    Userpost findUserpostById(long id);

    List<Userpost> findByUserName(String username);

    List<Userpost> findByCurrentLocation(String location);

    List<Userpost> findByNotUserid(long id);

    void delete(long id);
//    User user, String title, long zip, String line1, String imageurl
    Userpost update(Userpost userpost, long userpostid, boolean isAdmin);
}
