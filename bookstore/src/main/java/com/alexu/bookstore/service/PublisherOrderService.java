package com.alexu.bookstore.service;

import com.alexu.bookstore.model.PublisherOrder;
import com.alexu.bookstore.repository.PublisherOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublisherOrderService {

    @Autowired
    private PublisherOrderRepository orderRepo;

    public List<PublisherOrder> getAllOrders() {
        return orderRepo.findAll();
    }

    public void placeOrder(PublisherOrder order) {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        orderRepo.save(order);
    }
}