package com.alexu.bookstore.repository;

import com.alexu.bookstore.model.PublisherOrder;
import com.alexu.bookstore.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PublisherOrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Get All Orders (History)
    public List<PublisherOrder> findAll() {
        String sql = "SELECT * FROM Publisher_Order";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    // 2. Place New Order
    public int save(PublisherOrder order) {
        String sql = "INSERT INTO Publisher_Order (book_isbn, quantity, order_date, status) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                order.getBookIsbn(),
                order.getQuantity(),
                new java.sql.Timestamp(new java.util.Date().getTime()), // Current Time
                OrderStatus.ORDERED.name() // Default status
        );
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE Publisher_Order SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    // Mapper
    private static class OrderRowMapper implements RowMapper<PublisherOrder> {
        @Override
        public PublisherOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PublisherOrder(
                    rs.getInt("id"),
                    rs.getString("book_isbn"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("order_date"),
                    OrderStatus.valueOf(rs.getString("status")));
        }
    }
}