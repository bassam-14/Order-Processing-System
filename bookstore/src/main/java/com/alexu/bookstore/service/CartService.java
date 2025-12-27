package com.alexu.bookstore.service;

import com.alexu.bookstore.model.ShoppingCart;
import com.alexu.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public List<ShoppingCart> getUserCart(int userId) {
        return cartRepo.findByUserId(userId);
    }

    public void addToCart(ShoppingCart cart) {
        if (cart.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        cartRepo.save(cart);
    }

    public void removeFromCart(int userId, String isbn) {
        cartRepo.delete(userId, isbn);
    }
    public void updateItemQuantity(int userId, String isbn, int quantity) {
    if (quantity <= 0) {
        cartRepo.delete(userId, isbn); // If 0, remove item
    } else {
        cartRepo.updateQuantity(userId, isbn, quantity);
    }
}
}