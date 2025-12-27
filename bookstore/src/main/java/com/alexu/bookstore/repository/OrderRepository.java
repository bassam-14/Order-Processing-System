package com.alexu.bookstore.repository;

import com.alexu.bookstore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate db;

    // Save order and return the new ID
    public Long createOrder(Long userId, double total, String card, String expiry) {
        String sql = "INSERT INTO customer_order (user_id, total_amount, credit_card_number, credit_card_expiry) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        db.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setDouble(2, total);
            ps.setString(3, card);
            ps.setString(4, expiry);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    // Save individual order items
    public void createOrderItem(Long orderId, String isbn, int qty, double price) {
        String sql = "INSERT INTO order_item (order_id, book_isbn, quantity, unit_price) VALUES (?, ?, ?, ?)";
        db.update(sql, orderId, isbn, qty, price);
    }

    // Get past orders for a user [cite: 86]
    public List<Map<String, Object>> findOrdersByUserId(Long userId) {
        String sql = "SELECT * FROM customer_order WHERE user_id = ? ORDER BY order_date DESC";
        return db.queryForList(sql, userId);
    }
    public List<Map<String, Object>> findOrderItems(Long orderId) {
        String sql = "SELECT b.title, b.isbn, i.quantity, i.unit_price " +
                     "FROM order_item i " +
                     "JOIN book b ON i.book_isbn = b.isbn " +
                     "WHERE i.order_id = ?";
        return db.queryForList(sql, orderId);
    }
}