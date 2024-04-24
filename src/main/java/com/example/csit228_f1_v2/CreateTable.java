package com.example.csit228_f1_v2;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public CreateTable(){}
    public void create() {
        Connection c = MySQLConnection.getConnection();
        String query1 = "CREATE TABLE IF NOT EXISTS users (" +
                "uid INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "pass VARCHAR(20) NOT NULL, " +
                "bio VARCHAR(100) NOT NULL);";
        String query2 = "CREATE TABLE IF NOT EXISTS posts (" +
                "postid INT AUTO_INCREMENT, " +
                "descrpt VARCHAR(50) NOT NULL, " +
                "userid INT NOT NULL, " +
                "PRIMARY KEY (`postid`), " +
                "FOREIGN KEY (`userid`) REFERENCES users(`uid`) ON DELETE CASCADE)";
        try {
            Statement statement1 = c.createStatement();
            Statement statement2 = c.createStatement();
            statement1.execute(query1);
            statement2.execute(query2);
            System.out.println("Table created succesfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
