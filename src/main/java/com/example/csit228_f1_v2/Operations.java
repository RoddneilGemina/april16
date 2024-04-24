package com.example.csit228_f1_v2;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.csit228_f1_v2.MySQLConnection.getConnection;

public class Operations {
    public static final String URL = "jdbc:mysql://localhost:3306/dbgeminaf1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public static int createUser(String name, String password) {
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO users (name, pass, bio) VALUES (?,?,?)"
             )) {
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, " ");
            int rowsInserted = statement.executeUpdate();
            return rowsInserted;
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static int deleteAccount(int id) {
        int postsDeleted = -1;
        try(
                Connection c = getConnection();
                PreparedStatement deleteUserStatement = c.prepareStatement("DELETE FROM users WHERE uid=?");
                PreparedStatement deletePostStatement = c.prepareStatement("DELETE FROM posts WHERE userid=?")
        ){
            c.setAutoCommit(false);
            deleteUserStatement.setInt(1,id);
            deleteUserStatement.execute();
            deletePostStatement.setInt(1,id);
            deletePostStatement.execute();
            c.commit();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return postsDeleted;
    }

    public static boolean verifyLogin(String name, String pass) {
        try(
                Connection c = getConnection();
                Statement statement = c.createStatement();
        ){
            String query = "SELECT * FROM users WHERE name='"+name+"'";
            ResultSet res = statement.executeQuery(query);
            while(res.next()){
                String setpassword = res.getString("pass");
                if(pass.matches(setpassword)){
                    CurrentUser.id = res.getInt("uid");
                    CurrentUser.password=pass;
                    CurrentUser.username=res.getString("name");
                    CurrentUser.bio=res.getString("bio");
                    return true;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void updateBio(int uid, String bio) {
        try(
                Connection c = getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE users SET bio=? WHERE uid=?");
        ){
            statement.setString(1,bio);
            statement.setInt(2,uid);
            int rowsUpdated = statement.executeUpdate();
            //System.out.println("Rows Updated: "+rowsUpdated);
            CurrentUser.bio = bio;
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static User getAccount(int id){
        User user = new User(-1,"NULL","NULL");
        try(
                Connection c = getConnection();
               Statement statement = c.createStatement();
        ){
            String query = "SELECT * FROM users WHERE uid="+id;
            ResultSet res = statement.executeQuery(query);
            if(res.next()){
                user = new User(
                        id,
                        res.getString("name"),
                        res.getString("bio")
                );
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<Post> getPosts(){
        ArrayList<Post> posts = new ArrayList<>();
        try(
                Connection c = getConnection();
                Statement statement = c.createStatement();
        ){
            String query = "SELECT * FROM posts";
            ResultSet res = statement.executeQuery(query);
            while(res.next()){
                int postid = res.getInt("postid");
                int accountid = res.getInt("userid");
                String contents = res.getString("descrpt");
                posts.add(new Post(postid,accountid,contents));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return posts;
    }

    public static int createPost(int userID, String text){
        try (Connection c = getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO posts (userid,descrpt) VALUES (?,?)"
             )) {
            statement.setInt(1, userID);
            statement.setString(2, text);
            return statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void editPost(int postID, String newContents){
        try(
                Connection c = getConnection();
                PreparedStatement statement = c.prepareStatement("UPDATE posts SET descrpt=? WHERE postid=?");
        ){
            statement.setString(1,newContents);
            statement.setInt(2,postID);
            int rowsUpdated = statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean deletePost(int postID){
        try(
                Connection c = getConnection();
                PreparedStatement statement = c.prepareStatement("DELETE FROM posts WHERE postid=?");
        ){
            statement.setInt(1,postID);
            int rowsDeleted = statement.executeUpdate();
            return (rowsDeleted>0);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
