package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

/**
 * Created by michal on 29.01.17.
 */
@Service
@Transactional
public class UserServiceBean {
    private final UserRepository userRepository;

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
        return user.getToDoItems();
    }

    public boolean deleteUser(Principal principal) {
        if (findLoggedInUser(principal) != null) {
            userRepository.delete(findLoggedInUser(principal));
            return true;
        }
        return false;
    }

    private User findUserbyUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findLoggedInUser(Principal principal) {
        return findUserbyUsername(principal.getName());
    }
}
