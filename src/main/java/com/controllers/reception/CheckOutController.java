package com.controllers.reception;

import com.company.Room;
import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckOutController implements Initializable {

    @FXML
    private Text alertText;

    @FXML
    private Label checkInDateLabel;

    @FXML
    private Text checkInText;

    @FXML
    private Button checkOutButton;

    @FXML
    private Label checkOutDateLabel;

    @FXML
    private Text checkOutText;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Text firstNameText;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Text lastNameText;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Text seccessAlertText;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Text totalPriceText;

    @FXML
    void checkOutButtonPressed(ActionEvent event) {
        if (firstNameText.getText().isEmpty()) {
            seccessAlertText.setText("You didn't choose any room!");
            return;
        }
        Room.checkOut(Integer.parseInt(searchTextField.getText()));
        seccessAlertText.setText("Successfully checked-out!");
    }

    @FXML
    void searchButtonPressed(ActionEvent event) {
        setTextsEmpty();
        setLabelsVisible(false);
        alertText.setText("");
        seccessAlertText.setText("");
        if (searchTextField.getText().isEmpty()) {
            alertText.setText("You didn't enter any number!");
            return;
        }
        try {
            ResultSet result = DBConnection.executeQuery(SQLQueries.select("room_number, costumer_id, check_in_date, check_out_date", "room", String.format("room_number = %d AND check_in_date IS NOT NULL", Integer.parseInt(searchTextField.getText()))));
            if (result.next()) {
                setLabelsVisible(true);
                int roomNumber = result.getInt("room_number");
                int costumerId = result.getInt("costumer_id");
                String checkInDate = result.getDate("check_in_date").toString();
                String checkOutDate = result.getDate("check_out_date").toString();
                int totalCost = Room.getTotalCost(roomNumber);
                ResultSet info = DBConnection.executeQuery(SQLQueries.select("first_name, last_name", "costumer", "id = " + costumerId));
                if (info.next()) {
                    String firstName = info.getString("first_name");
                    String lastName = info.getString("last_name");
                    firstNameText.setText(firstName);
                    lastNameText.setText(lastName);
                    checkInText.setText(checkInDate);
                    checkOutText.setText(checkOutDate);
                    totalPriceText.setText(Integer.toString(totalCost));
                    return;
                }
            }
            alertText.setText("Invalid input!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabelsVisible(boolean isVisiable) {
        Label[] labels = {firstNameLabel, lastNameLabel, checkInDateLabel, checkOutDateLabel, totalPriceLabel};
        for (Label label : labels) {
            label.setVisible(isVisiable);
        }
    }

    private void setTextsEmpty() {
        Text[] texts = {firstNameText, lastNameText, checkInText, checkOutText, totalPriceText};
        for (Text text : texts) {
            text.setText("");
        }
    }

    private void setButtonStyle(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #996633; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ac7339; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabelsVisible(false);
        setTextsEmpty();
        alertText.setText("");
        seccessAlertText.setText("");
        setButtonStyle(checkOutButton);
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #996633"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #ac7339"));
        searchButton.setStyle("-fx-prompt-text-fill: Black; -fx-background-color: #ac7339");
    }
}
