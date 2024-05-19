package com.example.food_app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection con;
    private static final String uri = "jdbc:mysql://" + DBConfig.host + ":" + DBConfig.port + "/" + DBConfig.dbname;
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(uri,DBConfig.username,DBConfig.password);
            System.out.println("thanh cong");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
	System.out.println("that bai");
        }
        return con;
    }

    public static void main(String[] args) {
        DBConnect.getConnection();
    }
}
