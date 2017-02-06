package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import com.doublemc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceBean(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
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

    public User findLoggedInUser(Principal principal) {
        return findUserbyUsername(principal.getName());
    }

    private User findUserbyUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
