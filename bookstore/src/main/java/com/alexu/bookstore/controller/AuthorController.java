package com.alexu.bookstore.controller;

import com.alexu.bookstore.model.Author;
import com.alexu.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAll() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public String add(@RequestBody Author author) {
        authorService.addAuthor(author);
        return "Author added successfully!";
    }
}