package com.controllers;

import com.company.OrderFactor;
import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private VBox foodVbox;
    @FXML
    private Text ID;
    @FXML
    private Text IDLabel;
    @FXML
    private Text alertText;
    @FXML
    private Text firstName;
    @FXML
    private Text firstNameLabel;
    @FXML
    private Text lastName;
    @FXML
    private Text lastNameLabel;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button addButton;
    @FXML
    private Text addButtonText;
    @FXML
    private Text totalPriceText;
    @FXML
    private ScrollPane foodScrollPane;
    private Map<Integer, Integer> orderFoods;

    @FXML
    void searchButtonPressed(ActionEvent event) {
        orderFoods.clear();
        ResultSet result = DBConnection.executeQuery(SQLQueries.select("first_name, last_name, id", "costumer", "room_number = " + Integer.parseInt(searchTextField.getText())));
        try {
            if (!result.next()) {
                alertText.setText("There is no costumer with this room number!");
                return;
            }
            setTextsVisiblity(true);
            firstName.setText(result.getString("first_name"));
            lastName.setText(result.getString("last_name"));
            ID.setText(Integer.toString(result.getInt("id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addButtonPressed(ActionEvent event) {
        if (orderFoods.isEmpty()) {
            addButtonText.setText("You haven't entered any items!");
            return;
        }
        OrderFactor.addFactor(Integer.parseInt(searchTextField.getText()), orderFoods);
        addButtonText.setText("The order is added!");
    }

    private void addFoodCell(String food_name, int food_id, int price) {
        // Create cell
        AnchorPane cell = new AnchorPane();
        cell.setPrefWidth(490);
        cell.setPrefHeight(35);
        cell.setStyle("-fx-background-color: #bf8040");
        // Add cell texts
        Text foodName = new Text(food_name);
        setText(foodName, 14, 23);
        Text foodId = new Text(Integer.toString(food_id));
        setText(foodId, 123, 23);
        Text foodPrice = new Text(Integer.toString(price));
        setText(foodPrice, 208, 23);
        Text number = new Text(Integer.toString(0));
        setText(number, 282, 23);
        Button add = new Button("+");
        setButton(add, 421, 6, 21, 21);
        Button remove = new Button("-");
        setButton(remove, 376, 6, 21, 21);
        // Add button functionalities
        add.setOnMouseClicked(e -> {
            number.setText(Integer.toString(Integer.parseInt(number.getText()) + 1));
            totalPriceText.setText(Integer.toString(Integer.parseInt(totalPriceText.getText()) + price));
            orderFoods.putIfAbsent(food_id, 0);
            orderFoods.put(food_id, orderFoods.get(food_id) + 1);
        });
        remove.setOnMouseClicked(e -> {
            if (!number.getText().equals("0")) {
                number.setText(Integer.toString(Integer.parseInt(number.getText()) - 1));
                totalPriceText.setText(Integer.toString(Integer.parseInt(totalPriceText.getText()) - price));
            }
            if (number.getText().equals("0") && orderFoods.containsKey(food_id)) {
                orderFoods.remove(food_id);
            }
        });
        cell.getChildren().addAll(foodName, foodId, foodPrice, number, add, remove);
        foodVbox.getChildren().add(cell);
    }

    private void setText(Text text, double x, double y) {
        text.setLayoutX(x);
        text.setLayoutY(y);
    }

    private void setButton(Button button, double x, double y, double width, double height) {
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle("-fx-background-color: #ac7339; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-border-color: Black");
    }

    private void createFoodList(ResultSet foods) {
        try {
            while (foods.next()) {
                String foodName = foods.getString("food_name");
                int foodId = foods.getInt("food_id");
                int price = foods.getInt("price");
                addFoodCell(foodName, foodId, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTextsVisiblity(boolean visible) {
        Text[] texts = {firstName, firstNameLabel, lastName, lastNameLabel, ID, IDLabel};
        for (Text text : texts) {
            text.setVisible(visible);
        }
    }
    private void setButtonSetting(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #996633; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ac7339; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //createFoodList(DBConnection.executeQuery(SQLQueries.select("*", "food")));
        setTextsVisiblity(false);
        orderFoods = new HashMap<>();
        foodScrollPane.setFitToWidth(true);
        alertText.setText("");
        addButtonText.setText("");
        searchTextField.setStyle("-fx-text-fill: #653434; -fx-background-color: #d2a679");
        Button[] buttons = {searchButton, addButton};
        for (Button button : buttons) {
            setButtonSetting(button);
        }
    }
}

