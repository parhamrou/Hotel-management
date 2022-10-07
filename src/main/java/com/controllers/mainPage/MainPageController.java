package com.controllers.mainPage;

import com.app.App;
import com.company.Admin;
import com.controllers.mainPage.LoginController;
import com.controllers.mainPage.LogsController;
import com.controllers.reception.ReceptionController;
import com.controllers.restaurant.RestaurantController;
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
    void eventsButtonPressed(ActionEvent event) {
        App.newStage(LogsController.class.getResource("Logs.fxml"), "Logs", 800, 500);
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Admin.logout();
        App.switchScene(event, LoginController.class.getResource("Login.fxml"));
    }

    @FXML
    void receptionButtonPressed(ActionEvent event) {
        App.switchScene(event, ReceptionController.class.getResource("Reception.fxml"));
    }

    @FXML
    void restaurantButtonPressed(ActionEvent event) {
        App.switchScene(event, RestaurantController.class.getResource("Restaurant.fxml"));
    }

    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgb(172, 115, 57, 0.6); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgb(191, 128, 64, 0.6); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setStyle("-fx-background-image: url('https://cdn.wallpaper.com/main/styles/responsive_1680w_scale/s3/muji-hotel-shenzhen-1.jpg');");
        Button[] buttons = {exit, reception, restaurant, events};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}



