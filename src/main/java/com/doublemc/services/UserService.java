package com.doublemc.services;

import com.doublemc.domain.User;

/**
 * Created by michal on 29.01.17.
 */

// Facade pattern
public interface UserService {
    User saveUser(User user);
}
