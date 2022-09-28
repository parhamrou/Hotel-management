package com.company;

import com.database.DBConnection;
import com.database.SQLQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class OrderFactor {

    public static void addFactor(int roomNumber, ArrayList<Map<Integer, Integer>> foods) {
        int id = SQLQueries.getMaxId("order_id", "order_factor") + 1;
        for (Map<Integer, Integer> food : foods) {
            addFoodToFactor(food, id);
        }
        int totalPrice = getTotalPrice(id);
        DBConnection.execute(SQLQueries.insert("order_factor", String.format("%d, %d, %d", id, roomNumber, totalPrice)));
    }

    public static void addFoodToFactor(Map<Integer, Integer> food, int id) {
        int foodId = (int) food.keySet().toArray()[0];
        DBConnection.execute(SQLQueries.insert("food_factor", "food_id, order_id", String.format("%d, %d", foodId, id)));
    }

    public static void removeFoodFromFactor(int factorId, int foodId) {
            DBConnection.execute(SQLQueries.delete("food_factor", String.format("food_id = %s AND factor_id = %s", foodId, factorId)));
    }

    public static int getTotalPrice(int orderId) {
        ResultSet resultSet = DBConnection.executeQuery(SQLQueries.select("SUM(price)", "food_factor", "order_id = " + orderId));
        try {
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
