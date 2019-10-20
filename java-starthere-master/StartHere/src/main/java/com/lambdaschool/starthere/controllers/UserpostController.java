package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Userpost;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UserpostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/posts")
public class UserpostController {

    private static final Logger logger = LoggerFactory.getLogger(UseremailController.class);

    @Autowired
    UserpostService userpostService;

    @Autowired
    UserService userService;

    // http://localhost:2019/posts/allposts
    @GetMapping(value = "/allposts",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUserposts(HttpServletRequest request, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");



        List<Userpost> allUserposts = userpostService.findAll();

        User user = userService.findByName(authentication.getName());
        for(Userpost up: allUserposts)
        {
            boolean checkMatch = userpostService.checkMatch(user, up);
            if(checkMatch == true)
            {
                up.setVoted(true);
            }
        }

        return new ResponseEntity<>(allUserposts,
                HttpStatus.OK);
    }

    @GetMapping(value = "/myposts",
            produces = {"application/json"})
    public ResponseEntity<?> findUserpostsByUserName(HttpServletRequest request, Authentication authentication) {
        String name;
        name = authentication.getName();
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Userpost> userposts = userpostService.findByUserName(name);

        User user = userService.findByName(authentication.getName());
        for(Userpost up: userposts)
        {
            boolean checkMatch = userpostService.checkMatch(user, up);
            if(checkMatch == true)
            {
                up.setVoted(true);
            }
        }

        return new ResponseEntity<>(userposts,
                HttpStatus.OK);
    }

    @GetMapping(value = "/currentlocation",
            produces = {"application/json"})
    public ResponseEntity<?> findUserpostsByLocation(HttpServletRequest request, Authentication authentication) {
        String name;
        name = authentication.getName();
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");
        User currentUser = userService.findByName(name);
        String location = currentUser.getLocation();

        List<Userpost> userposts = userpostService.findByCurrentLocation(location);

        for(Userpost up: userposts)
        {
            boolean checkMatch = userpostService.checkMatch(currentUser, up);
            if(checkMatch == true)
            {
                up.setVoted(true);
            }
        }

        return new ResponseEntity<>(userposts,
                HttpStatus.OK);
    }

    @GetMapping(value = "/otherposts",
            produces = {"application/json"})
    public ResponseEntity<?> findAllOtherUserpostsExceptMine(HttpServletRequest request, Authentication authentication) {
        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());
        long id = currentUser.getUserid();

        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Userpost> userposts = userpostService.findByNotUserid(id);


        for(Userpost up: userposts)
        {
            boolean checkMatch = userpostService.checkMatch(currentUser, up);
            if(checkMatch == true)
            {
                up.setVoted(true);
            }
        }

        return new ResponseEntity<>(userposts,
                HttpStatus.OK);
    }

    @PutMapping(value = "/post/{userpostid}")
    public ResponseEntity<?> updatePost(@PathVariable long userpostid, HttpServletRequest request,
                                        @RequestBody
                                                Userpost updatedPost, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = new User();
        Userpost currentPost = new Userpost();
        currentPost = userpostService.findUserpostById(userpostid);
        currentUser = userService.findByName(authentication.getName());

        if (currentPost.getUser().getUserid() == currentUser.getUserid()) {
            Userpost returnPost = userpostService.update(updatedPost, userpostid, request.isUserInRole("ADMIN"));



                boolean checkMatch = userpostService.checkMatch(currentUser, returnPost);
                if(checkMatch == true)
                {
                    returnPost.setVoted(true);
                }


            return new ResponseEntity<>(returnPost, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/post/increment/{userpostid}")
    public ResponseEntity<?> incrementPostCount(@PathVariable long userpostid, HttpServletRequest request,Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");
        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());

        Userpost currentPost = new Userpost();
        currentPost = userpostService.findUserpostById(userpostid);

        currentPost = userpostService.increment(currentUser, currentPost);

            return new ResponseEntity<>(currentPost, HttpStatus.OK);
    }

    @PutMapping(value = "/post/decrement/{userpostid}")
    public ResponseEntity<?> decrementPostCount(@PathVariable long userpostid,
                                                HttpServletRequest request,
                                                Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());

        Userpost currentPost = new Userpost();
        currentPost = userpostService.findUserpostById(userpostid);

        currentPost = userpostService.decrement(currentUser, currentPost);

        return new ResponseEntity<>(currentPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/{userpostid}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserPostById(HttpServletRequest request,
                                             @PathVariable
                                                     Long userpostid) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        Userpost up = userpostService.findUserpostById(userpostid);
        return new ResponseEntity<>(up,
                HttpStatus.OK);
    }

    @DeleteMapping("/post/{userpostid}")
    public ResponseEntity<?> deleteUserpostById(HttpServletRequest request,
                                                @PathVariable
                                                        long userpostid, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());

        Userpost currentPost = new Userpost();
        currentPost = userpostService.findUserpostById(userpostid);


        if (currentPost.getUser().getUserid() == currentUser.getUserid()) {
            userpostService.delete(userpostid);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/post",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewUserpost(HttpServletRequest request,
                                        @Valid
                                        @RequestBody
                                                Userpost newuserpost, Authentication authentication) throws URISyntaxException {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User user = new User();
        user = userService.findByName(authentication.getName());

        newuserpost = userpostService.save(newuserpost, user);


        return new ResponseEntity<>(newuserpost,
                HttpStatus.CREATED);
    }
}
