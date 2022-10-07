package com.controllers.mainPage;

import com.database.DBConnection;
import com.database.SQLQueries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class LogsController implements Initializable {

    @FXML
    private VBox logsVbox;

    @FXML
    private ScrollPane scrollPane;

    private void addLogCell(int log_id, String log_type, String log_des, Time log_time) {
        // Create cell
        AnchorPane cell = new AnchorPane();
        cell.setPrefWidth(712);
        cell.setPrefHeight(40);
        cell.setStyle("-fx-background-color: #734d26");
        // Add texts
        Text id = new Text(Integer.toString(log_id));
        setText(id, 22);
        Text logType = new Text(log_type);
        setText(logType, 113);
        Text logDes = new Text(log_des);
        setText(logDes, 242);
        Text logTime = new Text(log_time.toString());
        setText(logTime, 614);
        cell.getChildren().addAll(id, logType, logDes, logTime);
        logsVbox.getChildren().add(cell);
    }

    private void setText(Text text, double x) {
        text.setLayoutX(x);
        text.setLayoutY(25);
        text.setStyle("-fx-fill: #f6f3f3");
    }

    private void createLogList(ResultSet logs) {
        try {
            while (logs.next()) {
                int id = logs.getInt("id");
                String logType = logs.getString("type");
                String logDescription = logs.getString("description");
                Time logTime = logs.getTime("time");
                addLogCell(id, logType, logDescription, logTime);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createLogList(DBConnection.executeQuery(SQLQueries.select("*", "log ORDER BY id DESC")));
        scrollPane.setFitToWidth(true);
    }
}