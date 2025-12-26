DELIMITER //
CREATE TRIGGER prevent_negative_stock
BEFORE UPDATE ON book
FOR EACH ROW
BEGIN
    IF NEW.stock_quantity < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Stock quantity cannot be negative.';
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER restock_level_check
AFTER UPDATE ON book
FOR EACH ROW
BEGIN
    IF NEW.stock_quantity < NEW.threshold AND OLD.stock_quantity >= OLD.threshold THEN
        INSERT INTO publisher_order (book_isbn, quantity, status)
        VALUES (NEW.isbn, 50, 'Pending'); -- Assumes fixed order quantity of 50
    END IF;
END;
//
DELIMITER ;


DELIMITER //
CREATE TRIGGER confirm_publisher_order
AFTER UPDATE ON publisher_order
FOR EACH ROW
BEGIN
    -- Only run if status changed to 'Confirmed'
    IF NEW.status = 'Confirmed' AND OLD.status != 'Confirmed' THEN
        UPDATE book
        SET stock_quantity = stock_quantity + NEW.quantity
        WHERE isbn = NEW.book_isbn;
    END IF;
END;
//
DELIMITER ;