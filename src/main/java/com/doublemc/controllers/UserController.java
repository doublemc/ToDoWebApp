package com.doublemc.controllers;

import com.doublemc.customexceptions.UsernameAlreadyExistsException;
import com.doublemc.domain.User;
import com.doublemc.services.UserServiceBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by michal on 29.01.17.
 */

@RestController
public class UserController {
    private final UserServiceBean userService;
    private final ObjectMapper mapper;

    @Autowired
    UserController(UserServiceBean userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.mapper = objectMapper;
    }

    // CREATE A USER
    @PostMapping("/users")
    public ResponseEntity<ObjectNode> createUser(@RequestBody User user) {
        ObjectNode jsonObject = mapper.createObjectNode();
        if (userService.userExists(user)) throw new UsernameAlreadyExistsException();
        userService.saveUser(user);
        jsonObject.put("status", "User created.");
        return new ResponseEntity<>(jsonObject, HttpStatus.CREATED);
    }

    // DELETE YOUR ACCOUNT - deletes logged in user
    @DeleteMapping("/users")
    public ResponseEntity deleteUser(Principal principal) {
        if (userService.deleteUser(principal)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}