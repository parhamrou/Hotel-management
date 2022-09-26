# At first you have to create database Restaurant with this command: CREATE DATABASE Hotel-management
# Then you can run this script to create tables

CREATE TABLE admin (
    username VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20)
);


CREATE TABLE costumer (
    id INT PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    age INT,
    national_id VARCHAR(15),
    room_number INT,
    phone_number VARCHAR(17)
);


CREATE TABLE room (
    room_number INT PRIMARY KEY,
    capacity INT,
    price_per_day INT,
    check_in_date DATE,
    check_out_date DATE,
    costumer_id INT,
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


DELIMITER $$
CREATE TRIGGER remove_costumer AFTER UPDATE ON room
FOR EACH ROW BEGIN
    DELETE FROM costumer
    WHERE OLD.room_number = costumer.room_number;
END$$

DELIMITER ;
