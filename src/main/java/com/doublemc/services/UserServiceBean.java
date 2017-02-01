package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import com.doublemc.security.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by michal on 29.01.17.
 */
@Service
@Transactional
public class UserServiceBean {


    private UserRepository userRepository;
    UserAuthService userAuthService;

    @Autowired
    public UserServiceBean(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            return false;
        }
        return true;
    }

    public Iterable<ToDoItem> getAllToDoItems(User user) {
        if (!userExists(user)) {
            throw new IllegalArgumentException("There is no such user in db");
        }
        return user.getToDoItems();

    }

    public void deleteUser(User user) {
        if (!userExists(user)) {
            throw new IllegalArgumentException("There is no such user in db");
        }

        userRepository.delete(user.getId());
    }

    public User findUserbyUsername(String username) {
        return userRepository.findByUsername(username);
    }





}
