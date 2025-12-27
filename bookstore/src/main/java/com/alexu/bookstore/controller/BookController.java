package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.Book;
import com.alexu.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public String add(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully!";
    }

    // GET /api/books/12345 (View specific book)
    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        // You'll need to add findByIsbn to your Service too!
        return bookService.getBookByIsbn(isbn);
    }

    // GET /api/books/category/SCIENCE
    @GetMapping("/category/{category}")
    public List<Book> getByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    // PUT /api/books/12345 (Update book details)
    @PutMapping("/{isbn}")
    public String updateBook(@PathVariable String isbn, @RequestBody Book book) {
        book.setIsbn(isbn); // Ensure ID matches
        bookService.updateBookDetails(book);
        return "Book updated!";
    }

    // GET /api/books/author/Rowling
    @GetMapping("/author/{name}")
    public List<Book> getByAuthor(@PathVariable String name) {
        return bookService.searchBooksByAuthor(name);
    }
}
