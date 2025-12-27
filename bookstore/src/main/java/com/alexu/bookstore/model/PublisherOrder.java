package com.alexu.bookstore.model;

import com.alexu.bookstore.enums.OrderStatus;
import java.util.Date;

public class PublisherOrder {
    private int id;
    private String bookIsbn;    // FK to Book
    private int quantity;
    private Date orderDate;
    private OrderStatus status; // Enum

    // --- CONSTRUCTORS ---
    public PublisherOrder() {}

    public PublisherOrder(int id, String bookIsbn, int quantity, Date orderDate, OrderStatus status) {
        this.id = id;
        this.bookIsbn = bookIsbn;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
    }

    // --- GETTERS & SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}