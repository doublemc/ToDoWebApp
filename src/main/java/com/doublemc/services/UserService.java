package com.doublemc.services;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import org.springframework.stereotype.Service;

/**
 * Created by michal on 29.01.17.
 */

// Facade pattern
@Service
public interface UserService {
    User saveUser(User user);

}
