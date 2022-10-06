package com.company;

import com.database.DBConnection;
import com.database.SQLQueries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Costumer {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String nationalId;
    private int age;
    private int roomNumber;

    public Costumer(String firstName, String lastName, String phoneNumber, String nationalId, int age, int roomNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.nationalId = nationalId;
        this.age = age;
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getId() {
        ResultSet result = DBConnection.executeQuery(SQLQueries.select("id", "costumer", String.format("room_number = %d", this.roomNumber)));
        try {
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public static void addCostumer(String firstName, String lastName, int age, String nationalId, int roomNumber, String phoneNumber) {
        DBConnection.execute(SQLQueries.insert("costumer", "first_name, last_name, age, national_id, room_number, phone_number", String.format("\"%s\", \"%s\", %d, \"%s\", %d, \"%s\"", firstName, lastName, age, nationalId, roomNumber, phoneNumber)));
    }

    public static void remove(int id) {
        DBConnection.execute(SQLQueries.delete("costumer", "id = " + id));
    }
}
