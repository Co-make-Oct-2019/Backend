package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.Userpost;

import java.util.List;

public interface PostcommentService {
    List<Postcomment> findAll();

    Userpost findPostcommentById(long id);

    List<Postcomment> findByUserName(String username,
                                  boolean isAdmin);

    //Need to find by post id?

    void delete(long id,
                boolean isAdmin);

    //may need user id also to authenticate
    Userpost update(long postcommentid,
                    String title,
                    String line1,
                    String imageurl,
                    boolean isAdmin);
}
