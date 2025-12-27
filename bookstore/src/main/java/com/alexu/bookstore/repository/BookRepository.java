package com.alexu.bookstore.repository;

import com.alexu.bookstore.model.Book;
import com.alexu.bookstore.enums.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. GET ALL BOOKS
    public List<Book> findAll() {
        String sql = "SELECT * FROM Book";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    // 2. SEARCH BY TITLE (Example Query)
    public List<Book> findByTitle(String title) {
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        return jdbcTemplate.query(sql, new BookRowMapper(), "%" + title + "%");
    }

    // 3. ADD NEW BOOK
    public int save(Book book) {
        String sql = "INSERT INTO Book (isbn, title, publisher_id, publication_year, price, category, threshold, stock_quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                book.getIsbn(),
                book.getTitle(),
                book.getPublisherId(),
                book.getPublicationYear(),
                book.getPrice(),
                book.getCategory().name(), // Convert Enum to String for DB
                book.getThreshold(),
                book.getStockQuantity());
    }

    // 4. UPDATE STOCK (Critical for Part 1)
    public int updateStock(String isbn, int newQuantity) {
        String sql = "UPDATE Book SET stock_quantity = ? WHERE isbn = ?";
        return jdbcTemplate.update(sql, newQuantity, isbn);
    }

    // 5. GET SINGLE BOOK BY ISBN (For the "Details" page)
    public Book findByIsbn(String isbn) {
        String sql = "SELECT * FROM Book WHERE isbn = ?";
        // We use queryForObject when we expect exactly one result
        return jdbcTemplate.queryForObject(sql, new BookRowMapper(), isbn);
    }

    // 6. FILTER BY CATEGORY (For the sidebar filters)
    public List<Book> findByCategory(String categoryName) {
        String sql = "SELECT * FROM Book WHERE category = ?";
        return jdbcTemplate.query(sql, new BookRowMapper(), categoryName);
    }

    // 7. EDIT BOOK DETAILS (For the Manager to fix mistakes)
    public int update(Book book) {
        String sql = "UPDATE Book SET title=?, publisher_id=?, publication_year=?, price=?, category=?, threshold=? WHERE isbn=?";
        return jdbcTemplate.update(sql,
                book.getTitle(),
                book.getPublisherId(),
                book.getPublicationYear(),
                book.getPrice(),
                book.getCategory().name(),
                book.getThreshold(),
                book.getIsbn());
    }
    public List<Book> findByAuthor(String authorName) {
    String sql = "SELECT b.* FROM Book b " +
                 "JOIN Book_Author ba ON b.isbn = ba.book_isbn " +
                 "JOIN Author a ON ba.author_id = a.id " +
                 "WHERE a.name LIKE ?";
                 
    return jdbcTemplate.query(sql, new BookRowMapper(), "%" + authorName + "%");
}
    // Assign an Author to a Book
    public int addBookAuthor(String isbn, int authorId) {
        String sql = "INSERT INTO Book_Author (book_isbn, author_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, isbn, authorId);
    }

    // Mapper: Converts DB Row -> Java Object
    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setIsbn(rs.getString("isbn"));
            book.setTitle(rs.getString("title"));
            book.setPublisherId(rs.getInt("publisher_id"));
            book.setPublicationYear(rs.getInt("publication_year"));
            book.setPrice(rs.getBigDecimal("price"));
            book.setCategory(Category.valueOf(rs.getString("category"))); // String -> Enum
            book.setThreshold(rs.getInt("threshold"));
            book.setStockQuantity(rs.getInt("stock_quantity"));
            return book;
        }
    }
}