# At first you have to create database Restaurant with this command: CREATE DATABASE Hotel-management
# Then you can run this script to create tables

CREATE TABLE admin (
    username VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20)
);


CREATE TABLE costumer (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    age INT,
    national_id VARCHAR(15),
    room_number INT,
    phone_number VARCHAR(17),
    PRIMARY KEY(id)
);


CREATE TABLE room (
    room_number INT PRIMARY KEY,
    capacity INT,
    price_per_day INT,
    check_in_date DATE DEFAULT NULL,
    check_out_date DATE DEFAULT  NULL,
    costumer_id INT DEFAULT NULL,
    FOREIGN KEY(costumer_id) REFERENCES costumer(id) ON DELETE SET NULL
);


ALTER TABLE costumer
ADD FOREIGN KEY(room_number) REFERENCES room(room_number) ON DELETE SET NULL;


CREATE TABLE food (
    food_id INT AUTO_INCREMENT,
    food_name VARCHAR(20),
    price INT,
    PRIMARY KEY(food_id)
);


CREATE TABLE order_factor (
    order_id INT AUTO_INCREMENT,
    room_number INT,
    total_price INT,
    is_delivered BIT DEFAULT 0,
    time TIMESTAMP default CURRENT_TIMESTAMP,
    PRIMARY KEY(order_id),
    FOREIGN KEY(room_number) REFERENCES room(room_number) ON UPDATE CASCADE
);


CREATE TABLE food_factor (
    id INT AUTO_INCREMENT,
    food_id INT,
    order_id INT,
    PRIMARY KEY(id),
    FOREIGN KEY(order_id) REFERENCES order_factor(order_id) ON DELETE CASCADE,
    FOREIGN KEY(food_id) REFERENCES food(food_id)
);


Create TABLE log (
    id INT AUTO_INCREMENT,
    type VARCHAR(20),
    description VARCHAR(30),
    time DATETIME default CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

# Triggers
DELIMITER $$
CREATE TRIGGER remove_costumer AFTER UPDATE ON room
FOR EACH ROW BEGIN
    IF NEW.check_in_date IS NULL THEN
        DELETE FROM costumer
        WHERE OLD.room_number = costumer.room_number;
    END IF;
END$$

CREATE TRIGGER add_costumer AFTER INSERT ON costumer
    FOR EACH ROW BEGIN
        INSERT INTO log(type, description) VALUES("add_costumer", CONCAT("Insert new costumer with national id: ", NEW.national_id));
End$$

CREATE TRIGGER remove_costumer AFTER DELETE ON costumer
    FOR EACH ROW BEGIN
    INSERT INTO log(type, description) VALUES("remove_costumer", CONCAT("Remove costumer with national id: ", OLD.national_id));
End$$

CREATE TRIGGER add_admin AFTER INSERT ON admin
    FOR EACH ROW BEGIN
    INSERT INTO log(type, description) VALUES("add_admin", CONCAT("Insert new admin with username: ", NEW.username));
End$$

CREATE TRIGGER remove_admin AFTER DELETE ON admin
    FOR EACH ROW BEGIN
    INSERT INTO log(type, description) VALUES("remove_admin", CONCAT("Remove admin with username: ", OLD.username));
End$$

CREATE TRIGGER check_in AFTER UPDATE ON room
    FOR EACH ROW BEGIN
        IF NEW.check_in_date IS NOT NULL THEN
            INSERT INTO log(type, description) VALUES("check_out", CONCAT("Check in for room ", NEW.room_number));
        END IF;
End$$

CREATE TRIGGER check_out AFTER UPDATE ON room
    FOR EACH ROW BEGIN
        IF NEW.check_in_date IS NULL THEN
            INSERT INTO log(type, description) VALUES("check_out", CONCAT("Check out for room ", OLD.room_number));
        END IF;
End$$

CREATE TRIGGER add_food AFTER INSERT ON food
    FOR EACH ROW BEGIN
    INSERT INTO log(type, description) VALUES("add_food", CONCAT("Insert new food with name ", NEW.food_name));
End$$

CREATE TRIGGER remove_food AFTER DELETE ON food
    FOR EACH ROW BEGIN
    INSERT INTO log(type, description) VALUES("remove_food", CONCAT("Remove food with name ", OLD.food_name));
End$$

CREATE TRIGGER add_order AFTER INSERT ON order_factor
    FOR EACH ROW BEGIN
    INSERT INTO log(type, description) VALUES("add_order", CONCAT("Insert new order for room ", NEW.room_number));
End$$

DELIMITER ;
