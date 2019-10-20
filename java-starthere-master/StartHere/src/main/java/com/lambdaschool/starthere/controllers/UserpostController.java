package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.models.Useremail;
import com.lambdaschool.starthere.models.Userpost;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UseremailService;
import com.lambdaschool.starthere.services.UserpostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    // http://localhost:2019/useremails/useremails
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/allposts",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUserposts(HttpServletRequest request) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Userpost> allUserposts = userpostService.findAll();
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
        return new ResponseEntity<>(userposts,
                HttpStatus.OK);
    }

//    @GetMapping(value = "/myposts",
//            produces = {"application/json"})
//    public ResponseEntity<?> findUserpostsByLocation(HttpServletRequest request, Authentication authentication) {
//        String name;
//        name = authentication.getName();
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//        User currentUser = userService.findByName(name);
////        long zipcode = currentUser.getUserid()
//
//        List<Userpost> userposts = userpostService.findByUserName(name);
//        return new ResponseEntity<>(userposts,
//                HttpStatus.OK);
//    }

    @GetMapping(value = "/otherposts",
            produces = {"application/json"})
    public ResponseEntity<?> findAllOtherUserpostsExceptMine(HttpServletRequest request, Authentication authentication) {
        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());
        long id = currentUser.getUserid();

        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Userpost> userposts = userpostService.findByNotUserid(id);
        return new ResponseEntity<>(userposts,
                HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/post/{userpostid}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserPostById(HttpServletRequest request,
                                              @PathVariable
                                                      Long userpostid)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        Userpost up = userpostService.findUserpostById(userpostid);
        return new ResponseEntity<>(up,
                HttpStatus.OK);
    }


}
