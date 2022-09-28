package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

public class Costumer {


    public static void addCostumer(String firstName, String lastName, int age, String nationalId, int roomNumber, String phoneNumber) {
        DBConnection.execute(SQLQueries.insert("costumer", "first_name, last_name, age, national_id, room_number, phone_number", String.format("%s, %s, %d, %s, %d, %s", firstName, lastName, age, nationalId, roomNumber, phoneNumber)));
    }

    public static void remove(int id) {
        DBConnection.execute(SQLQueries.delete("costumer", "id = " + id));
    }
}
