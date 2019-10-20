package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.logging.Loggable;
import com.lambdaschool.starthere.models.Postcomment;
import com.lambdaschool.starthere.models.Userpost;
import com.lambdaschool.starthere.repository.PostcommentRepository;
import com.lambdaschool.starthere.repository.UserpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "postcommentService")
public class PostcommentServiceImplementation implements PostcommentService{

    @Autowired
    private PostcommentRepository postcommentrepos;

    @Override
    public List<Postcomment> findAll() {
        List<Postcomment> list = new ArrayList<>();
        postcommentrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Postcomment findPostcommentById(long id) {
        return postcommentrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postcomment with id " + id + " Not Found!"));
    }

    @Override
    public List<Postcomment> findByUserName(String username, boolean isAdmin) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName().toLowerCase()) || isAdmin)
        {
            return postcommentrepos.findAllByUser_Username(username.toLowerCase());
        } else
        {
            throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
        }
    }

    @Override
    public void delete(long id, boolean isAdmin) {
        if (postcommentrepos.findById(id)
                .isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (postcommentrepos.findById(id)
                    .get()
                    .getUser()
                    .getUsername()
                    .equalsIgnoreCase(authentication.getName()) || isAdmin)
            {
                postcommentrepos.deleteById(id);
            } else
            {
                throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
            }
        } else
        {
            throw new ResourceNotFoundException("Postcomment with id " + id + " Not Found!");
        }
    }

    @Override
    public Postcomment update(Postcomment newPostcomment, long postcommentid) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (postcommentrepos.findById(postcommentid)
                .isPresent())
        {

            Postcomment postcommentToEdit = findPostcommentById(postcommentid);

            if (newPostcomment.getTitle() != null) {
                postcommentToEdit.setTitle(newPostcomment.getTitle());
            }
            if (newPostcomment.getImageurl() != null) {
                postcommentToEdit.setImageurl(newPostcomment.getImageurl());
            }
            if (newPostcomment.getLine1() != null) {
                postcommentToEdit.setLine1(newPostcomment.getLine1());
            }

                return postcommentrepos.save(postcommentToEdit);
            } else
            {
                throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
            }

    }
}
