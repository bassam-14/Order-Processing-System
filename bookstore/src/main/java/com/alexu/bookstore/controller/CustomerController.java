package com.alexu.bookstore.controller;

import com.alexu.bookstore.entity.User;
import com.alexu.bookstore.repository.CartRepository;
import com.alexu.bookstore.repository.UserRepository;
import com.alexu.bookstore.service.BookService; // NEW
import com.alexu.bookstore.service.OrderService;
import com.alexu.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerController {

    // Services
    @Autowired private UserService userService;
    @Autowired private OrderService orderService;
    @Autowired private BookService bookService; // NEW: Use Service instead of Repository

    // Repositories (Direct access is okay for simple reads/writes if no logic needed)
    @Autowired private CartRepository cartRepo; 
    @Autowired private UserRepository userRepo;

    // --- AUTH ---
    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        return userService.signUp(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam String user, @RequestParam String pass) {
        return userService.login(user, pass);
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestBody User user) {
        userRepo.update(user); 
        return "Profile Updated";
    }

    // --- BOOKS ---
    @GetMapping("/books/search")
    public List<Map<String, Object>> searchBooks(@RequestParam String query) {
        // Updated to use the Service layer
        return bookService.searchBooks(query); 
    }

    // --- CART ---
    @GetMapping("/cart")
    public List<Map<String, Object>> viewCart(@RequestParam Long userId) {
        return cartRepo.getCartDetails(userId); 
    }

    @PostMapping("/cart/add")
    public void addToCart(@RequestParam Long userId, @RequestParam String isbn, @RequestParam int qty) {
        cartRepo.addToCart(userId, isbn, qty);
    }

    @DeleteMapping("/cart/remove")
    public void removeFromCart(@RequestParam Long userId, @RequestParam String isbn) {
        cartRepo.removeFromCart(userId, isbn);
    }

    // --- CHECKOUT ---
    @PostMapping("/checkout")
    public String checkout(@RequestParam Long userId, @RequestParam String card, @RequestParam String expiry) {
        try {
            return orderService.checkout(userId, card, expiry); 
        } catch (RuntimeException e) {
            return "Checkout Failed: " + e.getMessage();
        }
    }

    // --- HISTORY ---
    @GetMapping("/orders")
    public List<Map<String, Object>> viewOrders(@RequestParam Long userId) {
        return orderService.getUserOrders(userId); 
    }
    @PostMapping("/logout")
    public String logout(@RequestParam Long userId) {
        // The requirement specifically says logout must clear the cart
        cartRepo.clearCart(userId);
        return "Logged out and cart cleared!";
    }

    @GetMapping("/orders/details")
    public List<Map<String, Object>> viewOrderDetails(@RequestParam Long orderId) {
        // Returns the list of books for a specific order
        return orderService.getOrderDetails(orderId);
    }
}