package com.alexu.bookstore.service;

import com.alexu.bookstore.model.Publisher;
import com.alexu.bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepo;

    public List<Publisher> getAllPublishers() {
        return publisherRepo.findAll();
    }

    public void addPublisher(Publisher publisher) {
        publisherRepo.save(publisher);
    }

    // Logic to add a phone number to an existing publisher
    public void addPublisherPhone(int publisherId, String phoneNumber) {
        publisherRepo.addPhone(publisherId, phoneNumber);
    }
}