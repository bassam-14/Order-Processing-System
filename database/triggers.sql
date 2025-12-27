DELIMITER //
CREATE TRIGGER prevent_negative_stock
BEFORE UPDATE ON Book
FOR EACH ROW
BEGIN
    IF NEW.stock_quantity < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Stock cannot be negative';
    END IF;
END;
//

-- Trigger B: Auto-Order from Publisher when stock is low
CREATE TRIGGER place_publisher_order
AFTER UPDATE ON Book
FOR EACH ROW
BEGIN
    -- If stock drops below threshold AND it wasn't below before (to prevent duplicate orders)
    IF NEW.stock_quantity < NEW.threshold AND OLD.stock_quantity >= OLD.threshold THEN
        INSERT INTO Publisher_Order (book_isbn, quantity, status)
        VALUES (NEW.isbn, 10, 'ORDERED'); -- Auto-order 10 copies
    END IF;
END;
//

-- Trigger C: Restock when Admin confirms order
CREATE TRIGGER confirm_order_restock
AFTER UPDATE ON Publisher_Order
FOR EACH ROW
BEGIN
    IF NEW.status = 'RECEIVED' AND OLD.status != 'RECEIVED' THEN
        UPDATE Book SET stock_quantity = stock_quantity + NEW.quantity 
        WHERE isbn = NEW.book_isbn;
    END IF;
END;
//
DELIMITER ;