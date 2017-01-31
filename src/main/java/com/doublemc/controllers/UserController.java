package com.doublemc.controllers;

import com.doublemc.domain.User;
import com.doublemc.services.UserServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String currentUserName(Principal principal) {

        return principal.getName();
    }

    // DELETE YOUR ACCOUNT - deletes logged in user
    // doesn't work for now
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
    ){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDb = userService.findUserInDb(currentUser);
        userService.deleteUser(userFromDb);

        return ResponseEntity.ok(userFromDb);
    }

    @GetMapping("/todos")
    public ResponseEntity<?> viewToDos() {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDb = userService.findUserbyUsername(currentUsername);
        return ResponseEntity.ok(userService.getAllToDoItems(userFromDb));

    }

}