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
        ResultSet resultSet = DBConnection.executeQuery(SQLQueries.select("COALESCE(MAX(food_id), 0)", "food"));
        try {
            if (resultSet.next()) {
                System.out.println("Max id is " + resultSet.getInt(1));
                this.id = resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFood(Food food) {
        setNewId();
        DBConnection.execute(SQLQueries.insert("food", String.format("%d, %s, %d", food.getId(), food.getName(), food.getPrice())));
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
