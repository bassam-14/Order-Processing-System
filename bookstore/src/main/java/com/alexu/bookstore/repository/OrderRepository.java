package com.alexu.bookstore.repository;

import com.alexu.bookstore.model.CustomerOrder;
import com.alexu.bookstore.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Save the main Order and return the generated ID
    public int saveOrder(CustomerOrder order) {
        String sql = "INSERT INTO Customer_Order (user_id, order_date, total_price, credit_card_number, credit_card_expiry) VALUES (?, NOW(), ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setBigDecimal(2, order.getTotalPrice());
            ps.setString(3, order.getCreditCardNumber());
            ps.setString(4, order.getCreditCardExpiry());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // 2. Save an individual item
    public void saveOrderItem(int orderId, OrderItem item) {
        String sql = "INSERT INTO Order_Item (order_id, book_isbn, quantity, unit_price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderId, item.getBookIsbn(), item.getQuantity(), item.getUnitPrice());
    }
}