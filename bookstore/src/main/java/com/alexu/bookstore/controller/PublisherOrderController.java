package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.PublisherOrder;
import com.alexu.bookstore.service.PublisherOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/publisher-orders")
public class PublisherOrderController {

    @Autowired
    private PublisherOrderService orderService;

    @GetMapping
    public List<PublisherOrder> getAll() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public String placeOrder(@RequestBody PublisherOrder order) {
        orderService.placeOrder(order);
        return "Order sent to publisher successfully!";
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable int id, @RequestParam String status) {
        orderService.updateStatus(id, status);
    }
}