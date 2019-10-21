package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostcommentService {

    @Transactional
    List<Postcomment> findAll();

    @Transactional
    Postcomment findPostcommentById(long id);

    @Transactional
    List<Postcomment> findByUserName(String username,
                                  boolean isAdmin);

    //Need to find by post id?

    @Transactional
    void delete(long id);

    //may need user id also to authenticate
    @Transactional
    Postcomment update(Postcomment postcomment, long postcommentid);

    @Transactional
    Postcomment save(Postcomment postcomment, User user, Userpost userpost);

}
