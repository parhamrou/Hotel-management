package com.controllers;

import com.app.App;
import com.company.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private AnchorPane background;

    @FXML
    private Button events;

    @FXML
    private Button exit;

    @FXML
    private Button reception;

    @FXML
    private Button restaurant;

    @FXML
    private Button setting;

    @FXML
    void eventsButtonPressed(ActionEvent event) {

    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Admin.logout();
        App.switchScene(event, LoginController.class.getResource("Login.fxml"));
    }

    @FXML
    void receptionButtonPressed(ActionEvent event) {

    }

    @FXML
    void restaurantButtonPressed(ActionEvent event) {
        App.switchScene(event, RestaurantController.class.getResource("Restaurant.fxml"));
    }

    @FXML
    void settingButtonPressed(ActionEvent event) {

    }

    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgb(172, 115, 57, 0.6); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgb(191, 128, 64, 0.6); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setStyle("-fx-background-image: url('https://cdn.wallpaper.com/main/styles/responsive_1680w_scale/s3/muji-hotel-shenzhen-1.jpg');");
        Button[] buttons = {exit, reception, restaurant, events, setting};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}



