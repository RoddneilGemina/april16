package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    public static void main(String[] args) {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {{
                String query = "SELECT * FROM users ";
                ResultSet res = statement.executeQuery(query);
                while(res.next()){
                    int id = res.getInt(1);
                    String name = res.getString(2);
                    String email = res.getString(3);
                    System.out.println("ID: " + id+
                            " | Name: " + name+
                            " | Email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
