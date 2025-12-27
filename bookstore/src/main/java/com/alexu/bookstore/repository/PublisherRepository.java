package com.alexu.bookstore.repository;

import com.alexu.bookstore.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PublisherRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Get All Publishers
    public List<Publisher> findAll() {
        String sql = "SELECT * FROM Publisher";
        List<Publisher> publishers = jdbcTemplate.query(sql, new PublisherRowMapper());
        
        // Optional: Populate phone numbers for each publisher
        for (Publisher p : publishers) {
            p.setPhoneNumbers(getPhones(p.getId()));
        }
        return publishers;
    }

    // 2. Add New Publisher
    public int save(Publisher publisher) {
        String sql = "INSERT INTO Publisher (name, address) VALUES (?, ?)";
        return jdbcTemplate.update(sql, publisher.getName(), publisher.getAddress());
    }

    // 3. Add Phone Number (For Publisher_Phone table)
    public int addPhone(int publisherId, String phoneNumber) {
        String sql = "INSERT INTO Publisher_Phone (publisher_id, phone_number) VALUES (?, ?)";
        return jdbcTemplate.update(sql, publisherId, phoneNumber);
    }
    
    // Helper: Get phones for a specific publisher
    private List<String> getPhones(int publisherId) {
        String sql = "SELECT phone_number FROM Publisher_Phone WHERE publisher_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("phone_number"), publisherId);
    }

    // Mapper: DB Row -> Java Object
    private static class PublisherRowMapper implements RowMapper<Publisher> {
        @Override
        public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Publisher(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("address")
            );
        }
    }
}