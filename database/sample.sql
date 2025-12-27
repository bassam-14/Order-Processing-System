USE bookstore_db;

-- 1. TURN OFF SAFETY TO ALLOW DELETIONS
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- 2. CLEAR ALL TABLES
TRUNCATE TABLE Publisher_Order;
TRUNCATE TABLE Order_Item;
TRUNCATE TABLE Customer_Order;
TRUNCATE TABLE Shopping_Cart;
TRUNCATE TABLE User_Phone;
TRUNCATE TABLE User;
TRUNCATE TABLE Book_Author;
TRUNCATE TABLE Book;
TRUNCATE TABLE Author;
TRUNCATE TABLE Publisher_Phone;
TRUNCATE TABLE Publisher;

-- 3. PUBLISHERS
INSERT INTO Publisher (id, name, address) VALUES 
(1, 'Pearson', 'London, UK'),
(2, 'O Reilly Media', 'California, USA'),
(3, 'National Geographic', 'Washington, USA'),
(4, 'Taschen', 'Cologne, Germany'),
(5, 'Oxford Press', 'Oxford, UK');

-- 4. AUTHORS
INSERT INTO Author (id, name) VALUES 
(1, 'Robert C. Martin'),
(2, 'Stephen Hawking'),
(3, 'Vincent Van Gogh'),
(4, 'Ibn Khaldun'),
(5, 'Marco Polo');

-- 5. BOOKS (STRICT CATEGORIES: SCIENCE, ART, RELIGION, HISTORY, GEOGRAPHY)
INSERT INTO Book (isbn, title, publisher_id, publication_year, price, category, stock_quantity, threshold) VALUES 
-- SCIENCE
('978-013235088', 'Clean Code', 1, 2008, 40.00, 'SCIENCE', 15, 5),
('978-055338016', 'A Brief History of Time', 1, 1988, 15.00, 'SCIENCE', 5, 5),

-- ART
('978-383652877', 'Van Gogh: The Complete Paintings', 4, 2012, 60.00, 'ART', 8, 3),
('978-071483355', 'The Story of Art', 4, 1995, 45.00, 'ART', 12, 4),

-- HISTORY
('978-069114788', 'The Muqaddimah', 5, 1377, 35.00, 'HISTORY', 20, 5),
('978-014044913', 'The Histories', 5, 2003, 12.50, 'HISTORY', 10, 5),

-- GEOGRAPHY
('978-014044057', 'The Travels of Marco Polo', 3, 1298, 18.00, 'GEOGRAPHY', 7, 2),
('978-079226525', 'Atlas of the World', 3, 2020, 95.00, 'GEOGRAPHY', 4, 2),

-- RELIGION
('978-006065292', 'Mere Christianity', 2, 1952, 14.00, 'RELIGION', 25, 5),
('978-019953595', 'The Varieties of Religious Experience', 5, 1902, 22.00, 'RELIGION', 6, 3);

-- 6. LINK AUTHORS TO BOOKS
INSERT INTO Book_Author (book_isbn, author_id) VALUES 
('978-013235088', 1), -- Clean Code -> Robert Martin
('978-055338016', 2), -- Hawking
('978-383652877', 3), -- Van Gogh
('978-069114788', 4), -- Ibn Khaldun
('978-014044057', 5); -- Marco Polo

-- 7. USERS
INSERT INTO User (id, username, password, first_name, last_name, email, address, role) VALUES 
(1, 'admin', 'admin123', 'System', 'Admin', 'admin@alexu.edu.eg', 'Faculty of Engineering', 'ADMIN'),
(2, 'customer1', '12345678', 'John', 'Doe', 'john@example.com', '123 Main St', 'CUSTOMER'),
(3, 'customer2', '12345678', 'Sarah', 'Smith', 'sarah@example.com', '456 Sea View', 'CUSTOMER');

-- 8. ORDER HISTORY (For Reports)
INSERT INTO Customer_Order (id, user_id, order_date, total_price, credit_card_number, credit_card_expiry) VALUES 
(101, 2, DATE_SUB(NOW(), INTERVAL 1 MONTH), 40.00, '1111', '12/25'), -- John bought Science
(102, 3, DATE_SUB(NOW(), INTERVAL 2 DAY), 60.00, '2222', '10/26');  -- Sarah bought Art

INSERT INTO Order_Item (order_id, book_isbn, quantity, unit_price) VALUES 
(101, '978-013235088', 1, 40.00),
(102, '978-383652877', 1, 60.00);

-- 9. RESTORE SAFETY
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;