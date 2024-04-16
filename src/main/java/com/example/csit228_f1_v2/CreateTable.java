package com.example.csit228_f1_v2;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public CreateTable(){}
    public void create() {
        Connection c = MySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "pass VARCHAR(20) NOT NULL)";
        try {
            Statement statement = c.createStatement();
            statement.execute(query);
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
