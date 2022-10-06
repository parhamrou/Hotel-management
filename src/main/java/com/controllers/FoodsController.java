package com.controllers;

import com.company.Food;
import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FoodsController implements Initializable {

    @FXML
    private Button addFoodButton;

    @FXML
    private Text alertText;

    @FXML
    private TextField foodNameTextField;

    @FXML
    private TextField foodPriceTextField;

    @FXML
    private ScrollPane foodsScrollPane;

    @FXML
    private VBox foodsVbox;

    private void addFoodCell(int food_id, String food_name, int price) {
        // Create cell
        AnchorPane cell = new AnchorPane();
        cell.setPrefWidth(712);
        cell.setPrefHeight(40);
        cell.setStyle("-fx-background-color: #bf8040");
        // Add cell textFields
        TextField foodName = new TextField(food_name);
        setTextField(foodName, 185,  120);
        TextField foodId = new TextField(Integer.toString(food_id));
        setTextField(foodId, 27, 90);
        TextField foodPrice = new TextField(Integer.toString(price));
        setTextField(foodPrice, 356, 80);
        // Add buttons
        Button change = new Button("change");
        setButton(change, 475);
        Button remove = new Button("remove");
        setButton(remove, 600);
        Button submit = new Button("submit");
        setButton(submit, 475);
        cancelButton(submit, true);
        // setting buttons actions
        change.setOnMouseClicked(e -> {
            cancelButton(submit, false);
            cancelButton(change, true);
            foodName.setDisable(false);
            foodPrice.setDisable(false);
        });
        submit.setOnMouseClicked(e -> {
            cancelButton(submit, true);
            cancelButton(change, false);
            foodName.setDisable(true);
            foodPrice.setDisable(true);
            DBConnection.execute(SQLQueries.update("food", String.format("food_name = \"%s\", food_price = %s", foodName.getText(), foodPrice.getText()), "food_id = " + foodId.getText()));
        });
        remove.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are ou sure that you want to delete this item?", ButtonType.YES, ButtonType.NO);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("Dialog.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Food.remove(food_id);
                foodsVbox.getChildren().remove(cell);
            }
        });
        cell.getChildren().addAll(foodName, foodId, foodPrice, change, remove, submit);
        foodsVbox.getChildren().addAll(cell);
    }

    private void setTextField(TextField textField ,double x, double width) {
        textField.setLayoutX(x);
        textField.setLayoutY(8);
        textField.setPrefWidth(width);
        textField.setPrefHeight(24);
        textField.setDisable(true);
        textField.setStyle("-fx-background-color: #bf8040; -fx-text-fill: Black; -fx-opacity: 0.8");
    }

    private void setButton(Button button, double x) {
        button.setPrefWidth(80);
        button.setPrefHeight(24);
        button.setLayoutX(x);
        button.setLayoutY(8);
        button.setStyle("-fx-background-color: #d2a679; -fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-text-fill: Black");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #ac7339; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #d2a679; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5"));
    }

    private void cancelButton(Button button, boolean isCanceled) {
        button.setDisable(isCanceled);
        button.setVisible(!isCanceled);
    }

    private void createFoodList(ResultSet foods) {
        try {
            while (foods.next()) {
                String foodName = foods.getString("food_name");
                int foodId = foods.getInt("food_id");
                int price = foods.getInt("price");
                addFoodCell(foodId, foodName, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addFoodButtonPressed(ActionEvent event) {
        alertText.setText("");
        if (foodNameTextField.getText().isEmpty() || foodPriceTextField.getText().isEmpty()) {
            alertText.setText("Enter values!");
            return;
        }
        String foodName = foodNameTextField.getText();
        String foodPrice = foodPriceTextField.getText();
        DBConnection.execute(SQLQueries.insert("food", "food_name, price", String.format("\"%s\", %s", foodName, foodPrice)));
        int id = SQLQueries.getMaxId("food_id", "food");
        addFoodCell(id, foodName, Integer.parseInt(foodPrice));
        foodNameTextField.clear();
        foodPriceTextField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createFoodList(DBConnection.executeQuery(SQLQueries.select("*", "food")));
        foodsScrollPane.setFitToWidth(true);
        foodNameTextField.setStyle("-fx-prompt-text-fill: Black");
        foodPriceTextField.setStyle("-fx-prompt-text-fill: Black");
        addFoodButton.setOnMouseEntered(e -> addFoodButton.setStyle("-fx-background-color: #996633; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        addFoodButton.setOnMouseExited(e -> addFoodButton.setStyle("-fx-background-color: #ac7339; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10"));
        alertText.setText("");
    }
}

