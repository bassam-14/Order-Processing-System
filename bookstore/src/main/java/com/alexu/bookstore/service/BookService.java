package com.alexu.bookstore.service;

import com.alexu.bookstore.model.Book;
import com.alexu.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public void addBook(Book book) {
        // Business Logic: Check if price is positive
        if (book.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        bookRepo.save(book);
    }
    
    public void editBook(String isbn, Book updatedBook) {
    // 1. Ensure the ISBN in the object matches the URL
    updatedBook.setIsbn(isbn); 

    // 2. Business Logic: Prevent negative prices
    if (updatedBook.getPrice().doubleValue() < 0) {
        throw new IllegalArgumentException("Price cannot be negative");
    }

    // 3. Call the Repository (The SQL method we just created)
    int rowsAffected = bookRepo.update(updatedBook);

    // 4. Check if it actually worked
    if (rowsAffected == 0) {
        throw new RuntimeException("Book with ISBN " + isbn + " not found!");
    }
}
    public Book getBookByIsbn(String isbn) {
        // Rule: You could check if isbn is valid format here before calling DB
        return bookRepo.findByIsbn(isbn);
    }

    // 2. MIDDLEMAN FOR "FIND BY CATEGORY"
    public List<Book> getBooksByCategory(String category) {
        return bookRepo.findByCategory(category);
    }

    // 3. MIDDLEMAN FOR "UPDATE BOOK"
    public void updateBookDetails(Book book) {
        // Rule: You might want to check if the new price is valid
        if (book.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        bookRepo.update(book);
    }
}
