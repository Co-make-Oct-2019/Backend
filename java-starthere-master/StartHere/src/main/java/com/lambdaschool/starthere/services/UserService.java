package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService
{
    @Transactional
    UserDetails loadUserByUsername(String username);

    @Transactional
    List<User> findAll(Pageable pageable);

    @Transactional
    List<User> findByNameContaining(String username,
                                    Pageable pageable);

    @Transactional
    User findUserById(long id);

    @Transactional
    User findByName(String name);

    @Transactional
    void delete(long id);

    @Transactional
    User save(User user);

    @Transactional
    User update(User user,
                long id,
                boolean isAdmin);

    @Transactional
    void deleteUserRole(long userid,
                        long roleid);

    @Transactional
    void addUserRole(long userid,
                     long roleid);
}