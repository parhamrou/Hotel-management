package com.controllers;

import com.app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.AnnotatedArrayType;
import java.net.URL;
import java.util.ResourceBundle;

public class RestaurantController implements Initializable {

    @FXML
    private AnchorPane background;
    @FXML
    private Button addOrder;

    @FXML
    private Button exit;

    @FXML
    private Button foods;

    @FXML
    void addOrderButtonPressed(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(OrderController.class.getResource("Order.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Order");
            stage.setScene(new Scene(root, 500, 150));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        App.switchScene(event, MainPageController.class.getResource("MainPage.fxml"));
    }

    @FXML
    void foodsButtonPressed(ActionEvent event) {

    }

    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgb(172, 115, 57, 0.7); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgb(191, 128, 64, 0.7); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setStyle("-fx-background-image: url('https://cdn.wallpaper.com/main/styles/responsive_1680w_scale/s3/muji-hotel-shenzhen-1.jpg');");
        Button[] buttons = {addOrder, foods, exit};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}