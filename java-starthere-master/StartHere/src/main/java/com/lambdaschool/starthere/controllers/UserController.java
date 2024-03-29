package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.RoleService;
import com.lambdaschool.starthere.services.UserService;
import com.lambdaschool.starthere.services.UserpostService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Loggable
@RestController
//@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserService userService;

    @Autowired
    private UserpostService userpostService;

    @Autowired
    private RoleService roleService;

    @Autowired
    AuthenticationManager authManager;

    // http://localhost:2019/users/users/?page=1&size=1
    // http://localhost:2019/users/users/?sort=username,desc&sort=<field>,asc
    @ApiOperation(value = "returns all Users",
            response = User.class,
            responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve (0..N)"), @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records per page."), @ApiImplicitParam(name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). " + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users/users",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request,
                                          @PageableDefault(page = 0,
                                                  size = 10_000)
                                                  Pageable pageable, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");


        List<User> myUsers = userService.findAll(pageable);

        User currentUser = userService.findByName(authentication.getName());
        for (User u : myUsers) {
            List<Userpost> userposts = u.getUserposts();
            for (Userpost up : userposts) {
                boolean checkMatch = userpostService.checkMatch(currentUser, up);
                if (checkMatch == true) {
                    up.setVoted(true);
                }
            }

            Comparator<Userpost> compareByCount = (Userpost o1, Userpost o2) -> o2.getCount().compareTo( o1.getCount() );
            Comparator<Userpost> compareByModifiedDate = (Userpost o1, Userpost o2) -> o2.getCreatedDate().compareTo( o1.getCreatedDate() );
            Collections.sort(userposts, compareByCount.thenComparing(compareByModifiedDate));

        }


