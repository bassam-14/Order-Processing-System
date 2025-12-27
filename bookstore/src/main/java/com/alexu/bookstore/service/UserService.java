package com.alexu.bookstore.service;

import com.alexu.bookstore.entity.User;
import com.alexu.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Logic for Sign Up 
    public String signUp(User user) {
        // Rule: Check if username is already taken
        if (userRepo.exists(user.username)) {
            return "Username already exists!";
        }
        
        // Save to database
        userRepo.save(user);
        return "Success!";
    }

    // Logic for Login [cite: 65]
    public String login(String username, String password) {
        User user = userRepo.findByUsername(username);
        
        if (user == null) {
            return "User not found!";
        }
        
        if (user.password.equals(password)) {
            return "Login Successful!";
        } else {
            return "Wrong password!";
        }
    }
}