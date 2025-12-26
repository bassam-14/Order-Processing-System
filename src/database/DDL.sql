-- Create the Database
CREATE DATABASE IF NOT EXISTS bookstore_db;
USE bookstore_db;

-- Drop tables if they exist
DROP TABLE IF EXISTS publisher_order;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS customer_order;
DROP TABLE IF EXISTS shopping_cart;
DROP TABLE IF EXISTS user_phone;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS publisher_phone;
DROP TABLE IF EXISTS publisher;

-- Publisher Table
CREATE TABLE publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Publisher Phones
CREATE TABLE publisher_phone (
    publisher_id INT,
    phone_number VARCHAR(20),
    PRIMARY KEY (publisher_id, phone_number),
    FOREIGN KEY (publisher_id) REFERENCES publisher(id) ON DELETE CASCADE
);

-- Author Table
CREATE TABLE author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Book Table
CREATE TABLE book (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INT,
    price DECIMAL(10, 2) NOT NULL,
    category ENUM('Science', 'Art', 'Religion', 'History', 'Geography') NOT NULL,
    stock_quantity INT DEFAULT 0,
    threshold INT DEFAULT 10,
    publisher_id INT,
    FOREIGN KEY (publisher_id) REFERENCES publisher(id) ON DELETE SET NULL,

    -- Constraint: Stock cannot be negative 
    CONSTRAINT chk_stock_non_negative CHECK (stock_quantity >= 0)
);

-- Book_Author (Many-to-Many Relationship)
CREATE TABLE book_author (
    book_isbn VARCHAR(20),
    author_id INT,
    PRIMARY KEY (book_isbn, author_id),
    FOREIGN KEY (book_isbn) REFERENCES book(isbn) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

-- Users (Combines Admin and Customer)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    address VARCHAR(255),
    role ENUM('customer', 'admin') DEFAULT 'customer',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User Phones
CREATE TABLE user_phone (
    user_id INT,
    phone_number VARCHAR(20),
    PRIMARY KEY (user_id, phone_number),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE shopping_cart (
    user_id INT,
    book_isbn VARCHAR(20),
    quantity INT DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, book_isbn),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_isbn) REFERENCES book(isbn) ON DELETE CASCADE
);

-- Customer Order
CREATE TABLE customer_order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Pending', 'Completed', 'Cancelled') DEFAULT 'Pending',
    credit_card_number VARCHAR(20),
    credit_card_expiry VARCHAR(5),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Order Items
CREATE TABLE order_item (
    order_id INT,
    book_isbn VARCHAR(20),
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (order_id, book_isbn),
    FOREIGN KEY (order_id) REFERENCES customer_order(id) ON DELETE CASCADE,
    FOREIGN KEY (book_isbn) REFERENCES book(isbn)
);

-- Publisher Order 
CREATE TABLE publisher_order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_isbn VARCHAR(20),
    quantity INT NOT NULL, -- This is the constant quantity fixed
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Pending', 'Confirmed') DEFAULT 'Pending',
    FOREIGN KEY (book_isbn) REFERENCES book(isbn) ON DELETE CASCADE
);