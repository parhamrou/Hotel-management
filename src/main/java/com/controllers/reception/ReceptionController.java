package com.controllers.reception;

import com.app.App;
import com.controllers.mainPage.MainPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionController implements Initializable {

    @FXML
    private AnchorPane background;

    @FXML
    private Button checkIn;

    @FXML
    private Button checkOut;

    @FXML
    private Button exit;

    @FXML
    void checkInButtonPressed(ActionEvent event) {
        App.newStage(CheckInController.class.getResource("CheckIn.fxml"), "Check-in", 800, 500);
    }

    @FXML
    void checkOutButtonPressed(ActionEvent event) {
        App.newStage(CheckOutController.class.getResource("CheckOut.fxml"), "Check-out", 600, 250);
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        App.switchScene(event, MainPageController.class.getResource("MainPage.fxml"));
    }

    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgb(172, 115, 57, 0.6); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgb(191, 128, 64, 0.6); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setStyle("-fx-background-image: url('https://cdn.wallpaper.com/main/styles/responsive_1680w_scale/s3/muji-hotel-shenzhen-1.jpg');");
        Button[] buttons = {checkIn, checkOut, exit};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}

