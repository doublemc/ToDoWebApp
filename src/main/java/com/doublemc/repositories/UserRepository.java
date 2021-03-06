package com.doublemc.repositories;

import com.doublemc.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michal on 28.01.17.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
