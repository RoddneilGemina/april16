package com.example.csit228_f1_v2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    public void start() {
        try (Connection c = MySQLConnection.getConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO users (name, email) VALUES (?,?)"
            );
            String name = "Paula May D. Manuel";
            String email = "paulamay.manuel@cmu.edu.ph";
            statement.setString(1,name);
            statement.setString(2,email);
            int rows = statement.executeUpdate();
            System.out.println("Rows inserted: " + rows);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
