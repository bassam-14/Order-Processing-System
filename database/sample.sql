USE bookstore_db;

-- --------------------------------------------------------
-- 1. INSERT PUBLISHERS
-- --------------------------------------------------------
INSERT INTO publisher (name, address) VALUES 
('TechBooks Publishing', '123 Silicon Valley, CA'),
('History House', '45 Old Town, London, UK'),
('Artistic Minds', '789 Creative Blvd, Paris, France');

-- Insert Publisher Phones (Child Table)
INSERT INTO publisher_phone (publisher_id, phone_number) VALUES 
(1, '123-456-7890'),
(1, '098-765-4321'), -- TechBooks has two numbers
(2, '44-20-7946-0958'),
(3, '33-1-9876-5432');

-- --------------------------------------------------------
-- 2. INSERT AUTHORS
-- --------------------------------------------------------
INSERT INTO author (name) VALUES 
('Robert C. Martin'),
('Yuval Noah Harari'),
('Stephen Hawking'),
('Leonardo da Vinci'),
('Karen Armstrong');

-- --------------------------------------------------------
-- 3. INSERT BOOKS
-- (Note: Categories must match ENUM: 'Science', 'Art', 'Religion', 'History', 'Geography')
-- --------------------------------------------------------
INSERT INTO book (isbn, title, publication_year, price, category, stock_quantity, threshold, publisher_id) VALUES 
('978-0132350884', 'Clean Code', 2008, 45.00, 'Science', 20, 5, 1),
('978-0062316097', 'Sapiens: A Brief History', 2011, 22.50, 'History', 15, 10, 2),
('978-0553380163', 'A Brief History of Time', 1988, 18.00, 'Science', 8, 10, 1), -- Low stock (below threshold)
('978-1400079988', 'Leonardo da Vinci', 2017, 35.00, 'Art', 12, 5, 3),
('978-0307278038', 'A History of God', 1993, 19.99, 'Religion', 30, 5, 2),
('978-0137081073', 'The Clean Coder', 2011, 40.00, 'Science', 25, 5, 1),
('978-0199213117', 'Geography: A Very Short Introduction', 2008, 12.00, 'Geography', 50, 10, 2),
('978-0714833552', 'The Story of Art', 1995, 55.00, 'Art', 5, 8, 3), -- Low stock
('978-1846041242', 'Homo Deus', 2015, 24.00, 'History', 18, 5, 2),
('978-0385504225', 'The Da Vinci Code', 2003, 15.00, 'Art', 100, 10, 1);

-- --------------------------------------------------------
-- 4. LINK BOOKS TO AUTHORS (Many-to-Many)
-- --------------------------------------------------------
INSERT INTO book_author (book_isbn, author_id) VALUES 
('978-0132350884', 1), -- Clean Code -> Robert Martin
('978-0137081073', 1), -- Clean Coder -> Robert Martin
('978-0062316097', 2), -- Sapiens -> Harari
('978-1846041242', 2), -- Homo Deus -> Harari
('978-0553380163', 3), -- Brief History -> Hawking
('978-1400079988', 4), -- Leonardo -> Leonardo (Biographical)
('978-0307278038', 5), -- History of God -> Armstrong
('978-0714833552', 4), -- Story of Art -> Leonardo (Hypothetical)
('978-0385504225', 1); -- Da Vinci Code -> Robert Martin (Hypothetical)

-- --------------------------------------------------------
-- 5. INSERT USERS (Admin and Customer)
-- Note: In a real app, passwords should be Hashed (e.g., bcrypt). 
-- For now, we use plain text '123456' to keep testing simple.
-- --------------------------------------------------------

-- 1. The Admin User
INSERT INTO users (username, password, first_name, last_name, email, role, address) VALUES 
('admin_mohamed', '123456', 'Mohamed', 'Bassam', 'admin@bookstore.com', 'admin', 'Alexandria Engineering Faculty');

-- 2. The Customer User
INSERT INTO users (username, password, first_name, last_name, email, role, address) VALUES 
('customer_jane', '123456', 'Jane', 'Doe', 'jane@gmail.com', 'customer', '123 Seaside Ave, Alexandria');

-- Insert User Phones
INSERT INTO user_phone (user_id, phone_number) VALUES 
(1, '010-1234-5678'), -- Admin Phone
(2, '012-9876-5432'); -- Customer Phone