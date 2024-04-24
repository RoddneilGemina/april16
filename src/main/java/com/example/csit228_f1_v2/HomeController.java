package com.example.csit228_f1_v2;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.plaf.ScrollPaneUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.csit228_f1_v2.MySQLConnection.getConnection;

public class HomeController {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public ProgressBar pbProgress;
    private Scene prevscene;
    private Scene scene;
    private TextField tfPost;
    private TextArea taBio;
    private Label lblBio;
    private Label lblUser;
    private ScrollPane spPosts;
    private VBox vbPosts;

    public void onNightModeClick() throws IOException {
        AnchorPane p = (AnchorPane)tbNight.getParent();
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }

    public void setPrevScene(Scene s){
        prevscene = s;
    }
    public void setCurrScene(Scene s){
        scene = s;
        tfPost = (TextField) scene.lookup("#tfPost");
        lblUser = (Label) scene.lookup("#lblUser");
        lblBio = (Label) scene.lookup("#lblBio");
        taBio = (TextArea) scene.lookup("#taBio");
        spPosts = (ScrollPane) scene.lookup("#spPosts");
    }

    public void btnBioPressed(ActionEvent event){
        String bioUpdate = taBio.getText();
        Operations.updateBio(CurrentUser.id, bioUpdate);
        refresh();
    }

    public void btnPostPressed(){
        Operations.createPost(CurrentUser.id,tfPost.getText());
        refresh();
    }

    public void refresh(){
        lblBio.setText(CurrentUser.bio);
        lblUser.setText(CurrentUser.username);

        vbPosts = new VBox();
        spPosts.setContent(vbPosts);

        ArrayList<Post> posts = Operations.getPosts();
        for(int i = posts.size()-1; i>=0; i--){
            vbPosts.getChildren().add(displayPost(posts.get(i)));
        }
    }

    private Node displayPost(Post post){
        VBox container = new VBox();
        User user = Operations.getAccount(post.getUserid());
        HBox header = new HBox();
        Label username = new Label(user.getUsername()+"   ");
        username.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        header.getChildren().add(username);
        header.getChildren().add(new Label(user.getBio()));
        container.getChildren().add(header);
        container.getChildren().add(new Label(post.getContents()));
        HBox buttons = new HBox();
        VBox editui = new VBox();
        TextField tfEdit = new TextField(post.getContents());
        editui.getChildren().add(tfEdit);
        Button confirmEdit = new Button("Save");
        confirmEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Operations.editPost(post.getId(),tfEdit.getText());
                refresh();
            }
        });
        editui.getChildren().add(confirmEdit);
        editui.setVisible(false);
        editui.setManaged(false);
        container.getChildren().add(editui);
        Button edit = new Button("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            private boolean shown = false;
            @Override
            public void handle(ActionEvent actionEvent) {
                if(shown) {
                    edit.setText("Edit");
                }
                else{
                    edit.setText("Cancel");
                }
                shown = !shown;
                editui.setVisible(shown);
                editui.setManaged(shown);
            }
        });
        Button delete = new Button("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Operations.deletePost(post.getId()))
                    refresh();
            }
        });
        buttons.getChildren().add(edit);
        buttons.getChildren().add(delete);
        if(CurrentUser.id == user.getId())
            container.getChildren().add(buttons);
        container.setPadding(new Insets(0,0,10,0));
        return container;
    }

    public void btnLogoutPressed(ActionEvent event){
        CurrentUser.logOut();
        Stage stage =HelloApplication.loginstage;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(prevscene);
        stage.show();
    }

    public void btnDeletePressed(ActionEvent event){
        Operations.deleteAccount(CurrentUser.id);
        CurrentUser.logOut();
        Stage stage = HelloApplication.loginstage;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(prevscene);
        stage.show();
    }
}
