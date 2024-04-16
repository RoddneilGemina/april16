package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateData {
    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection(); PreparedStatement statement = c.prepareStatement("UPDATE users SET name=? WHERE id=? RETURNING *")){
            String new_name = "Paui";
            int id = 4;
            statement.setString(1,new_name);
            statement.setInt(2,id);
            int rows = statement.executeUpdate();
            System.out.println("Rows updated: " + rows);
            ResultSet res = statement.getResultSet();
            while(res.next()){
                int id2 = res.getInt(1);
                String name = res.getString(2);
                String email = res.getString(3);
                System.out.println("ID: " + id2+
                        " | Name: " + name+
                        " | Email: " + email);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
