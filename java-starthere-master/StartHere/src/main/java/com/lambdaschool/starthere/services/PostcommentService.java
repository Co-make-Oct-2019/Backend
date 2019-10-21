package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostcommentService {

    List<Postcomment> findAll();

    Postcomment findPostcommentById(long id);

    List<Postcomment> findByUserName(String username,
                                  boolean isAdmin);

    //Need to find by post id?

    void delete(long id);

    //may need user id also to authenticate
    Postcomment update(Postcomment postcomment, long postcommentid);

    Postcomment save(Postcomment postcomment, User user, Userpost userpost);
}
