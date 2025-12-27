package com.alexu.bookstore.service;

import com.alexu.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

    public List<Map<String, Object>> searchBooks(String query) {
        // You can add extra logic here if needed (e.g., logging searches)
        return bookRepo.search(query);
    }
}