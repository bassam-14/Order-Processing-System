package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.User;
import com.alexu.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/users/register
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return "Registration successful!";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // POST /api/users/login
    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}