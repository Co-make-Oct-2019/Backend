package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.PostcommentService;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UserpostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Loggable
@RestController
@RequestMapping("/comments")
public class PostcommentController {
    private static final Logger logger = LoggerFactory.getLogger(PostcommentController.class);

    @Autowired
    UserService userService;

    @Autowired
    PostcommentService postcommentService;

    @Autowired
    UserpostService userpostService;

    @ApiOperation(value = "View a comment with given id",
            response = Postcomment.class)
    @GetMapping(value = "/comment/{postcommentid}", produces = {"application/json"})
    public ResponseEntity<?> getPostComment(@ApiParam(value = "Comment Id", required = true, example = "12") @PathVariable long postcommentid, HttpServletRequest request,
                                            Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = userService.findByName(authentication.getName());

        Postcomment currentPostcomment = postcommentService.findPostcommentById(postcommentid);

        Userpost up = currentPostcomment.getUserpost();

        boolean checkMatch = userpostService.checkMatch(currentUser, up);

        if (checkMatch == true) {
            up.setVoted(true);
        }

        return new ResponseEntity<>(currentPostcomment, HttpStatus.OK);
    }


    @ApiOperation(value = "Update a comment with given id",
            response = Postcomment.class)
    @PutMapping(value = "/comment/{postcommentid}")
    public ResponseEntity<?> updatePostComment(@ApiParam(value = "Comment Id", required = true, example = "12") @PathVariable long postcommentid, HttpServletRequest request,
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

    @ApiOperation(value = "Delete a comment",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment deleted successfully", response = Postcomment.class),
            @ApiResponse(code = 500, message = "Error deleting comment", response = ErrorDetail.class),
            @ApiResponse(code = 400, message = "Error deleting comment", response = ErrorDetail.class)
    })
    @DeleteMapping("/comment/{postcommentid}")
    public ResponseEntity<?> deletePostcommentById(HttpServletRequest request,
                                                   @ApiParam(value = "Comment Id", required = true, example = "12") @PathVariable
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

    @ApiOperation(value = "Create a new comment",
            response = Postcomment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment created successfully", response = Postcomment.class),
            @ApiResponse(code = 500, message = "Error creating comment", response = ErrorDetail.class),
            @ApiResponse(code = 400, message = "Error creating comment", response = ErrorDetail.class)
    })
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
