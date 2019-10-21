package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserpostService {

    @Transactional
    List<Userpost> findAll();

    @Transactional
    Userpost save(Userpost userpost, User user);

    @Transactional
    Userpost findUserpostById(long id);

    @Transactional
    List<Userpost> findByUserName(String username);

    @Transactional
    List<Userpost> findByCurrentLocation(String location);

    @Transactional
    List<Userpost> findByNotUserid(long id);

    @Transactional
    Userpost increment(User user, Userpost userpost);

    @Transactional
    Userpost decrement(User user, Userpost userpost);

    @Transactional
    boolean checkMatch(User user, Userpost userpost);

    @Transactional
    int getCount(Userpost userpost);

    @Transactional
    void delete(long id);
//    User user, String title, long zip, String line1, String imageurl
    @Transactional
    Userpost update(Userpost userpost, long userpostid, boolean isAdmin);
}
