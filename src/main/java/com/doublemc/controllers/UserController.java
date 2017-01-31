package com.doublemc.controllers;

import com.doublemc.domain.User;
import com.doublemc.services.UserServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<Void> createUser(
            @RequestBody User user
    ) {
        if (userService.userExists(user)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    // TODO: 31.01.17 Create it when Spring Security is ready
//    // DELETE A USER
//    @DeleteMapping("/delete")
//    public ResponseEntity<User> deleteUser(
//    ){
//    }

}