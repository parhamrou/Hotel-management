# adding rooms
INSERT INTO room(room_number, capacity, price_per_day) VALUES(101, 4, 120);
INSERT INTO room(room_number, capacity, price_per_day) VALUES(202, 3, 100);
INSERT INTO room(room_number, capacity, price_per_day) VALUES(305, 2, 150);
INSERT INTO room(room_number, capacity, price_per_day) VALUES(605, 3, 110);
INSERT INTO room(room_number, capacity, price_per_day) VALUES(706, 1, 125);

# adding foods
INSERT INTO food(food_name, price) VALUES("Burger", 6);
INSERT INTO food(food_name, price) VALUES("Pizza", 10);
INSERT INTO food(food_name, price) VALUES("Pasta", 7);
INSERT INTO food(food_name, price) VALUES("Soda", 1);
INSERT INTO food(food_name, price) VALUES("Steak", 15);

# add one admin
INSERT INTO admin VALUES("root", "123456");