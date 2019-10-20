package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;

import java.util.List;

public interface UserpostService {

    List<Userpost> findAll();

    Userpost save(Userpost userpost);

    Userpost findUserpostById(long id);

    List<Userpost> findByUserName(String username);

    List<Userpost> findByCurrentLocation(String location);

    List<Userpost> findByNotUserid(long id);

    void delete(long id,
                boolean isAdmin);
//    User user, String title, long zip, String line1, String imageurl
    Userpost update(long userpostid,
                     String title,
                     String location,
                     String line1,
                     String imageurl,
                     boolean isAdmin);
}
