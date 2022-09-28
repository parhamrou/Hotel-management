package com.company;

import com.database.DBConnection;
import com.database.SQLQueries;

import java.sql.SQLException;

public class Food {

    public static boolean doesFoodExist(String name) {
        try {
            return DBConnection.executeQuery(SQLQueries.select("*", "food", String.format("food_name = \"%s\"", name))).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void add(String name, int price) {
        DBConnection.execute(SQLQueries.insert("food", "food_name, price", String.format("\"%s\", %d", name, price)));
    }

    public static void remove(int id) {
        DBConnection.execute(SQLQueries.delete("food", "food_id = " + id));
    }

    public static void setName(String newName, int id) {
        DBConnection.execute(SQLQueries.update("food", String.format("food_name = \"%s\"", newName), "food_id = " + id));
    }

    public void setPrice(int newPrice, int id) {
        DBConnection.execute(SQLQueries.update("food", "price = " + newPrice, "food_id = " + id));
    }
}
