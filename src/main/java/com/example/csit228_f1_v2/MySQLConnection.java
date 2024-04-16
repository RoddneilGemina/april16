package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/dbgeminaf1";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    static Connection getConnection(){
        Connection c = null;
        try {
            c = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("CONNECTION SUCCESS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }


    public static void main(String[] args) {
        getConnection();
    }
};
