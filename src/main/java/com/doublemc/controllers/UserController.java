package com.doublemc.controllers;

import com.doublemc.model.User;
import com.doublemc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michal on 29.01.17.
 */

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    // exposes new User to the Thymeleaf (register form)
    @GetMapping("/register")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    // this function captures completed form and creates new User with them + adds him to the DB
    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "success";
    }



}
