package com.alexu.bookstore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class CartRepository {
    @Autowired
    private JdbcTemplate db;

    public void addToCart(Long userId, String isbn, int qty) {
        // ON DUPLICATE KEY UPDATE: If book is already in cart, just increase quantity
        String query = "INSERT INTO shopping_cart (user_id, book_isbn, quantity) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        db.update(query, userId, isbn, qty, qty);
    }

    public void removeFromCart(Long userId, String isbn) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ? AND book_isbn = ?";
        db.update(query, userId, isbn);
    }

    public void clearCart(Long userId) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ?";
        db.update(query, userId);
    }

    // NEW: Get detailed cart items (Title, Price, Total)
    public List<Map<String, Object>> getCartDetails(Long userId) {
        String query = "SELECT b.title, b.price, c.quantity, (b.price * c.quantity) as total_price, b.isbn " +
                       "FROM shopping_cart c " +
                       "JOIN book b ON c.book_isbn = b.isbn " +
                       "WHERE c.user_id = ?";
        return db.queryForList(query, userId);
    }
}