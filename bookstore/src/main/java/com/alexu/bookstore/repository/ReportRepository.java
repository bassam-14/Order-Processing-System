package com.alexu.bookstore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class ReportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Report A: Total Sales for Previous Month [cite: 58]
    public Double getTotalSalesPrevMonth() {
        String sql = "SELECT SUM(total_price) FROM Customer_Order WHERE MONTH(order_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Report B: Top 5 Customers (Last 3 Months) [cite: 61]
    public List<Map<String, Object>> getTop5Customers() {
        String sql = "SELECT u.username, SUM(co.total_price) as total_spent " +
                     "FROM Customer_Order co JOIN User u ON co.user_id = u.id " +
                     "WHERE co.order_date >= CURRENT_DATE - INTERVAL 3 MONTH " +
                     "GROUP BY u.id ORDER BY total_spent DESC LIMIT 5";
        return jdbcTemplate.queryForList(sql);
    }

    // Report C: Top 10 Selling Books (Last 3 Months) [cite: 62]
    public List<Map<String, Object>> getTop10Books() {
        String sql = "SELECT b.title, SUM(oi.quantity) as copies_sold " +
                     "FROM Order_Item oi " +
                     "JOIN Customer_Order co ON oi.order_id = co.id " +
                     "JOIN Book b ON oi.book_isbn = b.isbn " +
                     "WHERE co.order_date >= CURRENT_DATE - INTERVAL 3 MONTH " +
                     "GROUP BY b.isbn ORDER BY copies_sold DESC LIMIT 10";
        return jdbcTemplate.queryForList(sql);
    }
    // Report D: Total Sales on a Specific Day
    public Double getTotalSalesByDay(String date) {
        // format: YYYY-MM-DD
        String sql = "SELECT SUM(total_price) FROM Customer_Order WHERE DATE(order_date) = ?";
        Double total = jdbcTemplate.queryForObject(sql, Double.class, date);
        return (total != null) ? total : 0.0;
    }

    // Report E: How many times a book was restocked (Publisher Orders)
    public Integer getBookRestockCount(String isbn) {
        String sql = "SELECT COUNT(*) FROM Publisher_Order WHERE book_isbn = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, isbn);
    }
}