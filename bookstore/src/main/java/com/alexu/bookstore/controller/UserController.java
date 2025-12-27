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
    // PUT /api/users/1
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        userService.updateUser(id, user);
        return "User updated successfully";
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