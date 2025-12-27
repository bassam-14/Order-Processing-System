package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.ShoppingCart;
import com.alexu.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // GET /api/cart/5 (Get cart for user ID 5)
    @GetMapping("/{userId}")
    public List<ShoppingCart> getCart(@PathVariable int userId) {
        return cartService.getUserCart(userId);
    }

    // POST /api/cart/add
    @PostMapping("/add")
    public String add(@RequestBody ShoppingCart cart) {
        cartService.addToCart(cart);
        return "Item added to cart!";
    }

    // DELETE /api/cart/5/remove/123-456 (Remove specific book)
    @DeleteMapping("/{userId}/remove/{isbn}")
    public String remove(@PathVariable int userId, @PathVariable String isbn) {
        cartService.removeFromCart(userId, isbn);
        return "Item removed from cart!";
    }
}