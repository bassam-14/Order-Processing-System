package com.alexu.bookstore.model;

import com.alexu.bookstore.enums.Category;
import java.math.BigDecimal; 


public class Book {
    private String isbn;            // PK
    private String title;
    private int publisherId;        // FK to Publisher table
    private int publicationYear;
    private BigDecimal price;
    private Category category;      // Enum
    private int threshold;
    private int stockQuantity;
    public Book() {
    }
    public String getIsbn() {
        return isbn;
    }
    public Book(String isbn, String title, int publisherId, int publicationYear, BigDecimal price, Category category,
            int threshold, int stockQuantity) {
        this.isbn = isbn;
        this.title = title;
        this.publisherId = publisherId;
        this.publicationYear = publicationYear;
        this.price = price;
        this.category = category;
        this.threshold = threshold;
        this.stockQuantity = stockQuantity;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getThreshold() {
        return threshold;
    }
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

   
}