package com.alexu.bookstore.service;

import com.alexu.bookstore.model.User;
import com.alexu.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public void registerUser(User user) {
        // Check if username exists
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        userRepo.save(user);
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Login success
        }
        return null; // Login failed
    }

    public void addUserPhone(String username, String phone) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            userRepo.addPhone(user.getId(), phone);
        }
    }
}