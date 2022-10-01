package com.controllers;

import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Text totalPriceText;

    private Map<Integer, Integer> orderFoods;

    @FXML
    void searchButtonPressed(ActionEvent event) {
        orderFoods = new HashMap<>();
        addFoodCell("chicken", 1, 1000);
    }

    private void addFoodCell(String food_name, int food_id, int price) {
        // Create cell
        AnchorPane cell = new AnchorPane();
        cell.setPrefWidth(490);
        cell.setPrefHeight(35);
        cell.setStyle("-fx-background-color: #bf8040");
        // Add cell texts
        Text foodName = new Text(food_name);
        foodName.setLayoutX(14);
        foodName.setLayoutY(23);
        Text foodId = new Text(Integer.toString(food_id));
        foodId.setLayoutX(123);
        foodId.setLayoutY(23);
        Text foodPrice = new Text(Integer.toString(price));
        foodPrice.setLayoutX(208);
        foodPrice.setLayoutY(23);
        Text number = new Text(Integer.toString(0));
        number.setLayoutX(282);
        number.setLayoutY(23);
        Button add = new Button("+");
        add.setPrefWidth(21);
        add.setPrefHeight(21);
        add.setLayoutX(421);
        add.setLayoutY(6);
        add.setStyle("-fx-background-color: #ac7339; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-border-color: Black");
        Button remove = new Button("-");
        remove.setPrefWidth(21);
        remove.setPrefHeight(21);
        remove.setLayoutX(376);
        remove.setLayoutY(6);
        remove.setStyle("-fx-background-color: #ac7339; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-border-color: Black");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //createFoodList(DBConnection.executeQuery(SQLQueries.select("*", "food")));
        alertText.setText("");
        searchTextField.setStyle("-fx-text-fill: #653434");
    }
}

