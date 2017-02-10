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
        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEmail());
        return userRepository.save(newUser);
    }

    public boolean userWithThatUsernameAlreadyExists(User user) {
        return userRepository.findByUsername(user.getUsername()) != null;
    }

    public Iterable<ToDoItem> getAllToDoItems(Principal principal) {
        return findLoggedInUser(principal).getToDoItems();
    }

    public boolean deleteCurrentlyLoggedInUser(Principal principal) {
        if (findLoggedInUser(principal) != null) {
            userRepository.delete(findLoggedInUser(principal));
            return true;
        }
        return false;
    }

    public User findLoggedInUser(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }

}
