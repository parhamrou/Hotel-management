package com.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLQueries {

    public static String select(String items, String table, String conditions) {
        return String.format("SELECT %s FROM %s WHERE %s;", items, table, conditions);
    }

    public static String select(String items, String table) {
        return String.format("SELECT %s FROM %s;", items, table);
    }

    public static String delete(String table, String conditions) {
        return String.format("DELETE FROM %s WHERE %s;", table, conditions);
    }

    public static String delete(String table, String conditions, int limitNumber) {
        return String.format("DELETE FROM %s WHERE %s LIMIT %d;", table, conditions, limitNumber);
    }

    public static String update(String table, String values, String conditions) {
        return String.format("UPDATE %s SET %s WHERE %s;", table, values, conditions);
    }

    public static String insert(String table, String values) {
        return String.format("INSERT INTO %s VALUES(%s);", table, values);
    }

    public static String insert(String table, String attributesList, String values) {
        return String.format("INSERT INTO %s(%s) VALUES(%s);", table, attributesList, values);
    }

    public static int getMaxId(String columnName, String table) {
        try {
            ResultSet result = DBConnection.executeQuery(SQLQueries.select(String.format("COALESCE(MAX(%s), 0)", columnName), table));
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

}
