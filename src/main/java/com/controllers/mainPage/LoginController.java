package com.controllers.mainPage;

import com.app.App;
import com.company.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Text alertText;

    @FXML
    private AnchorPane background;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void loginButtonPressed(ActionEvent event) {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            alertText.setText("You have to enter your username and password!");
            return;
        }
        if (!Admin.login(username.getText(), password.getText())) {
            alertText.setText("username or password is incorrect!");
        }
        App.switchScene(event, MainPageController.class.getResource("MainPage.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertText.setText("");
        background.setStyle("-fx-background-image: url('https://cdn.wallpaper.com/main/styles/responsive_1680w_scale/s3/muji-hotel-shenzhen-1.jpg');");
    }
}
