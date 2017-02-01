package com.doublemc.controllers;

import com.doublemc.domain.User;
import com.doublemc.services.UserServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by michal on 29.01.17.
 */

@RestController
public class UserController {

    private UserServiceBean userService;

    @Autowired
    public UserController(UserServiceBean userService) {
        this.userService = userService;
    }

    // CREATE A USER
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @RequestBody User user
    ) {
        if (userService.userExists(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with that username already exists.");
        }

        // TODO: 02.02.17 http://stackoverflow.com/questions/671118/what-exactly-is-restful-programming
        return ResponseEntity.ok(userService.saveUser(user));
    }



    // DELETE YOUR ACCOUNT - deletes logged in user
    // doesn't work for now
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Principal principal){
        String currentUsername = principal.getName();
        User userFromDb = userService.findUserbyUsername(currentUsername);
        userService.deleteUser(userFromDb);

        return ResponseEntity.ok(userFromDb.toString() + " deleted.");
    }



}