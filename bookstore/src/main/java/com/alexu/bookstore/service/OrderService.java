package com.alexu.bookstore.service;

import com.alexu.bookstore.model.*;
import com.alexu.bookstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private BookRepository bookRepo;

    @Transactional // CRITICAL: If any step fails, everything rolls back
    public void checkout(int userId, String ccNumber, String ccExpiry) {

        // 1. Get items from Cart
        List<ShoppingCart> cartItems = cartRepo.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        // 2. Calculate Total Price & Validate Stock
        BigDecimal total = BigDecimal.ZERO;
        for (ShoppingCart cartItem : cartItems) {
            Book book = bookRepo.findByIsbn(cartItem.getBookIsbn());

            // Check Stock (Part 1 Logic)
            if (book.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }

            // Add to total
            BigDecimal itemTotal = book.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            total = total.add(itemTotal);
        }

        // 3. Create the Order Record
        CustomerOrder order = new CustomerOrder();
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setCreditCardNumber(ccNumber);
        order.setCreditCardExpiry(ccExpiry);

        int orderId = orderRepo.saveOrder(order); // Get the new ID

        // 4. Move items to Order_Item and Deduct Stock
        for (ShoppingCart cartItem : cartItems) {
            Book book = bookRepo.findByIsbn(cartItem.getBookIsbn());

            // Create Order Item
            OrderItem orderItem = new OrderItem(book.getIsbn(), cartItem.getQuantity(), book.getPrice());
            orderRepo.saveOrderItem(orderId, orderItem);

            // DEDUCT STOCK (Update Part 1)
            bookRepo.updateStock(book.getIsbn(), book.getStockQuantity() - cartItem.getQuantity());
        }

        // 5. Clear the Cart
        cartRepo.deleteAll(userId);
    }

    public List<CustomerOrder> getOrdersByUserId(int userId) {
        return orderRepo.findByUserId(userId);
    }
}