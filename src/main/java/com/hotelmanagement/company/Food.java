package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

public class Food {

    private int id;
    private String name;
    private int price;

    public Food(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
