package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import com.lambdaschool.starthere.services.PostcommentService;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UserpostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Loggable
@RestController
@RequestMapping("/comments")
public class PostcommentController {
    private static final Logger logger = LoggerFactory.getLogger(UseremailController.class);

//    @Autowired
//    UserpostService userpostService;

    @Autowired
    UserService userService;

    @Autowired
    PostcommentService postcommentService;

    @PutMapping(value = "/edit/{postcommentid}")
    public ResponseEntity<?> updatePost(@PathVariable long postcommentid, HttpServletRequest request,
                                        @RequestBody
                                                Postcomment updatedPostcomment, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());

        Postcomment currentPostcomment = new Postcomment();
        currentPostcomment = postcommentService.findPostcommentById(postcommentid);


        if (currentPostcomment.getUser().getUserid() == currentUser.getUserid()) {
            Postcomment returnPostcomment = postcommentService.update(updatedPostcomment, postcommentid);

            return new ResponseEntity<>(returnPostcomment, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
