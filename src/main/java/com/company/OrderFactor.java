package com.company;

import com.database.DBConnection;
import com.database.SQLQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class OrderFactor {

    public static void addFactor(int roomNumber, Map<Integer, Integer> foods) {
        int id = SQLQueries.getMaxId("order_id", "order_factor") + 1;
        DBConnection.execute(SQLQueries.insert("order_factor", "order_id, room_number", String.format("%d, %d", id, roomNumber)));
        for (Map.Entry<Integer, Integer> food : foods.entrySet()) {
            int foodPrice = 0;
            ResultSet result = DBConnection.executeQuery(SQLQueries.select("price", "food", "food_id = " + food.getKey()));
            try {
                if (result.next()) {
                    foodPrice = result.getInt("price");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            addFoodToFactor(food.getKey(), food.getValue(), id, foodPrice);
        }
        int totalPrice = getTotalPrice(id);
        DBConnection.execute(SQLQueries.update("order_factor", "total_price = " + totalPrice, "order_id = " + id));
    }

    public static void addFoodToFactor(int foodId, int foodNumber, int orderId, int foodPrice) {
        for (int i = 0; i < foodNumber; i++) {
            DBConnection.execute(SQLQueries.insert("food_factor", "food_id, order_id, food_price", String.format("%d, %d, %d", foodId, orderId, foodPrice)));
        }
    }

    public static void removeFoodFromFactor(int factorId, int foodId) {
            DBConnection.execute(SQLQueries.delete("food_factor", String.format("food_id = %s AND factor_id = %s", foodId, factorId)));
    }

    public static int getTotalPrice(int orderId) {
        // TODO
        ResultSet resultSet = DBConnection.executeQuery(SQLQueries.select("SUM(food_price)", "food_factor", "order_id = " + orderId));
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
