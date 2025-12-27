package com.alexu.bookstore.repository;

import com.alexu.bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate db;

    public void save(User user) {
        String query = "INSERT INTO users (username, password, first_name, last_name, email, address) VALUES (?, ?, ?, ?, ?, ?)";
        db.update(query, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress());
    }

    // NEW: Update existing user info
    public void update(User user) {
        String query = "UPDATE users SET password = ?, first_name = ?, last_name = ?, address = ? WHERE username = ?";
        db.update(query, user.getPassword(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getUsername());
    }

    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        List<Map<String, Object>> rows = db.queryForList(query, username);
        
        if (rows.isEmpty()) return null;

        Map<String, Object> row = rows.get(0);
        User user = new User();
        user.setId(((Number) row.get("id")).longValue());
        user.setUsername((String) row.get("username"));
        user.setPassword((String) row.get("password"));
        user.setFirstName((String) row.get("first_name"));
        user.setLastName((String) row.get("last_name"));
        user.setEmail((String) row.get("email"));
        user.setAddress((String) row.get("address"));
        return user;
    }

    public boolean exists(String username) {
        Integer count = db.queryForObject("SELECT COUNT(*) FROM users WHERE username = ?", Integer.class, username);
        return count != null && count > 0;
    }
}