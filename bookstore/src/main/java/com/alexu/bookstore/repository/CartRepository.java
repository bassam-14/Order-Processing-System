package com.alexu.bookstore.repository;

import com.alexu.bookstore.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Get Cart Items for a User (JOINs with Book table)
    public List<ShoppingCart> findByUserId(int userId) {
        String sql = "SELECT sc.*, b.title, b.price " +
                     "FROM Shopping_Cart sc " +
                     "JOIN Book b ON sc.book_isbn = b.isbn " +
                     "WHERE sc.user_id = ?";
        return jdbcTemplate.query(sql, new CartRowMapper(), userId);
    }

    // 2. Add Item to Cart
    public int save(ShoppingCart cart) {
        // Simple logic: If exists, we should usually update quantity, but for now simple insert:
        // You might want to run a "check if exists" query first in a real app.
        String sql = "INSERT INTO Shopping_Cart (user_id, book_isbn, quantity, added_at) VALUES (?, ?, ?, NOW())";
        return jdbcTemplate.update(sql,
            cart.getUserId(),
            cart.getBookIsbn(),
            cart.getQuantity()
        );
    }

    // 3. Remove Item
    public int delete(int userId, String isbn) {
        String sql = "DELETE FROM Shopping_Cart WHERE user_id = ? AND book_isbn = ?";
        return jdbcTemplate.update(sql, userId, isbn);
    }

    // 4. Clear Cart (After checkout)
    public int deleteAll(int userId) {
        String sql = "DELETE FROM Shopping_Cart WHERE user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }
    public int updateQuantity(int userId, String isbn, int newQuantity) {
    String sql = "UPDATE Shopping_Cart SET quantity = ? WHERE user_id = ? AND book_isbn = ?";
    return jdbcTemplate.update(sql, newQuantity, userId, isbn);
}

    // Mapper
    private static class CartRowMapper implements RowMapper<ShoppingCart> {
        @Override
        public ShoppingCart mapRow(ResultSet rs, int rowNum) throws SQLException {
            ShoppingCart cart = new ShoppingCart();
            cart.setUserId(rs.getInt("user_id"));
            cart.setBookIsbn(rs.getString("book_isbn"));
            cart.setQuantity(rs.getInt("quantity"));
            cart.setAddedAt(rs.getTimestamp("added_at"));
            
            // Map the joined columns
            cart.setBookTitle(rs.getString("title"));
            cart.setPrice(rs.getBigDecimal("price"));
            return cart;
        }
    }
}