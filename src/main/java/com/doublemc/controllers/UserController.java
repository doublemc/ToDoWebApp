package com.doublemc.controllers;

import com.doublemc.domain.User;
import com.doublemc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by michal on 29.01.17.
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    // exposes new User to Thymeleaf template (register form)
    @GetMapping("/register")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    // this function captures completed form and creates new User + adds it to DB
    @PostMapping("/register")
    public String saveNewUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "success";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}