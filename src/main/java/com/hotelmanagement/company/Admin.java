package com.hotelmanagement.company;

import com.hotelmanagement.Database.DBConnection;
import com.hotelmanagement.Database.SQLQueries;

import java.sql.SQLException;

public class Admin {

    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean doesUserExist(String username) {
        try {
            return DBConnection.executeQuery(SQLQueries.select("*", "admin", "username = " + username)).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAdmin(Admin admin) {
        DBConnection.execute(SQLQueries.insert("admin", String.format("%s, %s", admin.getUsername(), admin.getPassword())));
    }

    public void setUsername(String username) {
        DBConnection.execute(SQLQueries.update("admin", "username = " + username, "username = " + this.username));
        this.username = username;
    }

    public void setPassword(String password) {
        DBConnection.execute(SQLQueries.update("admin", "password = " + password, "username = " + this.username));
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
