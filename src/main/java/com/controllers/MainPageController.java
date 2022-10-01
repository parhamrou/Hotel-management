package com.controllers;

import com.app.App;
import com.company.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

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

    }

    @FXML
    void settingButtonPressed(ActionEvent event) {

    }

    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #8c8c8c"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color:  #999999"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setting buttons setting
        Button[] buttons = {exit, reception, restaurant, events, setting};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}



