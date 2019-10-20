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
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

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

    @Autowired
    UserpostService userpostService;

    @PutMapping(value = "/comment/{postcommentid}")
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


            Userpost up = returnPostcomment.getUserpost();


            boolean checkMatch = userpostService.checkMatch(currentUser, up);

            if (checkMatch == true) {
                up.setVoted(true);
            }


            return new ResponseEntity<>(returnPostcomment, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comment/{postcommentid}")
    public ResponseEntity<?> deletePostcommentById(HttpServletRequest request,
                                                   @PathVariable
                                                           long postcommentid, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());

        Postcomment currentPostcomment = new Postcomment();
        currentPostcomment = postcommentService.findPostcommentById(postcommentid);


        if (currentPostcomment.getUser().getUserid() == currentUser.getUserid()) {
            postcommentService.delete(postcommentid);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/comment/{userpostid}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewPostcomment(@PathVariable long userpostid, HttpServletRequest request,
                                               @Valid
                                               @RequestBody
                                                       Postcomment newpostcomment, Authentication authentication) throws URISyntaxException {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User user = new User();
        user = userService.findByName(authentication.getName());

        Userpost userpost = new Userpost();
        userpost = userpostService.findUserpostById(userpostid);

        newpostcomment = postcommentService.save(newpostcomment, user, userpost);


        Userpost up = newpostcomment.getUserpost();


        boolean checkMatch = userpostService.checkMatch(user, up);

        if (checkMatch == true) {
            up.setVoted(true);
        }


        return new ResponseEntity<>(newpostcomment,
                HttpStatus.CREATED);
    }


}
