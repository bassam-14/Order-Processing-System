package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.CustomerOrder;
import com.alexu.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/orders/checkout/5
    // Body: { "creditCardNumber": "1234", "creditCardExpiry": "12/25" }
    @PostMapping("/checkout/{userId}")
    public String checkout(@PathVariable int userId, @RequestBody CustomerOrder paymentDetails) {
        try {
            orderService.checkout(
                userId, 
                paymentDetails.getCreditCardNumber(), 
                paymentDetails.getCreditCardExpiry()
            );
            return "Order placed successfully!";
        } catch (RuntimeException e) {
            return "Checkout Failed: " + e.getMessage();
        }
    }
}