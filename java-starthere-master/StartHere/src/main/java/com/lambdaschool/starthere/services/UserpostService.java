package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Userpost;

import java.util.List;

public interface UserpostService {

    List<Userpost> findAll();

    Userpost findUserpostById(long id);

    List<Userpost> findByUserName(String username,
                              boolean isAdmin);

    void delete(long id,
                boolean isAdmin);
//    User user, String title, long zip, String line1, String imageurl
    Userpost update(long userpostid,
                     String title,
                     long zip,
                     String line1,
                     String imageurl,
                     boolean isAdmin);
}
