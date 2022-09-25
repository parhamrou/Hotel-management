# At first you have to create database Restaurant with this command: CREATE DATABASE Hotel-management
# Then you can run this script to create tables.

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
    food_id INT PRIMARY KEY,
    food_name VARCHAR(20),
    price INT
);


CREATE TABLE room_factor (
    factor_id INT AUTO_INCREMENT,
    room_number INT,
    total_price INT,
    PRIMARY KEY(factor_id),
    FOREIGN KEY(room_number) REFERENCES room(room_number)
);


CREATE TABLE food_factor (
    id INT AUTO_INCREMENT,
    food_id INT,
    factor_id INT,
    PRIMARY KEY(id),
    FOREIGN KEY(food_id) REFERENCES food(food_id),
    FOREIGN KEY(factor_id) REFERENCES room_factor(factor_id) ON DELETE CASCADE
);


DELIMITER $$
CREATE TRIGGER empty_room AFTER DELETE ON room_factor 
FOR EACH ROW BEGIN
    UPDATE room
    SET check_in_date = NULL, check_out_date = NULL, costumer_id = NULL
    WHERE room_number = OLD.room_number;
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER remove_costumer AFTER DELETE ON room_factor
FOR EACH ROW BEGIN
    DELETE FROM costumer
    WHERE OLD.room_number = costumer.room_number;
END$$

DELIMITER ;
