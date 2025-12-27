package com.alexu.bookstore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {
    @Autowired
    private JdbcTemplate db;

    public List<Map<String, Object>> search(String text) {
        String searchPattern = "%" + text + "%";
        // JOINs are necessary to link Books to Publishers and Authors for searching
        String query = "SELECT DISTINCT b.* FROM book b " +
                       "LEFT JOIN publisher p ON b.publisher_id = p.id " +
                       "LEFT JOIN book_author ba ON b.isbn = ba.book_isbn " +
                       "LEFT JOIN author a ON ba.author_id = a.id " +
                       "WHERE b.title LIKE ? OR b.isbn LIKE ? OR b.category LIKE ? " +
                       "OR p.name LIKE ? OR a.name LIKE ?";
                       
        return db.queryForList(query, searchPattern, searchPattern, searchPattern, searchPattern, searchPattern);
    }
    
    // Helper to check stock (used in checkout)
    public Integer getStock(String isbn) {
        return db.queryForObject("SELECT stock_quantity FROM book WHERE isbn = ?", Integer.class, isbn);
    }
}