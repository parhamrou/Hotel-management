package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

public class Costumer {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String nationalId;
    private int roomNumber;
    private String phoneNumber;


    public Costumer(String firstName, String lastName, int age, String nationalId, int roomNumber, String phoneNumber) {
        this.firstName  = firstName;
        this.lastName = lastName;
        this.age = age;
        this.nationalId = nationalId;
        this.roomNumber = roomNumber;
        this.phoneNumber = phoneNumber;
    }

    private void setNewId() {
        this.id = SQLQueries.getMaxId("id", "costumer") + 1;
    }

    public void addCostumer() {
        setNewId();
        DBConnection.execute(SQLQueries.insert("costumer", String.format("%d, %s, %s, %d, %s, %d, %s", this.id, this.firstName, this.lastName, this.age, this.nationalId, this.roomNumber, this.phoneNumber)));
    }

    public void remove() {
        DBConnection.execute(SQLQueries.delete("costumer", "id = " + this.id));
    }
}
