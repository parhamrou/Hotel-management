package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFactor {

    private int id;
    private int roomNumber;
    private ArrayList<Food> foods;
    private int totalPrice;

    public OrderFactor(int roomNumber, ArrayList<Food> foods) {
        this.roomNumber = roomNumber;
        this.foods = foods;
        this.totalPrice = 0;
    }

    private void setNewId() {
        this.id = SQLQueries.getMaxId("order_id", "order_factor") + 1;
    }

    public void addFoodToFactor(Food food) {
            totalPrice += food.getPrice();
            int id = SQLQueries.getMaxId("id", "food_factor") + 1;
            DBConnection.execute(SQLQueries.insert("food_factor", String.format("%d, %d, %d", id, food.getId(), this.getId())));
    }

    public void removeFoodFromFactor(Food food) {
            totalPrice -= food.getPrice();
            foods.remove(food);
            DBConnection.execute(SQLQueries.delete("food_factor", "food_id = " + food.getId(), 1));
    }

    private void addFoodFactors() {
        for (Food food : foods) {
            addFoodToFactor(food);
        }
    }

    public void addFactor() {
        setNewId();
        addFoodFactors();
        DBConnection.execute(SQLQueries.insert("order_factor", String.format("%d, %d, %d", this.id, this.roomNumber, this.totalPrice)));
    }

    public int getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }
}
