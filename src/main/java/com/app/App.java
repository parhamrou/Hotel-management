package com.app;

import com.controllers.*;
import com.database.DBConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

public class App extends Application {

    private static Parent root;
    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(LoginController.class.getResource("Login.fxml"));
        scene = new Scene(root);
        stage = primaryStage;
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        DBConnection.connect();
        launch();
        DBConnection.disconnect();
    }

    public static void switchScene(ActionEvent event, URL url)  {
        try {
            root = FXMLLoader.load(url);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newStage(URL url, String title) {
        try {
            Parent newRoot = FXMLLoader.load(url);
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(new Scene(root, 800, 500));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}