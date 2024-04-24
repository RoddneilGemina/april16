package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage loginstage;

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
        CreateTable createTable = new CreateTable();
        createTable.create();
        loginstage = stage;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text txtWelcome = new Text("Welcome to CIT");
        txtWelcome.setFont(Font.font("Chiller", FontWeight.EXTRA_BOLD, 69));
        txtWelcome.setFill(Color.RED);
        grid.setPadding(new Insets(20));
        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        grid.add(txtWelcome, 0, 0, 3, 1);

        Label lbUsername = new Label("Username: ");
        lbUsername.setTextFill(Color.LIGHTSKYBLUE);
        lbUsername.setFont(Font.font(30));
        grid.add(lbUsername, 0, 1);

        TextField tfUsername = new TextField();
        grid.add(tfUsername, 1, 1);
        tfUsername.setFont(Font.font(30));

        Label lbPassword = new Label("Password");
        lbPassword.setFont(Font.font(30));
        lbPassword.setTextFill(Color.CHARTREUSE);
        grid.add(lbPassword, 0, 2);

        PasswordField pfPassword = new PasswordField();
        pfPassword.setFont(Font.font(30));
        grid.add(pfPassword, 1, 2);

        TextField tmpPassword = new TextField(pfPassword.getText());
        tmpPassword.setFont(Font.font(30));
        grid.add(tmpPassword, 1, 2);
        tmpPassword.setVisible(false);

        Label status = new Label("Status");
        grid.add(status, 0, 5, 5, 5);

        ToggleButton btnShow = new ToggleButton("( )");
        btnShow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setText(pfPassword.getText());
                tmpPassword.setVisible(true);
            }
        });

        EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tmpPassword.setVisible(false);
                pfPassword.setText(tmpPassword.getText());
            }
        };

        btnShow.setOnMouseReleased(release);
        btnShow.setOnMouseExited(release);
        grid.add(btnShow, 2,2);

        Button btnLogin = new Button("Log In");
        btnLogin.setFont(Font.font(40));
        grid.add(btnLogin, 0, 3, 2, 1);

        Button btnSignIn = new Button("Register");
        btnSignIn.setFont(Font.font(40));
        grid.add(btnSignIn, 1, 3, 2, 1);

        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = tfUsername.getText();
                String pass = pfPassword.getText();
                try{
                    int added = Operations.createUser(name,pass);
                    status.setText("Successful Registered");
                } catch (Exception e){
                    status.setText("Invalid Register");
                }
            }
        });

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = tfUsername.getText();
                String pass = pfPassword.getText();
                if(Operations.verifyLogin(name,pass)){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                        Parent p = loader.load();
                        Scene s = new Scene(p);
                        HomeController controller = loader.getController();
                        controller.setPrevScene(btnLogin.getScene());
                        controller.setCurrScene(s);
                        tfUsername.setText("");
                        pfPassword.setText("");
                        controller.refresh();
                        stage.setScene(s);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    status.setText("Invalid Login !");
                }
            }
        });

        Scene scene = new Scene(grid, 700, 500, Color.BLACK);
        stage.setScene(scene);
        scene.setFill(Color.CORNFLOWERBLUE);
        stage.show();
        txtWelcome.minWidth(grid.getWidth());
    }

    public static void main(String[] args) {
        launch();
    }
}