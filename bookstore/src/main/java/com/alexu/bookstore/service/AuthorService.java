package com.alexu.bookstore.service;

import com.alexu.bookstore.model.Author;
import com.alexu.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepo;

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public void addAuthor(Author author) {
        // Simple validation
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }
        authorRepo.save(author);
    }
}