//


        return new ResponseEntity<>(myUsers,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/users/all
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @GetMapping(value = "/users/all",
//            produces = {"application/json"})
//    public ResponseEntity<?> reallyListAllUsers(HttpServletRequest request) {
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        List<User> myUsers = userService.findAll(Pageable.unpaged());
//        return new ResponseEntity<>(myUsers,
//                HttpStatus.OK);
//    }


    // http://localhost:2019/users/user/7
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Returns a user of given userid",
            response = User.class)
    @GetMapping(value = "/users/user/{userId}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserById(HttpServletRequest request,@ApiParam(value = "User Id", required = true, example = "12")
                                         @PathVariable
                                                 Long userId, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findUserById(userId);

        User currentUser = userService.findByName(authentication.getName());

        List<Userpost> userposts = u.getUserposts();
        for (Userpost up : userposts) {
            boolean checkMatch = userpostService.checkMatch(currentUser, up);
            if (checkMatch == true) {
                up.setVoted(true);
            }
        }

        Comparator<Userpost> compareByCount = (Userpost o1, Userpost o2) -> o2.getCount().compareTo( o1.getCount() );
        Comparator<Userpost> compareByModifiedDate = (Userpost o1, Userpost o2) -> o2.getCreatedDate().compareTo( o1.getCreatedDate() );
        Collections.sort(userposts, compareByCount.thenComparing(compareByModifiedDate));


        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/cinnamon
    @ApiOperation(value = "Returns a user of given name",
            response = User.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users/user/name/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserByName(HttpServletRequest request,@ApiParam(value = "Exact Username", required = true, example = "john22")
                                           @PathVariable
                                                   String userName, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findByName(userName);

        User currentUser = userService.findByName(authentication.getName());

        List<Userpost> userposts = u.getUserposts();
        for (Userpost up : userposts) {
            boolean checkMatch = userpostService.checkMatch(currentUser, up);
            if (checkMatch == true) {
                up.setVoted(true);
            }
        }

        Comparator<Userpost> compareByCount = (Userpost o1, Userpost o2) -> o2.getCount().compareTo( o1.getCount() );
        Comparator<Userpost> compareByModifiedDate = (Userpost o1, Userpost o2) -> o2.getCreatedDate().compareTo( o1.getCreatedDate() );
        Collections.sort(userposts, compareByCount.thenComparing(compareByModifiedDate));

        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/like/da?sort=username
    @ApiOperation(value = "returns all Users with names containing a given string",
            response = User.class,
            responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve (0..N)"), @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records per page."), @ApiImplicitParam(name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). " + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users/user/name/like/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(HttpServletRequest request,@ApiParam(value = "Username", required = true, example = "john")
                                             @PathVariable
                                                     String userName,
                                             @PageableDefault(page = 0,
                                                     size = 10_000)
                                                     Pageable pageable, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<User> u = userService.findByNameContaining(userName,
                pageable);


        User currentUser = userService.findByName(authentication.getName());
        for (User u1 : u) {
            List<Userpost> userposts = u1.getUserposts();
            for (Userpost up : userposts) {
                boolean checkMatch = userpostService.checkMatch(currentUser, up);
                if (checkMatch == true) {
                    up.setVoted(true);
                }
            }

            Comparator<Userpost> compareByCount = (Userpost o1, Userpost o2) -> o2.getCount().compareTo( o1.getCount() );
            Comparator<Userpost> compareByModifiedDate = (Userpost o1, Userpost o2) -> o2.getCreatedDate().compareTo( o1.getCreatedDate() );
            Collections.sort(userposts, compareByCount.thenComparing(compareByModifiedDate));
        }

        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // http://localhost:2019/users/getusername
//    @GetMapping(value = "/getusername",
//            produces = {"application/json"})
//    @ResponseBody
//    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request,
//                                                Authentication authentication) {
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        return new ResponseEntity<>(authentication.getPrincipal(),
//                HttpStatus.OK);
//    }

    // http://localhost:2019/users/getuserinfo
    @ApiOperation(value = "returns info for current",
            response = User.class)
    @GetMapping(value = "/users/getuserinfo",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(HttpServletRequest request,
                                                Authentication authentication) {

        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User authenticatedUser = userService.findByName(userDetails.getUsername());

//        System.out.println(userDetails.getUsername());


        User u = userService.findByName(authentication.getName());

        List<Userpost> userposts = u.getUserposts();
        for (Userpost up : userposts) {
            boolean checkMatch = userpostService.checkMatch(u, up);
            if (checkMatch == true) {
                up.setVoted(true);
            }
        }

        Comparator<Userpost> compareByCount = (Userpost o1, Userpost o2) -> o2.getCount().compareTo( o1.getCount() );
        Comparator<Userpost> compareByModifiedDate = (Userpost o1, Userpost o2) -> o2.getCreatedDate().compareTo( o1.getCreatedDate() );
        Collections.sort(userposts, compareByCount.thenComparing(compareByModifiedDate));


        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }




    @ApiOperation(value = "Update and return info for current user",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully", response = User.class),
            @ApiResponse(code = 500, message = "Error updating user", response = ErrorDetail.class)
    })
    @PutMapping(value = "/users/user/profile/edit")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody
                                                User updateUser, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User currentUser = new User();
        currentUser = userService.findByName(authentication.getName());
        long id = currentUser.getUserid();

        User updatedUser = userService.update(updateUser,
                id,
                request.isUserInRole("ADMIN"));


//        UsernamePasswordAuthenticationToken authReq
//                = new UsernamePasswordAuthenticationToken(updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getAuthority());
//        System.out.println(updatedUser.getPassword());
//        authReq.setDetails(updatedUser);
//        Authentication auth = authManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        UsernamePasswordAuthenticationToken authReq =

//
//        Authentication auth = new UsernamePasswordAuthenticationToken(updatedUser.getUsername(), updatedUser.getPassword());
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

        List<Userpost> userposts = currentUser.getUserposts();

        for (Userpost up : userposts) {
            boolean checkMatch = userpostService.checkMatch(currentUser, up);
            if (checkMatch == true) {
                up.setVoted(true);
            }
        }

        Comparator<Userpost> compareByCount = (Userpost o1, Userpost o2) -> o2.getCount().compareTo( o1.getCount() );
        Comparator<Userpost> compareByModifiedDate = (Userpost o1, Userpost o2) -> o2.getCreatedDate().compareTo( o1.getCreatedDate() );
        Collections.sort(userposts, compareByCount.thenComparing(compareByModifiedDate));


        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @ApiOperation(value = "Delete current user",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting user", response = ErrorDetail.class)
    })
    @DeleteMapping("/users/user")
    public ResponseEntity<?> deleteCurrentUser(HttpServletRequest request, Authentication authentication) {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User user = userService.findByName(authentication.getName());
        userService.delete(user.getUserid());

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer",
                    "")
                    .trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/users/user/15/role/2
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//
//    @DeleteMapping("/users/user/{userid}/role/{roleid}")
//    public ResponseEntity<?> deleteUserRoleByIds(HttpServletRequest request,
//                                                 @PathVariable
//                                                         long userid,
//                                                 @PathVariable
//                                                         long roleid) {
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        userService.deleteUserRole(userid,
//                roleid);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    // http://localhost:2019/users/user/15/role/2
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PostMapping("/user/{userid}/role/{roleid}")
//    public ResponseEntity<?> postUserRoleByIds(HttpServletRequest request,
//                                               @PathVariable
//                                                       long userid,
//                                               @PathVariable
//                                                       long roleid) {
//        logger.trace(request.getMethod()
//                .toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        userService.addUserRole(userid,
//                roleid);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

//
    @ApiOperation(value = "Create a new user",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User Created successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating user", response = ErrorDetail.class)
    })
    @PostMapping(value = "/newuser/createnewuser",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request,
//                                        @RequestParam(defaultValue = "true")
//                                                boolean getaccess,
                                        @Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        Role role = roleService.findByName("admin");
        ArrayList<UserRoles> newRoles = new ArrayList<>();
        newRoles.add(new UserRoles(newuser,
                role));
        newuser.setUserroles(newRoles);

        newuser = userService.save(newuser);

//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newUserURI = ServletUriComponentsBuilder.fromUriString(request.getServerName() + ":" + request.getLocalPort() + "/users/user/{userId}")
//                .buildAndExpand(newuser.getUserid())
//                .toUri();
//        responseHeaders.setLocation(newUserURI);

//        String theToken = "";
//        if (getaccess) {
//
//            RestTemplate restTemplate = new RestTemplate();
//            String requestURI = "http://" + request.getServerName() + ":" + request.getLocalPort() + "/login";
//
//            List<MediaType> acceptableMediaTypes = new ArrayList<>();
//            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.setAccept(acceptableMediaTypes);
//            headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
//                    System.getenv("OAUTHCLIENTSECRET"));
////            headers.setBearerAuth(theToken);
//            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            map.add("grant_type",
//                    "password");
//            map.add("scope",
//                    "read write trust");
//            map.add("username",
//                    newuser.getUsername());
//            map.add("password",
//                    newuser.getPassword());
//            HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<>(map,
//                    headers);
//            System.out.println(4);
//            System.out.println(requestURI);
//            System.out.println(request2);
//            theToken = restTemplate.postForObject(requestURI,
//                    request2,
//                    String.class);
//            System.out.println(5);
//        } else {
//            // nothing;
//            System.out.println(6);
//        }
//        // set the location header for the newly created resource
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{userid}")
//                .buildAndExpand(newuser.getUserid())
//                .toUri();
//        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}