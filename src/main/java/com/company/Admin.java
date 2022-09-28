package com.company;

import com.database.DBConnection;
import com.database.SQLQueries;

import java.sql.SQLException;

public class Admin {

    private static String username;
    private static String password;
    private static boolean logedIn;

    public static boolean doesUserExist(String username) {
        try {
            return DBConnection.executeQuery(SQLQueries.select("*", "admin", "username = " + username)).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean login(String username, String password) {
        try {
            if (DBConnection.executeQuery(SQLQueries.select("*", "admin", String.format("username = \"%s\" AND password = \"%s\"", username, password))).next()) {
                Admin.username = username;
                Admin.password = password;
                logedIn = true;
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAdmin(String username, String password) {
        DBConnection.execute(SQLQueries.insert("admin", String.format("\"%s\", \"%s\"", username, password)));
    }

    public static void removeAdmin(String username) {
        DBConnection.execute(SQLQueries.delete("admin", String.format("username = \"%s\"", username)));
    }
    public static void setUsername(String newUsername, String oldUsername) {
        DBConnection.execute(SQLQueries.update("admin", String.format("username = \"%s\"", newUsername), String.format("username = \"%s\"", oldUsername)));
    }

    public void setPassword(String newPassword, String username) {
        DBConnection.execute(SQLQueries.update("admin", String.format("password = \"%s\"", newPassword), String.format("username = \"%s\"", username)));
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
