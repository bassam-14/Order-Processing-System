package com.alexu.bookstore.model;

import java.util.Date;
import java.math.BigDecimal;

public class ShoppingCart {
    private int userId;
    private String bookIsbn;
    private int quantity;
    private Date addedAt;

    // --- HELPER FIELDS (Not in Shopping_Cart table, but fetched via JOIN) ---
    private String bookTitle;
    private BigDecimal price;

    public ShoppingCart() {}

    public ShoppingCart(int userId, String bookIsbn, int quantity, Date addedAt) {
        this.userId = userId;
        this.bookIsbn = bookIsbn;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    // --- GETTERS & SETTERS ---
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getAddedAt() { return addedAt; }
    public void setAddedAt(Date addedAt) { this.addedAt = addedAt; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}