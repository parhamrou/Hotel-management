package com.controllers.restaurant;

import com.app.App;
import com.controllers.mainPage.MainPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RestaurantController implements Initializable {

    @FXML
    private AnchorPane background;
    @FXML
    private Button addOrder;

    @FXML
    private Button orders;

    @FXML
    private Button exit;

    @FXML
    private Button foods;

    @FXML
    void addOrderButtonPressed(ActionEvent event) {
        App.newStage(AddOrderController.class.getResource("AddOrder.fxml"), "Add Order", 800, 500);
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        App.switchScene(event, MainPageController.class.getResource("MainPage.fxml"));
    }

    @FXML
    void foodsButtonPressed(ActionEvent event) {
        App.newStage(FoodsController.class.getResource("Foods.fxml"), "Foods", 800, 500);
    }

    @FXML
    void ordersButtonPressed(ActionEvent event) {
        App.newStage(OrdersController.class.getResource("Orders.fxml"), "Orders", 800, 500);
    }

    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgb(172, 115, 57, 0.7); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgb(191, 128, 64, 0.7); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setStyle("-fx-background-image: url('https://cdn.wallpaper.com/main/styles/responsive_1680w_scale/s3/muji-hotel-shenzhen-1.jpg');");
        Button[] buttons = {addOrder, foods, exit, orders};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}
