package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Food {

    private int id;
    private String name;
    private int price;

    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static boolean doesFoodExist(String name) {
        try {
            return DBConnection.executeQuery(SQLQueries.select("*", "food", "food_name = " + name)).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNewId() {
        this.id = SQLQueries.getMaxId("food_id", "food") + 1;
    }

    private void addFood() {
        setNewId();
        DBConnection.execute(SQLQueries.insert("food", String.format("%d, %s, %d", this.id, this.name, this.price)));
    }

    public void remove() {
        DBConnection.execute(SQLQueries.delete("food", "food_id = " + id));
    }

    public void setName(String name) {
        DBConnection.execute(SQLQueries.update("food", "food_name = " + name, "food_id = " + id));
        this.name = name;
    }

    public void setPrice(int price) {
        DBConnection.execute(SQLQueries.update("food", "price = " + price, "food_id = " + id));
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
