package com.company;

import com.database.DBConnection;
import com.database.SQLQueries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Room {

    public static void addRoom(int roomNumber, int capacity, int pricePerDay) {
        DBConnection.execute(SQLQueries.insert("room", "room_number, capacity, price_per_day", String.format("%d, %d, %d" ,roomNumber, capacity, pricePerDay)));
    }

    public static void removeRoom(int roomNumber) {
        DBConnection.execute(SQLQueries.delete("room", "room_number = " + roomNumber));
    }

    public static void checkIn(int roomNumber, int costumerId, String checkInDate, String checkOutDate) {
        DBConnection.execute(SQLQueries.update("room", String.format("costumer_id = %d, check_in_date = \"%s\", check_out_date = \"%s\"", costumerId, checkInDate, checkOutDate), "room_number = " + roomNumber));
    }

    public static void checkOut(int roomNumber) {
        DBConnection.execute(SQLQueries.update("room", "check_in_date = NULL, check_out_date = NULL, costumer_id = NULL", "room_number = " + roomNumber));
    }

    public static int getCapacity(int roomNumber) {
        ResultSet result = DBConnection.executeQuery(SQLQueries.select("capacity", "room", String.format("room_number = %d", roomNumber)));
        try {
            if (result.next()) {
                return result.getInt("capacity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
