package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.repository.UserpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "userpostService")
public class UserpostServiceImplementation implements UserpostService {

    @Autowired
    private UserpostRepository userpostrepos;

    @Autowired
    private UserService userService;

    @Override
    public Userpost save(Userpost userpost, User user) {

        Userpost newUserpost = new Userpost();
        newUserpost.setImageurl(userpost.getImageurl());
        newUserpost.setDescription(userpost.getDescription());
        newUserpost.setLocation(userpost.getLocation());
        newUserpost.setTitle(userpost.getTitle());
        newUserpost.setUser(user);
        newUserpost.setCount(0);

        return userpostrepos.save(newUserpost);
    }

    @Override
    public List<Userpost> findAll() {
        List<Userpost> list = new ArrayList<>();
        userpostrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Userpost findUserpostById(long userpostid) {
        return userpostrepos.findById(userpostid)
                .orElseThrow(() -> new ResourceNotFoundException("Userpost with id " + userpostid + " Not Found!"));
    }

    @Override
    public List<Userpost> findByCurrentLocation(String location) {
        List<Userpost> list = new ArrayList<>();
        userpostrepos.findAllByLocationContainingIgnoreCase(location)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Userpost> findByUserName(String username) {
        return userpostrepos.findAllByUser_Username(username.toLowerCase());
    }

    @Override
    public List<Userpost> findByNotUserid(long id) {
        System.out.println("HERE");
        return userpostrepos.findByNotUserid(id);
    }

    @Override
    public void delete(long id) {
        if (userpostrepos.findById(id)
                .isPresent()) {
            userpostrepos.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Userpost with id " + id + " Not Found!");
        }
    }

    @Override
    public Userpost update(Userpost userpost, long userpostid, boolean isAdmin) {

        if (userpostrepos.findById(userpostid)
                .isPresent()) {

            Userpost userpostToEdit = findUserpostById(userpostid);

            if (userpost.getTitle() != null) {
                userpostToEdit.setTitle(userpost.getTitle());
            }
            if (userpost.getLocation() != null) {
                userpostToEdit.setLocation(userpost.getLocation());
            }
            if (userpost.getDescription() != null) {
                userpostToEdit.setDescription(userpost.getDescription());
            }
            if (userpost.getImageurl() != null) {
                userpostToEdit.setImageurl(userpost.getImageurl());
            }
            if (userpost.getCount() != null) {
                userpostToEdit.setCount(userpost.getCount());
            }


            return userpostrepos.save(userpostToEdit);
        } else {
            throw new ResourceNotFoundException("Userpost with id " + userpostid + " Not Found!");
        }
    }

    @Override
    public boolean checkMatch(User user, Userpost userpost) {


        boolean matching = userpostrepos.checkMatch(user.getUserid(), userpost.getUserpostid());
        return matching;
    }

    @Override
    public Userpost increment(User user, Userpost userpost) {
        boolean matching = checkMatch(user, userpost);
        System.out.println("Matching from increment: " + matching);
        if (matching != true) {
            userpostrepos.addVote(user.getUserid(), userpost.getUserpostid());
            System.out.println("Vote added");
            User updateUser = new User();
            updateUser.setReputation(userpost.getUser().getReputation() + 1);
            userService.update(updateUser, userpost.getUser().getUserid(), true);
        } else {
            System.out.println("You already upvoted this post");
        }
        int myCount = getCount(userpost);
        userpost.setCount(myCount);
        update(userpost, userpost.getUserpostid(), true);
        userpost.setVoted(true);
        return userpost;
    }

    @Override
    public Userpost decrement(User user, Userpost userpost) {
        boolean matching = checkMatch(user, userpost);
        System.out.println("Matching from increment: " + matching);
        if (matching == true) {
            userpostrepos.removeVote(user.getUserid(), userpost.getUserpostid());
            System.out.println("Vote removed");
            User updateUser = new User();
            updateUser.setReputation(userpost.getUser().getReputation() - 1);
            userService.update(updateUser, userpost.getUser().getUserid(), true);
        } else {
            System.out.println("You haven't upvoted this yet");
        }
        int myCount = getCount(userpost);
        userpost.setCount(myCount);
        update(userpost, userpost.getUserpostid(), true);
        userpost.setVoted(false);
        return userpost;
    }

    @Override
    public int getCount(Userpost userpost) {
        int myCount = userpostrepos.getCount(userpost.getUserpostid());
        System.out.println("COUNT: " + myCount);
        return myCount;
    }

//    @Override
//    public int getReputation(User user) {
//        int reputationCount = userpostrepos.getReputation(user.getUserid());
//        return reputationCount;
//    }

    @Override
    public List<Userpost> findByNameContaining(String titlestring) {
        return userpostrepos.findByTitleContainingIgnoreCase(titlestring.toLowerCase());
    }
}
