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
    }

    private void setNewId() {
        ResultSet resultSet = DBConnection.executeQuery(SQLQueries.select("COALESCE(MAX(order_id), 0)", "order_factor"));
        try {
            System.out.println("Max id is " + resultSet.getInt(1));
            this.id = resultSet.getInt(1) + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFoodFactor(Food food) {
        try {
            ResultSet resultSet = DBConnection.executeQuery(SQLQueries.select("COALESCE(MAX(id), 0)", "food_factor"));
            System.out.println("Max id is " + resultSet.getInt(1));
            int id = resultSet.getInt(1) + 1;
            DBConnection.execute(SQLQueries.insert("food_factor", String.format("%d, %d, %d", id, food.getId(), this.getId())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFoodFactors() {
        for (Food food : foods) {
            addFoodFactor(food);
        }
    }

    public void addFactor(OrderFactor orderFactor) {
        setNewId();
        addFoodFactors();
        calculateTotalPrice();
        DBConnection.execute(SQLQueries.insert("order_factor", String.format("%d, %d, %d", orderFactor.getId(), orderFactor.getRoomNumber(), orderFactor.getTotalPrice())));
    }

    private void calculateTotalPrice() {
        int total = 0;
        for (Food food : foods) {
            total += food.getPrice();
        }
        this.totalPrice = total;
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
