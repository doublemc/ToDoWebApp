package com.doublemc.repositories;

import com.doublemc.domain.ToDoItem;
import com.doublemc.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by michal on 28.01.17.
 */

public interface UserRepository extends CrudRepository<User, Long> {

    // TODO: 29.01.17 Create a query to find all todos for logged in user

    @Query("select td from User u inner join u.toDoItems td where u = :user")
    public Iterable<ToDoItem> findAllToDosForLoggedInUser(@Param("user") User user);

    public User findByUsername(String username);

}
