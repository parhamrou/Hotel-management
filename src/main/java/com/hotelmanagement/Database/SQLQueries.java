package com.hotelmanagement.Database;
import java.sql.*;


public class SQLQueries {

    private static SQLQueries instance;
    private static final String DB_URL = "jdbc:mysql://localhost/hotel_management";
    private static final String USER = "parhamrou";
    private static final String PASS = "@Parham1381";
    private static Connection connection;
    private static Statement statement;

    /**
     * This method creates connection and then creates a statement to access to the database.
     */
    public void initialize() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAdmin() {

    }
}
