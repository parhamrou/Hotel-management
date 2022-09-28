package com.database;
import java.sql.*;

/**
 * This class is for Connecting to the database and executing queries to INSERT, UPDATE and DELETE data from database;
 */
public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost/hotel_management";
    private static final String USER = "parhamrou";
    private static final String PASS = "@Parham1381";
    private static Connection connection;
    private static Statement statement;

    /**
     * This method creates connection and then creates a statement to access to the database.
     */
    public static void connect() {
        try {
            System.out.println("Trying to connect to the database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean execute(String query) {
        try {
            return statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int executeUpdate(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
