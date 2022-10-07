package com.controllers.restaurant;

import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private ScrollPane ordersScrollPane;

    @FXML
    private VBox ordersVbox;

    private void addOrderCell(int order_id, int room_number, int total_price, String time) {
        // Create cell
        AnchorPane cell = new AnchorPane();
        cell.setPrefWidth(719);
        cell.setPrefHeight(35);
        cell.setStyle("-fx-background-color: #734d26");
        // Add cell texts
        Text orderId = new Text(Integer.toString(order_id));
        setText(orderId, 29);
        Text roomNumber = new Text(Integer.toString(room_number));
        setText(roomNumber, 154);
        Text totalPrice = new Text(Integer.toString(total_price));
        setText(totalPrice, 332);
        Text timeStamp = new Text(time);
        setText(timeStamp, 450);
        Button delivered = new Button("Deliver");
        delivered.setLayoutX(608);
        delivered.setLayoutY(6);
        delivered.setPrefWidth(70);
        delivered.setPrefHeight(24);
        delivered.setStyle("-fx-background-color: #86592d; -fx-background-radius: 3 3 3 3; -fx-border-radius: 3 3 3 3; -fx-text-fill: #f6f3f3");
        // Add button functionalities
        delivered.setOnMouseEntered(e -> delivered.setStyle("-fx-background-color: #996633; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5; -fx-text-fill: #f6f3f3"));
        delivered.setOnMouseExited(e -> delivered.setStyle("-fx-background-color: #86592d; -fx-border-radius: 3 3 3 3; -fx-background-radius: 3 3 3 3; -fx-text-fill: #f6f3f3"));
        delivered.setOnMouseClicked(e -> {
            DBConnection.execute(SQLQueries.update("order_factor", "is_delivered = 1", "order_id = " + order_id));
            ordersVbox.getChildren().remove(cell);
        });
        cell.getChildren().addAll(orderId, roomNumber, totalPrice, timeStamp, delivered);
        ordersVbox.getChildren().addAll(cell);
    }

    private void setText(Text text, double x) {
        text.setLayoutX(x);
        text.setLayoutY(24);
        text.setStyle("-fx-fill: #f6f3f3");
    }


    private void createOrdersList(ResultSet orders) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            while (orders.next()) {
                int id = orders.getInt("order_id");
                int roomNumber = orders.getInt("room_number");
                int totalPrice = orders.getInt("total_price");
                String time = formatter.format(orders.getTimestamp("time"));
                addOrderCell(id, roomNumber, totalPrice, time);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersScrollPane.setFitToWidth(true);
        createOrdersList(DBConnection.executeQuery(SQLQueries.select("*", "order_factor", "is_delivered = 0")));
    }
}

