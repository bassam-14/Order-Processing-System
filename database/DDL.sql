CREATE DATABASE IF NOT EXISTS bookstore_db;
USE bookstore_db;

-- 1. TABLES
CREATE TABLE Publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE Publisher_Phone (
    publisher_id INT,
    phone_number VARCHAR(20),
    PRIMARY KEY (publisher_id, phone_number),
    FOREIGN KEY (publisher_id) REFERENCES Publisher(id) ON DELETE CASCADE
);

CREATE TABLE Author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Book (
    isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publisher_id INT,
    publication_year INT,
    price DECIMAL(10,2),
    category VARCHAR(50), -- Science, Art, Religion, History, Geography
    stock_quantity INT DEFAULT 0,
    threshold INT DEFAULT 5,
    FOREIGN KEY (publisher_id) REFERENCES Publisher(id)
);

CREATE TABLE Book_Author (
    book_isbn VARCHAR(13),
    author_id INT,
    PRIMARY KEY (book_isbn, author_id),
    FOREIGN KEY (book_isbn) REFERENCES Book(isbn),
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- In real app, use Hashing!
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    address VARCHAR(255),
    role VARCHAR(20) -- CUSTOMER, ADMIN, MANAGER
);

CREATE TABLE User_Phone (
    user_id INT,
    phone_number VARCHAR(20),
    PRIMARY KEY (user_id, phone_number),
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE Shopping_Cart (
    user_id INT,
    book_isbn VARCHAR(13),
    quantity INT DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, book_isbn),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);

CREATE TABLE Customer_Order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2),
    credit_card_number VARCHAR(20),
    credit_card_expiry VARCHAR(5),
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Order_Item (
    order_id INT,
    book_isbn VARCHAR(13),
    quantity INT,
    unit_price DECIMAL(10,2),
    PRIMARY KEY (order_id, book_isbn),
    FOREIGN KEY (order_id) REFERENCES Customer_Order(id),
    FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);

CREATE TABLE Publisher_Order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_isbn VARCHAR(13),
    quantity INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20), -- ORDERED, RECEIVED
    FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);