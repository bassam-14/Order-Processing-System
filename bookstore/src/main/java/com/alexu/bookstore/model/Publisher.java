package com.alexu.bookstore.model;

import java.util.List;

public class Publisher {
    private int id;
    private String name;
    private String address;
    private List<String> phoneNumbers; // To hold data from Publisher_Phone table

    // --- CONSTRUCTORS ---
    public Publisher() {
    }

    public Publisher(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // --- GETTERS & SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<String> getPhoneNumbers() { return phoneNumbers; }
    public void setPhoneNumbers(List<String> phoneNumbers) { this.phoneNumbers = phoneNumbers; }
}