package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceFoundException;
import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.repository.RoleRepository;
import com.lambdaschool.starthere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService,
        UserService {

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userrepos.findByUsername(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername().toLowerCase(),
                user.getPassword(),
                user.getAuthority());
    }

    public User findUserById(long id) throws ResourceNotFoundException {
        return userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username,
                                           Pageable pageable) {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase(),
                pageable);
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        List<User> list = new ArrayList<>();
        userrepos.findAll(pageable)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id) {
        userrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name) {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null) {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user) {
        if (userrepos.findByUsername(user.getUsername().toLowerCase()) != null) {
            throw new ResourceFoundException(user.getUsername() + " is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setLocation(user.getLocation());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserroles()) {
            long id = ur.getRole()
                    .getRoleid();
            Role role = rolerepos.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
            newRoles.add(new UserRoles(newUser,
                    ur.getRole()));
        }
        newUser.setUserroles(newRoles);

        for (Userpost up : user.getUserposts()) {

            newUser.getUserposts()
                    .add(new Userpost(newUser,
                            up.getTitle(), up.getLocation(), up.getLine1(), up.getImageurl()));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user,
                       long id,
                       boolean isAdmin) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        User authenticatedUser = userrepos.findByUsername(authentication.getName());

        if (id == authenticatedUser.getUserid() || isAdmin) {
            User currentUser = findUserById(id);

//            if (user.getUsername() != null) {
//                currentUser.setUsername(user.getUsername().toLowerCase());
//            }
            if (user.getLocation() != null) {
                currentUser.setLocation(user.getLocation());
            }
            if(user.getLine1() != null){
                currentUser.setLine1(user.getLine1());
            }
            if(user.getImageurl() != null)
            {
                currentUser.setImageurl(user.getImageurl());
            }

            if (user.getPassword() != null) {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            return userrepos.save(currentUser);
        } else {
            throw new ResourceNotFoundException(id + " Not current user");
        }
    }

    @Transactional
    @Override
    public void deleteUserRole(long userid,
                               long roleid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid,
                roleid)
                .getCount() > 0) {
            rolerepos.deleteUserRoles(userid,
                    roleid);
        } else {
            throw new ResourceNotFoundException("Role and User Combination Does Not Exists");
        }
    }

    @Transactional
    @Override
    public void addUserRole(long userid,
                            long roleid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid,
                roleid)
                .getCount() <= 0) {
            rolerepos.insertUserRoles(userid,
                    roleid);
        } else {
            throw new ResourceFoundException("Role and User Combination Already Exists");
        }
    }
}
