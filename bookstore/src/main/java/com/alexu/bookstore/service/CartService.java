package com.alexu.bookstore.service;

import com.alexu.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    // Requirement: Add books to a shopping cart
    public void add(Long userId, String isbn, int quantity) {
        // Logic: You could add a check here to see if the book exists first
        cartRepo.addToCart(userId, isbn, quantity);
    }

    // Requirement: Remove items from the cart
    public void remove(Long userId, String isbn) {
        cartRepo.removeFromCart(userId, isbn);
    }

    /** * Requirement 6: Logout of the system.
     * Doing this will remove all the items in the current cart.
     */
    public void logoutAndClear(Long userId) {
        // When the user logs out, we call the repository to run the manual DELETE SQL
        cartRepo.clearCart(userId);
    }
}