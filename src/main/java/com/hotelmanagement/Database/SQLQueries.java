package com.hotelmanagement.Database;

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

    public static String update(String table, String values, String conditions) {
        return String.format("UPDATE %s SET %s WHERE %s;", table, values, conditions);
    }
}
