package com.controllers.reception;

import com.company.Costumer;
import com.company.Room;
import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {


    @FXML
    private VBox costumerVbox;

    @FXML
    private ChoiceBox<Integer> roomChoiceBox;

    @FXML
    private Button addButton;

    @FXML
    private Text alertText;

    @FXML
    private DatePicker checkInDate;

    @FXML
    private DatePicker checkOutDate;

    private ArrayList<Costumer> costumers;
    private int roomCapacity;
    private int roomNumber;

    public CheckInController() {
    }

    @FXML
    void addButtonPressed(ActionEvent event) {
        if (costumers.size() != roomCapacity || checkInDate.getValue() == null || checkOutDate.getValue() == null) {
            alertText.setText("You have to fill all fields!");
            return;
        }
        for (Costumer costumer : costumers) {
            Costumer.addCostumer(costumer.getFirstName(), costumer.getLastName(), costumer.getAge(), costumer.getNationalId(), costumer.getRoomNumber(), costumer.getPhoneNumber());
        }
        Room.checkIn(roomNumber, costumers.get(0).getId(), checkInDate.getValue().toString(), checkOutDate.getValue().toString());
        alertText.setText("This room is added successfully!");
    }

    private void createRoomList(ResultSet rooms) {
        try {
            while (rooms.next()) {
                roomChoiceBox.getItems().add(rooms.getInt("room_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCostumerCell(int number) {
        alertText.setText("");
        AnchorPane cell = new AnchorPane();
        cell.setPrefWidth(800);
        cell.setPrefHeight(57);
        cell.setStyle("-fx-background-color:  #c68c53");
        Text text = new Text(Integer.toString(number));
        text.setLayoutX(7);
        text.setLayoutY(34);
        // Create textFields
        TextField firstName = new TextField();
        setTextField(firstName, "First name", 33, 146);
        TextField lastName = new TextField();
        setTextField(lastName, "Last name", 194, 146);
        TextField nationalId = new TextField();
        setTextField(nationalId, "National ID", 352, 111);
        TextField phoneNumber = new TextField();
        setTextField(phoneNumber, "Phone number", 476, 146);
        TextField age = new TextField();
        setTextField(age, "Age", 632, 64);
        // Create button
        Button check = new Button("Check");
        check.setPrefHeight(30);
        check.setLayoutX(710);
        check.setLayoutY(15);
        setButton(check);
        // set button action
        check.setOnMouseClicked(e -> {
            if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || nationalId.getText().isEmpty() || phoneNumber.getText().isEmpty() || age.getText().isEmpty()) {
                alertText.setText("You have to complete all fields!");
                return;
            }
            firstName.setDisable(true);
            lastName.setDisable(true);
            nationalId.setDisable(true);
            phoneNumber.setDisable(true);
            age.setDisable(true);
            check.setDisable(true);
            check.setText("Checked!");
            costumers.add(new Costumer(firstName.getText(), lastName.getText(), phoneNumber.getText(), nationalId.getText(), Integer.parseInt(age.getText()), roomChoiceBox.getValue()));
        });
        cell.getChildren().addAll(text, firstName, lastName, nationalId, phoneNumber, age, check);
        costumerVbox.getChildren().add(cell);
    }

    private void setButton(Button button) {
        button.setStyle("-fx-background-color: #ac7339; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-text-fill: White; -fx-font-weight: bold");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #996633; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ac7339; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-text-fill: White; -fx-font-weight: bold"));
    }

    private void setTextField(TextField textField, String promptText, double x, double width) {
        textField.setLayoutX(x);
        textField.setLayoutY(15);
        textField.setPrefWidth(width);
        textField.setPrefHeight(30);
        textField.setPromptText(promptText);
    }

    private void setChoiceBoxAction() {
        roomChoiceBox.setOnAction(e -> {
            costumers = new ArrayList<>();
            costumerVbox.getChildren().clear();
            roomNumber = roomChoiceBox.getValue();
            roomCapacity = Room.getCapacity(roomNumber);
            for (int  i = 0; i < roomCapacity; i++) {
                createCostumerCell(i + 1);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createRoomList(DBConnection.executeQuery(SQLQueries.select("room_number", "room", "check_in_date IS NULL")));
        setChoiceBoxAction();
        setButton(addButton);
        alertText.setText("");
    }
}

