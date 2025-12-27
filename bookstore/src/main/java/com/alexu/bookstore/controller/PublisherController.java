package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.Publisher;
import com.alexu.bookstore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public List<Publisher> getAll() {
        return publisherService.getAllPublishers();
    }

    @PostMapping
    public String add(@RequestBody Publisher publisher) {
        publisherService.addPublisher(publisher);
        return "Publisher added successfully!";
    }

    // Endpoint to add a phone number: POST /api/publishers/1/phones?number=123456
    @PostMapping("/{id}/phones")
    public String addPhone(@PathVariable int id, @RequestParam String number) {
        publisherService.addPublisherPhone(id, number);
        return "Phone number added!";
    }
}