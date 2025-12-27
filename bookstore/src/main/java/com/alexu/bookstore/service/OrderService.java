package com.alexu.bookstore.service;

import com.alexu.bookstore.repository.CartRepository;
import com.alexu.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private JdbcTemplate db; // Used for stock updates
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private OrderRepository orderRepo; // NEW: Injected Repository

    @Transactional
    public String checkout(Long userId, String creditCard, String expiry) {
        // 1. Get cart items
        List<Map<String, Object>> cartItems = cartRepo.getCartDetails(userId);
        if (cartItems.isEmpty()) return "Cart is empty!";

        // 2. Calculate Total
        double totalAmount = 0;
        for (Map<String, Object> item : cartItems) {
            totalAmount += ((Number) item.get("total_price")).doubleValue();
        }

        // 3. Save Order using Repository (No raw SQL here)
        Long newOrderId = orderRepo.createOrder(userId, totalAmount, creditCard, expiry);

        // 4. Process Items: Save to history and Deduct Stock
        for (Map<String, Object> item : cartItems) {
            String isbn = (String) item.get("isbn");
            int quantity = ((Number) item.get("quantity")).intValue();
            double price = ((Number) item.get("price")).doubleValue();

            // A. Save Item using Repository
            orderRepo.createOrderItem(newOrderId, isbn, quantity, price);

            // B. Deduct Stock (Direct SQL is fine here for specific logic, or move to BookRepo)
            int updated = db.update("UPDATE book SET stock_quantity = stock_quantity - ? WHERE isbn = ? AND stock_quantity >= ?", 
                                    quantity, isbn, quantity);
            
            if (updated == 0) {
                throw new RuntimeException("Out of stock for book: " + isbn); // Rollback entire transaction
            }
        }

        // 5. Clear Cart
        cartRepo.clearCart(userId);
        
        return "Order Placed Successfully! Order ID: " + newOrderId;
    }

    // New: View Past Orders
    public List<Map<String, Object>> getUserOrders(Long userId) {
        return orderRepo.findOrdersByUserId(userId);
    }
    public List<Map<String, Object>> getOrderDetails(Long orderId) {
    return orderRepo.findOrderItems(orderId);
}
}