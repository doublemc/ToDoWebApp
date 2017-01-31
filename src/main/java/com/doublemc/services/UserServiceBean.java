package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by michal on 29.01.17.
 */
@Service
public class UserServiceBean {


    private UserRepository userRepository;

    @Autowired
    public UserServiceBean(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            return false;
        }
        return true;
    }

    public Iterable<ToDoItem> findAllToDoItems(User user) {
        if (userRepository.findOne(user.getId()) == null) {
            throw new IllegalArgumentException("There is no logged in user");
        }
        return user.getToDoItems();

    }






}
