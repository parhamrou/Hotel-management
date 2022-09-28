package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

import java.sql.SQLException;

public class Admin {


    public static boolean doesUserExist(String username) {
        try {
            return DBConnection.executeQuery(SQLQueries.select("*", "admin", "username = " + username)).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAdmin(String username, String password) {
        DBConnection.execute(SQLQueries.insert("admin", String.format("%s, %s", username, password)));
    }

    public static void removeAdmin(String username) {
        DBConnection.execute(SQLQueries.delete("admin", "username = " + username));
    }
    public static void setUsername(String newUsername, String oldUsername) {
        DBConnection.execute(SQLQueries.update("admin", "username = " + newUsername, "username = " + oldUsername));
    }

    public void setPassword(String newPassword, String username) {
        DBConnection.execute(SQLQueries.update("admin", "password = " + newPassword, "username = " + username));
    }
}
