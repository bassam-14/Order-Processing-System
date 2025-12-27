package com.alexu.bookstore.repository;

import com.alexu.bookstore.model.User;
import com.alexu.bookstore.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Find User by Username (For Login)
    public User findByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
        } catch (EmptyResultDataAccessException e) {
            return null; // User not found
        }
    }

    public void update(User user) {
        String sql = "UPDATE User SET first_name=?, last_name=?, email=?, address=?, password=? WHERE id=?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress(),
                user.getPassword(), user.getId());
    }

    public User findById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    // 2. Register New User
    public int save(User user) {
        String sql = "INSERT INTO User (username, password, first_name, last_name, email, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(), // In a real app, hash this!
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getRole().name());
    }

    // 3. Add Phone Number
    public int addPhone(int userId, String phoneNumber) {
        String sql = "INSERT INTO User_Phone (user_id, phone_number) VALUES (?, ?)";
        return jdbcTemplate.update(sql, userId, phoneNumber);
    }

    // Mapper
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setRole(UserRole.valueOf(rs.getString("role")));
            return user;
        }
    }
}